package br.ufal.aracomp.loja.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.EstoqueInsuficienteException;
import br.ufal.aracomp.loja.exception.UsuarioInativoException;
import br.ufal.aracomp.loja.models.Cliente;
import br.ufal.aracomp.loja.models.Pedido;
import br.ufal.aracomp.loja.models.PessoaFisica;
import br.ufal.aracomp.loja.models.Produto;
import br.ufal.aracomp.loja.models.Periferico;
import br.ufal.aracomp.loja.models.StatusPedido;
import br.ufal.aracomp.loja.repository.PedidoRepository;
import br.ufal.aracomp.loja.repository.ProdutoRepository;
import br.ufal.aracomp.loja.repository.ServicoTecnicoRepository;
import br.ufal.aracomp.loja.repository.UsuarioRepository;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class PedidoServiceTest {
    @TempDir
    Path diretorioTemporario;

    private PedidoRepository pedidoRepository;
    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuarioRepository;
    private PedidoService service;
    private Produto produto;
    private Cliente cliente;

    @BeforeEach
    public void configurar() throws Exception {
        pedidoRepository = new PedidoRepository(diretorioTemporario.resolve("pedidos.dat").toString());
        produtoRepository = new ProdutoRepository(diretorioTemporario.resolve("produtos.dat").toString());
        usuarioRepository = new UsuarioRepository(diretorioTemporario.resolve("usuarios.dat").toString());
        ServicoTecnicoRepository servicoRepository = new ServicoTecnicoRepository(
                diretorioTemporario.resolve("servicos.dat").toString());
        ServicoTecnicoService servicoService = new ServicoTecnicoService(servicoRepository);
        service = new PedidoService(pedidoRepository, usuarioRepository, produtoRepository, servicoService);

        cliente = new PessoaFisica("c1", "Ana", "ana@email.com", "Rua A", "documento");
        usuarioRepository.adicionarUsuario(cliente);
        produto = new Periferico("p1", "Mouse", 50.0, 5, "USB");
        produtoRepository.adicionarProduto(produto);
    }

    @Test
    public void finalizaPedidoEAtualizaEstoque() throws Exception {
        Pedido pedido = criarPedido("pedido1");
        service.cadastrarPedido(pedido);
        service.adicionarProdutoPedido("pedido1", "p1", 2);

        service.finalizarPedido("pedido1");

        assertEquals(StatusPedido.FINALIZADO, pedido.getStatus());
        assertEquals(3, produto.getQtdEstoque());
        assertEquals(100.0, pedido.getValorFinal());
    }

    @Test
    public void naoPermiteAdicionarMaisQueOEstoque() throws Exception {
        service.cadastrarPedido(criarPedido("pedido1"));

        assertThrows(EstoqueInsuficienteException.class,
                () -> service.adicionarProdutoPedido("pedido1", "p1", 6));
        assertEquals(0, service.buscarPorId("pedido1").listarItens().size());
        assertEquals(5, produto.getQtdEstoque());
    }

    @Test
    public void naoFinalizaPedidoSemItens() throws Exception {
        service.cadastrarPedido(criarPedido("pedido1"));

        assertThrows(DadoEntradaException.class, () -> service.finalizarPedido("pedido1"));
        assertEquals(StatusPedido.ABERTO, service.buscarPorId("pedido1").getStatus());
        assertEquals(5, produto.getQtdEstoque());
    }

    @Test
    public void naoBaixaEstoqueQuandoFinalizacaoFalhaNaValidacao() throws Exception {
        service.cadastrarPedido(criarPedido("pedido1"));
        service.adicionarProdutoPedido("pedido1", "p1", 2);
        produto.removerEstoque(5);
        produtoRepository.salvarAlteracoes();

        assertThrows(EstoqueInsuficienteException.class,
                () -> service.finalizarPedido("pedido1"));
        assertEquals(StatusPedido.ABERTO, service.buscarPorId("pedido1").getStatus());
        assertEquals(0, produto.getQtdEstoque());
    }

    @Test
    public void naoCadastraPedidoDeClienteInativo() throws Exception {
        cliente.desativar();

        assertThrows(UsuarioInativoException.class,
                () -> service.cadastrarPedido(criarPedido("pedido1")));
    }

    private Pedido criarPedido(String id) {
        return new Pedido(id, cliente, "2026-11-26");
    }
}

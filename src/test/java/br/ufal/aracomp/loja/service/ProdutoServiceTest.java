package br.ufal.aracomp.loja.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.ProdutoIndisponivelException;
import br.ufal.aracomp.loja.exception.RegistroCadastradoException;
import br.ufal.aracomp.loja.exception.RegistroNaoEncontradoException;
import br.ufal.aracomp.loja.models.Produto;
import br.ufal.aracomp.loja.models.Periferico;
import br.ufal.aracomp.loja.models.Software;
import br.ufal.aracomp.loja.repository.ProdutoRepository;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ProdutoServiceTest {
    @TempDir
    Path diretorioTemporario;

    private ProdutoRepository repository;
    private ProdutoService service;

    @BeforeEach
    public void configurar() throws Exception {
        repository = new ProdutoRepository(diretorioTemporario.resolve("produtos.dat").toString());
        service = new ProdutoService(repository);
    }

    @Test
    public void cadastraProdutoERejeitaIdDuplicado() throws Exception {
        service.cadastrarProduto(new Periferico("p1", "Mouse", 50.0, 5, "USB"));

        assertThrows(RegistroCadastradoException.class,
                () -> service.cadastrarProduto(new Periferico("p1", "Teclado", 80.0, 3, "USB")));
        assertEquals(1, service.listarProdutos().size());
    }

    @Test
    public void rejeitaProdutoComPrecoOuEstoqueInvalido() {
        assertThrows(DadoEntradaException.class,
                () -> service.cadastrarProduto(new Periferico("p1", "Mouse", 0.0, 5, "USB")));
        assertThrows(DadoEntradaException.class,
                () -> service.cadastrarProduto(new Periferico("p2", "Mouse", 50.0, 0, "USB")));
    }

    @Test
    public void adicionaEstoqueERejeitaQuantidadeInvalida() throws Exception {
        service.cadastrarProduto(new Periferico("p1", "Mouse", 50.0, 5, "USB"));

        service.adicionarEstoque("p1", 3);

        assertEquals(8, service.buscarPorId("p1").getQtdEstoque());
        assertThrows(DadoEntradaException.class, () -> service.adicionarEstoque("p1", 0));
        assertThrows(RegistroNaoEncontradoException.class, () -> service.adicionarEstoque("ausente", 1));
    }

    @Test
    public void alteraPrecoERejeitaValoresInvalidos() throws Exception {
        service.cadastrarProduto(new Periferico("p1", "Mouse", 50.0, 5, "USB"));

        service.alterarPreco("p1", 65.0);

        assertEquals(65.0, service.buscarPorId("p1").getPreco());
        ProdutoRepository repositoryReaberto =
                new ProdutoRepository(diretorioTemporario.resolve("produtos.dat").toString());
        assertEquals(65.0, repositoryReaberto.listarProdutos().get(0).getPreco());
        assertThrows(DadoEntradaException.class, () -> service.alterarPreco("p1", 0.0));
        assertThrows(RegistroNaoEncontradoException.class, () -> service.alterarPreco("ausente", 10.0));
    }

    @Test
    public void exibeChaveDeAtivacaoDoSoftware() throws Exception {
        Software software = new Software("s1", "Editor", 20.0, 1, "CHAVE-123", "Windows");

        assertTrue(software.toString().contains("Chave: CHAVE-123"));
    }

    @Test
    public void desativaProdutoEImpedeDesativacaoRepetida() throws Exception {
        service.cadastrarProduto(new Periferico("p1", "Mouse", 50.0, 5, "USB"));

        service.desativarProduto("p1");

        assertFalse(service.buscarPorId("p1").isDisponivel());
        assertThrows(ProdutoIndisponivelException.class, () -> service.desativarProduto("p1"));
    }
}

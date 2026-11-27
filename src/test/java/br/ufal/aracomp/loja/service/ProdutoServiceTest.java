package br.ufal.aracomp.loja.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.ProdutoIndisponivelException;
import br.ufal.aracomp.loja.exception.RegistroCadastradoException;
import br.ufal.aracomp.loja.exception.RegistroNaoEncontradoException;
import br.ufal.aracomp.loja.models.Produto;
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
    public void configurar() {
        repository = new ProdutoRepository(diretorioTemporario.resolve("produtos.dat").toString());
        service = new ProdutoService(repository);
    }

    @Test
    public void cadastraProdutoERejeitaIdDuplicado() throws Exception {
        service.cadastrarProduto(new Produto("p1", "Mouse", 50.0, 5));

        assertThrows(RegistroCadastradoException.class,
                () -> service.cadastrarProduto(new Produto("p1", "Teclado", 80.0, 3)));
        assertEquals(1, service.listarProdutos().size());
    }

    @Test
    public void rejeitaProdutoComPrecoOuEstoqueInvalido() {
        assertThrows(DadoEntradaException.class,
                () -> service.cadastrarProduto(new Produto("p1", "Mouse", 0.0, 5)));
        assertThrows(DadoEntradaException.class,
                () -> service.cadastrarProduto(new Produto("p2", "Mouse", 50.0, 0)));
    }

    @Test
    public void adicionaEstoqueERejeitaQuantidadeInvalida() throws Exception {
        service.cadastrarProduto(new Produto("p1", "Mouse", 50.0, 5));

        service.adicionarEstoque("p1", 3);

        assertEquals(8, service.buscarPorId("p1").getQtdEstoque());
        assertThrows(DadoEntradaException.class, () -> service.adicionarEstoque("p1", 0));
        assertThrows(RegistroNaoEncontradoException.class, () -> service.adicionarEstoque("ausente", 1));
    }

    @Test
    public void desativaProdutoEImpedeDesativacaoRepetida() throws Exception {
        service.cadastrarProduto(new Produto("p1", "Mouse", 50.0, 5));

        service.desativarProduto("p1");

        assertFalse(service.buscarPorId("p1").isDisponivel());
        assertThrows(ProdutoIndisponivelException.class, () -> service.desativarProduto("p1"));
    }
}

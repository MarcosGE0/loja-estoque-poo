package br.ufal.aracomp.loja.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.EstoqueInsuficienteException;
import org.junit.jupiter.api.Test;

public class ProdutoTest {

    @Test
    public void adicionarEstoqueAtualizaQuantidade() throws Exception {
        Produto produto = new Periferico("p1", "Mouse", 50.0, 3, "USB");

        produto.adicionarEstoque(2);

        assertEquals(5, produto.getQtdEstoque());
    }

    @Test
    public void construtorRejeitaDadosInvalidos() {
        assertThrows(DadoEntradaException.class, () -> new Periferico("p1", " ", 50.0, 3, "USB"));
        assertThrows(DadoEntradaException.class, () -> new Periferico("p1", "Mouse", -1.0, 3, "USB"));
        assertThrows(DadoEntradaException.class, () -> new Periferico("p1", "Mouse", 50.0, 0, "USB"));
    }

    @Test
    public void registrarVendaValidaQuantidadeEEstoque() throws Exception {
        Produto produto = new Periferico("p1", "Mouse", 50.0, 3, "USB");

        assertThrows(EstoqueInsuficienteException.class, () -> produto.registrarVenda(4));
        assertEquals(3, produto.getQtdEstoque());

        assertThrows(DadoEntradaException.class, () -> produto.registrarVenda(0));
        assertEquals(3, produto.getQtdEstoque());

        produto.registrarVenda(2);
        assertEquals(1, produto.getQtdEstoque());
    }
}

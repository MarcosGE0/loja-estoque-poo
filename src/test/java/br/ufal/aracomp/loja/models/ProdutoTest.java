package br.ufal.aracomp.loja.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProdutoTest {

    @Test
    public void adicionarEstoqueAtualizaQuantidade() throws Exception {
        Produto produto = new Produto("p1", "Mouse", 50.0, 3);

        produto.adicionarEstoque(2);

        assertEquals(5, produto.getQtdEstoque());
    }
}

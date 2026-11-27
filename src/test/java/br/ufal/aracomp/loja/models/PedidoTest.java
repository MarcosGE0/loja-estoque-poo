package br.ufal.aracomp.loja.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import br.ufal.aracomp.loja.exception.DadoEntradaException;

public class PedidoTest {

    @Test
    public void aplicaDescontoPromocionalAoPedido() throws Exception {
        Cliente cliente = new PessoaFisica("c1", "Ana", "ana@email.com", "Rua A", "529.982.247-25");
        Pedido pedido = new Pedido("p1", cliente, "2026-11-26");
        pedido.adicionarItem(new ItemPedido(2, new Produto("x1", "Mouse", 50.0, 10)));
        pedido.aplicarDesconto(new DescontoPromocional(10.0));

        assertEquals(100.0, pedido.getValorBruto());
        assertEquals(10.0, pedido.getValorDesconto());
        assertEquals(90.0, pedido.getValorFinal());
    }

    @Test
    public void aplicaDescontoPorQuantidadeQuandoMinimoEAtendido() throws Exception {
        Cliente cliente = new PessoaFisica("c1", "Ana", "ana@email.com", "Rua A", "529.982.247-25");
        Pedido pedido = new Pedido("p1", cliente, "2026-11-26");
        pedido.adicionarItem(new ItemPedido(3, new Produto("x1", "Mouse", 20.0, 10)));
        pedido.aplicarDesconto(new DescontoPorQuantidade(3, 20.0));

        assertEquals(12.0, pedido.getValorDesconto());
        assertEquals(48.0, pedido.getValorFinal());
    }

    @Test
    public void pedidoNovoNaoPossuiDesconto() throws Exception {
        Cliente cliente = new PessoaFisica("c1", "Ana", "ana@email.com", "Rua A", "529.982.247-25");
        Pedido pedido = new Pedido("p1", cliente, "2026-11-26");

        assertEquals(0.0, pedido.getValorDesconto());
        assertEquals(pedido.getValorBruto(), pedido.getValorFinal());
    }

    @Test
    public void rejeitaPercentualPromocionalInvalido() {
        assertThrows(DadoEntradaException.class, () -> new DescontoPromocional(-1.0));
        assertThrows(DadoEntradaException.class, () -> new DescontoPromocional(101.0));
    }

    @Test
    public void rejeitaDescontoPorQuantidadeInvalido() {
        assertThrows(DadoEntradaException.class, () -> new DescontoPorQuantidade(0, 10.0));
        assertThrows(DadoEntradaException.class, () -> new DescontoPorQuantidade(3, 101.0));
    }

    @Test
    public void valorFinalAplicadoPermaneceAposFinalizar() throws Exception {
        Cliente cliente = new PessoaFisica("c1", "Ana", "ana@email.com", "Rua A", "529.982.247-25");
        Pedido pedido = new Pedido("p1", cliente, "2026-11-26");
        pedido.adicionarItem(new ItemPedido(2, new Produto("x1", "Mouse", 50.0, 10)));
        pedido.aplicarDesconto(new DescontoPromocional(10.0));
        pedido.finalizarPedido();

        assertEquals(10.0, pedido.getValorDesconto());
        assertEquals(90.0, pedido.getValorFinal());
    }

    @Test
    public void recalculaDescontoQuandoQuantidadeDoPedidoMuda() throws Exception {
        Cliente cliente = new PessoaFisica("c1", "Ana", "ana@email.com", "Rua A", "529.982.247-25");
        Pedido pedido = new Pedido("p1", cliente, "2026-11-26");
        ItemPedido item = new ItemPedido(2, new Produto("x1", "Mouse", 20.0, 10));
        pedido.adicionarItem(item);
        pedido.aplicarDesconto(new DescontoPorQuantidade(3, 20.0));

        assertEquals(0.0, pedido.getValorDesconto());

        item.adicionarQuantidade(1);

        assertEquals(12.0, pedido.getValorDesconto());
        assertEquals(48.0, pedido.getValorFinal());
    }

    @Test
    public void removeDescontoComSemDesconto() throws Exception {
        Cliente cliente = new PessoaFisica("c1", "Ana", "ana@email.com", "Rua A", "529.982.247-25");
        Pedido pedido = new Pedido("p1", cliente, "2026-11-26");
        pedido.adicionarItem(new ItemPedido(2, new Produto("x1", "Mouse", 50.0, 10)));
        pedido.aplicarDesconto(new DescontoPromocional(10.0));
        pedido.aplicarDesconto(new SemDesconto());

        assertEquals("Sem desconto", pedido.getNomeDesconto());
        assertEquals(0.0, pedido.getValorDesconto());
        assertEquals(100.0, pedido.getValorFinal());
    }

    @Test
    public void impedeAlteracoesAposFinalizar() throws Exception {
        Cliente cliente = new PessoaFisica("c1", "Ana", "ana@email.com", "Rua A", "529.982.247-25");
        Pedido pedido = new Pedido("p1", cliente, "2026-11-26");
        ItemPedido item = new ItemPedido(2, new Produto("x1", "Mouse", 50.0, 10));
        pedido.adicionarItem(item);
        pedido.finalizarPedido();

        assertThrows(DadoEntradaException.class,
                () -> pedido.adicionarItem(new ItemPedido(1, new Produto("x2", "Teclado", 30.0, 10))));
        assertThrows(DadoEntradaException.class, () -> pedido.removerItem(item));
        assertThrows(DadoEntradaException.class,
                () -> pedido.aplicarDesconto(new DescontoPromocional(20.0)));
    }
}

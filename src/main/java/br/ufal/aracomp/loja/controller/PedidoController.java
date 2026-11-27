package br.ufal.aracomp.loja.controller;

import br.ufal.aracomp.loja.exception.*;
import br.ufal.aracomp.loja.exception.*;
import br.ufal.aracomp.loja.models.Pedido;
import br.ufal.aracomp.loja.models.DescontoStrategy;
import br.ufal.aracomp.loja.service.PedidoService;
import java.util.List;

public class PedidoController {
    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public void cadastrarPedido(Pedido novoPedido)
            throws DadoEntradaException, RegistroCadastradoException, RegistroNaoEncontradoException, UsuarioInativoException {
        pedidoService.cadastrarPedido(novoPedido);
    }

    public Pedido buscarPorId(String idPedido)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        return pedidoService.buscarPorId(idPedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    public void removerPedido(String idPedido)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        pedidoService.removerPedido(idPedido);
    }

    public void adicionarProdutoPedido(String idPedido, String idProduto, int quantidade)
            throws DadoEntradaException, RegistroNaoEncontradoException, ProdutoIndisponivelException, EstoqueInsuficienteException {
        pedidoService.adicionarProdutoPedido(idPedido, idProduto, quantidade);
    }

    public void adicionarServicoPedido(String idPedido, String idServico, int quantidade)
            throws DadoEntradaException, RegistroNaoEncontradoException, ServicoIndisponivelException {
        pedidoService.adicionarServicoPedido(idPedido, idServico, quantidade);
    }

    public void removerItemPedido(String idPedido, String idItem, int quantidade)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        pedidoService.removerItemPedido(idPedido, idItem, quantidade);
    }

    public void aplicarDesconto(String idPedido, DescontoStrategy desconto)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        pedidoService.aplicarDesconto(idPedido, desconto);
    }

    public void finalizarPedido(String idPedido)
            throws DadoEntradaException, RegistroNaoEncontradoException, ProdutoIndisponivelException, EstoqueInsuficienteException {
        pedidoService.finalizarPedido(idPedido);
    }

    public void cancelarPedido(String idPedido)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        pedidoService.cancelarPedido(idPedido);
    }
}

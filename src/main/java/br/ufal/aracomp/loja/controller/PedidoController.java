package br.ufal.aracomp.loja.controller;

import br.ufal.aracomp.loja.exception.*;
import br.ufal.aracomp.loja.exception.*;
import br.ufal.aracomp.loja.models.Pedido;
import br.ufal.aracomp.loja.models.DescontoStrategy;
import br.ufal.aracomp.loja.service.PedidoService;
import java.util.List;

public class PedidoController {
    private PedidoService pedidoService;

    // Recebe o service responsável pelas regras de pedidos.
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Cadastra um novo pedido.
    public void cadastrarPedido(Pedido novoPedido)
            throws DadoEntradaException, RegistroCadastradoException, RegistroNaoEncontradoException, UsuarioInativoException {
        pedidoService.cadastrarPedido(novoPedido);
    }

    // Busca um pedido pelo ID.
    public Pedido buscarPorId(String idPedido)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        return pedidoService.buscarPorId(idPedido);
    }

    // Retorna todos os pedidos cadastrados.
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    // Remove um pedido pelo ID.
    public void removerPedido(String idPedido)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        pedidoService.removerPedido(idPedido);
    }

    // Adiciona um produto ao pedido.
    public void adicionarProdutoPedido(String idPedido, String idProduto, int quantidade)
            throws DadoEntradaException, RegistroNaoEncontradoException, ProdutoIndisponivelException, EstoqueInsuficienteException {
        pedidoService.adicionarProdutoPedido(idPedido, idProduto, quantidade);
    }

    // Adiciona um serviço técnico ao pedido.
    public void adicionarServicoPedido(String idPedido, String idServico, int quantidade)
            throws DadoEntradaException, RegistroNaoEncontradoException, ServicoIndisponivelException {
        pedidoService.adicionarServicoPedido(idPedido, idServico, quantidade);
    }

    // Remove uma quantidade de um item do pedido.
    public void removerItemPedido(String idPedido, String idItem, int quantidade)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        pedidoService.removerItemPedido(idPedido, idItem, quantidade);
    }

    public void aplicarDesconto(String idPedido, DescontoStrategy desconto)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        pedidoService.aplicarDesconto(idPedido, desconto);
    }

    // Finaliza um pedido aberto.
    public void finalizarPedido(String idPedido)
            throws DadoEntradaException, RegistroNaoEncontradoException, ProdutoIndisponivelException, EstoqueInsuficienteException {
        pedidoService.finalizarPedido(idPedido);
    }

    // Cancela um pedido aberto.
    public void cancelarPedido(String idPedido)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        pedidoService.cancelarPedido(idPedido);
    }
}

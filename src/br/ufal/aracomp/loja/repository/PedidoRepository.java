package br.ufal.aracomp.loja.repository;

import br.ufal.aracomp.loja.models.*;
import java.util.*;

public class PedidoRepository {
    private  List<Pedido> pedidos;

    public PedidoRepository() {
        this.pedidos = new ArrayList<>();
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public Pedido buscarPedido(String idPedido) {
        for (Pedido pedido : pedidos) {
            if (pedido.getIdPedido().equals(idPedido)) {
                return pedido;
            }
        }

        return null;
    }

    public void removerPedido(String idPedido) {
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getIdPedido().equals(idPedido)) {
                pedidos.remove(i);
                return;
            }
        }
    }

    public List<Pedido> listarPedidos() {
        return new ArrayList<>(pedidos);
    }
}

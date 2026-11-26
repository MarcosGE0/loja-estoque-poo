package br.ufal.aracomp.loja.repository;

import br.ufal.aracomp.loja.models.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository extends ArquivoRepository<Pedido> {
    private final List<Pedido> pedidos;

    public PedidoRepository() {
        super("dados/pedidos.dat");
        this.pedidos = carregar();
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
        salvar(pedidos);
    }

    public void salvarAlteracoes() {
        salvar(pedidos);
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
                salvar(pedidos);
                return;
            }
        }
    }

    public List<Pedido> listarPedidos() {
        return new ArrayList<>(pedidos);
    }
}

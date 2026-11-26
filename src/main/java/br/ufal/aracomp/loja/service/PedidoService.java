package br.ufal.aracomp.loja.service;

import br.ufal.aracomp.loja.exception.*;
import br.ufal.aracomp.loja.models.*;
import br.ufal.aracomp.loja.repository.PedidoRepository;
import br.ufal.aracomp.loja.repository.ProdutoRepository;
import br.ufal.aracomp.loja.repository.UsuarioRepository;
import br.ufal.aracomp.loja.exception.*;
import br.ufal.aracomp.loja.models.*;
import br.ufal.aracomp.loja.repository.*;
import java.util.List;


public class PedidoService {
    private PedidoRepository pedidos;
    private UsuarioRepository usuarios;
    private ProdutoRepository produtos;
    private ServicoTecnicoService servicosTecnicos;

    // Recebe os repositories e services necessários para aplicar as regras dos pedidos.
    public PedidoService(PedidoRepository pedidos, UsuarioRepository usuarios, ProdutoRepository produtos,  ServicoTecnicoService servicosTecnicos) {
        this.pedidos = pedidos;
        this.usuarios = usuarios;
        this.produtos = produtos;
        this.servicosTecnicos = servicosTecnicos;
    }

    public void cadastrarPedido(Pedido novoPedido) throws DadoEntradaException, RegistroCadastradoException, RegistroNaoEncontradoException, UsuarioInativoException {
        // Impede o cadastro de um pedido nulo.
        if (novoPedido == null) {
            throw new DadoEntradaException("Pedido não pode ser nulo.");
        }

        // Valida o id do pedido para não ser nulo e nem uma string vazia.
        if (novoPedido.getIdPedido() == null || novoPedido.getIdPedido().equals("")) {
            throw new DadoEntradaException("ID do pedido não pode ser vazio.");
        }

        // Impede pedidos com IDs já cadastrados.
        if (pedidos.buscarPedido(novoPedido.getIdPedido()) != null) {
            throw new RegistroCadastradoException("Já existe um pedido com o ID " + novoPedido.getIdPedido() + ".");
        }

        // Exige que o pedido possua um cliente, não pode ser nulo.
        Cliente cliente = novoPedido.getCliente();
        if (cliente == null) {
            throw new DadoEntradaException("Pedido deve possuir um cliente.");
        }

        // Procura o cliente cadastrado pelo ID.
        Usuario clienteCadastrado = null;
        for (Usuario usuario : usuarios.listarUsuarios()) {
            if (usuario.getId().equals(cliente.getId())) {
                clienteCadastrado = usuario;
                break;
            }
        }

        // Confirma que o usuário encontrado é um cliente cadastrado.
        if (!(clienteCadastrado instanceof Cliente)) {
            throw new RegistroNaoEncontradoException("Cliente com ID " + cliente.getId() + " não cadastrado.");
        }

        // Impede pedidos feitos por clientes inativos.
        if (!clienteCadastrado.isAtivo()) {
            throw new UsuarioInativoException("Cliente com ID " + cliente.getId() + " está inativo.");
        }

        // Garante que pedidos novos comecem em aberto.
        if (novoPedido.getStatus() != StatusPedido.ABERTO) {
            throw new DadoEntradaException("Novo pedido deve iniciar com status ABERTO.");
        }

        // Salva o pedido após todas as validações.
        pedidos.adicionarPedido(novoPedido);
    }

    public void removerPedido(String idPedido) throws DadoEntradaException, RegistroNaoEncontradoException {
        // Impede a remoção sem um ID válido.
        if (idPedido == null || idPedido.equals("")) {
            throw new DadoEntradaException("ID do pedido não pode ser vazio.");
        }

        // Impede remoção de pedido que não está cadastrado.
        if (pedidos.buscarPedido(idPedido) == null) {
            throw new RegistroNaoEncontradoException("Pedido com ID " + idPedido + " não encontrado.");
        }

        // Remove o pedido encontrado pelo repositório.
        pedidos.removerPedido(idPedido);
    }

    // Valida o pedido e adiciona um produto, informando a quantidade desejada.
    public void adicionarProdutoPedido(String idPedido, String idProduto, int quantidade)
            throws DadoEntradaException, RegistroNaoEncontradoException,
            ProdutoIndisponivelException, EstoqueInsuficienteException {
        Pedido pedido = buscarPedidoAberto(idPedido);

        if (quantidade <= 0) {
            throw new DadoEntradaException("Quantidade deve ser maior que zero (recebida: " + quantidade + ").");
        }

        Produto produto = null;
        for (Produto produtoCadastrado : produtos.listarProdutos()) {
            if (produtoCadastrado.getId().equals(idProduto)) {
                produto = produtoCadastrado;
                break;
            }
        }

        if (produto == null) {
            throw new RegistroNaoEncontradoException("Produto com ID " + idProduto + " não encontrado.");
        }

        if (!produto.isDisponivel()) {
            throw new ProdutoIndisponivelException("Produto com ID " + idProduto + " está indisponível.");
        }

        int quantidadeNoPedido = 0;
        for (ItemPedido item : pedido.listarItens()) {
            if (item.getItem() instanceof Produto
                    && ((Produto) item.getItem()).getId().equals(idProduto)) {
                quantidadeNoPedido += item.getQuantidade();
            }
        }

        if (produto.getQtdEstoque() < quantidadeNoPedido + quantidade) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para adicionar " + quantidade + " unidade(s) do produto " + idProduto + "; disponível: " + (produto.getQtdEstoque() - quantidadeNoPedido) + ".");
        }

        for (ItemPedido item : pedido.listarItens()) {
            if (item.getItem() instanceof Produto
                    && ((Produto) item.getItem()).getId().equals(idProduto)) {
                item.adicionarQuantidade(quantidade);
                pedidos.salvarAlteracoes();
                return;
            }
        }

        pedido.adicionarItem(new ItemPedido(quantidade, produto));
        pedidos.salvarAlteracoes();
    }

    // Valida o pedido e adiciona um serviço técnico ao pedido.
    public void adicionarServicoPedido(String idPedido, String idServico, int quantidade)
            throws DadoEntradaException, RegistroNaoEncontradoException,
            ServicoIndisponivelException {
        Pedido pedido = buscarPedidoAberto(idPedido);

        if (quantidade <= 0) {
            throw new DadoEntradaException("Quantidade deve ser maior que zero (recebida: " + quantidade + ").");
        }

        ServicoTecnico servico = null;
        for (ServicoTecnico servicoCadastrado : servicosTecnicos.getServicoTecnicoRepository().listarServicoTecnico()) {
            if (servicoCadastrado.getId().equals(idServico)) {
                servico = servicoCadastrado;
                break;
            }
        }

        if (servico == null) {
            throw new RegistroNaoEncontradoException("Serviço técnico com ID " + idServico + " não encontrado.");
        }

        if (!servico.isDisponivel()) {
            throw new ServicoIndisponivelException("Serviço técnico com ID " + idServico + " está indisponível.");
        }

        for (ItemPedido item : pedido.listarItens()) {
            if (item.getItem() instanceof ServicoTecnico
                    && ((ServicoTecnico) item.getItem()).getId().equals(idServico)) {
                item.adicionarQuantidade(quantidade);
                pedidos.salvarAlteracoes();
                return;
            }
        }

        pedido.adicionarItem(new ItemPedido(quantidade, servico));
        pedidos.salvarAlteracoes();
    }

    // Remove uma quantidade de um produto ou serviço pelo ID do item do pedido.
    public void removerItemPedido(String idPedido, String idItem, int quantidade)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        Pedido pedido = buscarPedidoAberto(idPedido);

        if (idItem == null || idItem.equals("")) {
            throw new DadoEntradaException("ID do item não pode ser vazio.");
        }

        if (quantidade <= 0) {
            throw new DadoEntradaException("Quantidade deve ser maior que zero (recebida: " + quantidade + ").");
        }

        for (int i = 0; i < pedido.listarItens().size(); i++) {
            ItemPedido item = pedido.listarItens().get(i);
            if (item.getItem() instanceof Produto
                    && ((Produto) item.getItem()).getId().equals(idItem)) {
                removerQuantidadeItem(pedido, item, quantidade);
                pedidos.salvarAlteracoes();
                return;
            }

            if (item.getItem() instanceof ServicoTecnico
                    && ((ServicoTecnico) item.getItem()).getId().equals(idItem)) {
                removerQuantidadeItem(pedido, item, quantidade);
                pedidos.salvarAlteracoes();
                return;
            }
        }

        throw new RegistroNaoEncontradoException("Item com ID " + idItem + " não encontrado no pedido " + idPedido + ".");
    }

    // Atualiza ou remove o item conforme a quantidade informada.
    private void removerQuantidadeItem(Pedido pedido, ItemPedido item, int quantidade)
            throws DadoEntradaException {
        if (quantidade > item.getQuantidade()) {
            throw new DadoEntradaException("Quantidade solicitada (" + quantidade + ") é maior que a quantidade no pedido (" + item.getQuantidade() + ").");
        }

        if (quantidade == item.getQuantidade()) {
            pedido.removerItem(item);
        } else {
            item.removerQuantidade(quantidade);
        }
    }

    public void aplicarDesconto(String idPedido, DescontoStrategy desconto)
            throws DadoEntradaException, RegistroNaoEncontradoException {
        Pedido pedido = buscarPedidoAberto(idPedido);
        pedido.aplicarDesconto(desconto);
        pedidos.salvarAlteracoes();
    }

    // Busca um pedido e exige que ele esteja aberto.
    private Pedido buscarPedidoAberto(String idPedido) throws DadoEntradaException, RegistroNaoEncontradoException {
        Pedido pedido = buscarPorId(idPedido);

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new DadoEntradaException("Pedido com ID " + idPedido + " não está aberto.");
        }

        return pedido;
    }

    // Busca um pedido pelo ID.
    public Pedido buscarPorId(String idPedido) throws DadoEntradaException, RegistroNaoEncontradoException {
        if (idPedido == null || idPedido.equals("")) {
            throw new DadoEntradaException("ID do pedido não pode ser vazio.");
        }

        Pedido pedido = pedidos.buscarPedido(idPedido);
        if (pedido == null) {
            throw new RegistroNaoEncontradoException("Pedido com ID " + idPedido + " não encontrado.");
        }

        return pedido;
    }

    // Retorna a lista de pedidos cadastrados.
    public List<Pedido> listarPedidos() {
        return pedidos.listarPedidos();
    }

    // Finaliza um pedido aberto que possua pelo menos um item.
    public void finalizarPedido(String idPedido)
            throws DadoEntradaException, RegistroNaoEncontradoException,
                   ProdutoIndisponivelException, EstoqueInsuficienteException {
        Pedido pedido = buscarPedidoAberto(idPedido);

        if (pedido.listarItens().size() == 0) {
            throw new DadoEntradaException("Pedido deve possuir pelo menos um item.");
        }

        // Valida todos os produtos antes de alterar o estoque.
        for (ItemPedido item : pedido.listarItens()) {
            if (item.getItem() instanceof Produto) {
                Produto produto = buscarProdutoCadastrado(((Produto) item.getItem()).getId());

                if (!produto.isDisponivel()) {
                    throw new ProdutoIndisponivelException("Produto com ID " + produto.getId() + " está indisponível.");
                }

                if (produto.getQtdEstoque() < item.getQuantidade()) {
                    throw new EstoqueInsuficienteException("Estoque insuficiente para o produto " + produto.getId() + ": solicitado " + item.getQuantidade() + ", disponível " + produto.getQtdEstoque() + ".");
                }
            }
        }

        // Baixa o estoque somente após todas as validações.
        for (ItemPedido item : pedido.listarItens()) {
            if (item.getItem() instanceof Produto) {
                Produto produto = buscarProdutoCadastrado(((Produto) item.getItem()).getId());
                produto.registrarVenda(item.getQuantidade());
            }
        }

        pedido.finalizarPedido();
        produtos.salvarAlteracoes();
        pedidos.salvarAlteracoes();
    }

    private Produto buscarProdutoCadastrado(String idProduto) throws RegistroNaoEncontradoException {
        for (Produto produto : produtos.listarProdutos()) {
            if (produto.getId().equals(idProduto)) {
                return produto;
            }
        }

        throw new RegistroNaoEncontradoException("Produto com ID " + idProduto + " não cadastrado.");
    }

    // Cancela um pedido aberto.
    public void cancelarPedido(String idPedido) throws DadoEntradaException, RegistroNaoEncontradoException {
        Pedido pedido = buscarPedidoAberto(idPedido);
        pedido.cancelarPedido();
        pedidos.salvarAlteracoes();
    }
}

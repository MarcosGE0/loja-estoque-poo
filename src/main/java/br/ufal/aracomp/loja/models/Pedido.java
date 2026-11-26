package br.ufal.aracomp.loja.models;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import br.ufal.aracomp.loja.exception.DadoEntradaException;

public class Pedido implements Serializable {
	private String id;
	private StatusPedido status;  // ao iniciar um pedido ele é criado em ABERTO
	private Cliente cliente;
	private List<ItemPedido> itens; // relacionamento da composição
	private DescontoStrategy desconto;
	private String dataCriacao;
	private String dataFinalizacao;
	private double valorDescontoAplicado;
	private double valorFinalAplicado;
	
	public Pedido(String id, Cliente cliente, String dataCriacao) {
		this.id = id;
		this.status = StatusPedido.ABERTO;
		this.cliente = cliente;
		this.itens = new ArrayList<ItemPedido>();
		this.dataCriacao = dataCriacao;
		this.dataFinalizacao = null; // só atualkzia quando o status for para FINALIZADO
		this.desconto = new SemDesconto();
		this.valorDescontoAplicado = 0.0;
		this.valorFinalAplicado = 0.0;
	}
	
	public void adicionarItem(ItemPedido novo_itempedido) throws DadoEntradaException {
		if (this.status != StatusPedido.ABERTO) {
			throw new DadoEntradaException("Não é possível alterar um pedido finalizado ou cancelado.");
		}
		this.itens.add(novo_itempedido);
	}

	public void removerItem(ItemPedido item) throws DadoEntradaException {
		if (this.status != StatusPedido.ABERTO) {
			throw new DadoEntradaException("Não é possível alterar um pedido finalizado ou cancelado.");
		}
		this.itens.remove(item);
	}
	
	public List<ItemPedido> listarItens() {
		return new ArrayList<>(this.itens);
	}
	
	public void finalizarPedido() throws DadoEntradaException {
		if (this.status != StatusPedido.ABERTO) {
			throw new DadoEntradaException("Somente pedidos abertos podem ser finalizados.");
		}
		this.valorDescontoAplicado = this.getValorDesconto();
		this.valorFinalAplicado = this.getValorBruto() - this.valorDescontoAplicado;
		this.status = StatusPedido.FINALIZADO;
		this.dataFinalizacao = LocalDate.now().toString(); // eu tava pensando em passar a data também via parâmetros, não sei, validar com os guys
	}

	public void cancelarPedido() throws DadoEntradaException {
		if (this.status != StatusPedido.ABERTO) {
			throw new DadoEntradaException("Somente pedidos abertos podem ser cancelados.");
		}
		this.status = StatusPedido.CANCELADO;
		this.dataFinalizacao = LocalDate.now().toString();
	}
	
	public double getValorBruto() { // valor bruto é o valor sem desconto
		double valorBruto = 0;
		
		for(ItemPedido itemCarrinho: this.itens) { // o atributo itens é semelhante a um carrinho do ecomerce, tipo
			// em itemproduto é como se fosse cada item no carrinho, no meu carrinho do Mercado Livre tem:
			// 3 camisas (subtotal: quantidade x preco unitario da camisa) + 2x mochinas (subtotal: quantidade x preco unitário da mochila)
			valorBruto += itemCarrinho.calcularSubtotal();
		}
		
		return valorBruto;
	}

	public void aplicarDesconto(DescontoStrategy desconto) throws DadoEntradaException {
		if (desconto == null) {
			throw new DadoEntradaException("Estratégia de desconto não pode ser nula.");
		}

		if (this.status != StatusPedido.ABERTO) {
			throw new DadoEntradaException("Desconto só pode ser aplicado a pedidos abertos.");
		}

		this.desconto = desconto;
	}

	public double getValorDesconto() {
		if (this.status == StatusPedido.FINALIZADO) {
			return this.valorDescontoAplicado;
		}

		if (this.desconto == null) {
			return 0.0;
		}

		double desconto = this.desconto.calcularDesconto(this);
		double valorBruto = this.getValorBruto();

		if (desconto < 0.0) {
			return 0.0;
		}

		if (desconto > valorBruto) {
			return valorBruto;
		}

		return desconto;
	}

	public double getValorFinal() {
		if (this.status == StatusPedido.FINALIZADO) {
			return this.valorFinalAplicado;
		}
		return this.getValorBruto() - this.getValorDesconto();
	}

	public DescontoStrategy getDesconto() {
		if (this.desconto == null) {
			return new SemDesconto();
		}
		return this.desconto;
	}

	public String getNomeDesconto() {
		return this.getDesconto().getNome();
	}

	public String getDescricaoDesconto() {
		if (this.desconto instanceof DescontoPorQuantidade) {
			DescontoPorQuantidade descontoQuantidade =
					(DescontoPorQuantidade) this.desconto;
			int totalItens = 0;

			for (ItemPedido item : this.itens) {
				totalItens += item.getQuantidade();
			}

			if (totalItens < descontoQuantidade.getQuantidadeMinima()) {
				return "Desconto por quantidade não aplicado: pedido possui "
						+ totalItens + " itens; são necessários "
						+ descontoQuantidade.getQuantidadeMinima() + ".";
			}
		}

		return this.getDesconto().getDescricao();
	}
	
	public StatusPedido getStatus() {
		return this.status;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public String getDataFinalizacao() {
		return this.dataFinalizacao;
	}

	public String toString() {
		return "Pedido: " + this.id
				+ "\nCliente: " + this.cliente.getNome()
				+ "\nStatus: " + this.status
				+ "\nData: " + this.dataCriacao
				+ "\nValor bruto: R$ " + this.getValorBruto()
				+ "\nTipo de desconto: " + this.getNomeDesconto()
				+ "\nDetalhe: " + this.getDescricaoDesconto()
				+ "\nDesconto: R$ " + this.getValorDesconto()
				+ "\nValor final: R$ " + this.getValorFinal();
	}

	public String getIdPedido(){
		return this.id;
	}
	
}

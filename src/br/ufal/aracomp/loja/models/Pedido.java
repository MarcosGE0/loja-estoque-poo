package br.ufal.aracomp.loja.models;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate; // tempo date em Python

public class Pedido {
	private String id;
	private StatusPedido status;  // ao iniciar um pedido ele é criado em ABERTO
	private Cliente cliente;
	private List<ItemPedido> itens; // relacionamento da composição
	private DescontoStrategy desconto;
	private String dataCriacao;
	private String dataFinalizacao;
	
	public Pedido(String id, Cliente cliente, String dataCriacao) {
		this.id = id;
		this.status = StatusPedido.ABERTO;
		this.cliente = cliente;
		this.itens = new ArrayList<ItemPedido>();
		this.dataCriacao = dataCriacao;
		this.dataFinalizacao = null; // só atualkzia quando o status for para FINALIZADO
		// this.desconto = em desenvolvimento
	}
	
	public void adicionarItem(ItemPedido novo_itempedido) {
		this.itens.add(novo_itempedido);
	}

	public void removerItem(ItemPedido item) {
		this.itens.remove(item);
	}
	
	public List<ItemPedido> listarItens() {
		return this.itens;
	}
	
	public void finalizarPedido() {
		this.status = StatusPedido.FINALIZADO;
		this.dataFinalizacao = LocalDate.now().toString(); // eu tava pensando em passar a data também via parâmetros, não sei, validar com os guys
	}

	public void cancelarPedido() {
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
	
	public StatusPedido getStatus() {
		return this.status;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public String toString() {
		return String.format("%s | %s | %s | %s | R$ %s", this.id, this.cliente.getNome() ,this.status, this.dataCriacao, this.getValorBruto());
	}

	public String getIdPedido(){
		return this.id;
	}
	
}

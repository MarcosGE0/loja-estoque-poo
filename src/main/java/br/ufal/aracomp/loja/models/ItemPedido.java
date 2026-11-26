package br.ufal.aracomp.loja.models;

import java.io.Serializable;

public class ItemPedido implements Serializable {
	private int quantidade;
	private double precoUnitario;
	private Precificavel item;
	
	public ItemPedido(int quantidade, Precificavel item) {
		this.quantidade = quantidade;
		this.item = item;
		this.precoUnitario = item.getPreco(); // esse atributo é importante pois salva o histórico se o valor do produto mudar
		// tipo, se comprou algo por 40 e alterar para 34, mantem o histório para não ocorrer um efeito cascata
	}
	
	public double calcularSubtotal() {
		double subtotal = this.quantidade * this.precoUnitario;
		
		return subtotal;
	}
	
	public int getQuantidade() { // precisei do encapulusamento desse atributo para poder usar nos descontos
		return this.quantidade;
	}

	public Precificavel getItem() {
		return this.item;
	}

	public void adicionarQuantidade(int quantidade) {
		this.quantidade += quantidade;
	}

	public void removerQuantidade(int quantidade) {
		this.quantidade -= quantidade;
	}
}

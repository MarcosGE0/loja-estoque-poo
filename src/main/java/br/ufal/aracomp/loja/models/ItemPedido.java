package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;
import java.io.Serializable;

public class ItemPedido implements Serializable {
	private int quantidade;
	private double precoUnitario;
	private Precificavel item;
	
	public ItemPedido(int quantidade, Precificavel item) throws DadoEntradaException {
		if (quantidade <= 0) {
			throw new DadoEntradaException("Quantidade deve ser maior que zero.");
		}
		if (item == null) {
			throw new DadoEntradaException("Item do pedido não pode ser nulo.");
		}

		this.quantidade = quantidade;
		this.item = item;
		this.precoUnitario = item.getPreco();
	}
	
	public double calcularSubtotal() {
		double subtotal = this.quantidade * this.precoUnitario;
		
		return subtotal;
	}
	
	public int getQuantidade() {
		return this.quantidade;
	}

	public Precificavel getItem() {
		return this.item;
	}

	public void adicionarQuantidade(int quantidade) throws DadoEntradaException {
		if (quantidade <= 0) {
			throw new DadoEntradaException("Quantidade deve ser maior que zero.");
		}
		this.quantidade += quantidade;
	}

	public void removerQuantidade(int quantidade) throws DadoEntradaException {
		if (quantidade <= 0) {
			throw new DadoEntradaException("Quantidade deve ser maior que zero.");
		}
		if (quantidade > this.quantidade) {
			throw new DadoEntradaException("Quantidade solicitada é maior que a quantidade no pedido.");
		}
		this.quantidade -= quantidade;
	}
}

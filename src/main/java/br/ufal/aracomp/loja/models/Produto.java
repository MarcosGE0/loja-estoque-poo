package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;
import br.ufal.aracomp.loja.exception.EstoqueInsuficienteException;

import java.io.Serializable;

public class Produto implements Precificavel, Serializable {
	private String id;
	private String nome;
	private double preco;
	private boolean ativo; 
	private int qtdEstoque;
	
	public Produto(String id, String nome, double preco, int qtdEstoque) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.ativo = true; // decidi que por padrão, ao criar o produto e colocar em estoque ele já está disponível
		this.qtdEstoque = qtdEstoque;
	}
	
	public double getPreco() {
		return this.preco;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getId() {
		return this.id;
	}
	
	public boolean isDisponivel() {
		return this.ativo;
	}
	
	public String toString() {
		return String.format("%s | %s | R$ %s | Qtd: %s | %s", this.getId(), this.getNome(), this.getPreco(), this.getQtdEstoque(), this.isDisponivel());
	}
	
	public int getQtdEstoque() {
		return this.qtdEstoque;
	}
	
	public void desativar() { // estabelecemos que, mesmo se tiver estoque, e esse atributo
		this.ativo = false; // estiver desativado, ele não vende de forma alguma kkka
	}
	
	public void adicionarEstoque(int qtd) throws DadoEntradaException {
		if (qtd <= 0) {
			throw new DadoEntradaException("A quantidade a adicionar deve ser maior que zero (recebida: " + qtd + ").");
		}

		this.qtdEstoque += qtd;
	}
	
	public void removerEstoque(int qtd) throws DadoEntradaException, EstoqueInsuficienteException {
		if (qtd <= 0) {
			throw new DadoEntradaException("A quantidade a remover deve ser maior que zero (recebida: " + qtd + ").");
		}
		if (qtd > this.qtdEstoque) {
			throw new EstoqueInsuficienteException("Estoque insuficiente para remover " + qtd + " unidade(s); disponível: " + this.qtdEstoque + ".");
		}

		this.qtdEstoque -= qtd;
	}
	
	public void registrarVenda(int qtd) {
		this.qtdEstoque -= qtd;
	}
	
}

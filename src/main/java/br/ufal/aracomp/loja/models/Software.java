package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;

public class Software extends Produto {
	private String chaveAtivacao;
	private String plataforma;
	
	public Software(String id, String nome, double preco, int qtdEstoque, String chaveAtivacao, String plataforma) throws DadoEntradaException {
		super(id, nome, preco, qtdEstoque);
		this.chaveAtivacao = chaveAtivacao;
		this.plataforma = plataforma;
	}
	
	public String getChaveAtivacao() {
		return this.chaveAtivacao;
	}
	
	@Override
	public String toString() {
		return String.format("%s | %s | R$ %s | Qtd: %s | %s | %s", this.getId(), this.getNome(), this.getPreco(), this.getQtdEstoque(), this.isDisponivel(), this.plataforma);
	}
}

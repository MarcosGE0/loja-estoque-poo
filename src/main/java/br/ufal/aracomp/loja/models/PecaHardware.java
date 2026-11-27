package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;

public class PecaHardware extends Produto {
	private String compatibilidade;
	
	public PecaHardware(String id, String nome, double preco, int qtdEstoque, String compatibilidade) throws DadoEntradaException {
		super(id, nome, preco, qtdEstoque);
		this.compatibilidade = compatibilidade;
	}
	
	public String getCompatibilidade() {
		return this.compatibilidade;
	}
	 
	@Override
	public String toString() {
		return String.format("%s | %s | R$ %s | Qtd: %s | %s | %s", this.getId(), this.getNome(), this.getPreco(), this.getQtdEstoque(), this.isDisponivel(), this.getCompatibilidade());
	}
}

package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;

public class Periferico extends Produto {
	private String conexao;

	public Periferico(String id, String nome, double preco, int qtdEstoque, String conexao) throws DadoEntradaException {
		super(id, nome, preco, qtdEstoque);
		this.conexao = conexao;
	}
	
	public String getConexao() {
		return this.conexao;
	}
	
	@Override
	public String toString() {
		return String.format("%s | %s | R$ %s | Qtd: %s | %s | %s", this.getId(), this.getNome(), this.getPreco(), this.getQtdEstoque(), this.isDisponivel(), this.getConexao());
	}
}

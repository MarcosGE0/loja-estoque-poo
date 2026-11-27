package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;

public abstract class Cliente extends Usuario {
	private String endereco;
	
	public Cliente(String id, String nome, String email, String endereco) throws DadoEntradaException {
		super(id, nome, email);
		this.endereco = endereco;
	}
	
	public String getEndereco() {
		return this.endereco;
	}

	public void alterarEndereco(String endereco) {
		this.endereco = endereco;
	}
	
}
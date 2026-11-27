package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;

public abstract class Cliente extends Usuario {
	private String endereco;
	
	public Cliente(String id, String nome, String email, String endereco) throws DadoEntradaException {
		super(id, nome, email);
		validarEndereco(endereco);
		this.endereco = endereco;
	}
	
	public String getEndereco() {
		return this.endereco;
	}

	public void alterarDadosContato(String email, String endereco) throws DadoEntradaException {
		validarEndereco(endereco);
		super.alterarEmail(email);
		this.endereco = endereco;
	}

	private static void validarEndereco(String endereco) throws DadoEntradaException {
		if (endereco == null || endereco.isBlank()) {
			throw new DadoEntradaException("Endereço do cliente não pode ser vazio.");
		}
	}
}
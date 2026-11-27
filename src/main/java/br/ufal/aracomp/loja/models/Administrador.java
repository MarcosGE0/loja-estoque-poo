package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;

public class Administrador extends Usuario{
	private String setor;
	
	public Administrador(String id, String nome, String email, String setor) throws DadoEntradaException {
		super(id, nome, email);
		this.setor = setor;
	}
	
	public String getSetor() {
		return this.setor;
	}

	@Override
	public String toString() {
		return String.format("%s | Setor: %s", super.toString(), this.getSetor());
	}
}

package br.ufal.aracomp.loja.models;

public class Administrador extends Usuario{
	private String setor;
	
	public Administrador(String id, String nome, String email, String setor) {
		super(id, nome, email);
		this.setor = setor;
	}
	
	public String getSetor() {
		return this.setor;
	}
}

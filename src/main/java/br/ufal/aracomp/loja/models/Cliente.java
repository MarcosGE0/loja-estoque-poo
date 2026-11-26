package br.ufal.aracomp.loja.models;

public abstract class Cliente extends Usuario {
	private String endereco;
	
	public Cliente(String id, String nome, String email, String endereco) {
		super(id, nome, email);
		this.endereco = endereco;
	}
	
	public String getEndereco() {
		return this.endereco;
	}
	
	public String getDocumentacao() {
		return this.endereco;
	}
}
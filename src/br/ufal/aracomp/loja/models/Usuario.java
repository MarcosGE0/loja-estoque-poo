package br.ufal.aracomp.loja.models;

public abstract class Usuario {
	private String id;
	private String nome;
	private String email;
	private boolean ativo;
	
	public Usuario(String id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.ativo = true; // um usuário é criado com o status de ativo, não sendo necessário passar como parâmetro
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public boolean isAtivo() {
		return this.ativo;
	}
	
	public String getEmail() { // vou precisar do acesso para validar se já existe um registro com o mesmo email
		return this.email;
	}
	
	public String getId() { // vou precisar do acesso para validar se já existe um registro com o mesmo id
		return this.id;
	}
	
	public String toString() {
		return String.format("%s | %s | %s | %s ", this.getId(), this.getNome(), this.getEmail(), this.isAtivo());
	}
}

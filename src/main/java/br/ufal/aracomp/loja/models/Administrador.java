package br.ufal.aracomp.loja.models;

public class Administrador extends Usuario{
	private String senha;
	private String setor;
	
	public Administrador(String id, String nome, String email, String senha, String setor) {
		super(id, nome, email);
		this.senha = senha;
		this.setor = setor;
	}
	
	public String getSetor() {
		return this.setor;
	}
	
	public boolean validarSenha(String senha) {
		if(this.senha.equals(senha)) {
			return true;
		}
		return false;
	}
}

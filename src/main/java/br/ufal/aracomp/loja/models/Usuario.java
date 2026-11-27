package br.ufal.aracomp.loja.models;

import br.ufal.aracomp.loja.exception.DadoEntradaException;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
	private String id;
	private String nome;
	private String email;
	private boolean ativo;
	
	public Usuario(String id, String nome, String email) throws DadoEntradaException {
		if (id == null || id.isBlank()) {
			throw new DadoEntradaException("ID do usuário não pode ser vazio.");
		}
		if (nome == null || nome.isBlank()) {
			throw new DadoEntradaException("Nome do usuário não pode ser vazio.");
		}
		if (email == null || email.isBlank()) {
			throw new DadoEntradaException("E-mail do usuário não pode ser vazio.");
		}

		this.id = id;
		this.nome = nome;
		this.email = email;
		this.ativo = true;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public boolean isAtivo() {
		return this.ativo;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getId() {
		return this.id;
	}

	public void desativar() {
		this.ativo = false;
	}
	
	public String toString() {
		return String.format("%s | %s | %s | %s ", this.getId(), this.getNome(), this.getEmail(), this.isAtivo());
	}
}

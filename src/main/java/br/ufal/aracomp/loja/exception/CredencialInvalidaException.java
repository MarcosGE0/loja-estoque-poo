package br.ufal.aracomp.loja.exception;

public class CredencialInvalidaException extends Exception {
	private String mensagem;
	
	public CredencialInvalidaException(String mensagem) {
		super(mensagem);
	}
}

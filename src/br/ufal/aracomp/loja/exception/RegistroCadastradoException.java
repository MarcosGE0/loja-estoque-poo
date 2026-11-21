package br.ufal.aracomp.loja.exception;

public class RegistroCadastradoException extends Exception{
	private String mensagem;
	
	public RegistroCadastradoException(String mensagem) {
		super(mensagem);
	}
}

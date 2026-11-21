package br.ufal.aracomp.loja.exception;

public class RegistroNaoEncontradoException extends Exception{
	private String mensagem;
	public RegistroNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
}

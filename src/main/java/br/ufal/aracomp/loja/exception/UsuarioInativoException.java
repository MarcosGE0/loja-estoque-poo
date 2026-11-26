package br.ufal.aracomp.loja.exception;

public class UsuarioInativoException extends Exception {
	private String mensagem;
	
	public UsuarioInativoException(String mensagem) {
		super(mensagem);
	}
}

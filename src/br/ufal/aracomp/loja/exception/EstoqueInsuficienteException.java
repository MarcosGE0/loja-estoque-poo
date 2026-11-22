package br.ufal.aracomp.loja.exception;

public class EstoqueInsuficienteException extends Exception {
	private String mensagem;
	
	public EstoqueInsuficienteException(String mensagem) {
		super(mensagem);
	}
}

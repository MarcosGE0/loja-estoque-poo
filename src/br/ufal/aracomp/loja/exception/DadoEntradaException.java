package br.ufal.aracomp.loja.exception;

public class DadoEntradaException extends Exception {
    private String mensagem;

    public DadoEntradaException(String message) {
        super(message);
    }
}

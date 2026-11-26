package br.ufal.aracomp.loja.exception;

public class ServicoIndisponivelException extends RuntimeException {
    private String mensagem;
    public ServicoIndisponivelException(String message) {
        super(message);
    }
}

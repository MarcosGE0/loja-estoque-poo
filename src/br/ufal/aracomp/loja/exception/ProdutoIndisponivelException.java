package br.ufal.aracomp.loja.exception;

public class ProdutoIndisponivelException extends RuntimeException {
    private String mensagem;
    public ProdutoIndisponivelException(String message) {
        super(message);
    }
}

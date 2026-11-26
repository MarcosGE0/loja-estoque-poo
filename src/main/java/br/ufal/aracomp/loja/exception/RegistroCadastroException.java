package br.ufal.aracomp.loja.exception;

public class RegistroCadastroException extends RuntimeException {
    private String mensagem;
    public RegistroCadastroException(String message) {
        super(message);
    }
}

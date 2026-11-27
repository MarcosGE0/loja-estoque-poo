package br.ufal.aracomp.loja.exception;

public abstract class DominioException extends Exception {

    protected DominioException(String mensagem){
        super(mensagem);
    }

    protected DominioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

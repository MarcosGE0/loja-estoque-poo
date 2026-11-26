package br.ufal.aracomp.loja.exception;

public abstract class DominioException extends Exception {

    protected DominioException(String mensagem){
        super(mensagem);
    }
}

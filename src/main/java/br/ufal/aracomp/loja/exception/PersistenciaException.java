package br.ufal.aracomp.loja.exception;

public class PersistenciaException extends DominioException {
    public PersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }
}

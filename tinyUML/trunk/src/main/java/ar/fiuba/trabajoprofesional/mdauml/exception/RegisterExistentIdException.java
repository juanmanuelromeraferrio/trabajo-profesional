package ar.fiuba.trabajoprofesional.mdauml.exception;

public class RegisterExistentIdException extends Exception {
    public RegisterExistentIdException(String msg) {
        super(msg);
    }

    public RegisterExistentIdException(String msg, Throwable e) {
        super(msg, e);
    }
}

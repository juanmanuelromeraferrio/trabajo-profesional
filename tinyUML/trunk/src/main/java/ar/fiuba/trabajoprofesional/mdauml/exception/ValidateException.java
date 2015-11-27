package ar.fiuba.trabajoprofesional.mdauml.exception;

public class ValidateException extends Exception {

    public ValidateException(String msg, Throwable ex){
        super(msg,ex);
    }
}

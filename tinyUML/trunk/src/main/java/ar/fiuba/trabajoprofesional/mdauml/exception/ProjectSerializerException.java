package ar.fiuba.trabajoprofesional.mdauml.exception;

public class ProjectSerializerException extends Exception {
    public ProjectSerializerException(String msg) {
        super(msg);
    }

    public ProjectSerializerException(String msg, Throwable e) {
        super(msg, e);
    }
}

package ar.fiuba.trabajoprofesional.mdauml.exception;

public class XmlObjectSerializerException extends Exception {
    public XmlObjectSerializerException(String msg) {
        super(msg);
    }

    public XmlObjectSerializerException(String msg, Throwable e) {
        super(msg, e);
    }
}

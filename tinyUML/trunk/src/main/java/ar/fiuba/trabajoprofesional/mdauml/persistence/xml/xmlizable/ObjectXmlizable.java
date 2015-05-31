package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;

import ar.fiuba.trabajoprofesional.mdauml.persistence.Registerer;

public abstract class ObjectXmlizable implements Xmlizable {

    protected static final String ID_ATTR = "id";

    protected Object instance;
    protected Long id;

    public ObjectXmlizable(Object instance) {
        this.instance = instance;
        this.id = Registerer.register(instance);
    }

    public ObjectXmlizable() {
        instance = null;
        id = null;
    }

}

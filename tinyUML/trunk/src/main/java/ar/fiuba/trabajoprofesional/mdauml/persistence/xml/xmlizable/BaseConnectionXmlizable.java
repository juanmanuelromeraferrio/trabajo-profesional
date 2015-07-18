package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;


import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.BaseConnection;
import org.w3c.dom.Element;

public class BaseConnectionXmlizable extends ObjectXmlizable {

    /*
    *    private Relation relation;
        private Connection connection;
     */
    private static final String RELATION_TAG = "relation";


    public BaseConnectionXmlizable(BaseConnection instance) {
        super(instance);
    }

    public BaseConnectionXmlizable() {
        super();
    }

    @Override public void serialize(Element element, Element root) throws Exception {

    }

    @Override public Object deserialize(Element element) throws Exception {
        return null;
    }
}

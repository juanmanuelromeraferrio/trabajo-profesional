package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;

import org.w3c.dom.Element;

import ar.fiuba.trabajoprofesional.mdauml.model.DefaultNamedElement;
import ar.fiuba.trabajoprofesional.mdauml.persistence.Registerer;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;


public class DefaultNamedElementXmlizable extends ObjectXmlizable {

    public static final String NAME_TAG = "name";

    public DefaultNamedElementXmlizable(DefaultNamedElement instance) {
        super(instance);
    }

    public DefaultNamedElementXmlizable() {
        super();
    }


    @Override public void serialize(Element element, Element root) throws Exception {

        DefaultNamedElement castedInstance = (DefaultNamedElement) instance;
        XmlHelper.addAtribute(root, element, ID_ATTR, this.id.toString());
        Element name = XmlHelper.addChildElement(root, element, NAME_TAG);
        name.setTextContent(castedInstance.getName());
    }

    @Override public Object deserialize(Element element) throws Exception {
        Element name = XmlHelper.getChild(element, NAME_TAG);
        ((DefaultNamedElement) instance).setName(name.getTextContent());

        return instance;
    }

}

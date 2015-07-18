package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;

import ar.fiuba.trabajoprofesional.mdauml.model.DefaultNamedElement;
import org.w3c.dom.Element;

import ar.fiuba.trabajoprofesional.mdauml.model.AbstractUmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;


public class AbstractUmlModelElementXmlizable extends DefaultNamedElementXmlizable {


    public AbstractUmlModelElementXmlizable(AbstractUmlModelElement instance) {
        super(instance);
    }

    public AbstractUmlModelElementXmlizable() {
        super();
    }


    @Override public void serialize(Element element, Element root) throws Exception {
        Element parent =
            XmlHelper.addChildElement(root, element, DefaultNamedElement.class.getName());
        super.serialize(parent, root);
        XmlHelper.addAtribute(root, element, ID_ATTR, this.id.toString());
    }

    @Override public Object deserialize(Element element) throws Exception {
        Element childElement = XmlHelper.getChild(element, DefaultNamedElement.class.getName());
        instance = super.deserialize(childElement);
        return instance;
    }

}

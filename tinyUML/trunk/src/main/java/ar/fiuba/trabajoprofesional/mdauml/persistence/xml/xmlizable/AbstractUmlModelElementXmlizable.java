package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;

import org.w3c.dom.Element;

import ar.fiuba.trabajoprofesional.mdauml.model.AbstractUmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;


public class AbstractUmlModelElementXmlizable extends DefaultNamedElementXmlizable {

    public static final String CLASS_TAG = "AbstractUmlModelElement";

    public AbstractUmlModelElementXmlizable(AbstractUmlModelElement instance) {
        super(instance);
    }

    public AbstractUmlModelElementXmlizable() {
        super();
    }

    @Override public Element toXml(Element root) throws Exception {

        Element element = XmlHelper.getNewElement(root, CLASS_TAG);
        XmlHelper.addAtribute(root, element, ID_ATTR, this.id.toString());
        element.appendChild(super.toXml(root));

        return element;
    }

    @Override public Object fromXml(Element element) throws Exception {

        Element defaultNamedElement =
            XmlHelper.getChild(element, DefaultNamedElementXmlizable.CLASS_TAG);
        super.fromXml(defaultNamedElement);
        return instance;

    }

}

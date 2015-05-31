package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;

import org.w3c.dom.Element;

import ar.fiuba.trabajoprofesional.mdauml.model.DefaultNamedElement;
import ar.fiuba.trabajoprofesional.mdauml.persistence.Registerer;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;


public class DefaultNamedElementXmlizable extends ObjectXmlizable {

    public static final String CLASS_TAG = "DefaultNamedElement";
    public static final String NAME_TAG = "name";

    public DefaultNamedElementXmlizable(DefaultNamedElement instance) {
        super(instance);
    }

    public DefaultNamedElementXmlizable() {
        super();
    }


    @Override public Element toXml(Element root) throws Exception {
        DefaultNamedElement castedInstance = (DefaultNamedElement) instance;
        Element element = XmlHelper.getNewElement(root, CLASS_TAG);
        XmlHelper.addAtribute(root, element, ID_ATTR, this.id.toString());
        Element name = XmlHelper.addChildElement(root, element, NAME_TAG);
        name.setTextContent(castedInstance.getName());
        return element;
    }

    @Override public Object fromXml(Element element) throws Exception {
        if (instance == null) {
            String id = element.getAttribute(ID_ATTR);
            this.instance = new DefaultNamedElement();
            this.id = Long.valueOf(id);
            Registerer.register(this.id, instance);
        }
        Element name = XmlHelper.getChild(element, NAME_TAG);
        ((DefaultNamedElement) instance).setName(name.getNodeValue());

        return instance;

    }

}

package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;

import ar.fiuba.trabajoprofesional.mdauml.model.AbstractUmlModelElement;
import org.w3c.dom.Element;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;
import ar.fiuba.trabajoprofesional.mdauml.persistence.Registerer;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;


public class UmlActorXmlizable extends AbstractUmlModelElementXmlizable {

    public static final String DESCRIPTION_TAG = "description";

    public UmlActorXmlizable(UmlActor instance) {
        super(instance);
    }

    public UmlActorXmlizable() {
        super();
    }


    @Override public void serialize(Element element, Element root) throws Exception {
        Element parent =
            XmlHelper.addChildElement(root, element, AbstractUmlModelElement.class.getName());
        super.serialize(parent, root);

        UmlActor castedInstance = (UmlActor) instance;
        Element description = XmlHelper.addChildElement(root, element, DESCRIPTION_TAG);
        description.setTextContent(castedInstance.getDescription());
    }

    @Override public Object deserialize(Element element) throws Exception {
        Element childElement = XmlHelper.getChild(element, AbstractUmlModelElement.class.getName());
        instance = super.deserialize(childElement);

        Element description = XmlHelper.getChild(element, DESCRIPTION_TAG);
        ((UmlActor) instance).setDescription(description.getTextContent());
        return instance;
    }

}

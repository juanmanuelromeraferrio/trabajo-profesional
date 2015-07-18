package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import org.w3c.dom.Element;


public class UmlUseCase$Flow$StepXmlizable extends ObjectXmlizable {

    public static final String DESCRIPTION_TAG = "description";

    public UmlUseCase$Flow$StepXmlizable(UmlUseCase.Flow.Step instance) {
        super(instance);
    }

    public UmlUseCase$Flow$StepXmlizable() {
        super();
    }

    @Override public void serialize(Element element, Element root) throws Exception {
        UmlUseCase.Flow.Step castedInstance = (UmlUseCase.Flow.Step) instance;
        Element description = XmlHelper.addChildElement(root, element, DESCRIPTION_TAG);
        description.setTextContent(castedInstance.getDescription());
    }

    @Override public Object deserialize(Element element) throws Exception {
        Element description = XmlHelper.getChild(element, DESCRIPTION_TAG);
        ((UmlUseCase.Flow.Step) instance).setDescription(description.getTextContent());
        return instance;
    }
}

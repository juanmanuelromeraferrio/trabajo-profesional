package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;



import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase.Flow.Step;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlSerializer2;
import org.w3c.dom.Element;


public class UmlUseCase$FlowXmlizable extends ObjectXmlizable {

    private static final String FLOW_TAG = "flow";

    public UmlUseCase$FlowXmlizable(UmlUseCase.Flow instance) {
        super(instance);
    }

    public UmlUseCase$FlowXmlizable() {
        super();
    }



    @Override public void serialize(Element element, Element root) throws Exception {
        UmlUseCase.Flow castedInstance = (UmlUseCase.Flow) instance;

        Element flow = XmlHelper.addChildElement(root, element, FLOW_TAG);
        for (Step step : castedInstance.getFlow()) {
            flow.appendChild(XmlSerializer2.toXml(root, step));
        }

    }

    @Override public Object deserialize(Element element) throws Exception {
        UmlUseCase.Flow castedInstance = (UmlUseCase.Flow) instance;
        Element flow = XmlHelper.getChild(element, FLOW_TAG);
        for (int i = 0; i < flow.getChildNodes().getLength(); i++) {
            Element step = (Element) flow.getChildNodes().item(i);
            castedInstance.addStep((Step) XmlSerializer2.fromXml(step));
        }
        return instance;
    }
}

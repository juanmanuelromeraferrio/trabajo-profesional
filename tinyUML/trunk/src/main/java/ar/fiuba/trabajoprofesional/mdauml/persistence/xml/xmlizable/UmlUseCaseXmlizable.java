package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;


import ar.fiuba.trabajoprofesional.mdauml.model.AbstractUmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlSerializer2;
import org.w3c.dom.Element;



public class UmlUseCaseXmlizable extends AbstractUmlModelElementXmlizable {

    public static final String DESCRIPTION_TAG = "description";
    public static final String MAIN_ACTORS_TAG = "mainActors";
    public static final String SECONDARY_ACTORS_TAG = "secondaryActors";
    public static final String UML_ACTORS_TAG = "umlActors";
    public static final String MAIN_FLOW_TAG = "mainFlow";
    public static final String ALTERNATIVE_FLOWS_TAG = "alternativeFlows";
    public static final String PRECONDITIONS_TAG = "preconditions";
    public static final String POSTCONDITIONS_TAG = "postconditions";


    public UmlUseCaseXmlizable(UmlUseCase instance) {
        super(instance);
    }

    public UmlUseCaseXmlizable() {
        super();
    }



    @Override public void serialize(Element element, Element root) throws Exception {
        Element parent =
            XmlHelper.addChildElement(root, element, AbstractUmlModelElement.class.getName());
        super.serialize(parent, root);

        UmlUseCase castedInstance = (UmlUseCase) instance;
        Element description = XmlHelper.addChildElement(root, element, DESCRIPTION_TAG);
        description.setTextContent(castedInstance.getDescription());

        Element mainActors = XmlHelper.addChildElement(root, element, MAIN_ACTORS_TAG);
        for (UmlActor actor : castedInstance.getMainActors()) {
            mainActors.appendChild(XmlSerializer2.toXml(root, actor));
        }
        Element secActors = XmlHelper.addChildElement(root, element, SECONDARY_ACTORS_TAG);
        for (UmlActor actor : castedInstance.getSecondaryActors()) {
            secActors.appendChild(XmlSerializer2.toXml(root, actor));
        }
        Element actors = XmlHelper.addChildElement(root, element, UML_ACTORS_TAG);
        for (UmlActor actor : castedInstance.getUmlActors()) {
            actors.appendChild(XmlSerializer2.toXml(root, actor));
        }
        Element mainFlow = XmlHelper.addChildElement(root, element, MAIN_FLOW_TAG);
        mainFlow.appendChild(XmlSerializer2.toXml(root, castedInstance.getMainFLow()));

        Element alternativeFlows = XmlHelper.addChildElement(root, element, ALTERNATIVE_FLOWS_TAG);
        for (UmlUseCase.Flow flow : castedInstance.getAlternativeFlows()) {
            alternativeFlows.appendChild(XmlSerializer2.toXml(root, flow));
        }

        Element preconditions = XmlHelper.addChildElement(root, element, PRECONDITIONS_TAG);
        for (String precondition : castedInstance.getPreconditions()) {
            XmlHelper.addChildElement(root, preconditions, precondition);
        }

        Element postconditions = XmlHelper.addChildElement(root, element, POSTCONDITIONS_TAG);
        for (String postcondition : castedInstance.getPreconditions()) {
            XmlHelper.addChildElement(root, postconditions, postcondition);
        }
    }

    @Override public Object deserialize(Element element) throws Exception {
        Element childElement = XmlHelper.getChild(element, AbstractUmlModelElement.class.getName());
        instance = super.deserialize(childElement);

        UmlUseCase castedInstance = (UmlUseCase) instance;
        Element description = XmlHelper.getChild(element, DESCRIPTION_TAG);
        castedInstance.setDescription(description.getTextContent());

        Element mainActors = XmlHelper.getChild(element, MAIN_ACTORS_TAG);
        for (int i = 0; i < mainActors.getChildNodes().getLength(); i++) {
            Element mainActor = (Element) mainActors.getChildNodes().item(i);
            castedInstance.addMainActor((UmlActor) XmlSerializer2.fromXml(mainActor));
        }
        Element secActors = XmlHelper.getChild(element, SECONDARY_ACTORS_TAG);
        for (int i = 0; i < secActors.getChildNodes().getLength(); i++) {
            Element secActor = (Element) secActors.getChildNodes().item(i);
            castedInstance.addSecondaryActor((UmlActor) XmlSerializer2.fromXml(secActor));
        }
        Element actors = XmlHelper.getChild(element, UML_ACTORS_TAG);
        for (int i = 0; i < actors.getChildNodes().getLength(); i++) {
            Element actor = (Element) actors.getChildNodes().item(i);
            castedInstance.addUmlActor((UmlActor) XmlSerializer2.fromXml(actor));
        }

        Element mainFlow = XmlHelper.getChild(element, MAIN_FLOW_TAG);
        Element childMainFlow = (Element) mainFlow.getChildNodes().item(0);
        castedInstance.setMainFLow((UmlUseCase.Flow) XmlSerializer2.fromXml(childMainFlow));

        Element alternativeFlows = XmlHelper.getChild(element, ALTERNATIVE_FLOWS_TAG);
        for (int i = 0; i < alternativeFlows.getChildNodes().getLength(); i++) {
            Element alternativeFlow = (Element) alternativeFlows.getChildNodes().item(i);
            castedInstance
                .addAlternativeFlow((UmlUseCase.Flow) XmlSerializer2.fromXml(alternativeFlow));
        }

        Element preconditions = XmlHelper.getChild(element, PRECONDITIONS_TAG);
        for (int i = 0; i < preconditions.getChildNodes().getLength(); i++) {
            Element precondition = (Element) preconditions.getChildNodes().item(i);
            castedInstance.addPrecondition(precondition.getTagName());
        }

        Element postconditions = XmlHelper.getChild(element, PRECONDITIONS_TAG);
        for (int i = 0; i < postconditions.getChildNodes().getLength(); i++) {
            Element postcondition = (Element) postconditions.getChildNodes().item(i);
            castedInstance.addPostcondition(postcondition.getTagName());
        }


        return instance;
    }

}

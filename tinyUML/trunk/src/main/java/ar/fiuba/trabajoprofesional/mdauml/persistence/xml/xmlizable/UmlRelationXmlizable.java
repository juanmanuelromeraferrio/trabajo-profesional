package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;

import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlSerializer2;
import org.w3c.dom.Element;

public class UmlRelationXmlizable extends AbstractUmlModelElementXmlizable {

    public static final String ELEMENT1_TAG = "element1";
    public static final String ELEMENT2_TAG = "element2";
    public static final String NAV_TO_ELEMENT1_TAG = "navigableToElement1";
    public static final String NAV_TO_ELEMENT2_TAG = "navigableToElement2";
    public static final String CAN_SET_NAV_ELEMENT1_TAG = "canSetElement1Navigability";
    public static final String CAN_SET_NAV_ELEMENT2_TAG = "canSetElement2Navigability";
    public static final String ELEMENT1_MULTIPLICITY_TAG = "element1Multiplicity";
    public static final String ELEMENT2_MULTIPLICITY_TAG = "element2Multiplicity";
    public static final String READING_DIRECTION_TAG = "readingDirection";

    public UmlRelationXmlizable(UmlRelation instance) {
        super(instance);
    }

    public UmlRelationXmlizable() {
        super();
    }

    @Override public void serialize(Element element, Element root) throws Exception {
        Element parent =
            XmlHelper.addChildElement(root, element, AbstractUmlModelElement.class.getName());
        super.serialize(parent, root);

        UmlRelation castedInstance = (UmlRelation) instance;

        Element element1 = XmlHelper.addChildElement(root, element, ELEMENT1_TAG);
        element1.appendChild(XmlSerializer2.toXml(root, castedInstance.getElement1()));

        Element element2 = XmlHelper.addChildElement(root, element, ELEMENT2_TAG);
        element2.appendChild(XmlSerializer2.toXml(root, castedInstance.getElement2()));

        Element navigableToElement1 = XmlHelper.addChildElement(root, element, NAV_TO_ELEMENT1_TAG);
        navigableToElement1.setTextContent(String.valueOf(castedInstance.isNavigableToElement1()));

        Element navigableToElement2 = XmlHelper.addChildElement(root, element, NAV_TO_ELEMENT2_TAG);
        navigableToElement2.setTextContent(String.valueOf(castedInstance.isNavigableToElement2()));

        Element canSetElement1Navigability =
            XmlHelper.addChildElement(root, element, CAN_SET_NAV_ELEMENT1_TAG);
        canSetElement1Navigability
            .setTextContent(String.valueOf(castedInstance.canSetElement1Navigability()));

        Element canSetElement2Navigability =
            XmlHelper.addChildElement(root, element, CAN_SET_NAV_ELEMENT2_TAG);
        canSetElement2Navigability
            .setTextContent(String.valueOf(castedInstance.canSetElement2Navigability()));

        Element element1Multiplicity =
            XmlHelper.addChildElement(root, element, ELEMENT1_MULTIPLICITY_TAG);
        element1Multiplicity
            .appendChild(XmlSerializer2.toXml(root, castedInstance.getElement1Multiplicity()));

        Element element2Multiplicity =
            XmlHelper.addChildElement(root, element, ELEMENT2_MULTIPLICITY_TAG);
        element2Multiplicity
            .appendChild(XmlSerializer2.toXml(root, castedInstance.getElement2Multiplicity()));

        Element readingDirection = XmlHelper.addChildElement(root, element, READING_DIRECTION_TAG);
        readingDirection.setTextContent(castedInstance.getNameReadingDirection().name());

    }

    @Override public Object deserialize(Element element) throws Exception {
        Element childElement = XmlHelper.getChild(element, AbstractUmlModelElement.class.getName());
        instance = super.deserialize(childElement);

        UmlRelation castedInstance = (UmlRelation) instance;

        Element element1 = XmlHelper.getChild(element, ELEMENT1_TAG);
        Element element1Child = (Element) element1.getChildNodes().item(0);
        castedInstance.setElement1((UmlModelElement) XmlSerializer2.fromXml(element1Child));

        Element element2 = XmlHelper.getChild(element, ELEMENT2_TAG);
        Element element2Child = (Element) element2.getChildNodes().item(0);
        castedInstance.setElement2((UmlModelElement) XmlSerializer2.fromXml(element2Child));

        Element navigableToElement1 = XmlHelper.getChild(element, NAV_TO_ELEMENT1_TAG);
        castedInstance
            .setNavigableToElement1(Boolean.parseBoolean(navigableToElement1.getTextContent()));

        Element navigableToElement2 = XmlHelper.getChild(element, NAV_TO_ELEMENT2_TAG);
        castedInstance
            .setNavigableToElement2(Boolean.parseBoolean(navigableToElement2.getTextContent()));

        Element canSetElement1Navigability = XmlHelper.getChild(element, CAN_SET_NAV_ELEMENT1_TAG);
        castedInstance.setCanSetElement1Navigability(
            Boolean.parseBoolean(canSetElement1Navigability.getTextContent()));

        Element canSetElement2Navigability = XmlHelper.getChild(element, CAN_SET_NAV_ELEMENT2_TAG);
        castedInstance.setCanSetElement2Navigability(
            Boolean.parseBoolean(canSetElement2Navigability.getTextContent()));

        Element element1Multiplicity = XmlHelper.getChild(element, ELEMENT1_MULTIPLICITY_TAG);
        Element element1MultiplicityChild = (Element) element1Multiplicity.getChildNodes().item(0);
        castedInstance.setElement1Multiplicity(
            (Multiplicity) XmlSerializer2.fromXml(element1MultiplicityChild));

        Element element2Multiplicity = XmlHelper.getChild(element, ELEMENT2_MULTIPLICITY_TAG);
        Element element2MultiplicityChild = (Element) element2Multiplicity.getChildNodes().item(0);
        castedInstance.setElement2Multiplicity(
            (Multiplicity) XmlSerializer2.fromXml(element2MultiplicityChild));

        Element readingDirection = XmlHelper.getChild(element, READING_DIRECTION_TAG);
        castedInstance.setNameReadingDirection(
            Relation.ReadingDirection.valueOf(readingDirection.getTextContent()));

        return instance;

    }

}

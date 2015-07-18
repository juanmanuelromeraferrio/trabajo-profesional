package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;


import ar.fiuba.trabajoprofesional.mdauml.draw.*;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlSerializer2;
import org.w3c.dom.Element;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class AbstractNodeXmlizable extends ObjectXmlizable {

    private static final String ORIGIN_TAG = "origin";
    private static final String SIZE_TAG = "size";
    private static final String MINIMUM_SIZE_TAG = "minimumSize";
    private static final String PARENT_TAG = "parent";
    private static final String CHANGE_LISTENERS_TAG = "changeListeners";
    private static final String CONNECTIONS_TAG = "connections";

    public AbstractNodeXmlizable(AbstractNode instance) {
        super(instance);
    }

    public AbstractNodeXmlizable() {
        super();
    }

    @Override public void serialize(Element element, Element root) throws Exception {
        AbstractNode castedInstance = (AbstractNode) instance;
        XmlHelper.addAtribute(root, element, ID_ATTR, this.id.toString());
        Element origin = XmlHelper.addChildElement(root, element, ORIGIN_TAG);
        origin.appendChild(XmlSerializer2.toXml(root, castedInstance.getOrigin()));

        Element size = XmlHelper.addChildElement(root, element, SIZE_TAG);
        size.appendChild(XmlSerializer2.toXml(root, castedInstance.getSize()));

        Element minSize = XmlHelper.addChildElement(root, element, MINIMUM_SIZE_TAG);
        minSize.appendChild(XmlSerializer2.toXml(root, castedInstance.getMinimumSize()));

        Element parent = XmlHelper.addChildElement(root, element, PARENT_TAG);
        parent.appendChild(XmlSerializer2.toXml(root, castedInstance.getParent()));

        Element changeListeners = XmlHelper.addChildElement(root, element, CHANGE_LISTENERS_TAG);
        for (NodeChangeListener nodeChangeListener : castedInstance.getChangeListeners()) {
            NodeSelection selection = castedInstance.getSelection();
            if (selection != null && nodeChangeListener.equals(selection))
                continue; //Selection is not serialized
            changeListeners.appendChild(XmlSerializer2.toXml(root, nodeChangeListener));
        }
        Element connections = XmlHelper.addChildElement(root, element, CONNECTIONS_TAG);
        for (Connection connection : castedInstance.getConnections()) {
            connections.appendChild(XmlSerializer2.toXml(root, connection));
        }
    }

    @Override public Object deserialize(Element element) throws Exception {
        AbstractNode castedInstance = (AbstractNode) instance;
        Element origin = XmlHelper.getChild(element, ORIGIN_TAG);
        Element originChild = (Element) origin.getChildNodes().item(0);
        castedInstance.setOrigin((Point2D) XmlSerializer2.fromXml(originChild));

        Element size = XmlHelper.getChild(element, SIZE_TAG);
        Element sizeChild = (Element) size.getChildNodes().item(0);
        castedInstance.setSize((Dimension2D) XmlSerializer2.fromXml(sizeChild));

        Element minSize = XmlHelper.getChild(element, MINIMUM_SIZE_TAG);
        Element minSizeChild = (Element) minSize.getChildNodes().item(0);
        castedInstance.setMinimumSize((Dimension2D) XmlSerializer2.fromXml(minSizeChild));

        Element parent = XmlHelper.getChild(element, PARENT_TAG);
        Element parentChild = (Element) parent.getChildNodes().item(0);
        castedInstance.setParent((CompositeNode) XmlSerializer2.fromXml(parentChild));

        Element listeners = XmlHelper.getChild(element, CHANGE_LISTENERS_TAG);
        for (int i = 0; i < listeners.getChildNodes().getLength(); i++) {
            Element listener = (Element) listeners.getChildNodes().item(i);
            castedInstance
                .addNodeChangeListener((NodeChangeListener) XmlSerializer2.fromXml(listener));
        }
        Element connections = XmlHelper.getChild(element, CONNECTIONS_TAG);
        for (int i = 0; i < connections.getChildNodes().getLength(); i++) {
            Element connection = (Element) connections.getChildNodes().item(i);
            castedInstance.addConnection((Connection) XmlSerializer2.fromXml(connection));
        }

        return instance;
    }
}

package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;


import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import org.w3c.dom.Element;

import java.awt.geom.Point2D;

public class Point2D$DoubleXmlizable extends ObjectXmlizable {

    private static final String X_TAG = "x";
    private static final String Y_TAG = "y";

    public Point2D$DoubleXmlizable(Point2D.Double instance) {
        super(instance);
    }

    public Point2D$DoubleXmlizable() {
        super();
    }

    @Override public void serialize(Element element, Element root) throws Exception {
        Point2D.Double castedInstance = (Point2D.Double) instance;
        Element x = XmlHelper.addChildElement(root, element, X_TAG);
        x.setTextContent(String.valueOf(castedInstance.getX()));

        Element y = XmlHelper.addChildElement(root, element, Y_TAG);
        y.setTextContent(String.valueOf(castedInstance.getY()));
    }

    @Override public Object deserialize(Element element) throws Exception {
        Point2D.Double castedInstance = (Point2D.Double) instance;
        Element x = XmlHelper.getChild(element, X_TAG);
        Element y = XmlHelper.getChild(element, Y_TAG);
        castedInstance
            .setLocation(Double.valueOf(x.getTextContent()), Double.valueOf(y.getTextContent()));
        return instance;
    }
}

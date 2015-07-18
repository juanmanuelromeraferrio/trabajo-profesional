package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;


import ar.fiuba.trabajoprofesional.mdauml.draw.DoubleDimension;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import org.w3c.dom.Element;

public class DoubleDimensionXmlizable extends ObjectXmlizable {

    private static final String WIDTH_TAG = "width";
    private static final String HEIGHT_TAG = "height";

    public DoubleDimensionXmlizable(DoubleDimension instance) {
        super(instance);
    }

    public DoubleDimensionXmlizable() {
        super();
    }

    @Override public void serialize(Element element, Element root) throws Exception {
        DoubleDimension castedInstance = (DoubleDimension) instance;
        Element w = XmlHelper.addChildElement(root, element, WIDTH_TAG);
        w.setTextContent(String.valueOf(castedInstance.getWidth()));

        Element h = XmlHelper.addChildElement(root, element, HEIGHT_TAG);
        h.setTextContent(String.valueOf(castedInstance.getHeight()));
    }

    @Override public Object deserialize(Element element) throws Exception {
        DoubleDimension castedInstance = (DoubleDimension) instance;
        Element w = XmlHelper.getChild(element, WIDTH_TAG);
        Element h = XmlHelper.getChild(element, HEIGHT_TAG);
        castedInstance
            .setSize(Double.valueOf(w.getTextContent()), Double.valueOf(h.getTextContent()));
        return instance;
    }
}

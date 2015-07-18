package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;

import org.w3c.dom.Element;

public interface Xmlizable {
    public Element toXml(Element root) throws Exception;

    public Object fromXml(Element element) throws Exception;


}

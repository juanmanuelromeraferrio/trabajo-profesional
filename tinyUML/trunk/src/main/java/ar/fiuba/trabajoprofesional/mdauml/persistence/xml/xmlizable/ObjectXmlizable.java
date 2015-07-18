package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable;


import ar.fiuba.trabajoprofesional.mdauml.persistence.Registerer;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import org.w3c.dom.Element;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class ObjectXmlizable implements Xmlizable {

    protected static final String ID_ATTR = "id";

    protected Object instance;
    protected Long id;

    public ObjectXmlizable(Object instance) {
        this.instance = instance;

    }

    public ObjectXmlizable() {
        instance = null;
        id = null;
    }

    @Override final public Element toXml(Element root) throws Exception {
        Element element = XmlHelper.getNewElement(root, instance.getClass().getName());
        if (Registerer.isRegistered(instance)) {
            this.id = Registerer.getId(instance);
            XmlHelper.addAtribute(root, element, ObjectXmlizable.ID_ATTR, this.id.toString());
        } else {
            this.id = Registerer.register(instance);
            XmlHelper.addAtribute(root, element, ObjectXmlizable.ID_ATTR, this.id.toString());
            this.serialize(element, root);
        }
        return element;
    }

    @Override final public Object fromXml(Element element) throws Exception {
        this.id = Long.valueOf(element.getAttribute(ID_ATTR));
        if (Registerer.isRegistered(id))
            return Registerer.getObject(id);
        instance = deserialize(element);
        return instance;

    }

    public abstract void serialize(Element element, Element root) throws Exception;

    public abstract Object deserialize(Element element) throws Exception;

}

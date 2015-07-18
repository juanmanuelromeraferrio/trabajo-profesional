package ar.fiuba.trabajoprofesional.mdauml.persistence.xml;

import ar.fiuba.trabajoprofesional.mdauml.exception.XmlSerializerException;
import ar.fiuba.trabajoprofesional.mdauml.persistence.Registerer;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable.Xmlizable;
import groovy.ui.Console;
import org.w3c.dom.Element;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class XmlSerializer2 {

    private static final String XMLIZABLE_PACKAGE = Xmlizable.class.getPackage().getName();
    private static final String INT_TAG = "int";
    private static final String DOUBLE_TAG = "double";
    private static final String BOOLEAN_TAG = "boolean";
    private static final String CHAR_TAG = "char";
    private static final String BYTE_TAG = "byte";
    private static final String SHORT_TAG = "short";
    private static final String LONG_TAG = "long";
    private static final String FLOAT_TAG = "float";
    private static final String ENUM_TAG = "enum";
    private static final String STRING_TAG = "string";
    private static final String OBJECT_TAG = "object";
    private static final String CLASS_ATT = "class";

    public static Element toXml(Element root, Object object) throws XmlSerializerException {

        try {
            serialize(root, object);
            Xmlizable xmlizable = getXmlizableFromInstance(object);
            return xmlizable.toXml(root);

        } catch (Exception e) {
            throw new XmlSerializerException("Error creating element from instance.", e);
        }
    }

    private static Xmlizable getXmlizableFromInstance(Object object) throws XmlSerializerException {
        try {

            Class<? extends Xmlizable> xmlizableClass = getXmliableClass(object.getClass());
            Constructor<? extends Xmlizable> constructor =
                xmlizableClass.getConstructor(object.getClass());
            return constructor.newInstance(object);

        } catch (Exception e) {
            throw new XmlSerializerException("Error instanciating xmlizable from class.", e);
        }
    }

    public static Object fromXml(Element element) throws XmlSerializerException {
        try {
            Xmlizable xmlizable = getXmlizableFromClass(Class.forName(element.getTagName()));
            return xmlizable.fromXml(element);

        } catch (ClassNotFoundException e) {
            throw new XmlSerializerException("Element class not found.", e);
        } catch (Exception e) {
            throw new XmlSerializerException("Error creating instance from element.", e);
        }
    }

    private static Xmlizable getXmlizableFromClass(Class<?> aClass) throws XmlSerializerException {
        try {

            Class<? extends Xmlizable> xmlizableClass = getXmliableClass(aClass);
            return xmlizableClass.newInstance();

        } catch (Exception e) {
            throw new XmlSerializerException("Error instanciating xmlizable from class.", e);
        }
    }

    private static Class<? extends Xmlizable> getXmliableClass(Class<?> aClass)
        throws XmlSerializerException {
        try {
            String[] classNameArray = aClass.getName().split("\\.");
            String className = classNameArray[classNameArray.length - 1];
            String xmlizableClassName = XMLIZABLE_PACKAGE + "." + className + "Xmlizable";
            return (Class<? extends Xmlizable>) Class.forName(xmlizableClassName);

        } catch (ClassNotFoundException e) {
            throw new XmlSerializerException("Class not found for xmlizable.", e);
        } catch (Exception e) {
            throw new XmlSerializerException("Error creating xmlizable class.", e);
        }
    }

    public static Element serialize(Element root, Element element, Object obj, Class<?> clazz)
        throws Exception {
        if (Registerer.isRegistered(obj)) {

        }
        XmlHelper.addAtribute(root, element, CLASS_ATT, clazz.getName());
        XmlHelper.addAtribute(root, element, ID_ATT, id);
        if (clazz != Object.class) {
            Element parent = XmlHelper.addChildElement(root, element, "parent");
            serialize(root, parent, obj, clazz.getSuperclass());
        }



        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                Field field = fields[i];
                int modifiers = field.getModifiers();
                if (Modifier.isTransient(modifiers))
                    continue;
                Class<?> type = field.getType();
                if (type.isPrimitive()) {

                    Element primitive = null;
                    if (type == Integer.TYPE) {
                        primitive = XmlHelper.addChildElement(root, element, INT_TAG);
                    } else if (type == Double.TYPE) {
                        primitive = XmlHelper.addChildElement(root, element, DOUBLE_TAG);
                    } else if (type == Boolean.TYPE) {
                        primitive = XmlHelper.addChildElement(root, element, BOOLEAN_TAG);
                    } else if (type == Character.TYPE) {
                        primitive = XmlHelper.addChildElement(root, element, CHAR_TAG);
                    } else if (type == Byte.TYPE) {
                        primitive = XmlHelper.addChildElement(root, element, BYTE_TAG);
                    } else if (type == Short.TYPE) {
                        primitive = XmlHelper.addChildElement(root, element, SHORT_TAG);
                    } else if (type == Long.TYPE) {
                        primitive = XmlHelper.addChildElement(root, element, LONG_TAG);
                    } else if (type == Float.TYPE) {
                        primitive = XmlHelper.addChildElement(root, element, FLOAT_TAG);
                    }
                    primitive.setTextContent(field.get(obj).toString());

                } else if (type.isEnum()) {
                    Element enumElement = XmlHelper.addChildElement(root, element, ENUM_TAG);
                    enumElement.setTextContent(field.get(obj).toString());

                } else if (type == String.class) {
                    Element enumElement = XmlHelper.addChildElement(root, element, STRING_TAG);
                    enumElement.setTextContent(field.get(obj).toString());
                } else if (Object.class.isAssignableFrom(type)) {
                    Element objectElement = XmlHelper.addChildElement(root, element, OBJECT_TAG);
                    serialize()
                }
            } catch (Exception e) {
                continue;
            }

        } return null;
    }

    public static Field findFieldWithName(Class<?> type, String name) {
        List<Field> all = getAllFields(type);
        for (Field field : all) {
            if (field.getName().equals(name))
                return field;
        }
        return null;
    }

    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}

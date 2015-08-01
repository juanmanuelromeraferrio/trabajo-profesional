package ar.fiuba.trabajoprofesional.mdauml.persistence.xml;

import ar.fiuba.trabajoprofesional.mdauml.exception.XmlSerializer2Exception;
import ar.fiuba.trabajoprofesional.mdauml.persistence.Registerer;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.*;
import java.util.*;


public class XmlSerializer2 {

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
    private static final String ID_ATT = "id";
    private static final String ID_REF_ATT = "id_ref";
    private static final String PARENT_TAG = "parent";
    private static final String FIELD_TAG = "field";
    private static final String NAME_ATT = "name";
    private static final String MODIFIERS_ATT = "modifiers";
    private static final String TYPE_ATT = "type";
    private static final String ARRAY_TAG = "array";
    private static final String LENGTH_ATT = "length";
    private static final String SERIALIZED_TAG = "serialized";
    private static final String NULL_TAG = "null";
    private static final String SET_TAG = "set";
    private static final String MAP_TAG = "map";
    private static final String KEY_TYPE_ATT = "keyType";
    private static final String VALUE_TYPE_ATT = "valueType";
    private static final String KEY_TAG = "key";
    private static final String VALUE_TAG = "value";
    private static final String LIST_TAG = "list";
    private static final String NUMBER_TAG = "number";
    private static final String CLASS_TAG = "class";

    private File file;
    private Document doc;
    private Element root;

    public XmlSerializer2(String filePath) throws XmlSerializer2Exception {
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            file = new File(filePath);
            doc = docBuilder.newDocument();
            root = doc.createElement(SERIALIZED_TAG);
            doc.appendChild(root);

        } catch (Exception e) {
            throw new XmlSerializer2Exception(
                "Error creating XmlSerializer for file: " + filePath + " .\n", e);
        }
    }

    private void saveXml(Document doc) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new FileWriter(file, false));
        transformer.transform(source, result);
    }

    public void writeObject(Object obj) throws Exception {
        Element element = toXml(obj);
        root.appendChild(element);
        saveXml(doc);

    }

    public Element toXml(Object obj) throws Exception {
        if (obj == null)
            return XmlHelper.getNewElement(root, NULL_TAG);

        Class<? extends Object> clazz = obj.getClass();

        String tag = getObjectTag(obj);
        Element element = XmlHelper.getNewElement(root, tag);
        XmlHelper.addAtribute(root, element, CLASS_ATT, clazz.getName());

        //these are not registered because they are inmutables
        if (String.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz)
            || Boolean.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)) {
            element.setTextContent(obj.toString());
            return element;
        }

        if (Registerer.isRegistered(obj)) {
            XmlHelper.addAtribute(root, element, ID_REF_ATT, Registerer.getId(obj).toString());
            return element;
        }
        Registerer.register(obj);
        XmlHelper.addAtribute(root, element, ID_ATT, Registerer.getId(obj).toString());


        if (Collection.class.isAssignableFrom(clazz))
            return collectionToXml(obj, element);
        if (Map.class.isAssignableFrom(clazz))
            return mapToXml((Map) obj, element);

        return toXml(obj, obj.getClass(), element);
    }

    private String getObjectTag(Object obj) {
        if (Set.class.isAssignableFrom(obj.getClass()))
            return SET_TAG;
        if (Map.class.isAssignableFrom(obj.getClass()))
            return MAP_TAG;
        if (List.class.isAssignableFrom(obj.getClass()))
            return LIST_TAG;
        if (Number.class.isAssignableFrom(obj.getClass()))
            return NUMBER_TAG;
        if (String.class.isAssignableFrom(obj.getClass()))
            return STRING_TAG;
        return OBJECT_TAG;
    }


    private Element collectionToXml(Object obj, Element element) throws Exception {
        if (Set.class.isAssignableFrom(obj.getClass()))
            return setToXml((Set) obj, element);
        if (List.class.isAssignableFrom(obj.getClass()))
            return listToXml((List) obj, element);

        return null;
    }

    private Element listToXml(List list, Element element) throws Exception {
        for (Object listEntry : list) {
            Element objectElement = toXml(listEntry);
            element.appendChild(objectElement);
        }
        return element;
    }

    private Element mapToXml(Map map, Element element) throws Exception {
        ;
        Set<Map.Entry> entries = map.entrySet();
        for (Map.Entry mapEntry : entries) {
            Element keyElement = XmlHelper.addChildElement(root, element, KEY_TAG);
            Element key = toXml(mapEntry.getKey());
            keyElement.appendChild(key);
            Element valueElement = XmlHelper.addChildElement(root, element, VALUE_TAG);
            Element value = toXml(mapEntry.getValue());
            valueElement.appendChild(value);
        }
        return element;
    }

    private Element setToXml(Set set, Element element) throws Exception {
        for (Object setEntry : set) {
            Element objectElement = toXml(setEntry);
            element.appendChild(objectElement);
        }
        return element;
    }


    private Element toXml(Object obj, Class<?> clazz, Element element)
        throws Exception {

        Element classElement = XmlHelper.getNewElement(root, CLASS_TAG);
        XmlHelper.addAtribute(root, classElement, NAME_ATT, clazz.getName());
        element.appendChild(classElement);

        Field[] fields = clazz.getDeclaredFields();

        if (Registerer.isRegistered(clazz)) {
            XmlHelper
                .addAtribute(root, classElement, ID_REF_ATT, Registerer.getId(clazz).toString());
        } else {
            Registerer.register(clazz);
            XmlHelper.addAtribute(root, classElement, ID_ATT, Registerer.getId(clazz).toString());

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                int modifiers = field.getModifiers();
                if (Modifier.isTransient(modifiers) || !Modifier.isStatic(modifiers))
                    continue;
                addFieldToObject(clazz, field, classElement);

            }
        }
        if (clazz.getSuperclass() != Object.class && clazz.getSuperclass() != null) {
            Element parent = XmlHelper.getNewElement(root, PARENT_TAG);
            XmlHelper.addAtribute(root, parent, CLASS_ATT, clazz.getSuperclass().getName());
            parent = toXml(obj, clazz.getSuperclass(), parent);
            element.appendChild(parent);
        }


        for (int i = 0; i < fields.length; i++) {
            try {
                Field field = fields[i];
                int modifiers = field.getModifiers();
                if (Modifier.isTransient(modifiers) || Modifier.isStatic(modifiers))
                    continue;

                addFieldToObject(obj, field, element);

            } catch (Exception e) {
                continue;
            }

        }
        return element;
    }

    private void addFieldToObject(Object obj, Field field, Element element) {
        try {
            field.setAccessible(true);
            int modifiers = field.getModifiers();
            Element fieldElement = XmlHelper.addChildElement(root, element, FIELD_TAG);
            XmlHelper.addAtribute(root, fieldElement, NAME_ATT, field.getName());
            XmlHelper.addAtribute(root, fieldElement, MODIFIERS_ATT, Modifier.toString(modifiers));
            XmlHelper.addAtribute(root, fieldElement, TYPE_ATT, field.getType().getName());

            Class<?> type = field.getType();
            if (type.isPrimitive()) {

                Element primitive = primitiveToXml(type, field.get(obj));
                fieldElement.appendChild(primitive);

            } else if (type.isEnum()) {
                Element enumElement = XmlHelper.addChildElement(root, fieldElement, ENUM_TAG);
                enumElement.setTextContent(field.get(obj).toString());

            } else if (type.isArray()) {
                Element arrayElement = XmlHelper.addChildElement(root, fieldElement, ARRAY_TAG);
                Object array = field.get(obj);
                int length = Array.getLength(array);
                XmlHelper.addAtribute(root, arrayElement, LENGTH_ATT, String.valueOf(length));
                XmlHelper
                    .addAtribute(root, arrayElement, TYPE_ATT, type.getComponentType().getName());

                for (int j = 0; j < length; j++) {
                    Class<?> componentType = type.getComponentType();
                    Object componentObject = Array.get(array, j);
                    Element componentElement;
                    if (componentType.isPrimitive())
                        componentElement = primitiveToXml(componentType, componentObject);
                    else if (componentType == String.class) {
                        componentElement = XmlHelper.getNewElement(root, STRING_TAG);
                        componentElement.setTextContent(componentObject.toString());
                    } else {
                        componentElement = toXml(componentObject);
                    }
                    arrayElement.appendChild(componentElement);

                }


            } else if (Object.class.isAssignableFrom(type)) {
                Element objectElement = toXml(field.get(obj));
                fieldElement.appendChild(objectElement);
            }

        } catch (Exception e) {

        }
    }

    private Element primitiveToXml(Class<?> type, Object obj) {
        Element primitive = null;

        if (type == Integer.TYPE) {
            primitive = XmlHelper.getNewElement(root, INT_TAG);
        } else if (type == Double.TYPE) {
            primitive = XmlHelper.getNewElement(root, DOUBLE_TAG);
        } else if (type == Boolean.TYPE) {
            primitive = XmlHelper.getNewElement(root, BOOLEAN_TAG);
        } else if (type == Character.TYPE) {
            primitive = XmlHelper.getNewElement(root, CHAR_TAG);
        } else if (type == Byte.TYPE) {
            primitive = XmlHelper.getNewElement(root, BYTE_TAG);
        } else if (type == Short.TYPE) {
            primitive = XmlHelper.getNewElement(root, SHORT_TAG);
        } else if (type == Long.TYPE) {
            primitive = XmlHelper.getNewElement(root, LONG_TAG);
        } else if (type == Float.TYPE) {
            primitive = XmlHelper.getNewElement(root, FLOAT_TAG);
        }
        primitive.setTextContent(obj.toString());

        return primitive;

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

    class Foo<T> {
        final Class<T> typeParameterClass;

        public Foo(Class<T> typeParameterClass) {
            this.typeParameterClass = typeParameterClass;
        }

        public void bar() {
            TypeVariable<? extends Class<? extends Foo>> type =
                this.getClass().getTypeParameters()[0];
            System.out.println(typeParameterClass.getTypeParameters()[0].getName());

        }
    }


}

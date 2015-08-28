package test.mdauml.persistence;

import ar.fiuba.trabajoprofesional.mdauml.exception.XmlObjectSerializerException;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlObjectSerializer;
import junit.framework.TestCase;
import test.mdauml.persistence.classes.NonDefaultConstructorClass;
import test.mdauml.persistence.classes.Primitives;
import test.mdauml.persistence.classes.SingletonClass;
import test.mdauml.persistence.classes.StaticFields;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 17/08/2015.
 */
public class SerializerTest extends TestCase {



    public void testSerializeString() throws Exception {

        XmlObjectSerializer s = new XmlObjectSerializer("testSerializeString.xml");
        s.writeObject("Hello World!");
        String readed = (String) s.readObject();

        assertEquals("Hello World!", readed);


    }

    public void testSerializeStrings() throws Exception {

        XmlObjectSerializer s = new XmlObjectSerializer("testSerializeStrings.xml");
        s.writeObject("Hello");
        s.writeObject("World");
        s.writeObject("");
        String hello = (String) s.readObject();
        String world = (String) s.readObject();
        String empty = (String) s.readObject();

        assertEquals("Hello", hello);
        assertEquals("World", world);
        assertEquals("", empty);


    }


    public void testSerializePrimitives() throws Exception {
        XmlObjectSerializer s = new XmlObjectSerializer("testSerializePrimitives.xml");

        Primitives original = new Primitives();

        original.setB((byte) 0x1F);
        original.setC('c');
        original.setD(12.235464);
        original.setF(0.0002346F);
        original.setSh((short) 5);
        original.setI(546);
        original.setL(123456789L);
        original.setFa(false);
        original.setTr(true);


        s.writeObject(original);
        Primitives primitives = (Primitives) s.readObject();

        assertEquals(original, primitives);

    }

    public void testStaticFields() throws Exception {
        StaticFields original = new StaticFields();

        original.setNonStaticField("Im not static");
        StaticFields.setStaticInt(9);
        StaticFields.setStaticString("9");
        Primitives primitives = new Primitives();

        primitives.setB((byte) 0x1F);
        primitives.setC('c');
        primitives.setD(12.235464);
        primitives.setF(0.0002346F);
        primitives.setSh((short) 5);
        primitives.setI(546);
        primitives.setL(123456789L);
        primitives.setFa(false);
        primitives.setTr(true);

        StaticFields.setStaticPrimitives(primitives);

        XmlObjectSerializer s = new XmlObjectSerializer("testStaticFields.xml");
        s.writeObject(original);

        StaticFields.setStaticInt(0);
        StaticFields.setStaticString("");
        StaticFields.setStaticPrimitives(new Primitives());

        StaticFields readed = (StaticFields) s.readObject();

        assertEquals(original.getNonStaticField(), readed.getNonStaticField());
        assertEquals(9, StaticFields.getStaticInt());
        assertEquals("9", StaticFields.getStaticString());
        assertEquals(primitives, StaticFields.getStaticPrimitives());



    }



    public void testSingletonClass() throws Exception {

        XmlObjectSerializer s = new XmlObjectSerializer("testSingletonClass.xml");
        SingletonClass.instance.setValue("hola");
        s.writeObject(SingletonClass.instance);

        SingletonClass readed = (SingletonClass) s.readObject();


        assertEquals(readed, SingletonClass.instance);
    }

    public void testNonDefaultConstructorClass() throws Exception {

        XmlObjectSerializer s = new XmlObjectSerializer("testNonDefaultConstructorClass.xml");
        int [] ia = {1,2};
        String [] sa = {"a","b"};
        String str = "hola" ;
        List<String> l = new ArrayList<String>();

        NonDefaultConstructorClass original = new NonDefaultConstructorClass(5,2,ia,sa,str,l);

        Object [] args = new Object[1];
        original.ellipsis(args);
        s.writeObject(original);

        NonDefaultConstructorClass readed = (NonDefaultConstructorClass) s.readObject();

        assertEquals(readed.getValue(),original.getValue());


    }

}

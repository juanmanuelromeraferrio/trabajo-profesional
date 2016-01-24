/**
 * Copyright 2007 Wei-ju Wu
 *
 * This file is part of TinyUML.
 *
 * TinyUML is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * TinyUML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TinyUML; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package test.mdauml.model;
import java.util.ArrayList;
import java.util.List;

import ar.fiuba.trabajoprofesional.mdauml.model.*;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * A test class for UmlClass.
 * @author Wei-ju Wu
 * @version 1.0
 */
public class UmlClassTest extends MockObjectTestCase {
  
  /**
   * Initial state.
   */
  public void testInitial() {
    UmlClass umlclass = UmlClass.getPrototype();
    assertFalse(umlclass.isAbstract());
    assertEquals(0, umlclass.getMethods().size());
  }
  
  /**
   * Tests the setAbstract method.
   */
  public void testSetters() {
    Mock mockListener = mock(UmlModelElementListener.class);
    UmlClass umlclass = UmlClass.getPrototype();
    UmlClass clonedClass = (UmlClass) UmlClass.getPrototype().clone();
    clonedClass.addModelElementListener((UmlModelElementListener)
      mockListener.proxy());
    mockListener.expects(once()).method("elementChanged").with(eq(clonedClass));
    clonedClass.setAbstract(true);
    assertTrue(clonedClass.isAbstract());
    assertFalse(umlclass.isAbstract());
  }
  
  /**
   * Tests the setMethods() method.
   */
  public void testSetMethods() {
    Mock mockListener = mock(UmlModelElementListener.class);
    UmlClass clonedClass = (UmlClass) UmlClass.getPrototype().clone();
    clonedClass.addModelElementListener((UmlModelElementListener)
      mockListener.proxy());
    List<UmlMethod> methodList = new ArrayList<UmlMethod>();
    UmlMethod method1 = (UmlMethod) UmlMethod.getPrototype().clone();
    UmlMethod method2 = (UmlMethod) UmlMethod.getPrototype().clone();
    UmlMethod method3 = (UmlMethod) UmlMethod.getPrototype().clone();
    methodList.add(method1);
    methodList.add(method2);
    methodList.add(method3);
    
    mockListener.expects(once()).method("elementChanged").with(eq(clonedClass));
    clonedClass.setMethods(methodList);
    assertEquals(methodList, clonedClass.getMethods());
  }

  /**
   * Tests the setAttributes() method.
   */
  public void testSetAttributes() {
    Mock mockListener = mock(UmlModelElementListener.class);
    UmlClass clonedClass = (UmlClass) UmlClass.getPrototype().clone();
    clonedClass.addModelElementListener((UmlModelElementListener)
      mockListener.proxy());
    List<UmlAttribute> attributeList = new ArrayList<UmlAttribute>();
    UmlAttribute attrib1 = (UmlAttribute) UmlAttribute.getPrototype().clone();
    UmlAttribute attrib2 = (UmlAttribute) UmlAttribute.getPrototype().clone();
    UmlAttribute attrib3 = (UmlAttribute) UmlAttribute.getPrototype().clone();
    attributeList.add(attrib1);
    attributeList.add(attrib2);
    attributeList.add(attrib3);
    
    mockListener.expects(once()).method("elementChanged").with(eq(clonedClass));
    clonedClass.setAttributes(attributeList);
    assertEquals(attributeList, clonedClass.getAttributes());
  }

  /**
   * Tests the setStereotypes() method.
   */
  public void testSetStereotypes() {
    Mock mockListener = mock(UmlModelElementListener.class);
    UmlClass clonedClass = (UmlClass) UmlClass.getPrototype().clone();
    clonedClass.addModelElementListener((UmlModelElementListener)
      mockListener.proxy());
    List<UmlStereotype> stereotypeList = new ArrayList<UmlStereotype>();
    UmlStereotype stereo1 = (UmlStereotype) UmlStereotype.getPrototype().clone();
    UmlStereotype stereo2 = (UmlStereotype) UmlStereotype.getPrototype().clone();
    UmlStereotype stereo3 = (UmlStereotype) UmlStereotype.getPrototype().clone();
    stereotypeList.add(stereo1);
    stereotypeList.add(stereo2);
    stereotypeList.add(stereo3);
    
    mockListener.expects(once()).method("elementChanged").with(eq(clonedClass));
    clonedClass.setStereotypes(stereotypeList);
    assertEquals(stereotypeList, clonedClass.getStereotypes());
  }
  
  /**
   * Tests  the clone() method.
   */
  public void testClone() {
    List<UmlMethod> methods = new ArrayList<UmlMethod>();
    List<UmlAttribute> attributes = new ArrayList<UmlAttribute>();
    List<UmlStereotype> stereotypes = new ArrayList<UmlStereotype>();
    for (int i = 0; i < 3; i++) {
      UmlMethod method = (UmlMethod) UmlMethod.getPrototype().clone();
      method.setName("method" + i);
      methods.add(method);
      UmlAttribute attribute = (UmlAttribute) UmlAttribute.getPrototype().clone();
      attribute.setName("attribute" + i);
      attributes.add(attribute);
      UmlStereotype stereo = (UmlStereotype) UmlStereotype.getPrototype().clone();
      stereo.setName("stereotype" + i);
      stereotypes.add(stereo);
    }
    
    UmlClass original = UmlClass.getPrototype();
    original.setAttributes(attributes);
    original.setMethods(methods);
    original.setStereotypes(stereotypes);
    
    UmlClass cloned = (UmlClass) original.clone();
    assertTrue(original.getAttributes() != cloned.getAttributes());
    assertEquals(original.getAttributes().size(),
      cloned.getAttributes().size());
    for (int i = 0; i < cloned.getAttributes().size(); i++) {
      assertTrue(original.getAttributes().get(i) ==
        cloned.getAttributes().get(i));
    }
    
    assertTrue(original.getMethods() != cloned.getMethods());
    assertEquals(original.getMethods().size(), cloned.getMethods().size());
    for (int i = 0; i < cloned.getMethods().size(); i++) {
      assertTrue(original.getMethods().get(i) ==
        cloned.getMethods().get(i));
    }
    
    assertTrue(original.getStereotypes() != cloned.getStereotypes());
    assertEquals(original.getStereotypes().size(),
      cloned.getStereotypes().size());
    for (int i = 0; i < cloned.getStereotypes().size(); i++) {
      assertTrue(original.getStereotypes().get(i) ==
        cloned.getStereotypes().get(i));
    }
  }
}

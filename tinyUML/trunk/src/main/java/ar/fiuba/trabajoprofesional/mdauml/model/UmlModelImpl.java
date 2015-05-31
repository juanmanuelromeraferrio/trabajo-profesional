/**
 * Copyright 2007 Wei-ju Wu
 *
 * This file is part of TinyUML.
 *
 * TinyUML is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * TinyUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with TinyUML; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301
 * USA
 */
package ar.fiuba.trabajoprofesional.mdauml.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.fiuba.trabajoprofesional.mdauml.exception.ElementNameAlreadyExist;

/**
 * This class is the default implementation of the UmlModel interface.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class UmlModelImpl implements UmlModel, NameChangeListener {

  private static final long serialVersionUID = -3440413376365267032L;
  // The list of main elements. Top-level elements go here. A top-level element
  // is an element without a parent namespace (package).
  private Set<UmlModelElement> mainElements = new HashSet<UmlModelElement>();
  private List<UmlDiagram> diagrams = new ArrayList<UmlDiagram>();
  private transient Set<UmlModelListener> modelListeners = new HashSet<UmlModelListener>();

  /**
   * Constructor.
   */
  public UmlModelImpl() {}

  /**
   * Writes the instance variables to the stream.
   * 
   * @param stream an ObjectOutputStream
   * @throws java.io.IOException if I/O error occured
   */
  private void writeObject(ObjectOutputStream stream) throws IOException {
    // listeners should not be written
    stream.writeObject(mainElements);
    stream.writeObject(diagrams);
  }

  /**
   * Reset the transient values for serialization.
   * 
   * @param stream an ObjectInputStream
   * @throws java.io.IOException if I/O error occured
   * @throws ClassNotFoundException if class was not found
   */
  private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
    modelListeners = new HashSet<UmlModelListener>();
    mainElements = (Set<UmlModelElement>) stream.readObject();
    diagrams = (List<UmlDiagram>) stream.readObject();
  }

  /**
   * {@inheritDoc}
   */
  public void addElement(UmlModelElement anElement) {
    mainElements.add(anElement);
  }

  /**
   * {@inheritDoc}
   */
  public void addElement(UmlModelElement anElement, UmlDiagram diagram) {
    anElement.addNameChangeListener(this);  
    mainElements.add(anElement);
    for (UmlModelListener l : modelListeners) {
      l.elementAdded(anElement, diagram);
    }
  }

  @Override
  public void removeElement(UmlModelElement anElement, UmlDiagram diagram) {
    mainElements.remove(anElement);
    for (UmlModelListener l : modelListeners) {
      l.elementRemoved(anElement, diagram);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean contains(UmlModelElement anElement) {
    return mainElements.contains(anElement);
  }

  /**
   * {@inheritDoc}
   */
  public Set<UmlModelElement> getElements() {
    return mainElements;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "UML model";
  }

  /**
   * {@inheritDoc}
   */
  public void addDiagram(UmlDiagram diagram) {
    diagrams.add(diagram);
    for (UmlModelListener l : modelListeners) {
      l.diagramAdded(diagram);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void removeDiagram(UmlDiagram diagram) {
    diagrams.remove(diagram);
    for (UmlModelListener l : modelListeners) {
      l.diagramRemoved(diagram);
    }
  }

  /**
   * {@inheritDoc}
   */
  public List<? extends UmlDiagram> getDiagrams() {
    return diagrams;
  }

  /**
   * {@inheritDoc}
   */
  public void addModelListener(UmlModelListener l) {
    modelListeners.add(l);
  }

  /**
   * {@inheritDoc}
   */
  public void removeModelListener(UmlModelListener l) {
    modelListeners.remove(l);
  }

  @Override
  public boolean exist(String name) {
    for (UmlModelElement element : mainElements) {
      if (element.getName().equals(name))
        return true;
    }
    return false;
  }

  @Override
  public void nameChanged(NamedElement element) throws ElementNameAlreadyExist {
    for (UmlModelElement modelElement : mainElements) {
      if (modelElement.getClass().equals(element.getClass()) && modelElement != element
          && modelElement.getName().equals(element.getName())) {
        throw new ElementNameAlreadyExist();
      }
    }

  }
}

package org.tinyuml.model;

/**
 * This class represents an UML Actor
 * 
 * @author Juan Manuel Romera
 *
 */
public class UmlActor extends AbstractUmlModelElement {

  /**
	 * 
	 */
  private static final long serialVersionUID = 5066199446764326666L;

  private static UmlActor prototype;

  /**
   * Returns the prototype instance.
   * 
   * @return the prototype instance
   */
  public static UmlActor getPrototype() {
    if (prototype == null)
      prototype = new UmlActor();
    return prototype;
  }

  /**
   * Constructor.
   */
  private UmlActor() {}

}

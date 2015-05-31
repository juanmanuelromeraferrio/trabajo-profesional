package ar.fiuba.trabajoprofesional.mdauml.model;

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
  private String description;

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
  protected UmlActor() {}


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  @Override
  public int hashCode() {
      return this.getName().length();
  }
  @Override
  public boolean equals(Object obj) {
      if ( obj instanceof UmlActor && ((UmlActor)obj).getName().equals(this.getName()))
        return true;
      return false;
  }

}

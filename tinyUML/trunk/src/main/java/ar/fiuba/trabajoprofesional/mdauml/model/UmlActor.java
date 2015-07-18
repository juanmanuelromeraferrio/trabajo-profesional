package ar.fiuba.trabajoprofesional.mdauml.model;

/**
 * This class represents an UML Actor
 *
 * @author Juan Manuel Romera
 */
public class UmlActor extends AbstractUmlModelElement {

    /**
     *
     */
    private static final long serialVersionUID = 5066199446764326666L;

    private static UmlActor prototype;
    private String description;
    private transient String example = "hola";

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    /**
     * Constructor.
     */
    public UmlActor() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override public boolean equals(Object obj) {
        if (obj instanceof UmlActor && ((UmlActor) obj).getName().equals(this.getName()))
            return true;
        return false;
    }

}

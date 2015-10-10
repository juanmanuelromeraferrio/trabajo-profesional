package ar.fiuba.trabajoprofesional.mdauml.model;

import java.util.HashSet;
import java.util.Set;

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
    private UmlActor parent=null;
    private Set<UmlActor> children = new HashSet<>();

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

    public void addParent(UmlActor parent){
        if(this.equals(parent) || (this.parent!=null && this.parent.equals(parent)))
            return;

        if(this.parent!=null)
            this.parent.removeChild(this);
        this.parent = parent;
        this.parent.addChild(this);

    }
    public void removeParent(){
        if(this.parent==null)
            return;
        this.parent.removeChild(this);
        this.parent=null;
    }
    private void addChild(UmlActor child){
        this.children.add(child);

    }

    private void removeChild(UmlActor child){
       this.children.remove(child);
    }

    @Override public boolean equals(Object obj) {
        if (obj instanceof UmlActor && ((UmlActor) obj).getName().equals(this.getName()))
            return true;
        return false;
    }

    @Override
    public ElementType getElementType() {
        return ElementType.ACTOR;
    }
}

package ar.fiuba.trabajoprofesional.mdauml.umldraw.clazz;

import ar.fiuba.trabajoprofesional.mdauml.draw.RectilinearConnection;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;

public class RectilinearAssociation extends Association {

    /**
     *
     */
    private static final long serialVersionUID = -3385959195759520249L;
    private static Association prototype;

    /**
     * Constructor.
     */
    private RectilinearAssociation() {
        setConnection(new RectilinearConnection());
        setupMultiplicityLabels();
        setupNameLabel();
    }

    /**
     * Returns the prototype instance.
     *
     * @return the prototype instance
     */
    public static Association getPrototype() {
        if (prototype == null)
            prototype = new RectilinearAssociation();
        return prototype;
    }

    @Override
    public void setModelElement(UmlModelElement model) {

    }
}

package ar.fiuba.trabajoprofesional.mdauml.ui;

import ar.fiuba.trabajoprofesional.mdauml.model.AbstractUmlModelElement;

public interface TreeDraggerListener {


    void setDraggerElement(AbstractUmlModelElement element);

    void setReleasePoint(double d, double e);

}

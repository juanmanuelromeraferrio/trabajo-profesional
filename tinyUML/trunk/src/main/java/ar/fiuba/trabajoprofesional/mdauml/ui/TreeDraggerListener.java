package ar.fiuba.trabajoprofesional.mdauml.ui;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;

public interface TreeDraggerListener {


    public void setDraggerElement(UmlModelElement element);

    public void setReleasePoint(double d, double e);

}

package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.serializer;

import ar.fiuba.trabajoprofesional.mdauml.draw.*;
import ar.fiuba.trabajoprofesional.mdauml.draw.Label;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;

import java.awt.*;

/**
 * Created by Fernando on 12/07/2015.
 */
public class ActorMock {

    private static final double DEFAULT_WIDHT = 40;
    private static final double DEFAULT_HEIGHT = 70;

    private static final double LABEL_MARGIN_TOP = 5;

    private static final Color BACKGROUND = Color.WHITE;

    private static final double MIN_WIDTH = 30;
    private static final double MIN_HEIGHT = 50;
    private static ActorMock prototype;
    private UmlActor actor;
    private ar.fiuba.trabajoprofesional.mdauml.draw.Label label;


    public ActorMock() {
        label = new SimpleLabel();
    }

    public UmlActor getActor() {
        return actor;
    }

    public void setActor(UmlActor actor) {
        this.actor = actor;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}

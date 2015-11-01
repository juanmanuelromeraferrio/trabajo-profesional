package ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase;


import ar.fiuba.trabajoprofesional.mdauml.draw.*;
import ar.fiuba.trabajoprofesional.mdauml.exception.AddConnectionException;
import ar.fiuba.trabajoprofesional.mdauml.model.ExtendRelation;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlRelation;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.ArrowConnection;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.BaseConnection;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.ConnectionNameLabel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Extend extends ArrowConnection {

    private static Extend prototype;
    private ConnectionNameLabel nameLabel;

    /**
     * Private constructor.
     */
    private Extend() {
        setConnection(new SimpleConnection());
        setIsDashed(true);
        setOpenHead(true);
        relation=new ExtendRelation();
        setupNameLabel();
    }
    @Override
    public Extend clone(){
        Extend cloned= (Extend) super.clone();
        cloned.setupNameLabel();
        cloned.nameLabel.setParent(nameLabel.getParent());
        return cloned;
    }

    /**
     * Returns the prototype instance.
     *
     * @return the prototype instance
     */
    public static Extend getPrototype() {
        if (prototype == null)
            prototype = new Extend();
        return prototype;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setParent(CompositeNode parent) {
        super.setParent(parent);
        nameLabel.setParent(parent);
    }

    /**
     * {@inheritDoc}
     */
    @Override public void draw(DrawingContext drawingContext) {
        super.draw(drawingContext);
        positionNameLabel();
        nameLabel.draw(drawingContext);
    }


    /**
     * Sets the name label.
     */
    public void setupNameLabel() {
        nameLabel = new ConnectionNameLabel();
        nameLabel.setConnection(this);

    }

    /**
     * Sets the position for the name label.
     */
    private void positionNameLabel() {
        // medium segment
        java.util.List<Line2D> segments = getSegments();
        Line2D middlesegment = segments.get(segments.size() / 2);
        int x = (int) (middlesegment.getX2() + middlesegment.getX1()) / 2;
        int y = (int) (middlesegment.getY2() + middlesegment.getY1()) / 2;
        nameLabel.setAbsolutePos(x, y);
    }

    @Override public void acceptNode(ConnectionVisitor node) {
        node.addConcreteConnection(this);
    }
    @Override public void cancelNode(ConnectionVisitor node){
        node.removeConcreteConnection(this);
    }
}
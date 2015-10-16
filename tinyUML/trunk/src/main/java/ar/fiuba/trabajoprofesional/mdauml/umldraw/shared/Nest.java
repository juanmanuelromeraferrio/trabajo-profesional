package ar.fiuba.trabajoprofesional.mdauml.umldraw.shared;

import ar.fiuba.trabajoprofesional.mdauml.draw.*;
import ar.fiuba.trabajoprofesional.mdauml.model.NestRelation;

import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Nest extends BaseConnection {

    private static Nest prototype;

    /**
     * Private constructor.
     */
    private Nest() {
        setConnection(new SimpleConnection());
        setIsDashed(false);
        relation=new NestRelation();
    }

    /**
     * Returns the prototype instance.
     *
     * @return the prototype instance
     */
    public static Nest getPrototype() {
        if (prototype == null)
            prototype = new Nest();
        return prototype;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void draw(DrawingContext drawingContext) {

        Node nesting = getConnection().getNode1();
        Node nested = getConnection().getNode2();


        if(nesting.contains(nested.getAbsCenterX(),nested.getAbsCenterY()) || nesting.intersects(nested.getAbsoluteBounds()))
            return;
        super.draw(drawingContext);
        drawAnchor(drawingContext);

    }

    private void drawAnchor(DrawingContext drawingContext) {
        Point2D endingPoint = getEndPoint1();
        double radius = 7;
        Point2D translation;
        if(isRigth(endingPoint))
            translation = new Point2D.Double(radius,0);
        else if(isLeft(endingPoint))
            translation = new Point2D.Double(-radius,0);
        else if(isUp(endingPoint))
            translation = new Point2D.Double(0,-radius);
        else
            translation = new Point2D.Double(0,radius);

        Point2D center =  new Point2D.Double(endingPoint.getX()+translation.getX(),endingPoint.getY()+translation.getY());



        drawingContext.drawEllipse(new Point2D.Double(center.getX() - radius, center.getY() - radius), new DoubleDimension(2*radius,2*radius),Color.white);
        drawingContext.drawLine(center.getX(),center.getY()-radius,center.getX(),center.getY()+radius);
        drawingContext.drawLine(center.getX()-radius,center.getY(),center.getX()+radius,center.getY());

    }

    private boolean isRigth(Point2D endpoint) {
        Rectangle2D bounds = getNode1().getAbsoluteBounds();
        return doubleEqual(endpoint.getX(),bounds.getMaxX());
    }
    private boolean isLeft(Point2D endpoint) {
        Rectangle2D bounds = getNode1().getAbsoluteBounds();
        return doubleEqual(endpoint.getX(),bounds.getMinX());
    }
    private boolean isUp(Point2D endpoint) {
        Rectangle2D bounds = getNode1().getAbsoluteBounds();
        return doubleEqual(endpoint.getY(),bounds.getMinY());
    }
    private boolean isDown(Point2D endpoint) {
        Rectangle2D bounds = getNode1().getAbsoluteBounds();
        return doubleEqual(endpoint.getY(),bounds.getMaxY());
    }

    private boolean doubleEqual(double d1, double d2){
        return Math.abs(d1-d2) <  0.2;
    }


}

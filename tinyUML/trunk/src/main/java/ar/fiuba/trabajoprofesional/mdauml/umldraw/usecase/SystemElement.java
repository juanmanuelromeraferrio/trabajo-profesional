package ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase;


import ar.fiuba.trabajoprofesional.mdauml.draw.AbstractNode;
import ar.fiuba.trabajoprofesional.mdauml.draw.DrawingContext;
import ar.fiuba.trabajoprofesional.mdauml.draw.Label;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlNode;

import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SystemElement extends AbstractNode implements UmlNode{


    private static SystemElement prototype;

    private SystemElement(){
        super();
        setSize(400,400);
    }

    public static SystemElement getPrototype(){

        if(prototype==null)
            prototype=new SystemElement();


        return  prototype;
    }

    @Override
    public boolean isInBack() {
        return true;
    }

    @Override
    public void draw(DrawingContext drawingContext) {
        Stroke originalStroke = drawingContext.getGraphics2D().getStroke();
        drawingContext.getGraphics2D().setStroke(new BasicStroke(3));
        drawingContext.getGraphics2D().setBackground(new Color(0,0,0,0));
        drawingContext.getGraphics2D().setColor(Color.black);
        drawingContext.getGraphics2D().draw(new Rectangle2D.Double(getAbsoluteX1(), getAbsoluteY1(), getSize().getWidth(), getSize().getHeight()));

    }

    @Override
    public void recalculateSize(DrawingContext drawingContext) {

    }

    @Override
    public Label getLabelAt(double mx, double my) {
        return null;
    }

    @Override
    public UmlModelElement getModelElement() {
        return null;
    }

    @Override
    public void setModelElement(UmlModelElement model) {

    }
}

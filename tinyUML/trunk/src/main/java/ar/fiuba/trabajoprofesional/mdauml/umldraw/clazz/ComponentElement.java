/**
 * Copyright 2007 Wei-ju Wu
 * <p/>
 * This file is part of TinyUML.
 * <p/>
 * TinyUML is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p/>
 * TinyUML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with TinyUML; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package ar.fiuba.trabajoprofesional.mdauml.umldraw.clazz;

import ar.fiuba.trabajoprofesional.mdauml.draw.*;
import ar.fiuba.trabajoprofesional.mdauml.draw.Label;
import ar.fiuba.trabajoprofesional.mdauml.model.RelationEndType;
import ar.fiuba.trabajoprofesional.mdauml.model.RelationType;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlComponent;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlNode;
import ar.fiuba.trabajoprofesional.mdauml.draw.DrawingContext.FontType;

import java.awt.*;
import java.awt.geom.Dimension2D;

/**
 * This class represents the visual view of a UmlComponent. It delegates
 * almost all size related operations to its main compartment, except for
 * the origin.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public final class ComponentElement extends AbstractCompositeNode implements LabelSource, UmlNode {

    private static final double MIN_WIDTH = 120;
    private static final double MIN_HEIGHT = 40;
    private static final double MARGIN_TOP = 20;
    private static ComponentElement prototype;
    private UmlComponent component;
    private Compartment mainCompartment;

    /**
     * Private constructor.
     */
    private ComponentElement() {
        mainCompartment = new Compartment();
        mainCompartment.setParent(this);
        Label mainLabel = new SimpleLabel();
        mainLabel.setSource(this);
        mainLabel.setFontType(FontType.ELEMENT_NAME);
        mainCompartment.addLabel(mainLabel);
        setMinimumSize(MIN_WIDTH, MIN_HEIGHT);
        mainCompartment.setMarginTop(MARGIN_TOP);
    }

    /**
     * Returns the prototype instance.
     *
     * @return the prototype instance
     */
    public static ComponentElement getPrototype() {
        if (prototype == null)
            prototype = new ComponentElement();
        return prototype;
    }

    /**
     * {@inheritDoc}
     */
    @Override public Object clone() {
        ComponentElement cloned = (ComponentElement) super.clone();
        if (component != null) {
            cloned.component = (UmlComponent) component.clone();
        }
        cloned.mainCompartment = (Compartment) mainCompartment.clone();
        cloned.mainCompartment.setParent(cloned);
        cloned.mainCompartment.getLabels().get(0).setSource(cloned);
        return cloned;
    }

    /**
     * Returns the main compartment. Exposed for testing purposes.
     *
     * @return the main compartment
     */
    public Compartment getMainCompartment() {
        return mainCompartment;
    }

    /**
     * Sets the main compartment. Just for testing purposes.
     *
     * @param aCompartment a compartment to replace the main compartment
     */
    public void setMainCompartment(Compartment aCompartment) {
        mainCompartment = aCompartment;
    }

    /**
     * {@inheritDoc}
     */
    public UmlModelElement getModelElement() {
        return component;
    }

    @Override
    public void setModelElement(UmlModelElement model) {
        if(model instanceof UmlComponent)
            component= (UmlComponent) model;
        else throw new IllegalArgumentException("UmlComponent expected");
    }

    /**
     * Sets the model element.
     *
     * @param aModelElement the model element
     */
    public void setModelElement(UmlComponent aModelElement) {
        component = aModelElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void recalculateSize(DrawingContext drawingContext) {
        mainCompartment.recalculateSize(drawingContext);
        notifyNodeResized();
    }

    /**
     * {@inheritDoc}
     */
    @Override public Dimension2D getMinimumSize() {
        return mainCompartment.getMinimumSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setMinimumSize(double width, double height) {
        mainCompartment.setMinimumSize(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override public Dimension2D getSize() {
        return mainCompartment.getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override public void setSize(double width, double height) {
        mainCompartment.setSize(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override public void draw(DrawingContext drawingContext) {
        if (!isValid()) {
            recalculateSize(drawingContext);
        }
        mainCompartment.draw(drawingContext);
        drawIcon(drawingContext);
    }

    /**
     * {@inheritDoc}
     */
    public String getLabelText() {
        return getModelElement().getName();
    }

    /**
     * {@inheritDoc}
     */
    public void setLabelText(String aText) {
        getModelElement().setName(aText);
    }

    /**
     * {@inheritDoc}
     */
    @Override public void invalidate() {
        mainCompartment.invalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override public boolean isValid() {
        return mainCompartment.isValid();
    }

    /**
     * Draws the icon.
     *
     * @param drawingContext the DrawingContext
     */
    private void drawIcon(DrawingContext drawingContext) {
        double x = getAbsoluteX1() + getSize().getWidth() - 30;
        double y = getAbsoluteY1() + 5;
        double width = 20;
        double height = 15;
        // larger rect
        drawingContext.drawRectangle(x, y, width, height, Color.WHITE);
        // two ports
        width = 8;
        height = 4;
        x -= 3;
        y += 2;
        drawingContext.drawRectangle(x, y, width, height, Color.WHITE);
        y += 6;
        drawingContext.drawRectangle(x, y, width, height, Color.WHITE);
    }

    /**
     * {@inheritDoc}
     */
    public Label getLabelAt(double mx, double my) {
        return mainCompartment.getLabelAt(mx, my);
    }

    /**
     * {@inheritDoc}
     */
    public boolean acceptsConnection(RelationType associationType, RelationEndType as,
        UmlNode with) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override public boolean isNestable() {
        return true;
    }
}

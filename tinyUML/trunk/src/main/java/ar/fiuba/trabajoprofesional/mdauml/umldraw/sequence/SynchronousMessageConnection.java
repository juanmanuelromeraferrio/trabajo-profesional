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
package ar.fiuba.trabajoprofesional.mdauml.umldraw.sequence;

import ar.fiuba.trabajoprofesional.mdauml.draw.DrawingContext;
import ar.fiuba.trabajoprofesional.mdauml.draw.SimpleConnection;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.BaseConnection;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * This class implements a synchronous message connection within a sequence
 * diagram.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public final class SynchronousMessageConnection extends BaseConnection {

    /**
     * The prototype.
     */
    private static SynchronousMessageConnection prototype;

    /**
     * Constructor.
     */
    private SynchronousMessageConnection() {
        setConnection(new SimpleConnection());
    }

    /**
     * Returns the prototype of this class.
     *
     * @return the prototype
     */
    public static SynchronousMessageConnection getPrototype() {
        if (prototype == null)
            prototype = new SynchronousMessageConnection();
        return prototype;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void draw(DrawingContext drawingContext) {
        super.draw(drawingContext);
        drawArrow(drawingContext, calculateRotationInEndPoint2());
    }

    /**
     * Draws the message arrow tip.
     *
     * @param drawingContext    the drawing context
     * @param rotationTransform the rotation transform
     */
    private void drawArrow(DrawingContext drawingContext, AffineTransform rotationTransform) {
        Point2D endpoint = getEndPoint2();
        double x = endpoint.getX(), y = endpoint.getY();
        GeneralPath arrow = new GeneralPath();
        arrow.moveTo(x - 7, y - 3);
        arrow.lineTo(x, y);
        arrow.lineTo(x - 7, y + 3);
        arrow.closePath();
        arrow.transform(rotationTransform);
        drawingContext.draw(arrow, Color.BLACK);
    }
}

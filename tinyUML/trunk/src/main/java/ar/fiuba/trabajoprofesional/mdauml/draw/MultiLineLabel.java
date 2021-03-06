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
package ar.fiuba.trabajoprofesional.mdauml.draw;

import ar.fiuba.trabajoprofesional.mdauml.draw.DrawingContext.FontType;
import ar.fiuba.trabajoprofesional.mdauml.draw.MultilineLayouter.MultilineLayout;

import java.awt.font.TextLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class implements a Label with multiple lines. It is important for
 * performance to implement a dedicated multiline label because layout is
 * a rather slow operation.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class MultiLineLabel extends SimpleLabel {


    private transient MultilineLayout layout;

    public MultilineLayout getLayout() {
        return layout;
    }

    public void setLayout(MultilineLayout layout) {
        this.layout = layout;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void draw(DrawingContext drawingContext) {
        double x = getAbsoluteX1(), y = getAbsoluteY1();
        double layoutWidth = getSize().getWidth();
        double textY = y;
        if (layout == null)
            recalculateSize(drawingContext);
        for (TextLayout line : layout.getLines()) {
            // Set the left position of the text depending on the text layout
            // direction
            double textX = line.isLeftToRight() ? x : layoutWidth - line.getAdvance();
            textY += line.getAscent();
            line.draw(drawingContext.getGraphics2D(), (float) textX, (float) textY);
            // Move text position another step downward
            textY += line.getDescent() + line.getLeading();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override public void recalculateSize(DrawingContext drawingContext) {
        layout = MultilineLayouter.getInstance()
            .calculateLayout(drawingContext.getGraphics2D().getFontRenderContext(),
                drawingContext.getFont(FontType.DEFAULT), getText(), getSize().getWidth());
        setSize(layout.getSize().getWidth(), layout.getSize().getHeight());
        setValid(true);
    }
}

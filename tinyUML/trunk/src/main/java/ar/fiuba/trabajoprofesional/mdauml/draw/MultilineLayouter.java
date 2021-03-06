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

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Dimension2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class lays out a specified text, with multiple lines and paragraphs.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public final class MultilineLayouter {

    private static MultilineLayouter instance = new MultilineLayouter();


    /**
     * Private constructor.
     */
    private MultilineLayouter() {
    }

    /**
     * Returns the layouter instance.
     *
     * @return the singleton instance
     */
    public static MultilineLayouter getInstance() {
        return instance;
    }

    /**
     * Calculates the layout.
     *
     * @param fontRenderContext the FontRenderContext
     * @param font              the Font
     * @param text              the text
     * @param layoutWidth       the width of the layout area
     * @return the layout result
     */
    public MultilineLayout calculateLayout(FontRenderContext fontRenderContext, Font font,
        String text, double layoutWidth) {
        // Layout multiline text
        MultilineLayout result = new MultilineLayout();
        if (text == null || text.isEmpty())
            return result;
        Map<TextAttribute, Object> styleMap = new HashMap<TextAttribute, Object>();
        styleMap.put(TextAttribute.FONT, font);
        String[] textlines = text.split("\n");
        double height = 0;
        for (String textline : textlines) {
            AttributedString attribText = new AttributedString(textline, styleMap);
            AttributedCharacterIterator iter = attribText.getIterator();
            int textStart = iter.getBeginIndex();
            int textEnd = iter.getEndIndex();
            LineBreakMeasurer measurer = new LineBreakMeasurer(iter, fontRenderContext);
            measurer.setPosition(textStart);
            while (measurer.getPosition() < textEnd) {
                TextLayout line = measurer.nextLayout((float) layoutWidth);
                result.addLine(line);
                height += (line.getAscent() + line.getDescent() + line.getLeading());
            }
        }
        result.setSize(layoutWidth, height);
        return result;
    }


    /**
     * Represents the result of a layout operation.
     */
    public static class MultilineLayout  {

        private List<TextLayout> lines = new LinkedList<TextLayout>();
        private Dimension2D size = new DoubleDimension();

        /**
         * Adds a line to the result.
         *
         * @param line the line to add
         */
        public void addLine(TextLayout line) {
            lines.add(line);
        }

        /**
         * Sets the size of the layout.
         *
         * @param width  the width
         * @param height the height
         */
        public void setSize(double width, double height) {
            size = new DoubleDimension(width, height);
        }

        /**
         * Returns the size of the layout.
         *
         * @return the size
         */
        public Dimension2D getSize() {
            return size;
        }

        /**
         * Returns the lines in the layout.
         *
         * @return the lines in the layout
         */
        public List<TextLayout> getLines() {
            return lines;
        }
    }
}

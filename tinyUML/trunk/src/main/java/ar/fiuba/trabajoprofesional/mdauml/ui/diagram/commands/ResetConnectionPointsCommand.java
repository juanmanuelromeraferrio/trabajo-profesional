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
package ar.fiuba.trabajoprofesional.mdauml.ui.diagram.commands;

import ar.fiuba.trabajoprofesional.mdauml.draw.Connection;
import ar.fiuba.trabajoprofesional.mdauml.util.Command;

import javax.swing.undo.AbstractUndoableEdit;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a reversible command to reset a connection's points.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class ResetConnectionPointsCommand extends AbstractUndoableEdit implements Command {

    private DiagramEditorNotification notification;
    private Connection connection;
    private List<Point2D> originalPoints;

    /**
     * Constructor.
     *
     * @param aNotification the notification object
     * @param conn          the connection
     */
    public ResetConnectionPointsCommand(DiagramEditorNotification aNotification, Connection conn) {
        notification = aNotification;
        connection = conn;
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        originalPoints = clonePointList(connection.getPoints());
        connection.resetPoints();
        notification.notifyElementsMoved();
    }

    /**
     * Makes a defensive copy of a point list.
     *
     * @param points the point list to clone
     * @return the cloned point list
     */
    private List<Point2D> clonePointList(List<Point2D> points) {
        List<Point2D> result = new ArrayList<Point2D>();
        for (Point2D point : points) {
            result.add((Point2D) point.clone());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void undo() {
        super.undo();
        connection.setPoints(originalPoints);
        notification.notifyElementsMoved();
    }

    /**
     * {@inheritDoc}
     */
    @Override public void redo() {
        super.redo();
        run();
    }
}

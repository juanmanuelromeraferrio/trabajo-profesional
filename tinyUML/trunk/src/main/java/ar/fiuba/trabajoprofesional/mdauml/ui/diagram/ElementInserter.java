package ar.fiuba.trabajoprofesional.mdauml.ui.diagram;

import ar.fiuba.trabajoprofesional.mdauml.draw.*;
import ar.fiuba.trabajoprofesional.mdauml.exception.AddConnectionException;
import ar.fiuba.trabajoprofesional.mdauml.model.PackageableUmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.model.RelationType;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlPackage;
import ar.fiuba.trabajoprofesional.mdauml.ui.AppFrame;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.commands.AddConnectionCommand;
import ar.fiuba.trabajoprofesional.mdauml.ui.diagram.commands.AddNodeCommand;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.PackageElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlConnection;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlNode;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Class responsible for insert an element into a diagram
 */
public class ElementInserter {
    
    public static void insert(Node element,DiagramEditor editor,Point2D pos){
        DrawingContext drawingContext = editor.getDrawingContext();
        Rectangle clipBounds = new Rectangle();
        Graphics g = AppFrame.get().getGraphics();
        g.getClipBounds(clipBounds);
        drawingContext.setGraphics2D((Graphics2D) g,clipBounds );
        element.recalculateSize(drawingContext);
        Rectangle2D bounds= element.getAbsoluteBounds();
        CompositeNode parent = editor.getDiagram();
        DiagramElement possibleParent =
                editor.getDiagram().getChildAt(pos.getX(), pos.getY());
        if (isNestingCondition(possibleParent,bounds)) {
            parent = (CompositeNode) possibleParent;
        }
        AddNodeCommand createCommand =
                new AddNodeCommand(editor, parent, element, pos.getX(), pos.getY());
        editor.execute(createCommand);
        PackageElement pkgElement = findNestingPackage(element,editor);
        if(pkgElement != null){
            editor.addNestConnectionToParent(element,pkgElement);

        }
        element.invalidate();
    }
    private static boolean isNestingCondition(DiagramElement nester,Rectangle2D bounds) {
        if (bounds == null)
            return false;
        return nester.canNestElements() && nester.getAbsoluteBounds().contains(bounds);
    }

    private static PackageElement findNestingPackage(Node element,DiagramEditor editor) {
        if(element instanceof UmlNode) {
            UmlModelElement model = ((UmlNode) element).getModelElement();
            if(model instanceof PackageableUmlModelElement){
                UmlPackage pkg = ((PackageableUmlModelElement) model).getPackage();
                if(pkg!=null  ){
                    DiagramElement pkgElement = editor.getDiagram().findElementFromModel(pkg);
                    return (PackageElement) pkgElement;
                }
            }

        }
        return null;

    }

    public static void insertConnection(UmlNode source, DiagramElement elem, DiagramEditor editor, RelationType relationType, Point2D anchor, Point2D tmpPos) throws AddConnectionException {
        LineConnectMethod connectMethod = editor.getDiagram().getElementFactory().getConnectMethod(relationType);
        if ( source != null && elem instanceof ConnectionVisitor) {
            ((ConnectionVisitor) elem).validateConnectionAsTarget(relationType, source);

            UmlConnection conn = editor.getDiagram().getElementFactory()
                    .createConnection(relationType, source, (UmlNode) elem);
            connectMethod
                    .generateAndSetPointsToConnection(conn, source, (UmlNode) elem, anchor, tmpPos);
            AddConnectionCommand command =
                    new AddConnectionCommand(editor, editor.getDiagram(), conn);
            editor.execute(command);

        }
    }
}

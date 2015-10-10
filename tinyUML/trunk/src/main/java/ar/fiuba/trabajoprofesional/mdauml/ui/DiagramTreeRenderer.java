package ar.fiuba.trabajoprofesional.mdauml.ui;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.clazz.ClassDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.UseCaseDiagram;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class DiagramTreeRenderer extends DefaultTreeCellRenderer {

    private static final String ACTOR_ICON = "actorNode.png";
    private static final String USECASE_ICON = "usecaseNode.png";
    private static final String USECASE_DIAG_ICON = "usecaseDiagramNode.png";
    private static final String CLASS_DIAG_ICON = "classDiagramNode.png";

    private Icon actorIcon = new ImageIcon(DiagramTreeRenderer.class.getResource(ACTOR_ICON));
    private Icon usecaseIcon = new ImageIcon(DiagramTreeRenderer.class.getResource(USECASE_ICON));
    private Icon usecaseDiagramIcon = new ImageIcon(DiagramTreeRenderer.class.getResource(USECASE_DIAG_ICON));
    private Icon classDiagramIcon = new ImageIcon(DiagramTreeRenderer.class.getResource(CLASS_DIAG_ICON));


    @Override
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value, boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected,expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object object = node.getUserObject();
        if (object instanceof UmlActor ) {
            setIcon(actorIcon);
        } else if (object instanceof UmlUseCase) {
            setIcon(usecaseIcon);
        } else if (object instanceof UseCaseDiagram) {
            setIcon(usecaseDiagramIcon);
        } else if (object instanceof ClassDiagram) {
            setIcon(classDiagramIcon);
        }
        return this;
    }
}

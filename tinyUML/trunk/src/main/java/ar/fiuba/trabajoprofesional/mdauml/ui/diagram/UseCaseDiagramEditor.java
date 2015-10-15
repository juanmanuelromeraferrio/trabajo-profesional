package ar.fiuba.trabajoprofesional.mdauml.ui.diagram;

import ar.fiuba.trabajoprofesional.mdauml.draw.DiagramElement;
import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.ui.AppFrame;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.Association;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.ActorElement;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.usecase.UseCaseElement;
import ar.fiuba.trabajoprofesional.mdauml.util.MethodCall;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a specialized version of a DiagramEditor editing use case diagrams.
 *
 * @author Juan Manuel Romera
 * @version 2.0
 */
public class UseCaseDiagramEditor extends DiagramEditor {

    /**
     *
     */
    private static final long serialVersionUID = 133830961841442513L;
    private static Map<String, MethodCall> selectorMap = new HashMap<String, MethodCall>();

    static {
        initSelectorMap();
    }

    /**
     * Constructor.
     *
     * @param aDiagram the diagram
     */
    public UseCaseDiagramEditor( GeneralDiagram aDiagram) {
        super(aDiagram);
    }

    /**
     * Initializes the selector map.
     */
    private static void initSelectorMap() {
        try {
            selectorMap.put("CREATE_ACTOR", new MethodCall(
                UseCaseDiagramEditor.class.getMethod("setCreationMode", ElementType.class),
                ElementType.ACTOR));

            selectorMap.put("CREATE_USE_CASE", new MethodCall(
                UseCaseDiagramEditor.class.getMethod("setCreationMode", ElementType.class),
                ElementType.USE_CASE));

            selectorMap.put("CREATE_PACKAGE", new MethodCall(
                    UseCaseDiagramEditor.class.getMethod("setCreationMode", ElementType.class),
                    ElementType.PACKAGE));

            selectorMap.put("CREATE_ASSOCIATION", new MethodCall(UseCaseDiagramEditor.class
                .getMethod("setCreateConnectionMode", RelationType.class),
                RelationType.ASSOCIATION));

            selectorMap.put("CREATE_INHERITANCE", new MethodCall(UseCaseDiagramEditor.class
                    .getMethod("setCreateConnectionMode", RelationType.class),
                    RelationType.INHERITANCE));

            selectorMap.put("CREATE_EXTEND", new MethodCall(UseCaseDiagramEditor.class
                    .getMethod("setCreateConnectionMode", RelationType.class),
                    RelationType.EXTEND));

            selectorMap.put("CREATE_INCLUDE", new MethodCall(UseCaseDiagramEditor.class
                    .getMethod("setCreateConnectionMode", RelationType.class),
                    RelationType.INCLUDE));


        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void editProperties(DiagramElement element) {
        Window window =(Window) AppFrame.get();
        if (element instanceof Association) {
            Association association = (Association) element;
            EditAssociationDialog dialog = new EditAssociationDialog(window, association, true);
            dialog.setLocationRelativeTo(AppFrame.get());
            dialog.setVisible(true);
            redraw();
        } else if (element instanceof ActorElement) {
            ActorElement actorElement = (ActorElement) element;
            UmlActor umlActor = (UmlActor) actorElement.getModelElement();
            EditActorDialog dialog = new EditActorDialog(window, actorElement, true);
            dialog.setLocationRelativeTo(AppFrame.get());
            dialog.setVisible(true);
            if (dialog.isOk()) {
                umlActor.setName(dialog.getName());
                umlActor.setDescription(dialog.getDescription());
                redraw();
            }

        } else if (element instanceof UseCaseElement) {
            UseCaseElement useCaseElement = (UseCaseElement) element;
            UmlUseCase umlUseCase = (UmlUseCase) useCaseElement.getModelElement();
            EditUseCaseDialog dialog = new EditUseCaseDialog(window, useCaseElement, true);
            dialog.setLocationRelativeTo(AppFrame.get());
            dialog.setVisible(true);
            if (dialog.isOk()) {
                umlUseCase.setName(dialog.getName());
                umlUseCase.setDescription(dialog.getDescription());
                umlUseCase.setMainActors(dialog.getMainActors());
                umlUseCase.setSecondaryActors(dialog.getSecondaryActors());
                umlUseCase.setPreconditions(dialog.getPreconditions());
                umlUseCase.setPostconditions(dialog.getPostconditions());
                umlUseCase.setMainFLow(dialog.getMainFlow());
                redraw();

            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override public void handleCommand(String command) {
        MethodCall methodcall = selectorMap.get(command);
        if (methodcall != null)
            methodcall.call(this);
        else
            super.handleCommand(command);
    }
}

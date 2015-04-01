package ar.fiuba.trabajoprofesional.mdauml.ui.diagram;

import ar.fiuba.trabajoprofesional.mdauml.draw.DiagramElement;
import ar.fiuba.trabajoprofesional.mdauml.model.ElementType;
import ar.fiuba.trabajoprofesional.mdauml.model.RelationType;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.structure.Association;
import ar.fiuba.trabajoprofesional.mdauml.util.MethodCall;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a specialized version of a DiagramEditor editing use case diagrams.
 *
 * @author Juan Manuel Romera
 * @version 1.0
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
   * Initializes the selector map.
   */
  private static void initSelectorMap() {
    try {
      selectorMap.put("CREATE_ACTOR",
          new MethodCall(
              UseCaseDiagramEditor.class.getMethod("setCreationMode", ElementType.class),
              ElementType.ACTOR));

      selectorMap.put("CREATE_USE_CASE",
          new MethodCall(
              UseCaseDiagramEditor.class.getMethod("setCreationMode", ElementType.class),
              ElementType.USE_CASE));

      selectorMap.put(
          "CREATE_ASSOCIATION",
          new MethodCall(StructureDiagramEditor.class.getMethod("setCreateConnectionMode",
              RelationType.class), RelationType.ASSOCIATION));

    } catch (NoSuchMethodException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Constructor.
   *
   * @param aWindow the main window
   * @param aDiagram the diagram
   */
  public UseCaseDiagramEditor(Component aWindow, GeneralDiagram aDiagram) {
    super(aWindow, aDiagram);
  }

  /**
   * {@inheritDoc}
   */
  public void editProperties(DiagramElement element) {
    Window window = (mainWindow instanceof Window) ? ((Window) mainWindow) : null;
    if (element instanceof Association) {
      Association association = (Association) element;
      EditAssociationDialog dialog = new EditAssociationDialog(window, association, true);
      dialog.setLocationRelativeTo(mainWindow);
      dialog.setVisible(true);
      redraw();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleCommand(String command) {
    MethodCall methodcall = selectorMap.get(command);
    if (methodcall != null)
      methodcall.call(this);
    else
      super.handleCommand(command);
  }
}

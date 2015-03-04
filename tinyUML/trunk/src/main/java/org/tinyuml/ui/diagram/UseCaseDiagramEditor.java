package org.tinyuml.ui.diagram;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import org.tinyuml.draw.DiagramElement;
import org.tinyuml.model.ElementType;
import org.tinyuml.model.RelationType;
import org.tinyuml.umldraw.shared.GeneralDiagram;
import org.tinyuml.util.MethodCall;

/**
 * This class is a specialized version of a DiagramEditor editing use case
 * diagrams.
 *
 * @author Wei-ju Wu
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
			selectorMap.put(
					"CREATE_LIFELINE",
					new MethodCall(UseCaseDiagramEditor.class.getMethod(
							"setCreationMode", ElementType.class),
							ElementType.LIFE_LINE));
			selectorMap.put(
					"CREATE_MESSAGE",
					new MethodCall(UseCaseDiagramEditor.class.getMethod(
							"setCreateConnectionMode", RelationType.class),
							RelationType.MESSAGE));
		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param aWindow
	 *            the main window
	 * @param aDiagram
	 *            the diagram
	 */
	public UseCaseDiagramEditor(Component aWindow, GeneralDiagram aDiagram) {
		super(aWindow, aDiagram);
	}

	/**
	 * {@inheritDoc}
	 */
	public void editProperties(DiagramElement element) {
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

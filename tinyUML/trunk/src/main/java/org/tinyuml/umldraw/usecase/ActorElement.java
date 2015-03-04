package org.tinyuml.umldraw.usecase;

import org.tinyuml.draw.AbstractCompositeNode;
import org.tinyuml.draw.DrawingContext;
import org.tinyuml.draw.DrawingContext.FontType;
import org.tinyuml.draw.Label;
import org.tinyuml.draw.LabelSource;
import org.tinyuml.draw.SimpleLabel;
import org.tinyuml.model.RelationEndType;
import org.tinyuml.model.RelationType;
import org.tinyuml.model.UmlClass;
import org.tinyuml.model.UmlModelElement;
import org.tinyuml.model.UmlModelElementListener;
import org.tinyuml.umldraw.shared.UmlNode;

/**
 * This class represents a Actor element in the editor. It is responsible for
 * rendering the information in the editor.
 *
 * @author Juan Manuel Romera
 * @version 1.0
 */
public final class ActorElement extends AbstractCompositeNode implements
		LabelSource, UmlNode, UmlModelElementListener {

	private static final long serialVersionUID = 8767029215902619069L;
	private UmlClass classData;
	private Label mainLabel;

	private static ActorElement prototype;

	/**
	 * Returns the prototype instance.
	 * 
	 * @return the prototype instance
	 */
	public static ActorElement getPrototype() {
		if (prototype == null)
			prototype = new ActorElement();
		return prototype;
	}

	/**
	 * Private constructor.
	 */
	private ActorElement() {
		mainLabel = new SimpleLabel();
		mainLabel.setSource(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clone() {
		ActorElement cloned = (ActorElement) super.clone();
		if (classData != null) {
			cloned.classData = (UmlClass) classData.clone();
			cloned.classData.addModelElementListener(cloned);
		}
		cloned.mainLabel = (Label) mainLabel.clone();
		cloned.mainLabel.setSource(cloned);
		return cloned;
	}

	/**
	 * Returns the main label for testing purposes.
	 * 
	 * @return the main label
	 */
	public Label getMainLabel() {
		return mainLabel;
	}

	/**
	 * Sets the model element.
	 * 
	 * @param aModelElement
	 *            the model element
	 */
	public void setModelElement(UmlClass aModelElement) {
		classData = aModelElement;
		if (classData != null) {
			classData.addModelElementListener(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public UmlModelElement getModelElement() {
		return classData;
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
	@Override
	public void draw(DrawingContext drawingContext) {
		if (!isValid()) {
			mainLabel.setFontType(getMainLabelFontType());
			recalculateSize(drawingContext);
		}

	}

	/**
	 * Returns the main label font type. Dependent whether the class is abstract
	 * or not.
	 * 
	 * @return the FontType for the main label
	 */
	private FontType getMainLabelFontType() {
		return (classData.isAbstract()) ? FontType.ABSTRACT_ELEMENT
				: FontType.ELEMENT_NAME;
	}

	@Override
	public Label getLabelAt(double mx, double my) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void elementChanged(UmlModelElement element) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean acceptsConnection(RelationType associationType,
			RelationEndType as, UmlNode with) {
		// TODO Auto-generated method stub
		return false;
	}
}

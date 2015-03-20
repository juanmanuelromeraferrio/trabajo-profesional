package org.tinyuml.umldraw.usecase;

import java.awt.Color;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.tinyuml.draw.AbstractCompositeNode;
import org.tinyuml.draw.DoubleDimension;
import org.tinyuml.draw.DrawingContext;
import org.tinyuml.draw.Label;
import org.tinyuml.draw.LabelSource;
import org.tinyuml.draw.SimpleLabel;
import org.tinyuml.model.RelationEndType;
import org.tinyuml.model.RelationType;
import org.tinyuml.model.UmlActor;
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

	private static final double DEFAULT_HEIGHT = 70;
	private static final double DEFAULT_WIDHT = 40;
	private static final double LABEL_HEIGHT = 16;

	private static final Color BACKGROUND = Color.WHITE;

	private UmlActor actor;
	private Label label;

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
		setSize(DEFAULT_WIDHT, DEFAULT_HEIGHT);
		label = new SimpleLabel();
		label.setSource(this);
		label.setParent(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clone() {
		ActorElement cloned = (ActorElement) super.clone();
		cloned.label = (Label) label.clone();
		cloned.label.setSource(cloned);
		cloned.label.setParent(cloned);

		if (actor != null) {
			cloned.actor = (UmlActor) actor.clone();
		}

		return cloned;

	}

	/**
	 * Returns the main label for testing purposes.
	 * 
	 * @return the main label
	 */
	public Label getMainLabel() {
		return label;
	}

	/**
	 * Sets the model element.
	 * 
	 * @param actor
	 *            the model element
	 */
	public void setModelElement(UmlActor actor) {
		this.actor = actor;
	}

	/**
	 * {@inheritDoc}
	 */
	public UmlModelElement getModelElement() {
		return actor;
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
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tinyuml.draw.AbstractCompositeNode#draw(org.tinyuml.draw.DrawingContext
	 * )
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tinyuml.draw.AbstractCompositeNode#draw(org.tinyuml.draw.DrawingContext
	 * )
	 */
	@Override
	public void draw(DrawingContext drawingContext) {

		double width = getSize().getWidth(), height = getSize().getHeight();
		double x = getAbsoluteX1(), y = getAbsoluteY1();
		double figureHeight = height - LABEL_HEIGHT;

		label.recalculateSize(drawingContext);

		double xLabelOrigin = (width / 2) - (label.getSize().getWidth() / 2);
		double yLabelOrigin = figureHeight;

		label.setOrigin(xLabelOrigin, yLabelOrigin);
		label.draw(drawingContext);

		// Drawing Head
		Ellipse2D head = new Ellipse2D.Double();
		double headDiameter = figureHeight / 3; // The diameter of the head is
												// 1/3 of the
		// figure height.
		double headRadius = headDiameter / 2;
		double xHeadOrigin = x + (width / 2) - headRadius;
		Point2D headOrigin = new Point2D.Double(xHeadOrigin, y);
		Dimension2D headDimension = new DoubleDimension(headDiameter,
				headDiameter);
		head.setFrame(headOrigin, headDimension);
		drawingContext.draw(head, BACKGROUND);

		// Drawing Body
		Line2D body = new Line2D.Double();
		double bodyStartX = x + (width / 2);
		double bodyStartY = y + (figureHeight / 3);
		Point2D bodyOrigin = new Point2D.Double(bodyStartX, bodyStartY);
		double bodyEndX = bodyStartX;
		double bodyEndY = y + (figureHeight * 2 / 3);
		Point2D bodyEnd = new Point2D.Double(bodyEndX, bodyEndY);
		body.setLine(bodyOrigin, bodyEnd);
		drawingContext.draw(body, BACKGROUND);

		// Drawing Arms
		Line2D arms = new Line2D.Double();
		double armsStartX = x + (0.15 * width);
		double armsStartY = y + (figureHeight * 4 / 9);
		Point2D armsOrigin = new Point2D.Double(armsStartX, armsStartY);
		double armsEndX = x + (0.85 * width);
		double armsEndY = armsStartY;
		Point2D armsEnd = new Point2D.Double(armsEndX, armsEndY);
		arms.setLine(armsOrigin, armsEnd);
		drawingContext.draw(arms, BACKGROUND);

		// Drawing Left Leg
		Line2D leftLeg = new Line2D.Double();
		double leftLegStartX = x + (width / 2);
		double leftLegStartY = y + (figureHeight * 2 / 3);
		Point2D leftOrigin = new Point2D.Double(leftLegStartX, leftLegStartY);
		double leftLegEndX = x + (0.15 * width);
		double leftLegEndY = y + figureHeight;
		Point2D leftLegEnd = new Point2D.Double(leftLegEndX, leftLegEndY);
		leftLeg.setLine(leftOrigin, leftLegEnd);
		drawingContext.draw(leftLeg, BACKGROUND);

		// Drawing Right Leg
		Line2D rightLeg = new Line2D.Double();
		double rightLegStartX = x + (width / 2);
		double rightLegStartY = y + (figureHeight * 2 / 3);
		Point2D rightOrigin = new Point2D.Double(rightLegStartX, rightLegStartY);
		double rightLegEndX = x + (0.85 * width);
		double rightLegEndY = y + figureHeight;
		Point2D rightLegEnd = new Point2D.Double(rightLegEndX, rightLegEndY);
		rightLeg.setLine(rightOrigin, rightLegEnd);
		drawingContext.draw(rightLeg, BACKGROUND);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid() {
		return label.isValid();
	}

	@Override
	public Label getLabelAt(double mx, double my) {
		if (inInnerArea(mx, my))
			return label;
		return null;
	}

	/**
	 * Returns true if the specified point is in the inner Note area. It keeps
	 * the margins from reacting to mouse clicks in order to improve usability.
	 * 
	 * @param mx
	 *            the mapped mouse x position
	 * @param my
	 *            the mapped mouse y position
	 * @return true if in label area, false otherwise
	 */
	private boolean inInnerArea(double mx, double my) {
		double figureHeight = getSize().getHeight() - LABEL_HEIGHT;

		return mx >= (getAbsoluteX1()) && mx <= (getAbsoluteX2())
				&& my >= (getAbsoluteY1() + figureHeight)
				&& my <= (getAbsoluteY2());
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

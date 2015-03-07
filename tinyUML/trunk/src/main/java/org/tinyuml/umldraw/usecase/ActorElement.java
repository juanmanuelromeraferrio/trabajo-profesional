package org.tinyuml.umldraw.usecase;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import org.tinyuml.draw.AbstractCompositeNode;
import org.tinyuml.draw.Compartment;
import org.tinyuml.draw.DrawingContext;
import org.tinyuml.draw.DrawingContext.FontType;
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
 * This class represents a Actor element in the editor. It is responsible for rendering the
 * information in the editor.
 *
 * @author Juan Manuel Romera
 * @version 1.0
 */
public final class ActorElement extends AbstractCompositeNode implements LabelSource, UmlNode,
    UmlModelElementListener {

  private static final long serialVersionUID = 8767029215902619069L;
  private static final double FACE_HEIGHT = 16;
  private static final double FACE_WIDTH = 16;
  private static final double BODY_SIZE = 15;
  private static final double LEG_SIZE = 10;
  private static final double ARM_HEIGHT = 5;
  private static final double ARM_WIDTH = 12;
  private static final Color BACKGROUND = Color.WHITE;

  private UmlActor actor;

  private Compartment mainCompartment;
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

    mainCompartment = new Compartment();
    mainCompartment.setParent(this);
    mainCompartment.setBackground(BACKGROUND);
    mainLabel = new SimpleLabel();
    mainLabel.setSource(this);
    mainLabel.setParent(this);
    mainLabel.setFontType(FontType.ELEMENT_NAME);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object clone() {
    ActorElement cloned = (ActorElement) super.clone();
    cloned.mainLabel = (Label) mainLabel.clone();
    cloned.mainLabel.setSource(cloned);
    cloned.mainCompartment = (Compartment) mainCompartment.clone();
    cloned.mainCompartment.setParent(cloned);
    cloned.mainCompartment.removeAllLabels();
    cloned.mainCompartment.addLabel(cloned.mainLabel);
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
    return mainLabel;
  }

  /**
   * Sets the model element.
   * 
   * @param actor the model element
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
  @Override
  public void draw(DrawingContext drawingContext) {
    if (!isValid()) {
      recalculateSize(drawingContext);
    }
    Ellipse2D face = new Ellipse2D.Float();
    double x = getAbsoluteX1(), y = getAbsoluteY1();
    face.setFrame(x, y, FACE_WIDTH, FACE_HEIGHT);
    drawingContext.draw(face, BACKGROUND);

    Line2D body = new Line2D.Float();
    double bodyStartPointX = x + FACE_WIDTH / 2;
    double bodyStartPointY = y + FACE_HEIGHT;
    double bodyEndPointX = bodyStartPointX;
    double bodyEndPointY = bodyStartPointY + BODY_SIZE;
    body.setLine(bodyStartPointX, bodyStartPointY, bodyEndPointX, bodyEndPointY);
    drawingContext.draw(body, BACKGROUND);

    Line2D leftLeg = new Line2D.Float();
    double leftLegStartPointX = bodyEndPointX;
    double leftLegStartPointY = bodyEndPointY;
    double leftLegEndPointX = leftLegStartPointX - LEG_SIZE;
    double leftLegEndPointY = leftLegStartPointY + LEG_SIZE;
    leftLeg.setLine(leftLegStartPointX, leftLegStartPointY, leftLegEndPointX, leftLegEndPointY);
    drawingContext.draw(leftLeg, BACKGROUND);

    Line2D rightLeg = new Line2D.Float();
    double rightLegStartPointX = bodyEndPointX;
    double rightLegStartPointY = bodyEndPointY;
    double rightLegEndPointX = rightLegStartPointX + LEG_SIZE;
    double rightLegEndPointY = rightLegStartPointY + LEG_SIZE;
    rightLeg
        .setLine(rightLegStartPointX, rightLegStartPointY, rightLegEndPointX, rightLegEndPointY);
    drawingContext.draw(rightLeg, BACKGROUND);

    Line2D leftArm = new Line2D.Float();
    double leftArmStartPointX = bodyStartPointX;
    double leftArmStartPointY = bodyStartPointY;
    double leftArmEndPointX = leftArmStartPointX - ARM_WIDTH;
    double leftArmEndPointY = leftArmStartPointY + ARM_HEIGHT;
    leftArm.setLine(leftArmStartPointX, leftArmStartPointY, leftArmEndPointX, leftArmEndPointY);
    drawingContext.draw(leftArm, BACKGROUND);

    Line2D rightArm = new Line2D.Float();
    double rightArmStartPointX = bodyStartPointX;
    double rightArmStartPointY = bodyStartPointY;
    double rightArmEndPointX = rightArmStartPointX + ARM_WIDTH;
    double rightArmEndPointY = rightArmStartPointY + ARM_HEIGHT;
    rightArm
        .setLine(rightArmStartPointX, rightArmStartPointY, rightArmEndPointX, rightArmEndPointY);
    drawingContext.draw(rightArm, BACKGROUND);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid() {
    return mainCompartment.isValid();
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
  public boolean acceptsConnection(RelationType associationType, RelationEndType as, UmlNode with) {
    // TODO Auto-generated method stub
    return false;
  }
}

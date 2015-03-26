package org.tinyuml.umldraw.structure;

import org.tinyuml.draw.SimpleConnection;

public class SimpleAssociation extends Association {


  /**
   * 
   */
  private static final long serialVersionUID = 600036367463923156L;
  private static Association prototype;

  /**
   * Returns the prototype instance.
   * 
   * @return the prototype instance
   */
  public static Association getPrototype() {
    if (prototype == null)
      prototype = new SimpleAssociation();
    return prototype;
  }

  /**
   * Constructor.
   */
  private SimpleAssociation() {
    setConnection(new SimpleConnection());
    setupMultiplicityLabels();
    setupNameLabel();
  }
}

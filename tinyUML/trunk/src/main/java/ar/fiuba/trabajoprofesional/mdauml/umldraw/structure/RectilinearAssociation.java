package ar.fiuba.trabajoprofesional.mdauml.umldraw.structure;

import ar.fiuba.trabajoprofesional.mdauml.draw.RectilinearConnection;

public class RectilinearAssociation extends Association {

  /**
   *
   */
  private static final long serialVersionUID = -3385959195759520249L;
  private static Association prototype;

  /**
   * Returns the prototype instance.
   *
   * @return the prototype instance
   */
  public static Association getPrototype() {
    if (prototype == null)
      prototype = new RectilinearAssociation();
    return prototype;
  }

  /**
   * Constructor.
   */
  private RectilinearAssociation() {
    setConnection(new RectilinearConnection());
    setupMultiplicityLabels();
    setupNameLabel();
  }

}

package ar.fiuba.trabajoprofesional.mdauml.persistence;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelImpl;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;
import ar.fiuba.trabajoprofesional.mdauml.util.ApplicationResources;

public class XmlParser {



  protected DocumentBuilder docBuilder;
  protected Element root;

  protected Project project;

  private ModelXmlParser modelXmlParser;
  private ViewXmlParser viewXmlParser;

  public XmlParser() throws Exception {
    this.docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
  }

  public XmlParser(Project project) throws Exception {
    this.docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    this.project = project;
    this.modelXmlParser = new ModelXmlParser(project);
    this.viewXmlParser = new ViewXmlParser(project);
  }

  public XmlParser(File projecFile) throws Exception {
    this();
    this.root = docBuilder.parse(projecFile).getDocumentElement();
    this.project = new Project(new UmlModelImpl());
    this.init(projecFile);
  }

  /**
   * Reads the project file and load the model and view files.
   */
  private void init(File projecFile) throws Exception {
    if (!this.validateFormat(this.root))
      throw new RuntimeException(ApplicationResources.getInstance().getString(
          "error.invalidformat.projectfile"));

    String dir = projecFile.getParent() + File.separator;

    String modelPath = XmlHelper.querySingle(this.root, "./" + Constants.MODEL_TAG).getTextContent();
    String viewPath = XmlHelper.querySingle(this.root, "./" + Constants.VIEW_TAG).getTextContent();

    this.modelXmlParser = new ModelXmlParser(this.project, dir + modelPath);
    this.viewXmlParser = new ViewXmlParser(this.project, dir + viewPath);
  }

  private boolean validateFormat(Element root2) {
    if (XmlHelper.querySingle(root, "./" + Constants.MODEL_TAG) != null
        && XmlHelper.querySingle(root, "./" + Constants.VIEW_TAG) != null)
      return true;

    return false;
  }
  
  public Project parse() throws Exception {
    this.modelXmlParser.parse();
    this.viewXmlParser.parse();
    return this.project;
}
  public Document generateProjectXml(String path) throws DOMException, Exception {
    Document doc = this.docBuilder.newDocument();
    this.root = doc.createElement(Constants.PROJECT_TAG);
    doc.appendChild(this.root);

    Element model = XmlHelper.getNewElement(root,Constants.MODEL_TAG);
    Element view = XmlHelper.getNewElement(root,Constants.VIEW_TAG);
    model.setTextContent(path + Constants.MODEL_EXTENTION);
    view.setTextContent(path + Constants.VIEW_EXTENTION);

    this.root.appendChild(model);
    this.root.appendChild(view);

    return doc;
}
  public Document generateModelXml() throws DOMException, Exception {
    return this.modelXmlParser.generateXml();
}
  public Document generateViewXml() throws DOMException, Exception {
    return this.viewXmlParser.generateXml();
}

}

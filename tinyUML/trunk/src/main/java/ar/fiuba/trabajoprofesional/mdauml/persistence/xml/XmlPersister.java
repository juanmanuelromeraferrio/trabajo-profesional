package ar.fiuba.trabajoprofesional.mdauml.persistence.xml;

import java.io.File;
import java.util.regex.Pattern;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import ar.fiuba.trabajoprofesional.mdauml.exception.ProjectPersisterException;
import ar.fiuba.trabajoprofesional.mdauml.exception.XmlPersisterException;
import ar.fiuba.trabajoprofesional.mdauml.persistence.Constants;
import ar.fiuba.trabajoprofesional.mdauml.persistence.ProjectPersister;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.parser.ProjectXmlParser;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;
import ar.fiuba.trabajoprofesional.mdauml.util.ApplicationResources;

public class XmlPersister implements ProjectPersister {

  private static XmlPersister instance = new XmlPersister();

  private XmlPersister() {};

  public static XmlPersister getInstace() {
    return instance;
  }

  @Override
  public void save(Project project, String path) throws ProjectPersisterException {
    ProjectXmlParser parser;
    try {

      String projectPath = getProjectPath(path);
      String modelPath = getModelPath(path);
      String viewPath = getViewPath(path);
      String pathWithoutExtention = getPathWithoutExtention(path);

      parser = new ProjectXmlParser(pathWithoutExtention);

      this.saveXml(parser.generateXml(project), projectPath);
      this.saveXml(parser.getModelXml(), modelPath);
      //this.saveXml(parser.getViewXml(), viewPath);

    } catch (Exception e) {
      String msg = ApplicationResources.getInstance().getString("error.saveproject.message");
      throw new XmlPersisterException(msg + " \"" + path + "\".", e);
    }
  }

  public String getViewPath(String path) {
    return getPathWithoutExtention(path) + Constants.VIEW_EXTENTION;
  }

  public String getModelPath(String path) {
    return getPathWithoutExtention(path) + Constants.MODEL_EXTENTION;
  }

  public String getProjectPath(String path) {
    return getPathWithoutExtention(path) + Constants.PROJECT_EXTENTION;
  }

  public String getPathWithoutExtention(String path) {
    Pattern projectPattern = Pattern.compile("(.*)\\" + Constants.PROJECT_EXTENTION);
    Pattern modelPattern = Pattern.compile("(.*)\\" + Constants.MODEL_EXTENTION);
    Pattern viewPattern = Pattern.compile("(.*)\\" + Constants.VIEW_EXTENTION);
    String pathWithoutExtention = path;
    if (projectPattern.matcher(path).matches())
      pathWithoutExtention = projectPattern.matcher(path).replaceFirst("$1");
    if (modelPattern.matcher(path).matches())
      pathWithoutExtention = modelPattern.matcher(path).replaceFirst("$1");
    if (viewPattern.matcher(path).matches())
      pathWithoutExtention = viewPattern.matcher(path).replaceFirst("$1");
    return pathWithoutExtention;
  }



  private void saveXml(Document doc, String path) throws Exception {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();

    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File(path));
    transformer.transform(source, result);
  }

  @Override
  public Project load(String path) throws ProjectPersisterException {
    try {
      String projectPath = getProjectPath(path);
      File projectFile = new File(projectPath);
      ProjectXmlParser parser = new ProjectXmlParser();
      Project project = parser.parse(projectFile);
      return project;
    } catch (Exception e) {
      String msg = ApplicationResources.getInstance().getString("error.loadproject.message");
      throw new XmlPersisterException(msg + " \"" + path + "\".", e);
    }

  }



}

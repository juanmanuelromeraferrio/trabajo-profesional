package ar.fiuba.trabajoprofesional.mdauml.persistence;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public class XmlPersister implements Persister{
  

  
  private Project project;
  
  public XmlPersister(Project project) {
    this.project=project;
  }

  @Override
  public void save(String path) throws Exception {
    File file = new File(path);
    String dir = file.getParent() + File.separator;

    XmlParser parser;
    try {
      parser = new XmlParser(this.project);
        this.saveXml(parser.generateProjectXml(path), path + Constants.PROJECT_EXTENTION);
        this.saveXml(parser.generateModelXml(), path + Constants.MODEL_EXTENTION);
        this.saveXml(parser.generateViewXml(),path + Constants.VIEW_EXTENTION);
    } catch (Exception e) {
        
        e.printStackTrace();
    }    
  }
  private void saveXml(Document doc, String path) throws Exception {
    TransformerFactory transformerFactory = TransformerFactory
            .newInstance();
    Transformer transformer = transformerFactory.newTransformer();

    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(
            "{http://xml.apache.org/xslt}indent-amount", "4");
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File(path));
    transformer.transform(source, result);
}

  @Override
  public void load(String path) throws Exception {
    // TODO Auto-generated method stub
    
  }
  
  
   

}

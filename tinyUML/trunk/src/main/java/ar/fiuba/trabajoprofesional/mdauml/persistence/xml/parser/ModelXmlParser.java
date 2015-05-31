package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.parser;

import java.io.File;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ar.fiuba.trabajoprofesional.mdauml.model.NamedElement;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlActor;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.persistence.Constants;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable.UmlActorXmlizable;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.xmlizable.Xmlizable;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlNode;

public class ModelXmlParser implements XmlParser {

    private Project project;

    public ModelXmlParser() {
    }

    public ModelXmlParser(Project project) {
        this.project = project;
    }

    @Override public Document generateXml(Project project) throws Exception {
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement(Constants.MODEL_TAG);
        doc.appendChild(root);
        Set<UmlModelElement> elements = project.getModel().getElements();
        for (NamedElement element : elements) {
            Xmlizable elementXmlizable = convertToXmlizable(element);
            Node elementNode = elementXmlizable.toXml(root);
            root.appendChild(elementNode);
        }
        return doc;
    }

    private Xmlizable convertToXmlizable(NamedElement element) throws Exception {
        if (element instanceof UmlActor) {
            return new UmlActorXmlizable((UmlActor) element);
        }
        throw new Exception("Nonexistent Xmlizable for: " + element.toString());
    }

    @Override public Project parse(File file) throws Exception {
        UmlModel model = project.getModel();
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Element root = docBuilder.parse(file).getDocumentElement();
        Element modelElement = XmlHelper.querySingle(root, "./" + Constants.MODEL_TAG);
        NodeList modelElements = modelElement.getChildNodes();
        for (int i = 0; i < modelElements.getLength(); i++) {
            if (modelElements.item(i) instanceof Element) {
                Element element = (Element) modelElements.item(i);
                Xmlizable xmlizable = getXmlizableOfTag(element.getTagName());
                Object instance = xmlizable.fromXml(element);
                if (instance instanceof UmlModelElement)
                    model.addElement((UmlModelElement) instance);

            }
        }
        return project;
    }

    private Xmlizable getXmlizableOfTag(String tagName) throws Exception {
        if (tagName.equals(UmlActorXmlizable.CLASS_TAG)) {
            return new UmlActorXmlizable();
        }
        throw new Exception("Nonexistent Xmlizable for: " + tagName);
    }

}

package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.parser;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelImpl;
import ar.fiuba.trabajoprofesional.mdauml.persistence.Constants;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;
import ar.fiuba.trabajoprofesional.mdauml.util.ApplicationResources;

public class ProjectXmlParser implements XmlParser {

    private String path;
    private Document modelXml;
    private Document viewXml;

    public ProjectXmlParser() throws Exception {
        super();
    }

    public ProjectXmlParser(String pathWithoutExtention) throws Exception {
        super();
        this.path = pathWithoutExtention;

    }

    @Override public Project parse(File projecFile) throws Exception {
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Element root = docBuilder.parse(projecFile).getDocumentElement();
        Project project = new Project(new UmlModelImpl());

        if (!this.validateFormat(root))
            throw new RuntimeException(
                ApplicationResources.getInstance().getString("error.invalidformat.projectfile"));

        String modelPath = XmlHelper.querySingle(root, "./" + Constants.MODEL_TAG).getTextContent();
        String viewPath = XmlHelper.querySingle(root, "./" + Constants.VIEW_TAG).getTextContent();

        ModelXmlParser modelXmlParser = new ModelXmlParser(project);
        ViewXmlParser viewXmlParser = new ViewXmlParser(project);
        modelXmlParser.parse(new File(modelPath));
        viewXmlParser.parse(new File(viewPath));
        return project;
    }

    private boolean validateFormat(Element root) {
        if (XmlHelper.querySingle(root, "./" + Constants.MODEL_TAG) != null
            && XmlHelper.querySingle(root, "./" + Constants.VIEW_TAG) != null)
            return true;

        return false;
    }

    @Override public Document generateXml(Project project) throws DOMException, Exception {
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement(Constants.PROJECT_TAG);
        doc.appendChild(root);

        Element model = XmlHelper.getNewElement(root, Constants.MODEL_TAG);
        Element view = XmlHelper.getNewElement(root, Constants.VIEW_TAG);
        model.setTextContent(path + Constants.MODEL_EXTENTION);
        view.setTextContent(path + Constants.VIEW_EXTENTION);

        root.appendChild(model);
        root.appendChild(view);

        ModelXmlParser modelXmlParser = new ModelXmlParser();
        this.modelXml = modelXmlParser.generateXml(project);

        ViewXmlParser viewXmlParser = new ViewXmlParser();
        this.viewXml = viewXmlParser.generateXml(project);

        return doc;
    }

    public Document getModelXml() {
        return modelXml;
    }

    public Document getViewXml() {
        return viewXml;
    }



}

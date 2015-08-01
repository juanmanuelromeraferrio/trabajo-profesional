package ar.fiuba.trabajoprofesional.mdauml.persistence.xml.serializer;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ar.fiuba.trabajoprofesional.mdauml.exception.ProjectSerializerException;
import ar.fiuba.trabajoprofesional.mdauml.exception.XmlPersisterException;
import ar.fiuba.trabajoprofesional.mdauml.persistence.Registerer;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelImpl;
import ar.fiuba.trabajoprofesional.mdauml.persistence.Constants;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;
import ar.fiuba.trabajoprofesional.mdauml.util.ApplicationResources;

public class ProjectXmlSerializer implements XmlSerializer {

    private static ProjectXmlSerializer instance = new ProjectXmlSerializer();

    private ProjectXmlSerializer() {
    }

    public static ProjectXmlSerializer getInstace() {
        return instance;
    }


    @Override public Object read(String path) throws ProjectSerializerException {
        try {

        Project project = new Project(new UmlModelImpl());
            String projectPath = getProjectPath(path);

            XMLDecoder decoder =
                new XMLDecoder(new BufferedInputStream(new FileInputStream(projectPath)));
            ProjectXml projectXml = (ProjectXml) decoder.readObject();
            decoder.close();

            ModelXmlSerializer modelXmlSerializer = new ModelXmlSerializer();
            ModelXml modelXml = (ModelXml) modelXmlSerializer.read(projectXml.getModelPath());

            ViewXmlSerializer viewXmlSerializer = new ViewXmlSerializer();
            ViewXml viewXml = (ViewXml) viewXmlSerializer.read(projectXml.getViewPath());

            //project.getModel().ad




        return project;
        } catch (Exception e) {
            String msg = ApplicationResources.getInstance().getString("error.loadproject.message");
            throw new ProjectSerializerException(msg + " \"" + path + "\".", e);
    }
    }

    private boolean validateFormat(Element root) {
        if (XmlHelper.querySingle(root, "./" + Constants.MODEL_TAG) != null
            && XmlHelper.querySingle(root, "./" + Constants.VIEW_TAG) != null)
            return true;

        return false;
    }

    @Override public void write(Project project, String path) throws ProjectSerializerException {
        try {

            String projectPath = getProjectPath(path);
            String modelPath = getModelPath(path);
            String viewPath = getViewPath(path);

            ProjectXml projectXml = new ProjectXml();
            projectXml.setModelPath(modelPath);
            projectXml.setViewPath(viewPath);

            FileOutputStream fos = new FileOutputStream(projectPath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            XMLEncoder xmlEncoder = new XMLEncoder(bos);
            xmlEncoder.writeObject(projectXml);
            xmlEncoder.close();

            Registerer.clean();
            ModelXmlSerializer modelXmlSerializer = new ModelXmlSerializer();
            modelXmlSerializer.write(project, modelPath);

            ViewXmlSerializer viewXmlSerializer = new ViewXmlSerializer();
            viewXmlSerializer.write(project, viewPath);

        } catch (Exception e) {
            String msg = ApplicationResources.getInstance().getString("error.saveproject.message");
            throw new ProjectSerializerException(msg + " \"" + path + "\".", e);
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



}

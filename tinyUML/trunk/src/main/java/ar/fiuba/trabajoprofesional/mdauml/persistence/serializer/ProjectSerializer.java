package ar.fiuba.trabajoprofesional.mdauml.persistence.serializer;

import java.beans.XMLDecoder;
import java.io.*;
import java.util.regex.Pattern;

import ar.fiuba.trabajoprofesional.mdauml.draw.*;
import ar.fiuba.trabajoprofesional.mdauml.exception.ProjectSerializerException;
import ar.fiuba.trabajoprofesional.mdauml.exception.ObjectSerializerException;
import ar.fiuba.trabajoprofesional.mdauml.model.NameChangeListener;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlDiagram;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelElement;
import ar.fiuba.trabajoprofesional.mdauml.persistence.*;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.GeneralDiagram;
import ar.fiuba.trabajoprofesional.mdauml.umldraw.shared.UmlDiagramElement;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;
import org.w3c.dom.Element;

import ar.fiuba.trabajoprofesional.mdauml.model.UmlModelImpl;
import ar.fiuba.trabajoprofesional.mdauml.persistence.xml.XmlHelper;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;

public abstract class ProjectSerializer implements Serializer {

    protected ObjectSerializer projectObjectSerializer ;
    protected ObjectSerializer modelObjectSerializer ;
    protected ObjectSerializer viewObjectSerializer ;
    protected String projectPath;
    protected String modelPath;
    protected String viewPath;

    public ProjectSerializer(String path) throws ObjectSerializerException {
        this.projectPath = getProjectPath(path);;
        this.modelPath = getModelPath(path);
        this.viewPath = getViewPath(path);
        this.projectObjectSerializer = buildObjectSerializer(projectPath);
        this.modelObjectSerializer = buildObjectSerializer(modelPath);
        this.viewObjectSerializer = buildObjectSerializer(viewPath);

    }

    protected abstract ObjectSerializer buildObjectSerializer(String path) throws ObjectSerializerException;



    @Override public Object read() throws ProjectSerializerException {
        try {

            Project project = new Project(new UmlModelImpl());

            Registerer.clean();
            ModelSerializer modelXmlSerializer = new ModelSerializer(modelObjectSerializer);
            ModelPersistence modelPersistence = (ModelPersistence) modelXmlSerializer.read();


            ViewSerializer viewXmlSerializer = new ViewSerializer(viewObjectSerializer);
            ViewPersistence viewPersistence = (ViewPersistence) viewXmlSerializer.read();



            for(UmlDiagram diagram : viewPersistence.getUmlDiagrams()) {
                if(diagram instanceof GeneralDiagram) {
                    GeneralDiagram generalDiagram = (GeneralDiagram) diagram;
                    generalDiagram.initialize(project.getModel());
                    for(Connection connection: generalDiagram.getConnections()){
                        if(connection instanceof NodeChangeListener){
                            NodeChangeListener conn = (NodeChangeListener)connection;
                            Node node1 = connection.getNode1();
                            Node node2 = connection.getNode2();
                            node1.addNodeChangeListener(conn);
                            node2.addNodeChangeListener(conn);
                        }


                    }
                }
                project.getModel().addDiagram(diagram);
                for(UmlDiagramElement umlDiagramElement :diagram.getElements())
                    project.getModel().addElement(umlDiagramElement.getModelElement(),diagram);

            }

        return project;

        } catch (Exception e) {
            String msg = Msg.get("error.loadproject.message");
            throw new ProjectSerializerException(msg + " \"" + projectPath + "\".", e);
    }
    }

    private boolean validateFormat(Element root) {
        if (XmlHelper.querySingle(root, "./" + Constants.MODEL_TAG) != null
            && XmlHelper.querySingle(root, "./" + Constants.VIEW_TAG) != null)
            return true;

        return false;
    }

    @Override public void write(Project project) throws ProjectSerializerException {
        try {
            ProjectPersistence projectPersistence = new ProjectPersistence();
            projectPersistence.setModelPath(modelPath);
            projectPersistence.setViewPath(viewPath);

            projectObjectSerializer.writeObject(projectPersistence);

            Registerer.clean();
            ModelSerializer modelSerializer = new ModelSerializer(modelObjectSerializer);
            modelSerializer.write(project);

            ViewSerializer viewXmlSerializer = new ViewSerializer(viewObjectSerializer);
            viewXmlSerializer.write(project);

        } catch (Exception e) {
            String msg = Msg.get("error.saveproject.message");
            throw new ProjectSerializerException(msg + " \"" + projectPath + "\".", e);
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

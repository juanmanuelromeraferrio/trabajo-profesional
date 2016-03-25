package ar.fiuba.trabajoprofesional.mdauml.conversion.impl;

import ar.fiuba.trabajoprofesional.mdauml.conversion.*;
import ar.fiuba.trabajoprofesional.mdauml.conversion.model.*;
import ar.fiuba.trabajoprofesional.mdauml.exception.CompactorException;
import ar.fiuba.trabajoprofesional.mdauml.exception.ConversionException;
import ar.fiuba.trabajoprofesional.mdauml.exception.ValidateException;
import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.ui.AppFrame;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

public class ConverterImpl implements Converter, PropertyChangeListener {

    private Validator validator = new ValidatorImpl();
    private EntityCompactor compactor = new EntityCompactorImpl();
    private DiagramResolver resolver = new DiagramResolverImpl();
    private ClassDiagramBuilder diagramBuilder = new ClassDiagramBuilderImpl();
    private ClassModelBuilder classModelBuilder = new ClassModelBuilderImpl();

    private ProgressMonitor progressMonitor;
    private ConversionTask task;
    private JTextArea taskOutput;
    private Map<String, DiagramBuilder>  diagramMap;
    private Map<String, List<UmlUseCase>> mainEntityMap;
    private Map<Class<? extends UmlClass>, List<UmlClass>> umlModel;

    @Override
    public void convert(Project project) throws ConversionException {
        try {
            taskOutput = new JTextArea(5, 20);
            taskOutput.setMargin(new Insets(5,5,5,5));
            taskOutput.setEditable(false);

            UmlModel model = project.getModel();
            UmlModel compactedModel;
            try {
                validator.validate(model);
            } catch (ValidateException e) {
                throw new ConversionException(Msg.get("error.conversion.validation") + e.getMessage(), e);
            }
            try {
                compactedModel = compactor.compact(model);
            } catch (CompactorException e) {
                throw new ConversionException(Msg.get("error.conversion.compactor") + e.getMessage(), e);
            }


            Set<UmlUseCase> useCases = (Set<UmlUseCase>) compactedModel.getAll(UmlUseCase.class);
             mainEntityMap = buildMainEntityMap(useCases);

            ConversionModel conversionModel = classModelBuilder.buildConversionModel(mainEntityMap);
            umlModel = classModelBuilder.buildUmlModel(conversionModel);

            Set<UmlPackage> packages = (Set<UmlPackage>) compactedModel.getAll(UmlPackage.class);
            List<UmlPackage> packagesList = new ArrayList<>(packages);
            Collections.sort(packagesList);
            diagramMap = resolver.resolveEntitiesByDiagram(mainEntityMap,packagesList);

            progressMonitor = new ProgressMonitor(AppFrame.get(),
                    Msg.get("converting.loading.message"),
                    "", 0, 100);

            progressMonitor.setProgress(0);
            task = new ConversionTask();
            task.addPropertyChangeListener(this);
            progressMonitor.setMillisToPopup(0);
            progressMonitor.setMillisToDecideToPopup(0);
            task.execute();



        }catch(Exception e){
            throw new ConversionException(Msg.get("error.conversion"+ e.getMessage()),e);
        }
    }



    private Map<String,List<UmlUseCase>> buildMainEntityMap(Set<UmlUseCase> useCases) {
        Map<String,List<UmlUseCase>> mainEntityMap = new HashMap<>();
        for(UmlUseCase useCase : useCases){
            String mainEntity = useCase.getMainEntity();
            if( !mainEntityMap.containsKey(mainEntity))
                mainEntityMap.put(mainEntity,new ArrayList<UmlUseCase>());
            mainEntityMap.get(mainEntity).add(useCase);
        }
        return mainEntityMap;
    }

    private Set<UmlUseCase> getUseCases(Set<UmlModelElement> elements) {
        Set<UmlUseCase> useCases = new HashSet<>();
        for(UmlModelElement element: elements){
            if(element instanceof UmlUseCase){
                useCases.add((UmlUseCase) element);
            }
        }
        return  useCases;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName() ) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message =
                    String.format(Msg.get("conversion.loading.completed")+" %d%%.\n", progress);
            progressMonitor.setNote(message);
            taskOutput.append(message);
            if (progressMonitor.isCanceled() || task.isDone()) {
                Toolkit.getDefaultToolkit().beep();
                if (progressMonitor.isCanceled()) {
                    task.cancel(true);
                }
            }

        }

    }

    public class ConversionTask extends SwingWorker<Void, Void> {

        private int diagrams;
        private double progress;

        @Override
        public Void doInBackground() {
            progress = 0;
            setProgress(0);
            setProgress(1);

            diagrams = diagramMap.keySet().size();
                for (String diagram : diagramMap.keySet()) {
                    ConversionModel conversionDiagram = diagramMap.get(diagram).build(mainEntityMap);
                    progress+= 10.0/diagrams;
                    setProgress((int) progress);
                    diagramBuilder.buildClassDiagram(umlModel, diagram, conversionDiagram,this);

                }

            return null;
        }


        public void step1() {
            progress+= 10.0/diagrams;
            setProgress((int)progress);

        }
        public void step2() {
            progress+= 10.0/diagrams;
            setProgress((int)progress);

        }
        public void step3(int size) {
            progress+= 70.0/(diagrams*size);
            setProgress((int)progress);

        }

        @Override
        protected void done() {
            progressMonitor.close();
            AppFrame.get().repaint();

            super.done();
        }
    }
}

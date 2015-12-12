package ar.fiuba.trabajoprofesional.mdauml.conversion.impl;

import ar.fiuba.trabajoprofesional.mdauml.conversion.*;
import ar.fiuba.trabajoprofesional.mdauml.conversion.model.*;
import ar.fiuba.trabajoprofesional.mdauml.exception.CompactorException;
import ar.fiuba.trabajoprofesional.mdauml.exception.ConversionException;
import ar.fiuba.trabajoprofesional.mdauml.exception.ValidateException;
import ar.fiuba.trabajoprofesional.mdauml.model.*;
import ar.fiuba.trabajoprofesional.mdauml.ui.model.Project;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;
import ar.fiuba.trabajoprofesional.mdauml.util.StringHelper;

import java.util.*;

public class ConverterImpl implements Converter {

    private Validator validator = new ValidatorImpl();
    private EntityCompactor compactor = new EntityCompactorImpl();
    private DiagramResolver resolver = new DiagramResolverImpl();
    private ClassDiagramBuilder diagramBuilder = new ClassDiagramBuilderImpl();

    @Override
    public void convert(Project project) throws ConversionException {
        try {
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
            Map<String, List<UmlUseCase>> mainEntityMap = buildMainEntityMap(useCases);
            Set<UmlPackage> packages = (Set<UmlPackage>) compactedModel.getAll(UmlPackage.class);
            List<UmlPackage> packagesList = new ArrayList<>(packages);
            Collections.sort(packagesList);
            Map<String, DiagramBuilder> diagramMap = resolver.resolveEntitiesByDiagram(mainEntityMap,packagesList);


            for (String diagram : diagramMap.keySet()) {
                ConversionDiagram conversionDiagram = diagramMap.get(diagram).build(mainEntityMap);
                diagramBuilder.buildClassDiagram(project, diagram, conversionDiagram);
            }
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

}

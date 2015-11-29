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


            Set<UmlModelElement> elements = compactedModel.getElements();
            Set<UmlUseCase> useCases = getUseCases(elements);
            Map<String, List<UmlUseCase>> mainEntityMap = buildMainEntityMap(useCases);

            Map<String, List<String>> diagramMap = resolver.resolveEntitiesByDiagram(mainEntityMap.keySet());


            for (String diagram : diagramMap.keySet()) {
                List<String> mainEntities = diagramMap.get(diagram);
                ConversionModel conversionModel = buildConversionModel(mainEntities, mainEntityMap);
                diagramBuilder.buildClassDiagram(project, diagram, conversionModel);
            }
        }catch(Exception e){
            throw new ConversionException(Msg.get("error.conversion"+ e.getMessage()),e);
        }
    }

    private ConversionModel buildConversionModel(List<String> entities,Map<String, List<UmlUseCase>> mainEntityMap) {
        ConversionModel conversionModel = new ConversionModel();
        for(String mainEntity: entities){
            Control control = conversionModel.getControl(StringHelper.toUpperCamelCase(mainEntity) + Msg.get("conversion.names.control"));
            for(UmlUseCase useCase : mainEntityMap.get(mainEntity)){
                control.addMethod(StringHelper.toLowerCamelCase(useCase.getName()));
                for(String entityName : useCase.getAllEntities()){
                    Entity entity = conversionModel.getEntity(StringHelper.toUpperCamelCase(entityName) + Msg.get("conversion.names.entity"));
                    conversionModel.addRelation(new SimpleRelation(control,entity));
                }
                for(UmlActor mainActor : useCase.getMainActors()){
                    Boundary boundary = conversionModel.getBoundary(StringHelper.toUpperCamelCase(mainActor.getName()) + Msg.get("conversion.names.boundary"));
                    boundary.addMethod(StringHelper.toLowerCamelCase(useCase.getName()));
                    conversionModel.addRelation(new SimpleRelation(boundary,control));
                }
            }
        }
        return conversionModel;
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

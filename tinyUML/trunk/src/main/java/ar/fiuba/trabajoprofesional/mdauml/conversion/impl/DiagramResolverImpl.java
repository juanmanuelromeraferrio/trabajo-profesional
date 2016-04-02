package ar.fiuba.trabajoprofesional.mdauml.conversion.impl;

import ar.fiuba.trabajoprofesional.mdauml.conversion.DiagramResolver;
import ar.fiuba.trabajoprofesional.mdauml.conversion.dialog.DiagramResolverDialog;
import ar.fiuba.trabajoprofesional.mdauml.conversion.model.DiagramBuilder;
import ar.fiuba.trabajoprofesional.mdauml.exception.ConversionCanceledException;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlPackage;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.ui.AppFrame;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;
import ar.fiuba.trabajoprofesional.mdauml.util.StringHelper;

import java.util.*;

public class DiagramResolverImpl implements DiagramResolver {
    @Override
    public Map<String, DiagramBuilder> resolveEntitiesByDiagram(Map<String, List<UmlUseCase>> mainEntityMap , List<UmlPackage> packages) throws ConversionCanceledException {
        Map<String, DiagramBuilder> diagramMap = new HashMap<>();
        UmlPackage rootPackage = UmlPackage.getPrototype();
        rootPackage.setName(Msg.get("conversion.defaultDiagram"));

        List<String> mainEntities = new ArrayList<>(mainEntityMap.keySet());
        Collections.sort(mainEntities);


        for(UmlPackage umlPackage:packages){
            Set<String> mainEntitiesOfPkg =new HashSet<>();
            DiagramBuilder diagramBuilder = new DiagramBuilder();
            diagramBuilder.setName(umlPackage.getName());
            diagramBuilder.setUmlPackage(umlPackage);
            diagramBuilder.setPackageRelated(true);
            for (String mainEntity : mainEntities) {
                for (UmlUseCase useCase : mainEntityMap.get(mainEntity))
                    if (useCase.getPackage() == umlPackage) {
                        mainEntitiesOfPkg.add(mainEntity);
                        break;
                    }
            }
            diagramBuilder.setMainEntities(new ArrayList<>(mainEntitiesOfPkg));
            Collections.sort(diagramBuilder.getMainEntities());
            diagramMap.put(diagramBuilder.getName(),diagramBuilder);
        }

        if(packages.isEmpty()){
            DiagramBuilder diagramBuilder = new DiagramBuilder();
            diagramBuilder.setName(AppFrame.get().getName());
            diagramBuilder.setUmlPackage(null);
            diagramBuilder.setPackageRelated(false);
            diagramBuilder.setMainEntities(mainEntities);
            diagramMap.put(AppFrame.get().getName(),diagramBuilder);
        }

        openDialog(diagramMap,mainEntities,packages);
        return removeEmpty(diagramMap);
    }

    private Map<String, DiagramBuilder> removeEmpty(Map<String, DiagramBuilder> diagramMap) {
        for (String diagram :
                diagramMap.keySet()) {
            if (diagramMap.get(diagram).getMainEntities().isEmpty())
                diagramMap.remove(diagram);
        }
        return diagramMap;
    }

    private void openDialog(Map<String, DiagramBuilder> diagramMap, List<String> mainEntities, List<UmlPackage>packages) throws ConversionCanceledException {
        DiagramResolverDialog dialog = new DiagramResolverDialog(diagramMap,mainEntities,packages);
        dialog.setLocationRelativeTo(AppFrame.get());
        dialog.setVisible(true);
        if(dialog.hasCanceled())
            throw new ConversionCanceledException();
    }


}

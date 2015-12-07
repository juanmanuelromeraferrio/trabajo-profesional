package ar.fiuba.trabajoprofesional.mdauml.conversion.impl;

import ar.fiuba.trabajoprofesional.mdauml.conversion.DiagramResolver;
import ar.fiuba.trabajoprofesional.mdauml.conversion.dialog.DiagramResolverDialog;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlPackage;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.ui.AppFrame;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;

import java.util.*;

public class DiagramResolverImpl implements DiagramResolver {
    @Override
    public Map<String, List<String>> resolveEntitiesByDiagram(Map<String, List<UmlUseCase>> mainEntityMap , List<UmlPackage> packages) {
        Map<String, List<String>> diagramEntitiesMap = new HashMap<>();
        UmlPackage rootPackage = UmlPackage.getPrototype();
        rootPackage.setName(Msg.get("conversion.defaultDiagram"));

        List<String> mainEntities = new ArrayList<>(mainEntityMap.keySet());
        Collections.sort(mainEntities);

        for (String mainEntity : mainEntities) {
            Map<UmlPackage, Integer> packageCount = new HashMap<>();
            for (UmlPackage umlPackage : packages) {
                packageCount.put(umlPackage, 0);
            }
            packageCount.put(rootPackage, 0);
            UmlPackage maxCountPackage = rootPackage;
            Integer maxCount = 0;
            for (UmlUseCase useCase : mainEntityMap.get(mainEntity)) {
                UmlPackage useCasePackage = useCase.getPackage() == null ? rootPackage : useCase.getPackage();
                Integer count = packageCount.get(useCasePackage) + 1;
                packageCount.put(useCasePackage, count);
                if (count > maxCount) {
                    maxCount = count;
                    maxCountPackage = useCasePackage;
                }
            }
            if (!diagramEntitiesMap.containsKey(maxCountPackage.getName()))
                diagramEntitiesMap.put(maxCountPackage.getName(), new ArrayList<String>());
            diagramEntitiesMap.get(maxCountPackage.getName()).add(mainEntity);
        }
        openDialog(diagramEntitiesMap,mainEntities);
        return removeEmpty(diagramEntitiesMap);
    }

    private Map<String, List<String>> removeEmpty(Map<String, List<String>> diagramEntitiesMap) {
        for (String diagram :
                diagramEntitiesMap.keySet()) {
            if (diagramEntitiesMap.get(diagram).isEmpty())
                diagramEntitiesMap.remove(diagram);
        }
        return diagramEntitiesMap;
    }

    private void openDialog(Map<String, List<String>> diagramEntitiesMap, List<String> mainEntities) {
        DiagramResolverDialog dialog = new DiagramResolverDialog(diagramEntitiesMap,mainEntities);
        dialog.setLocationRelativeTo(AppFrame.get());
        dialog.setVisible(true);
    }


}

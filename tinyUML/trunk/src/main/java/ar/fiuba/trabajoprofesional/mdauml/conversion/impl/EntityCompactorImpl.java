package ar.fiuba.trabajoprofesional.mdauml.conversion.impl;

import ar.fiuba.trabajoprofesional.mdauml.conversion.EntityCompactor;
import ar.fiuba.trabajoprofesional.mdauml.conversion.dialog.EntityCompactorDialog;
import ar.fiuba.trabajoprofesional.mdauml.conversion.dialog.MainEntityDialog;
import ar.fiuba.trabajoprofesional.mdauml.exception.CompactorException;
import ar.fiuba.trabajoprofesional.mdauml.exception.ConversionCanceledException;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.ui.AppFrame;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityCompactorImpl implements EntityCompactor {


    @Override
    public UmlModel compact( UmlModel model) throws CompactorException {
        Set<UmlUseCase> useCases = (Set<UmlUseCase>) model.getAll(UmlUseCase.class);
        Map<String,String> entities = new HashMap<>();
        for(UmlUseCase useCase: useCases){
            for(String entity : useCase.getAllEntities())
                entities.put(entity,entity);
        }
        openCompactorDialog(entities);
        updateModel(useCases,entities);
        return model;
    }

    private void updateModel(Set<UmlUseCase> useCases, Map<String, String> entities) {
        for (UmlUseCase usecase:  useCases) {
            for(String entity : entities.keySet())
            usecase.replaceEntity(entity,entities.get(entity));
        }
    }

    private void openCompactorDialog(Map<String, String> entities) {
        EntityCompactorDialog dialog = new EntityCompactorDialog(AppFrame.get(),entities);
        dialog.setLocationRelativeTo(AppFrame.get());
        dialog.setVisible(true);

    }
}

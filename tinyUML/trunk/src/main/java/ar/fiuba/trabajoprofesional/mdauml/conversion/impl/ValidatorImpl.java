package ar.fiuba.trabajoprofesional.mdauml.conversion.impl;


import ar.fiuba.trabajoprofesional.mdauml.conversion.Validator;
import ar.fiuba.trabajoprofesional.mdauml.conversion.dialog.MainEntityDialog;
import ar.fiuba.trabajoprofesional.mdauml.exception.ConversionCanceledException;
import ar.fiuba.trabajoprofesional.mdauml.exception.ValidateException;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlUseCase;
import ar.fiuba.trabajoprofesional.mdauml.ui.AppFrame;
import ar.fiuba.trabajoprofesional.mdauml.util.Msg;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class ValidatorImpl implements Validator{
    @Override
    public void validate(UmlModel model) throws ValidateException, ConversionCanceledException {
        Set<UmlUseCase> useCases = (Set<UmlUseCase>) model.getAll(UmlUseCase.class);
        validate(useCases);

    }

    private void validate(Set<UmlUseCase> useCases) throws ValidateException, ConversionCanceledException {

        for(UmlUseCase useCase : useCases){
            if(useCase.getMainActors().isEmpty())
                throw new ValidateException(Msg.get("error.validator.useCaseNoActor").replaceAll("@USECASE",useCase.getName()));
            if(useCase.getMainEntity()==null || useCase.getMainEntity().isEmpty())
                openMainEntityDialog(useCase);
        }

    }

    private void openMainEntityDialog(UmlUseCase useCase) throws ConversionCanceledException {
        MainEntityDialog dialog = new MainEntityDialog(AppFrame.get(),useCase);
        dialog.setLocationRelativeTo(AppFrame.get());
        dialog.setVisible(true);
        if(dialog.hasCanceled())
            throw new ConversionCanceledException();
    }


}

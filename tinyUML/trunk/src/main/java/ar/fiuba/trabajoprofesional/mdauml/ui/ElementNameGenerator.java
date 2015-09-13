package ar.fiuba.trabajoprofesional.mdauml.ui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ar.fiuba.trabajoprofesional.mdauml.model.ElementType;
import ar.fiuba.trabajoprofesional.mdauml.model.UmlModel;

public class ElementNameGenerator implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6984115003747185226L;

    private static transient UmlModel umlModel;
    private static Map<ElementType, Integer> nameMap = new HashMap<ElementType, Integer>();

    public static String getName(ElementType elementType) {

        if (!nameMap.containsKey(elementType)) {
            nameMap.put(elementType, 0);
        }

        String label;

        do {
            Integer index = nameMap.get(elementType);
            index = index + 1;
            nameMap.put(elementType, index);
            String name = elementType.getName();
            label = name + " " + index;
        } while (umlModel.exist(label));

        return label;

    }

    public static void setModel(UmlModel model) {
        umlModel = model;
    }

    public static Map<ElementType, Integer> getNameMap() {
        return nameMap;
    }
}

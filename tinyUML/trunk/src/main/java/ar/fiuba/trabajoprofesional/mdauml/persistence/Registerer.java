package ar.fiuba.trabajoprofesional.mdauml.persistence;

import java.util.HashMap;
import java.util.Map;

import ar.fiuba.trabajoprofesional.mdauml.exception.RegisterExistentIdException;

public class Registerer {

    private static HashMap<Integer, Long> idsByHashCode = new HashMap<>();
    private static HashMap<Long, Object> objects = new HashMap<>();
    private static Long nextId = 0L;

    public static Long register(Object object) {
        if (isRegistered(object))
            return getId(object);
        idsByHashCode.put(System.identityHashCode(object), nextId);
        objects.put(nextId, object);
        nextId++;
        return nextId - 1;
    }

    public static boolean isRegistered(Object object) {
        return getId(object) != null;

    }

    public static boolean isRegistered(Long id) {
        return objects.get(id) != null;
    }

    public static Long getId(Object object) {
        Long sameHashId = idsByHashCode.get(System.identityHashCode(object));
        if (sameHashId != null) {
            Object sameHashObject = objects.get(sameHashId);
            if (sameHashObject == object)
                return sameHashId;
            for (Map.Entry<Long, Object> entry : objects.entrySet()) {
                Long anId = entry.getKey();
                Object anObject = entry.getValue();
                if (anObject == object) {
                    return anId;
                }
            }
        }
        return null;
    }

    public static Object getObject(Long id) {
        return objects.get(id);
    }

    public static Long register(Long id, Object object) throws RegisterExistentIdException {
        if (objects.containsKey(id))
            throw new RegisterExistentIdException(
                "The id=" + id + " has already been registered for another object");
        idsByHashCode.put(System.identityHashCode(object), id);
        objects.put(id, object);
        if (nextId < id)
            nextId = id;
        nextId++;
        return id;
    }

    public static void clean() {
        idsByHashCode = new HashMap<>();
        objects = new HashMap<>();
        nextId = 0L;
    }

}

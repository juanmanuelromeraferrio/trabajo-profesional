package ar.fiuba.trabajoprofesional.mdauml.persistence;

import java.util.HashMap;

import ar.fiuba.trabajoprofesional.mdauml.exception.RegisterExistentIdException;

public class Registerer {

    private static HashMap<Object, Long> ids = new HashMap<Object, Long>();
    private static HashMap<Long, Object> objects = new HashMap<Long, Object>();
    private static Long nextId = 0L;

    public static Long register(Object object) {
        if (ids.containsKey(object))
            return ids.get(object);
        ids.put(object, nextId);
        objects.put(nextId, object);
        nextId++;
        return nextId - 1;
    }

    public static boolean isRegistered(Object object) {
        return ids.get(object) != null;
    }

    public static boolean isRegistered(Long id) {
        return objects.get(id) != null;
    }

    public static Long getId(Object object) {
        return ids.get(object);
    }

    public static Object getObject(Long id) {
        return objects.get(id);
    }

    public static Long register(Long id, Object object) throws RegisterExistentIdException {
        if (ids.containsKey(object))
            return ids.get(object);
        if (objects.containsKey(id))
            throw new RegisterExistentIdException(
                "The id=" + id + " has already been registered for another object");
        ids.put(object, id);
        objects.put(id, object);
        if (nextId < id)
            nextId = id;
        nextId++;
        return id;
    }

    public static void clean() {
        ids = new HashMap<Object, Long>();
        objects = new HashMap<Long, Object>();
        nextId = 0L;
    }

}

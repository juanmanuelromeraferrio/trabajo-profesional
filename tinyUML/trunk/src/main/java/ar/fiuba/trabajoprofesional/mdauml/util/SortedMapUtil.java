package ar.fiuba.trabajoprofesional.mdauml.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class SortedMapUtil {

  public static LinkedHashMap<String, Integer> sortHashMapByValues(
      HashMap<String, Integer> passedMap) {
    List<String> mapKeys = new ArrayList<String>(passedMap.keySet());
    List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());
    Collections.sort(mapValues);
    Collections.sort(mapKeys);

    LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();

    Iterator<Integer> valueIt = mapValues.iterator();
    while (valueIt.hasNext()) {
      Object val = valueIt.next();
      Iterator<String> keyIt = mapKeys.iterator();

      while (keyIt.hasNext()) {
        Object key = keyIt.next();
        String comp1 = passedMap.get(key).toString();
        String comp2 = val.toString();

        if (comp1.equals(comp2)) {
          passedMap.remove(key);
          mapKeys.remove(key);
          sortedMap.put((String) key, (Integer) val);
          break;
        }

      }

    }
    return sortedMap;
  }

}

package terrabot;

import entities.Entities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LearnFact {
    /**
     * Javadoc for method AddFactToDatabase.
     * Se foloseste de un HashMap pentru a retine invataturile
     * dar verifica mai intai daca acel obiect exista in inventar
     * adica daca a fost scanat de terrabot.
     */
    public String addFactToDatabase(final String fact, final String entity,
                                    final LinkedHashMap<String, String> learnMap,
                                    final ArrayList<Entities> inventory) {
        String result = "";
        boolean contains = false;
        for (final Entities e : inventory) {
            if (e.getName().equals(entity)) {
                contains = true;
                break;
            }
        }
        if (contains) {
            final LinkedHashMap<String, String> map = learnMap;
            learnMap.put(fact, entity);
        } else {
            result = "ERROR";
        }
        return result;
    }
}

package terrabot;

import entities.Entities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LearnFact {
    /**
     * Javadoc for method AddFactToDatabase.
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

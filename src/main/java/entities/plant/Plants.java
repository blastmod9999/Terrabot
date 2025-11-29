package entities.plant;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Entities;
import entities.air.Air;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Algae.class, name = "Algae"),
        @JsonSubTypes.Type(value = FloweringPlants.class, name = "FloweringPlants"),
        @JsonSubTypes.Type(value = GymnospermsPlants.class, name = "GymnospermsPlants"),
        @JsonSubTypes.Type(value = Ferns.class, name = "Ferns"),
        @JsonSubTypes.Type(value = Mosses.class, name = "Mosses")
})
public class Plants extends Entities {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private double growth = 0;

    /**
     * Javadoc for method copy.
     * Se face deepcopy, referinta in Air.
     */
    public Plants copy() {
        try {
            return MAPPER.treeToValue(MAPPER.valueToTree(this), Plants.class);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Javadoc for method getGrowth.
     * intoarce cresterea plantei
     */
    public double getGrowth() {
        return growth;
    }

    /**
     * Javadoc for method setGrowth.
     * seteaza cresterea (de ex planta moarta = 0)
     */
    public void setGrowth(final double growth) {
        this.growth = growth;
    }

    /**
     * Javadoc for method UpdateBox.
     * Specifica fiecarei plante, se foloseste de parametrii individuali ai acesteia
     * pentru a actualiza aerul in functie de cresterea plantelor.
     */
    public void updateBox(final Air air) {

    }
}

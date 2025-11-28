package entities.plant;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Entities;
import entities.air.*;
import map.MapBox;


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
    public double growth = 0;



    //Facem deepcopy nu Shallow
    // + try catch generat de IDE
    // Source - https://stackoverflow.com/questions/49903859/deep-copy-using-jackson-string-or-jsonnode
// Posted by Rad, modified by community. See post 'Timeline' for change history
// Retrieved 2025-11-27, License - CC BY-SA 3.0

    //MyPojo myPojo = new MyPojo();
    //ObjectMapper mapper = new ObjectMapper();
    //MyPojo newPojo = mapper.treeToValue(mapper.valueToTree(myPojo), MyPojo.class);

    public Plants copy() {
        try {
            return MAPPER.treeToValue(MAPPER.valueToTree(this), Plants.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public double getGrowth() {
        return growth;
    }

    public void setGrowth(double growth) {
        this.growth = growth;
    }

    public void UpdateBox (Air air) {

    }
}

package entities.animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Entities;
import entities.plant.*;
import entities.water.Water;
import map.InitializeMap;
import map.MapBox;

import java.util.ArrayList;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Herbivores.class, name = "Herbivores"),
        @JsonSubTypes.Type(value = Carnivores.class, name = "Carnivores"),
        @JsonSubTypes.Type(value = Omnivores.class, name = "Omnivores"),
        @JsonSubTypes.Type(value = Detritivores.class, name = "Detritivores"),
        @JsonSubTypes.Type(value = Parasites.class, name = "Parasites")
})
public class Animals extends Entities {

    private String state;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    //Facem deepcopy nu Shallow
    // + try catch generat de IDE
    // Source - https://stackoverflow.com/questions/49903859/deep-copy-using-jackson-string-or-jsonnode
// Posted by Rad, modified by community. See post 'Timeline' for change history
// Retrieved 2025-11-27, License - CC BY-SA 3.0

    //MyPojo myPojo = new MyPojo();
    //ObjectMapper mapper = new ObjectMapper();
    //MyPojo newPojo = mapper.treeToValue(mapper.valueToTree(myPojo), MyPojo.class);

    //locatia curenta
    int x, y;
    int[] dx = {0,1,0,-1};
    int[] dy = {1,0,-1,0};

//    void AnimalMoveDecider(InitializeMap map, int x, int y){
//        MapBox[][] mapBox = map.getEnvMap();
//        ArrayList<Water> waters = new ArrayList<>();
//        ArrayList<Plants> plants = new ArrayList<>();
//        this.x = x;
//        this.y = y;
//        int new_x = 0;
//        int new_y = 0;
//        int maximum = -9999;
//
//        int found = 0;
//        for (int i = 0; i < dx.length; i++) {
//            int new_i = x + dx[i];
//            int new_j = y + dy[i];
//            double maxWaterQ = -99;
//            if (new_i >= 0 && new_j >= 0 && new_i < map.getHeight() && new_j < map.getWidth()) {
//                if(mapBox[new_i][new_j].getPlant()!=null){
//                    waters.add(mapBox[new_i][new_j].getWater());
//                }
//                if(mapBox[new_i][new_j].getPlant()!=null){
//                    plants.add(mapBox[new_i][new_j].getPlant());
//                }
//            }
//        }
//
//        MapBox vecinbun = new MapBox();
//
//        for(int i = 0; i < 4 ;i++)
//        {
//            if(waters.get(i) != null && plants.get(i) != null){
//                    vecinbun
//            }
//        }
//
//    }
//
//    int CalculateFavorableScore(MapBox mapBox) {
//        int score = 0;
//        if(mapBox.getPlant()!=null){
//            Plants plant = mapBox.getPlant();
//            if(plant.scanned){
//                score+=10;
//            }
//        }
//        if(mapBox.getWater()!=null){
//            Water water = mapBox.getWater();
//            if(water.scanned){
//                score+=20;
//            }
//        }
//
//        return score;
//    }

    public Animals copy() {
        try {
            return MAPPER.treeToValue(MAPPER.valueToTree(this), Animals.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

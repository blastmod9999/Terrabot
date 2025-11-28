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
    int iteration;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void AnimalMove(InitializeMap map,int x,int y) {
        iteration++;
        if(iteration % 2 == 0){
            AnimalMoveDecider(map,x,y);
        }

    }

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
//    int[] dx = {0,1,0,-1};
//    int[] dy = {1,0,-1,0};

    int[] dx = {-1,0,1,0};
    int[] dy = {0,1,0,-1};
    void AnimalMoveDecider(InitializeMap map, int x, int y) {
        MapBox[][] mapBox = map.getEnvMap();

        ArrayList<Water> waters = new ArrayList<>();
        ArrayList<Plants> plants = new ArrayList<>();
        ArrayList<Animals> animals = new ArrayList<>();
        ArrayList<Indices> indices = new ArrayList<>();

        this.x = x;
        this.y = y;


        for (int i = 0; i < dx.length; i++) {
            int new_i = x + dx[i];
            int new_j = y + dy[i];

            if (new_i >= 0 && new_j >= 0 && new_i < map.getHeight() && new_j < map.getWidth()) {

                waters.add(mapBox[new_i][new_j].getWater());
                plants.add(mapBox[new_i][new_j].getPlant());
                animals.add(mapBox[new_i][new_j].getAnimal());

                Indices indic = new Indices();
                indic.x = new_i;
                indic.y = new_j;
                indices.add(indic);
            } else {
                waters.add(null);
                plants.add(null);
                animals.add(null);
                indices.add(null);
            }
        }


        int bestScore = -1;
        double bestWaterQ = -1.0;
        int bestIndex = -1;


        for (int i = 0; i < 4; i++) {
            if (indices.get(i) == null) continue;
            boolean hasWater = (waters.get(i) != null);
            boolean hasPlant = (plants.get(i) != null);
            // boolean hasAnimal = (animals.get(i) != null);

            int currentScore = 0;
            double currentWaterQ = 0.0;

            if (hasWater) {
                currentWaterQ = waters.get(i).getWater_quality();
            }


            if (hasPlant && hasWater) {
                currentScore = 3;
            } else if (hasPlant) {
                currentScore = 2;
            } else if (hasWater) {
                currentScore = 1;
            } else {
                currentScore = 0;
            }


            boolean isBetter = false;

            if (currentScore > bestScore) {
                isBetter = true;
            } else if (currentScore == bestScore) {
                if (currentScore == 3 || currentScore == 1) {
                    if (currentWaterQ > bestWaterQ) {
                        isBetter = true;
                    }
                }

            }


            if (isBetter) {
                bestScore = currentScore;
                bestWaterQ = currentWaterQ;
                bestIndex = i;
            }
        }


        if (bestIndex != -1) {

            mapBox[this.x][this.y].setAnimal(null);
            this.x = indices.get(bestIndex).x;
            this.y = indices.get(bestIndex).y;
            mapBox[this.x][this.y].setAnimal(this);

        }
    }

    int CalculateFavorableScore(MapBox mapBox) {
        int score = 0;
        if(mapBox.getPlant()!=null){
            Plants plant = mapBox.getPlant();
            if(plant.scanned){
                score+=10;
            }
        }
        if(mapBox.getWater()!=null){
            Water water = mapBox.getWater();
            if(water.scanned){
                score+=20;
            }
        }

        return score;
    }




    public Animals copy() {
        try {
            return MAPPER.treeToValue(MAPPER.valueToTree(this), Animals.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}


class Indices {
    int x;
    int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

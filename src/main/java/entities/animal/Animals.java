package entities.animal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Entities;
import entities.plant.Plants;
import entities.water.Water;
import map.InitializeMap;
import map.MapBox;

import java.util.ArrayList;

import static utils.MagicNumbers.THREE;


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

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final int[] dx = {-1, 0, 1, 0};
    private final int[] dy = {0, 1, 0, -1};
    private int iteration;
    private int currentPosX, currentPosY;
    private String state;

    /**
     * Javadoc for method AnimalMove.
     */
    public void animalMove(final InitializeMap map, final int x, final int y) {
        iteration++;
        if (iteration % 2 == 0) {
            animalMoveDecider(map, x, y);
        }

    }

    void animalMoveDecider(final InitializeMap map, final int x, final int y) {
        final MapBox[][] mapBox = map.getEnvMap();

        final ArrayList<Water> waters = new ArrayList<>();
        final ArrayList<Plants> plants = new ArrayList<>();
        final ArrayList<Animals> animals = new ArrayList<>();
        final ArrayList<Indices> indices = new ArrayList<>();

        currentPosX = x;
        currentPosY = y;


        for (int i = 0; i < dx.length; i++) {
            final int newI = x + dx[i];
            final int newJ = y + dy[i];

            if (newI >= 0 && newJ >= 0 && newI < map.getHeight() && newJ < map.getWidth()) {

                waters.add(mapBox[newI][newJ].getWater());
                plants.add(mapBox[newI][newJ].getPlant());
                animals.add(mapBox[newI][newJ].getAnimal());

                final Indices indic = new Indices();
                indic.setX(newI);
                indic.setY(newJ);
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


        for (int i = 0; i < waters.size(); i++) {
            if (indices.get(i) == null) {
                continue;
            }
            final boolean hasWater = (waters.get(i) != null);
            final boolean hasPlant = (plants.get(i) != null);
            // boolean hasAnimal = (animals.get(i) != null);

            int currentScore = 0;
            double currentWaterQ = 0.0;

            if (hasWater) {
                currentWaterQ = waters.get(i).getWaterQuality();
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
                if (currentScore == THREE || currentScore == 1) {
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

            mapBox[currentPosX][currentPosY].setAnimal(null);
            currentPosX = indices.get(bestIndex).getX();
            currentPosY = indices.get(bestIndex).getY();
            mapBox[currentPosX][currentPosY].setAnimal(this);

        }
    }


    /**
     * Javadoc for method copy.
     * Se face deepcopy , referinta adaugata in Air
     */
    public Animals copy() {
        try {
            return MAPPER.treeToValue(MAPPER.valueToTree(this), Animals.class);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

class Indices {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }
}

package entities.animal;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import entities.plant.Plants;
//import entities.soil.Soil;
//import entities.water.Water;
//import map.InitializeMap;
//import map.MapBox;
//import utils.MagicNumbers;

//import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.plant.Plants;
import entities.water.Water;
import map.InitializeMap;
import map.MapBox;

import java.util.ArrayList;

import static utils.MagicNumbers.THREE;

public final class Detritivores extends Animals {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final int[] dx = {-1, 0, 1, 0};
    private final int[] dy = {0, 1, 0, -1};
    private int currentPosX, currentPosY;



    @Override
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
                if (mapBox[newI][newJ].getWater() != null && (mapBox[newI][newJ].getWater()
                        .isScanned())) {
                    waters.add(mapBox[newI][newJ].getWater());
                } else {
                    waters.add(null);
                }

                if (mapBox[newI][newJ].getPlant() != null && (mapBox[newI][newJ].getPlant()
                        .isScanned())) {
                    plants.add(mapBox[newI][newJ].getPlant());
                } else {
                    plants.add(null);
                }

                if (mapBox[newI][newJ].getAnimal() != null) {
                    animals.add(mapBox[newI][newJ].getAnimal());
                } else {
                    animals.add(null);
                }


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
            final boolean hasAnimal = (animals.get(i) != null);


            int currentScore = 0;
            double currentWaterQ = 0.0;

            if (hasWater) {
                currentWaterQ = waters.get(i).getWaterQuality();
            }


            if (hasPlant && hasWater && !hasAnimal) {
                currentScore = 3;
            } else if (hasPlant && !hasAnimal) {
                currentScore = 2;
            } else if (hasWater && !hasAnimal) {
                currentScore = 1;
            } else if (hasAnimal) {
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
}


package map;

import entities.air.Air;
import entities.animal.Animals;
import entities.plant.Plants;
import entities.soil.Soil;
import entities.water.Water;

import java.util.Arrays;

public final class InitializeMap {
    private final MapBox[][] envMap;
    private int width;
    private int height;


    public InitializeMap(final String dims) {
        //https://www.geeksforgeeks.org/java/java-program-to-convert-string-to-integer-array
        final int[] dims2 = Arrays.stream(dims.split("x")).mapToInt(Integer::parseInt).toArray();
        this.width = dims2[0];
        this.height = dims2[1];
        //IO.println("Dims: " + dims2[1] + "x" + dims2[0]);
        this.envMap = new MapBox[dims2[0]][dims2[1]];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.envMap[i][j] = new MapBox();
            }
        }
    }

    public MapBox[][] getEnvMap() {
        return envMap;
    }

    /**
     * Javadoc for method populateMap.
     */
    public void populateMap(final TerritorySectionParams params) {
        if (params.getSoil() != null) {
            for (int i = 0; i < params.getSoil().size(); i++) {
                final Soil soil = params.getSoil().get(i);
                for (int j = 0; j < soil.getSections().size(); j++) {
                    final int x = soil.getSections().get(j).getX();
                    final int y = soil.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setSoil(soil.copy());
                    }
                }
            }
        }

        if (params.getPlants() != null) {
            for (int i = 0; i < params.getPlants().size(); i++) {
                final Plants plant = params.getPlants().get(i);
                for (int j = 0; j < plant.getSections().size(); j++) {
                    final int x = plant.getSections().get(j).getX();
                    final int y = plant.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        ////FACEM DEEPCOPY CA DACA NU PIERDEM ORE
                        ///IN SIR SA NE DAM SEAMA DE CE NU MERGE
                        envMap[x][y].setPlant(plant.copy());
                    }
                }
            }
        }

        if (params.getAnimals() != null) {
            for (int i = 0; i < params.getAnimals().size(); i++) {
                final Animals animal = params.getAnimals().get(i);
                for (int j = 0; j < animal.getSections().size(); j++) {
                    final int x = animal.getSections().get(j).getX();
                    final int y = animal.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setAnimal(animal.copy());
                    }
                }
            }
        }

        if (params.getWater() != null) {
            for (int i = 0; i < params.getWater().size(); i++) {
                final Water water = params.getWater().get(i);
                for (int j = 0; j < water.getSections().size(); j++) {
                    final int x = water.getSections().get(j).getX();
                    final int y = water.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setWater(water.copy());
                    }
                }
            }
        }

        if (params.getAir() != null) {
            for (int i = 0; i < params.getAir().size(); i++) {
                final Air air = params.getAir().get(i);
                for (int j = 0; j < air.getSections().size(); j++) {
                    final int x = air.getSections().get(j).getX();
                    final int y = air.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setAir(air.copy());
                    }
                }
            }
        }

    }


    /**
     * Javadoc for method getWidth.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Javadoc for method setWidth.
     */
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * Javadoc for method getHeight.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Javadoc for method setHeight.
     */
    public void setHeight(final int height) {
        this.height = height;
    }
}

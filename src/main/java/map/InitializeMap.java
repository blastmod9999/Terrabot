package map;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Entities;
import entities.air.Air;
import entities.animal.Animals;
import entities.plant.Plants;
import entities.soil.Soil;
import entities.water.Water;
import map.MapBox;
import map.TerritorySectionParams;

import java.util.Arrays;

public class InitializeMap {
    private MapBox[][] envMap;
    private int width;
    private int height;
    ObjectMapper mapper = new ObjectMapper();


    public MapBox[][] getEnvMap() {
        return envMap;
    }

    public InitializeMap(String dims) {
        //https://www.geeksforgeeks.org/java/java-program-to-convert-string-to-integer-array
        int[] dims2 = Arrays.stream(dims.split("x")).mapToInt(Integer::parseInt).toArray();
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


    public void populateMap(TerritorySectionParams params) {
        if(params.getSoil() != null) {
            for(int i = 0; i < params.getSoil().size(); i++) {
                Soil soil = params.getSoil().get(i);
                for(int j=0 ; j < soil.getSections().size(); j++) {
                    int x = soil.getSections().get(j).getX();
                    int y = soil.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setSoil(soil.copy());
                    }
                }
            }
        }

        if(params.getPlants() != null) {
            for(int i = 0; i < params.getPlants().size(); i++) {
                Plants plant = params.getPlants().get(i);
                for(int j=0 ; j < plant.getSections().size(); j++) {
                    int x = plant.getSections().get(j).getX();
                    int y = plant.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        /// ///FACEM DEEPCOPY CA DACA NU PIERDEM ORE IN SIR SA NE DAM SEAMA DE CE NU MERGE
                        envMap[x][y].setPlant(plant.copy());
                    }
                }
            }
        }

        if(params.getAnimals() != null) {
            for(int i = 0; i < params.getAnimals().size(); i++) {
                Animals animal = params.getAnimals().get(i);
                for(int j=0 ; j < animal.getSections().size(); j++) {
                    int x = animal.getSections().get(j).getX();
                    int y = animal.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setAnimal(animal.copy());
                    }
                }
            }
        }

        if(params.getWater() != null) {
            for(int i = 0; i < params.getWater().size(); i++) {
                Water water = params.getWater().get(i);
                for(int j=0 ; j < water.getSections().size(); j++) {
                    int x = water.getSections().get(j).getX();
                    int y = water.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setWater(water.copy());
                    }
                }
            }
        }

        if(params.getAir() != null) {
            for(int i = 0; i < params.getAir().size(); i++) {
                Air air = params.getAir().get(i);
                for(int j=0 ; j < air.getSections().size(); j++) {
                    int x = air.getSections().get(j).getX();
                    int y = air.getSections().get(j).getY();

                    if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
                        envMap[x][y].setAir(air.copy());
                    }
                }
            }
        }

    }


    public void printMapBox() {
        for(int x = 0; x < this.width; x++) {
            for(int y = 0; y < this.height; y++) {
                IO.print("["+x+" "+ y + "]" + " Box contains : " + envMap[x][y].getAir() + " " + envMap[x][y].getWater() + " " + envMap[x][y].getAnimal() + " " + envMap[x][y].getPlant() + " " + "\n");
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

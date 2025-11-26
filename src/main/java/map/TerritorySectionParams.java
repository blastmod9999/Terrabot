package map;

import entities.air.Air;
import entities.animal.Animals;
import entities.plant.Plants;
import entities.soil.Soil;
import entities.water.Water;

import java.util.ArrayList;

public class TerritorySectionParams {
    private ArrayList<Soil> soil;
    private ArrayList<Plants> plants;
    private ArrayList<Animals> animals;
    private ArrayList<Water> water;
    private ArrayList<Air> air;
    //test push pc buc
    public ArrayList<Soil> getSoil() {
        return soil;
    }

    public void setSoil(ArrayList<Soil> soil) {
        this.soil = soil;
    }

    public ArrayList<Plants> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<Plants> plants) {
        this.plants = plants;
    }

    public ArrayList<Animals> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animals> animals) {
        this.animals = animals;
    }

    public ArrayList<Water> getWater() {
        return water;
    }

    public void setWater(ArrayList<Water> water) {
        this.water = water;
    }

    public ArrayList<Air> getAir() {
        return air;
    }

    public void setAir(ArrayList<Air> air) {
        this.air = air;
    }
}


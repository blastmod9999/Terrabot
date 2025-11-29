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

    /**
     * Javadoc for method getSoil.
     */
    public ArrayList<Soil> getSoil() {
        return soil;
    }

    /**
     * Javadoc for method setSoil.
     */
    public void setSoil(final ArrayList<Soil> soil) {
        this.soil = soil;
    }

    /**
     * Javadoc for method getPlants.
     */
    public ArrayList<Plants> getPlants() {
        return plants;
    }

    /**
     * Javadoc for method setPlants.
     */
    public void setPlants(final ArrayList<Plants> plants) {
        this.plants = plants;
    }

    /**
     * Javadoc for method getAnimals.
     */
    public ArrayList<Animals> getAnimals() {
        return animals;
    }

    /**
     * Javadoc for method setAnimals.
     */
    public void setAnimals(final ArrayList<Animals> animals) {
        this.animals = animals;
    }

    /**
     * Javadoc for method getWater.
     */
    public ArrayList<Water> getWater() {
        return water;
    }

    /**
     * Javadoc for method setWater.
     */
    public void setWater(final ArrayList<Water> water) {
        this.water = water;
    }

    /**
     * Javadoc for method getAir.
     */
    public ArrayList<Air> getAir() {
        return air;
    }

    /**
     * Javadoc for method setAir.
     */
    public void setAir(final ArrayList<Air> air) {
        this.air = air;
    }
}


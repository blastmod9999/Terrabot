package map;

import entities.air.Air;
import entities.animal.Animals;
import entities.plant.Plants;
import entities.soil.Soil;
import entities.water.Water;

public class MapBox {
    private Animals animal;
    private Plants plant;
    private Air air;
    private Soil soil;
    private Water water;

    /**
     * Javadoc for method getAnimal.
     */
    public Animals getAnimal() {
        return animal;
    }

    /**
     * Javadoc for method setAnimal.
     */
    public void setAnimal(final Animals animal) {
        this.animal = animal;
    }

    /**
     * Javadoc for method getPlant.
     */
    public Plants getPlant() {
        return plant;
    }

    /**
     * Javadoc for method setPlant.
     */
    public void setPlant(final Plants plant) {
        this.plant = plant;
    }

    /**
     * Javadoc for method getAir.
     */
    public Air getAir() {
        return air;
    }

    /**
     * Javadoc for method setAir.
     */
    public void setAir(final Air air) {
        this.air = air;
    }

    /**
     * Javadoc for method getSoil.
     */
    public Soil getSoil() {
        return soil;
    }

    /**
     * Javadoc for method setSoil.
     */
    public void setSoil(final Soil soil) {
        this.soil = soil;
    }

    /**
     * Javadoc for method getWater.
     */
    public Water getWater() {
        return water;
    }

    /**
     * Javadoc for method setWater.
     */
    public void setWater(final Water water) {
        this.water = water;
    }
}


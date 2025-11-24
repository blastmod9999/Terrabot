package entities.water;

import entities.Entities;

public class Water extends Entities {
    private float purity;
    private float salinity;
    private int turbidity;
    private float contaminantIndex;
    private float pH;
    private boolean isFrozen;


    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getPurity() {
        return purity;
    }

    public void setPurity(float purity) {
        this.purity = purity;
    }

    public float getSalinity() {
        return salinity;
    }

    public void setSalinity(float salinity) {
        this.salinity = salinity;
    }

    public int getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(int turbidity) {
        this.turbidity = turbidity;
    }

    public float getContaminantIndex() {
        return contaminantIndex;
    }

    public void setContaminantIndex(float contaminantIndex) {
        this.contaminantIndex = contaminantIndex;
    }

    public float getpH() {
        return pH;
    }

    public void setpH(float pH) {
        this.pH = pH;
    }

    public boolean getisFrozen() {
        return isFrozen;
    }

    public void setisFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }
}

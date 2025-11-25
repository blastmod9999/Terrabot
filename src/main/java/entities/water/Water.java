package entities.water;

import entities.Entities;

import static java.lang.Math.abs;

public class Water extends Entities {
    private float purity;
    private float salinity;
    private float turbidity;
    private float contaminantIndex;
    private float pH;
    private boolean isFrozen;
    private double water_quality;

    public void setWater_quality()
    {
        double purity_score        = purity / 100;
        double pH_score            = 1 - abs(pH - 7.5) / 7.5;
        double salinity_score      = 1 - (salinity / 350);
        double turbidity_score     = 1 - (turbidity / 100);
        double contaminant_score   = 1 - (contaminantIndex / 100);
        double frozen_score        = isFrozen ? 0 : 1;

        this.water_quality = (0.3 * purity
                + 0.2 * pH_score
                + 0.15 * salinity_score
                + 0.1 * turbidity_score
                + 0.15 * contaminant_score
                + 0.2 * frozen_score) * 100;
    }

    public double getWater_quality() {
        return water_quality;
    }

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

    public float getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(float turbidity) {
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

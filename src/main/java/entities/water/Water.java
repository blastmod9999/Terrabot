package entities.water;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Entities;
import entities.air.Air;
import entities.plant.Plants;
import entities.soil.Soil;
import map.MapBox;
import utils.MagicNumbers;

import static java.lang.Math.abs;
import static utils.MagicNumbers.ONE_HUNDRED_INT;
import static utils.MagicNumbers.ZERO_POINT_ONE_FIVE;
import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;


public class Water extends Entities {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private float purity;
    private float salinity;
    private float turbidity;
    private float contaminantIndex;
    private float pH;
    private boolean isFrozen;
    private double waterQuality;
    @JsonIgnore
    private int iterationUpdates;

    /**
     * Javadoc for method setWater_quality.
     */
    public void setWaterQuality() {
        final double purityScore = purity / ONE_HUNDRED_INT;
        final double pHScore = 1 - abs(pH - 7.5) / 7.5;
        final double salinityScore = 1 - (salinity / 350);
        final double turbidityScore = 1 - (turbidity / ONE_HUNDRED_INT);
        final double contaminantScore = 1 - (contaminantIndex / ONE_HUNDRED_INT);
        final double frozenScore = isFrozen ? 0 : 1;

        this.waterQuality = (MagicNumbers.POINT_THREE * purity
                + MagicNumbers.POINT_TWO * pHScore
                + ZERO_POINT_ONE_FIVE * salinityScore
                + MagicNumbers.POINT_ONE * turbidityScore
                + ZERO_POINT_ONE_FIVE * contaminantScore
                + MagicNumbers.POINT_TWO * frozenScore) * ONE_HUNDRED_INT;
    }

    /**
     * Javadoc for method copy.
     * facem deepcopy referinta in air
     */
    public Water copy() {
        try {
            return MAPPER.treeToValue(MAPPER.valueToTree(this), Water.class);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Javadoc for method UpdateBox.
     */
    public void updateBox(final MapBox box) {

        //TIMP DE 2 ITERATII CRESTE SI FACE CE TREBUIE LA SOIL
        iterationUpdates++;
        if (iterationUpdates % 2 == 0) {
            if (box.getSoil() != null) {
                final Soil soil = box.getSoil();
                double updated = soil.getWaterRetention() + MagicNumbers.POINT_ONE;
                updated = Math.round(updated * ONE_HUNDRED_INT) / ONE_HUNDRED_DOUBLE;
                soil.setWaterRetention(updated);
            }
            if (box.getAir() != null) {
                final Air air = box.getAir();
                double updated = air.getHumidity() + MagicNumbers.POINT_ONE;
                updated = Math.round(updated * ONE_HUNDRED_INT) / ONE_HUNDRED_DOUBLE;
                air.setHumidity(updated);
            }

        }

        if (box.getPlant() != null) {
            final Plants plant = box.getPlant();
            double updated = plant.getGrowth() + MagicNumbers.POINT_TWO;
            updated = Math.round(updated * ONE_HUNDRED_INT) / ONE_HUNDRED_DOUBLE;
            plant.setGrowth(updated);
        }

    }

    /**
     * Javadoc for method getWater_quality.
     */
    public double getWaterQuality() {
        return waterQuality;
    }

    /**
     * Javadoc for method getPurity.
     */
    public float getPurity() {
        return purity;
    }

    /**
     * Javadoc for method setPurity.
     */
    public void setPurity(final float purity) {
        this.purity = purity;
    }

    /**
     * Javadoc for method getSalinity.
     */
    public float getSalinity() {
        return salinity;
    }

    /**
     * Javadoc for method setSalinity.
     */
    public void setSalinity(final float salinity) {
        this.salinity = salinity;
    }

    /**
     * Javadoc for method getTurbidity.
     */
    public float getTurbidity() {
        return turbidity;
    }

    /**
     * Javadoc for method setTurbidity.
     */
    public void setTurbidity(final float turbidity) {
        this.turbidity = turbidity;
    }

    /**
     * Javadoc for method getContaminantIndex.
     */
    public float getContaminantIndex() {
        return contaminantIndex;
    }

    /**
     * Javadoc for method setContaminantIndex.
     */
    public void setContaminantIndex(final float contaminantIndex) {
        this.contaminantIndex = contaminantIndex;
    }

    /**
     * Javadoc for method getpH.
     */
    public float getpH() {
        return pH;
    }

    /**
     * Javadoc for method setpH.
     */
    public void setpH(final float pH) {
        this.pH = pH;
    }

    /**
     * Javadoc for method getisFrozen.
     */
    public boolean getisFrozen() {
        return isFrozen;
    }

    /**
     * Javadoc for method setisFrozen.
     */
    public void setisFrozen(final boolean isFrozen) {
        this.isFrozen = isFrozen;
    }
}

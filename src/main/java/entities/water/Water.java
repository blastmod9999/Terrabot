package entities.water;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Entities;
import entities.air.Air;
import entities.plant.Plants;
import entities.soil.Soil;
import map.MapBox;

import static java.lang.Math.abs;

public class Water extends Entities {
    private float purity;
    private float salinity;
    private float turbidity;
    private float contaminantIndex;
    private float pH;
    private boolean isFrozen;
    private double water_quality;
    @JsonIgnore
    private int iterationUpdates;

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

    private static final ObjectMapper MAPPER = new ObjectMapper();

    //Facem deepcopy nu Shallow
    // + try catch generat de IDE
    // Source - https://stackoverflow.com/questions/49903859/deep-copy-using-jackson-string-or-jsonnode
// Posted by Rad, modified by community. See post 'Timeline' for change history
// Retrieved 2025-11-27, License - CC BY-SA 3.0

    //MyPojo myPojo = new MyPojo();
    //ObjectMapper mapper = new ObjectMapper();
    //MyPojo newPojo = mapper.treeToValue(mapper.valueToTree(myPojo), MyPojo.class);

    public Water copy() {
        try {
            return MAPPER.treeToValue(MAPPER.valueToTree(this), Water.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateBox(MapBox box) {


        //TIMP DE 2 ITERATII CRESTE SI FACE CE TREBUIE LA SOIL
        iterationUpdates++;
        if(iterationUpdates % 2 == 0)
        {
            if(box.getSoil()!=null){
                Soil soil = box.getSoil();
                double updated = soil.getWaterRetention() + 0.1;
                updated = Math.round(updated * 100) / 100.0;
                soil.setWaterRetention(updated);
            }
            if(box.getAir()!=null){
                Air air = box.getAir();
                double updated = air.getHumidity() + 0.1;
                updated = Math.round(updated * 100) / 100.0;
                air.setHumidity(updated);
            }

        }

        if(box.getPlant()!=null) {
            Plants plant = box.getPlant();
            double updated = plant.getGrowth() + 0.2;
            updated = Math.round(updated * 100) / 100.0;
            plant.setGrowth(updated);
        }

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

package entities.plant;

import entities.air.Air;
import map.MapBox;

public class FloweringPlants extends Plants {

    @Override
    public void UpdateBox(Air air) {

        growth += 0.2;
        double bonus = 0.2;

        growth = Math.round(growth * 100) / 100.0;
        if (growth >= 1.0) {
            bonus = 0.7;
        }

        if(growth >= 2.0) {
            bonus = 0.4;
        }

        if(growth >= 3.0) {
            return;
        }
        double currentO2 = air.getOxygenLevel();
        double updated = currentO2 + 6.0 + bonus;

        updated = Math.round(updated * 100) / 100.0;



        air.setOxygenLevel(updated);
        air.setAirQualityScore();

    }
}
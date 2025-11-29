package entities.air;

import lombok.Getter;
import lombok.Setter;
import simulation.Commands;
import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_HUNDRED_INT;
import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.MAX_DESERT;
import static utils.MagicNumbers.THIRTY;


public final class DesertAir extends Air {

    //@JsonIgnore
    @Getter
    @Setter
    private double dustParticles; //desert
    private double airQualityScore;

    @Getter
    @Setter
    private boolean desertStorm;


    @Override
    public void resetWeather() {
        this.desertStorm = false;
        setAirQualityScore();
    }

    @Override
    public boolean applyWeatherConditions(final Commands command) {
        if (command.getType().equals("desertStorm")) {
            this.desertStorm = command.isDesertStorm();
            setAirQualityScore();
            return true;
        }
        return false;
    }


    @Override
    public void setAirQualityScore() {
        airQualityScore = (getOxygenLevel() * 2) - (dustParticles * MagicNumbers.POINT_TWO)
                - (getTemperature() * MagicNumbers.POINT_THREE);
        final double normalizeScore = Math.max(0, Math.min(ONE_HUNDRED_INT, airQualityScore));
        airQualityScore = Math.round(normalizeScore * ONE_HUNDRED_DOUBLE) / ONE_HUNDRED_DOUBLE;

        if (this.desertStorm) {
            airQualityScore -= THIRTY;
        }

        setAirQuality(airQualityScore);

        //toxicitate:
        setAirToxicity(airQualityScore, MAX_DESERT);
    }

    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }

}

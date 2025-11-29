package entities.air;

import lombok.Getter;
import lombok.Setter;
import simulation.Commands;
import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_HUNDRED_INT;
import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.MAX_POLAR;
import static utils.MagicNumbers.ZERO_POINT_ZERO_FIVE;

public final class PolarAir extends Air {

    @Setter
    @Getter
    private double iceCrystalConcentration; //polar
    private double airQualityScore;
    private double windSpeed;


    @Override
    public void resetWeather() {
        this.windSpeed = 0;
        setAirQualityScore();
    }

    @Override
    public boolean applyWeatherConditions(final Commands command) {
        if (command.getType().equals("polarStorm")) {
            this.windSpeed = command.getWindSpeed();
            setAirQualityScore();
            return true;
        }
        return false;
    }


    @Override
    public void setAirQualityScore() {
        airQualityScore =
                (getOxygenLevel() * 2) + (ONE_HUNDRED_INT - Math.abs(getTemperature())) - (
                        iceCrystalConcentration * ZERO_POINT_ZERO_FIVE);
        final double normalizeScore = Math.max(0, Math.min(ONE_HUNDRED_INT, airQualityScore));
        airQualityScore = Math.round(normalizeScore * ONE_HUNDRED_DOUBLE) / ONE_HUNDRED_DOUBLE;

        if (windSpeed > 0) {
            airQualityScore -= (windSpeed * MagicNumbers.POINT_TWO);
        }

        setAirQuality(airQualityScore);

        setAirToxicity(airQualityScore, MAX_POLAR);
    }

    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }

}

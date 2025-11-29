package entities.air;

import simulation.Commands;
import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_HUNDRED_INT;
import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.MAX_POLAR;
import static utils.MagicNumbers.ZERO_POINT_ZERO_FIVE;

public final class PolarAir extends Air {
    private double iceCrystalConcentration; //polar
    private double airQualityScore;
    private double windSpeed;



/**
 * Javadoc for method resetWeather.
 */
    @Override
    public void resetWeather() {
        this.windSpeed = 0;
        setAirQualityScore();
    }


/**
 * Javadoc for method ApplyWeatherConditions.
 */
    @Override
    public boolean applyWeatherConditions(final Commands command) {
        if (command.getType().equals("polarStorm")) {
            this.windSpeed = command.getWindSpeed();
            setAirQualityScore();
            return true;
        }
        return false;
    }



/**
 * Javadoc for method setAirQualityScore.
 */
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


/**
 * Javadoc for method getAirQualityScore.
 */
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }

    /**
     * Javadoc for method getIceCrystalConcentration.
     */
    public double getIceCrystalConcentration() {
        return iceCrystalConcentration;
    }

    /**
     * Javadoc for method setIceCrystalConcentration.
     */
    public void setIceCrystalConcentration(final double iceCrystalConcentration) {
        this.iceCrystalConcentration = iceCrystalConcentration;
    }
}

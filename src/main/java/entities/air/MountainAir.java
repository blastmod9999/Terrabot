package entities.air;

import simulation.Commands;
import utils.MagicNumbers;

import static utils.MagicNumbers.MAX_MOUNTAIN;

public final class MountainAir extends Air {
    private double altitude; //montan
    private double airQualityScore;
    private int numberOfHikers;


/**
 * Javadoc for method ApplyWeatherConditions.
 */
    @Override
    public boolean applyWeatherConditions(final Commands command) {
        if (command.getType().equals("peopleHiking")) {
            this.numberOfHikers = command.getNumberOfHikers();
            setAirQualityScore();
            return true;
        }

        return false;
    }


/**
 * Javadoc for method resetWeather.
 */
    @Override
    public void resetWeather() {
        this.numberOfHikers = 0;
        setAirQualityScore();
    }


/**
 * Javadoc for method setAirQualityScore.
 */
    @Override
    public void setAirQualityScore() {

        final double oxygenFactor =
                getOxygenLevel() - ((altitude / 1000.0) * MagicNumbers.POINT_FIVE);
        airQualityScore = (oxygenFactor * 2) + (getHumidity() * MagicNumbers.POINT_SIX);

        if (numberOfHikers > 0) {
            airQualityScore = airQualityScore - (numberOfHikers * MagicNumbers.POINT_ONE);
        }
        setAirQuality(airQualityScore);

        setAirToxicity(airQualityScore, MAX_MOUNTAIN);

    }


/**
 * Javadoc for method getAirQualityScore.
 */
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }

    /**
     * Javadoc for method getAltitude.
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Javadoc for method setAltitude.
     */
    public void setAltitude(final double altitude) {
        this.altitude = altitude;
    }
}

package entities.air;

import simulation.Commands;
import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_HUNDRED_INT;
import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.FIFTEEN;
import static utils.MagicNumbers.MAX_TEMPERATE;

public final class TemperateAir extends Air {
    private double pollenLevel; //temperat
    private double airQualityScore;
    private String newSeason = "";


/**
 * Javadoc for method resetWeather.
 */
    @Override
    public void resetWeather() {
        this.newSeason = "";
        setAirQualityScore();
    }

/**
 * Javadoc for method ApplyWeatherConditions.
 */
    @Override
    public boolean applyWeatherConditions(final Commands command) {
        if (command.getType().equals("newSeason")) {
            this.newSeason = command.getSeason();
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
                (getOxygenLevel() * 2) + (getHumidity() * MagicNumbers.POINT_SEVEN) - (pollenLevel
                        * MagicNumbers.POINT_ONE);
        final double normalizeScore = Math.max(0, Math.min(ONE_HUNDRED_INT, airQualityScore));
        airQualityScore = Math.round(normalizeScore * ONE_HUNDRED_DOUBLE) / ONE_HUNDRED_DOUBLE;


        int seasonPenalty = 0;
        if ("Spring".equalsIgnoreCase(newSeason)) {
            seasonPenalty = FIFTEEN;
            airQualityScore -= seasonPenalty;
        }


        setAirQuality(airQualityScore);

        setAirToxicity(airQualityScore, MAX_TEMPERATE);
    }

/**
 * Javadoc for method getAirQualityScore.
 */
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }

    /**
     * Javadoc for method getPollenLevel.
     */
    public double getPollenLevel() {
        return pollenLevel;
    }

    /**
     * Javadoc for method setPollenLevel.
     */
    public void setPollenLevel(final double pollenLevel) {
        this.pollenLevel = pollenLevel;
    }
}

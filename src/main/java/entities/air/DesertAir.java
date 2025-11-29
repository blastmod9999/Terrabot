package entities.air;

import simulation.Commands;
import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_HUNDRED_INT;
import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.MAX_DESERT;
import static utils.MagicNumbers.THIRTY;


public final class DesertAir extends Air {
    //@JsonIgnore
    private double dustParticles; //desert
    private double airQualityScore;
    private boolean desertStorm;

/**
 * Javadoc for method isDesertStorm.
 */
    /**
     * Javadoc for method isDesertStorm.
     */
    public boolean isDesertStorm() {
        return desertStorm;
    }

/**
 * Javadoc for method setDesertStorm.
 */
    /**
     * Javadoc for method setDesertStorm.
     */
    public void setDesertStorm(final boolean desertStorm) {
        this.desertStorm = desertStorm;
    }

    @Override
/**
 * Javadoc for method resetWeather.
 */
/**
 * Javadoc for method resetWeather.
 */
    public void resetWeather() {
        this.desertStorm = false;
        setAirQualityScore();
    }

/**
 * Javadoc for method ApplyWeatherConditions.
 */
/**
 * Javadoc for method ApplyWeatherConditions.
 */
    @Override
    public boolean applyWeatherConditions(final Commands command) {
        if (command.getType().equals("desertStorm")) {
            //IO.println("AAAAAAAAAAAM INTRAT AICI" + command.isDesertStorm());
            this.desertStorm = command.isDesertStorm();
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

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
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

/**
 * Javadoc for method getAirQualityScore.
 */
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }

    /**
     * Javadoc for method getDustParticles.
     */
    public double getDustParticles() {
        return dustParticles;
    }

    /**
     * Javadoc for method setDustParticles.
     */
    public void setDustParticles(final double dustParticles) {
        this.dustParticles = dustParticles;
    }
}

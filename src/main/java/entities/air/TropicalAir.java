package entities.air;

import lombok.Getter;
import simulation.Commands;
import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_HUNDRED_INT;
import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.ZERO_POINT_ZERO_ONE;

public final class TropicalAir extends Air {
    @Getter
    private double co2Level; //tropical
    private double airQualityScore;
    private double rainfall;


    @Override
    public void resetWeather() {
        this.rainfall = 0;
        setAirQualityScore();
    }


    @Override
    public boolean applyWeatherConditions(final Commands command) {
        if (command.getType().equals("rainfall")) {
            this.rainfall = command.getRainfall();
            setAirQualityScore();
            return true;
        }

        return false;
    }

    @Override
    public void setAirQualityScore() {
        airQualityScore =
                (getOxygenLevel() * 2) + (getHumidity() * MagicNumbers.POINT_FIVE) - (co2Level
                        * ZERO_POINT_ZERO_ONE);
        final double normalizeScore = Math.max(0, Math.min(ONE_HUNDRED_INT, airQualityScore));
        airQualityScore = Math.round(normalizeScore * ONE_HUNDRED_DOUBLE) / ONE_HUNDRED_DOUBLE;

        if (rainfall > 0) {
            airQualityScore += (rainfall * MagicNumbers.POINT_THREE);
        }

        setAirQuality(airQualityScore);


        setAirToxicity(airQualityScore, MagicNumbers.MAX_TROPICAL);
    }


    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }


    public void setCo2Level(final double co2Level) {
        this.co2Level = Math.round(co2Level * ONE_HUNDRED_DOUBLE) / ONE_HUNDRED_DOUBLE;
    }
}

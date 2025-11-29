package entities.air;

import lombok.Getter;
import lombok.Setter;
import simulation.Commands;
import utils.MagicNumbers;

import static utils.MagicNumbers.MAX_MOUNTAIN;

public final class MountainAir extends Air {

    @Setter
    @Getter
    private double altitude; //montan
    private double airQualityScore;
    private int numberOfHikers;


    @Override
    public boolean applyWeatherConditions(final Commands command) {
        if (command.getType().equals("peopleHiking")) {
            this.numberOfHikers = command.getNumberOfHikers();
            setAirQualityScore();
            return true;
        }

        return false;
    }


    @Override
    public void resetWeather() {
        this.numberOfHikers = 0;
        setAirQualityScore();
    }


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

    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }

}

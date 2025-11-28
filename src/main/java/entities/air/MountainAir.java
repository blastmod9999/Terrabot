package entities.air;

import simulation.Commands;

public class MountainAir extends Air {
    private double altitude; //montan
    private double airQualityScore;
    private int numberOfHikers;

    @Override
    public boolean ApplyWeatherConditions(Commands command) {
        if(command.getType().equals("peopleHiking")) {
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

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
        double oxygenFactor = getOxygenLevel() - ((altitude / 1000.0) * 0.5);
        airQualityScore = (oxygenFactor * 2) + (getHumidity() * 0.6);

        if(numberOfHikers > 0){
            airQualityScore = airQualityScore - (numberOfHikers * 0.1);
        }
        setAirQuality(airQualityScore);

        setAirToxicity(airQualityScore,78);

    }
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}

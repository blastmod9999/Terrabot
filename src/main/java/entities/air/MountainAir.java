package entities.air;

public class MountainAir extends Air {
    private float altitude; //montan
    private double airQualityScore;

    @Override
    public void setAirQualityScore() {

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
        double oxygenFactor = getOxygenLevel() - ((altitude / 1000.0) * 0.5);
        airQualityScore = (oxygenFactor * 2) + (getHumidity() * 0.6);
        setAirQuality(airQualityScore);
    }
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }
}

package entities.air;

public class TemperateAir extends Air {
    private float pollenLevel; //temperat
    private double airQualityScore;

    @Override
    public void setAirQualityScore() {

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
        airQualityScore = 	(getOxygenLevel()*2) + (getHumidity()*0.7) - (pollenLevel*0.1);
        double normalizeScore = Math.max(0, Math.min(100, airQualityScore));
        airQualityScore = Math.round(normalizeScore * 100.0) / 100.0;
        setAirQuality(airQualityScore);

        setAirToxicity(airQualityScore,84);
    }
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }
    public float getPollenLevel() {
        return pollenLevel;
    }

    public void setPollenLevel(float pollenLevel) {
        this.pollenLevel = pollenLevel;
    }
}

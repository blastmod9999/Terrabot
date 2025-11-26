package entities.air;

public class TropicalAir extends Air {
    private float co2Level; //tropical
    private double airQualityScore;

    @Override
    public void setAirQualityScore() {

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
        airQualityScore = (getOxygenLevel()*2) + (getHumidity()*0.5) - (co2Level*0.01);
        //airQualityScore = (oxygenFactor * 2) + (getHumidity() * 0.6);
        double normalizeScore = Math.max(0, Math.min(100, airQualityScore));
        airQualityScore = Math.round(normalizeScore * 100.0) / 100.0;
        setAirQuality(airQualityScore);


        setAirToxicity(airQualityScore,82);
    }
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }
    public float getCo2Level() {
        return co2Level;
    }

    public void setCo2Level(float co2Level) {
        this.co2Level = co2Level;
    }
}

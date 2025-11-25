package entities.air;

public class PolarAir extends Air {
    private float iceCrystalConcentration; //polar
    private double airQualityScore;

    @Override
    public void setAirQualityScore() {

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
        airQualityScore = (getOxygenLevel()*2) + (100-Math.abs(getTemperature())) - (iceCrystalConcentration*0.05);
        double normalizeScore = Math.max(0, Math.min(100, airQualityScore));
        airQualityScore = Math.round(normalizeScore * 100.0) / 100.0;
        setAirQuality(airQualityScore);
    }
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }
    public float getIceCrystalConcentration() {
        return iceCrystalConcentration;
    }

    public void setIceCrystalConcentration(float iceCrystalConcentration) {
        this.iceCrystalConcentration = iceCrystalConcentration;
    }
}

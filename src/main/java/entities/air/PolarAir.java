package entities.air;

public class PolarAir extends Air {
    private double iceCrystalConcentration; //polar
    private double airQualityScore;

    @Override
    public void setAirQualityScore() {

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
        airQualityScore = (getOxygenLevel()*2) + (100-Math.abs(getTemperature())) - (iceCrystalConcentration*0.05);
        double normalizeScore = Math.max(0, Math.min(100, airQualityScore));
        airQualityScore = Math.round(normalizeScore * 100.0) / 100.0;
        setAirQuality(airQualityScore);

        setAirToxicity(airQualityScore,142);
    }
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }
    public double getIceCrystalConcentration() {
        return iceCrystalConcentration;
    }

    public void setIceCrystalConcentration(double iceCrystalConcentration) {
        this.iceCrystalConcentration = iceCrystalConcentration;
    }
}

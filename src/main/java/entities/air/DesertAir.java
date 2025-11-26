package entities.air;

public class DesertAir extends Air {
    private float dustParticles; //desert
    private double airQualityScore;

    @Override
    public void setAirQualityScore() {

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
        airQualityScore = 	(getOxygenLevel()*2) - (dustParticles*0.2) - (getTemperature()*0.3);
        double normalizeScore = Math.max(0, Math.min(100, airQualityScore));
        airQualityScore = Math.round(normalizeScore * 100.0) / 100.0;
        setAirQuality(airQualityScore);

        //toxicitate:
        setAirToxicity(airQualityScore,65);
    }
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }
    public float getDustParticles() {
        return dustParticles;
    }

    public void setDustParticles(float dustParticles) {
        this.dustParticles = dustParticles;
    }
}

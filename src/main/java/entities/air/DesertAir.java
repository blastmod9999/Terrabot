package entities.air;

import com.fasterxml.jackson.annotation.JsonIgnore;
import simulation.Commands;

public class DesertAir extends Air {
    //@JsonIgnore
    private double dustParticles; //desert
    private double airQualityScore;
    private boolean desertStorm;

    public boolean isDesertStorm() {
        return desertStorm;
    }

    public void setDesertStorm(boolean desertStorm) {
        this.desertStorm = desertStorm;
    }

    @Override
    public void resetWeather() {
        this.desertStorm = false;
        setAirQualityScore();
    }

    @Override
    public boolean ApplyWeatherConditions(Commands command) {
        if(command.getType().equals("desertStorm")) {
            IO.println("AAAAAAAAAAAAAAAAAAAAAAAAAM INTRAT AICI ------------------------------- " + command.isDesertStorm());
            this.desertStorm = command.isDesertStorm();
            setAirQualityScore();
            return true;
        }

        return false;
    }

    @Override
    public void setAirQualityScore() {

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
        airQualityScore = 	(getOxygenLevel()*2) - (dustParticles*0.2) - (getTemperature()*0.3);
        double normalizeScore = Math.max(0, Math.min(100, airQualityScore));
        airQualityScore = Math.round(normalizeScore * 100.0) / 100.0;

        if(this.desertStorm) {
            airQualityScore -= 30;
        }

        setAirQuality(airQualityScore);

        //toxicitate:
        setAirToxicity(airQualityScore,65);
    }
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }
    public double getDustParticles() {
        return dustParticles;
    }

    public void setDustParticles(double dustParticles) {
        this.dustParticles = dustParticles;
    }
}

package entities.air;

import simulation.Commands;

public class PolarAir extends Air {
    private double iceCrystalConcentration; //polar
    private double airQualityScore;
    private double windSpeed;

    @Override
    public void resetWeather() {
        this.windSpeed = 0;
        setAirQualityScore();
    }

    @Override
    public boolean ApplyWeatherConditions(Commands command) {
        if(command.getType().equals("polarStorm")) {
            IO.println("AAAAAAAAAAAAAAAAAAAAAAAAAM INTRAT AICI ------------------------------- " + command.isDesertStorm());
            this.windSpeed = command.getWindSpeed();
            setAirQualityScore();
            return true;
        }

        return false;
    }


    @Override
    public void setAirQualityScore() {

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
        airQualityScore = (getOxygenLevel()*2) + (100-Math.abs(getTemperature())) - (iceCrystalConcentration*0.05);
        double normalizeScore = Math.max(0, Math.min(100, airQualityScore));
        airQualityScore = Math.round(normalizeScore * 100.0) / 100.0;

        if(windSpeed > 0) {
            airQualityScore -= (windSpeed*0.2);
        }

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

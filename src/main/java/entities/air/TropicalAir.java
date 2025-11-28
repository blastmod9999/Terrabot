package entities.air;

import simulation.Commands;

public class TropicalAir extends Air {
    private double co2Level; //tropical
    private double airQualityScore;
    private double rainfall;

    @Override
    public void resetWeather() {
        this.rainfall = 0;
        setAirQualityScore();
    }

    @Override
    public boolean ApplyWeatherConditions(Commands command) {
        if(command.getType().equals("rainfall")) {
            IO.println("AAAAAAAAAAAAAAAAAAAAAAAAAM INTRAT AICI ------------------------------- " + command.isDesertStorm());
            this.rainfall = command.getRainfall();
            setAirQualityScore();
            return true;
        }

        return false;
    }

    @Override
    public void setAirQualityScore() {

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
        airQualityScore = (getOxygenLevel()*2) + (getHumidity()*0.5) - (co2Level*0.01);
        //airQualityScore = (oxygenFactor * 2) + (getHumidity() * 0.6);
        double normalizeScore = Math.max(0, Math.min(100, airQualityScore));
        airQualityScore = Math.round(normalizeScore * 100.0) / 100.0;

        if(rainfall > 0)
            airQualityScore += (rainfall * 0.3);

        setAirQuality(airQualityScore);


        setAirToxicity(airQualityScore,82);
    }
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }
    public double getCo2Level() {
        return co2Level;
    }

    public void setCo2Level(double co2Level) {
        this.co2Level = Math.round(co2Level * 100.0) / 100.0;
    }
}

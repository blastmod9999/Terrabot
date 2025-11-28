package entities.air;

import simulation.Commands;

public class TemperateAir extends Air {
    private double pollenLevel; //temperat
    private double airQualityScore;
    private String newSeason = "";

    @Override
    public void resetWeather() {
        this.newSeason = "";
        setAirQualityScore();
    }

    @Override
    public boolean ApplyWeatherConditions(Commands command) {
        if(command.getType().equals("newSeason")) {
            IO.println("AAAAAAAAAAAAAAAAAAAAAAAAAM INTRAT AICI ------------------------------- " + command.isDesertStorm());
            this.newSeason = command.getSeason();
            setAirQualityScore();
            return true;
        }

        return false;
    }


    @Override
    public void setAirQualityScore() {

        //IO.println(super.getOxygenLevel()+ "  " + super.getHumidity() +"\n" );
        airQualityScore = 	(getOxygenLevel()*2) + (getHumidity()*0.7) - (pollenLevel*0.1);
        double normalizeScore = Math.max(0, Math.min(100, airQualityScore));
        airQualityScore = Math.round(normalizeScore * 100.0) / 100.0;


            int seasonPenalty = 0;
            if ("Spring".equalsIgnoreCase(newSeason)) {
                seasonPenalty = 15;
                airQualityScore -= seasonPenalty;
            }


        setAirQuality(airQualityScore);

        setAirToxicity(airQualityScore,84);
    }
    @Override
    public double getAirQualityScore() {
        return airQualityScore;
    }
    public double getPollenLevel() {
        return pollenLevel;
    }

    public void setPollenLevel(double pollenLevel) {
        this.pollenLevel = pollenLevel;
    }
}

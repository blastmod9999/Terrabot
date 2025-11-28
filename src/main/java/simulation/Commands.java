package simulation;

public class Commands {
    private String command;
    private int timestamp;
    private int timeToCharge;

    private String type;
    private String season;
    private boolean desertStorm;
    private double rainfall;
    private double windSpeed;
    private double temperature;
    private int numberOfHikers;

    //Entitati
    private String color;
    private String smell;
    private String sound;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSmell() {
        return smell;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDesertStorm() {
        return desertStorm;
    }

    public void setDesertStorm(boolean desertStorm) {
        this.desertStorm = desertStorm;
    }

    public double getRainfall() {
        return rainfall;
    }

    public void setRainfall(double rainfall) {
        this.rainfall = rainfall;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getNumberOfHikers() {
        return numberOfHikers;
    }

    public void setNumberOfHikers(int numberOfHikers) {
        this.numberOfHikers = numberOfHikers;
    }

    public int getTimeToCharge() {
        return timeToCharge;
    }

    public void setTimeToCharge(int timeToCharge) {
        this.timeToCharge = timeToCharge;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}


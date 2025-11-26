package entities.air;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import entities.Entities;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PolarAir.class, name = "PolarAir"),
        @JsonSubTypes.Type(value = TemperateAir.class, name = "TemperateAir"),
        @JsonSubTypes.Type(value = TropicalAir.class, name = "TropicalAir"),
        @JsonSubTypes.Type(value = MountainAir.class, name = "MountainAir"),
        @JsonSubTypes.Type(value = DesertAir.class, name = "DesertAir")
})
public abstract class Air extends Entities {
    private float humidity;
    private float temperature;
    private float oxygenLevel;
    private String airQuality;
    @JsonIgnore
    private double airToxicity;

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(float oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public double getAirQualityScore() { return 0; }

    public void setAirToxicity(double airQualityScore, int maxScore) {
        double toxicityAQ = 100 * (1 - airQualityScore / maxScore);
        double finalResult = Math.round(toxicityAQ * 100.0) / 100.0;
        double normalizeScore = Math.max(0, Math.min(100, finalResult));
        finalResult = Math.round(normalizeScore * 100.0) / 100.0;
        this.airToxicity = finalResult;
    }

    public double getAirToxicity() {
        return airToxicity;
    }

    public void setAirQuality(double airQualityScore) {
        if(airQualityScore >= 70)
            airQuality = "good";
        else if (airQualityScore >= 40) {
            airQuality = "moderate";
        } else {
            airQuality = "poor";
        }
    }

    public void setAirQualityScore() {}
}

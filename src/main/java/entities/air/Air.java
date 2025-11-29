package entities.air;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Entities;
import simulation.Commands;
import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.ONE_HUNDRED_INT;


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

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private double humidity = 0;
    private double temperature = 0;
    private double oxygenLevel;
    private String airQuality;

    @JsonIgnore
    private double airToxicity;

    /**
     * Javadoc for method copy.
     * Se face deepCopy , nu facem Shallow pt ca altfel nu merge
     * referinte : https://stackoverflow.com/questions/49903859/
     * deep-copy-using-jackson-string-or-jsonnode
     */
    public Air copy() {
        try {
            return MAPPER.treeToValue(MAPPER.valueToTree(this), Air.class);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Javadoc for method ApplyWeatherConditions.
     */
    public boolean applyWeatherConditions(final Commands command) {
        return false;
    }

    /**
     * Javadoc for method getHumidity.
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Javadoc for method setHumidity.
     */
    public void setHumidity(final double humidity) {
        this.humidity = humidity;
    }

    /**
     * Javadoc for method getTemperature.
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Javadoc for method setTemperature.
     */
    public void setTemperature(final double temperature) {
        this.temperature = temperature;
    }

    /**
     * Javadoc for method getOxygenLevel.
     */
    public double getOxygenLevel() {
        return oxygenLevel;
    }

    /**
     * Javadoc for method setOxygenLevel.
     */
    public void setOxygenLevel(final double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    /**
     * Javadoc for method getAirQuality.
     */
    public String getAirQuality() {
        return airQuality;
    }

    /**
     * Javadoc for method setAirQuality.
     */
    public void setAirQuality(final double airQualityScore) {
        if (airQualityScore >= MagicNumbers.GOOD) {
            airQuality = "good";
        } else if (airQualityScore >= MagicNumbers.MODERATE) {
            airQuality = "moderate";
        } else {
            airQuality = "poor";
        }
    }

    /**
     * Javadoc for method getAirQualityScore.
     */
    public double getAirQualityScore() {
        return 0;
    }

    /**
     * Javadoc for method setAirToxicity.
     */
    public void setAirToxicity(final double airQualityScore, final int maxScore) {
        final double toxicityAQ = ONE_HUNDRED_INT * (1 - airQualityScore / maxScore);
        double finalResult = Math.round(toxicityAQ * ONE_HUNDRED_DOUBLE) / ONE_HUNDRED_DOUBLE;
        final double normalizeScore = Math.max(0, Math.min(ONE_HUNDRED_INT, finalResult));
        finalResult = Math.round(normalizeScore * ONE_HUNDRED_DOUBLE) / ONE_HUNDRED_DOUBLE;
        this.airToxicity = finalResult;
    }

    /**
     * Javadoc for method getAirToxicity.
     */
    public double getAirToxicity() {
        return airToxicity;
    }

    /**
     * Javadoc for method updateAirQualityScore.
     */
    public void updateAirQualityScore(final double score) {
    }

    /**
     * Javadoc for method setAirQualityScore.
     */
    public void setAirQualityScore() {
    }

    /**
     * Javadoc for method resetWeather.
     */
    public void resetWeather() {
    }

}

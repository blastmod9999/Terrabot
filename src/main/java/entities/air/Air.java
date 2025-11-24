package entities.air;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import entities.Entities;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
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
}

package entities.soil;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import entities.Entities;
import main.*;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ForestSoil.class, name = "ForestSoil"),
        @JsonSubTypes.Type(value = SwampSoil.class, name = "SwampSoil"),
        @JsonSubTypes.Type(value = TundraSoil.class, name = "TundraSoil"),
        @JsonSubTypes.Type(value = DesertSoil.class, name = "DesertSoil"),
        @JsonSubTypes.Type(value = GrasslandSoil.class, name = "GrasslandSoil")
})
public abstract class Soil extends Entities {
    private float nitrogen;
    private float waterRetention;
    private float soilpH;
    private float organicMatter;

    public float getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(float nitrogen) {
        this.nitrogen = nitrogen;
    }

    public float getWaterRetention() {
        return waterRetention;
    }

    public void setWaterRetention(float waterRetention) {
        this.waterRetention = waterRetention;
    }

    public float getSoilpH() {
        return soilpH;
    }

    public void setSoilpH(float soilpH) {
        this.soilpH = soilpH;
    }

    public float getOrganicMatter() {
        return organicMatter;
    }

    public void setOrganicMatter(float organicMatter) {
        this.organicMatter = organicMatter;
    }
}












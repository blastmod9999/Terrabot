package entities.soil;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import entities.Entities;
import main.*;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ForestSoil.class, name = "ForestSoil"),
        @JsonSubTypes.Type(value = SwampSoil.class, name = "SwampSoil"),
        @JsonSubTypes.Type(value = TundraSoil.class, name = "TundraSoil"),
        @JsonSubTypes.Type(value = DesertSoil.class, name = "DesertSoil"),
        @JsonSubTypes.Type(value = GrasslandSoil.class, name = "GrasslandSoil")
})
public abstract class Soil extends Entities {
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private String soilQuality;

    public double getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(double nitrogen) {
        this.nitrogen = nitrogen;
    }

    public double getWaterRetention() {
        return waterRetention;
    }

    public void setWaterRetention(double waterRetention) {
        this.waterRetention = waterRetention;
    }

    public double getSoilpH() {
        return soilpH;
    }

    public void setSoilpH(double soilpH) {
        this.soilpH = soilpH;
    }

    public double getOrganicMatter() {
        return organicMatter;
    }

    public void setOrganicMatter(double organicMatter) {
        this.organicMatter = organicMatter;
    }

    public void setSoilQualityScore() {}

    public double getSoilQualityScore() {return 0;}

    public void setSoilQuality(String soilQuality) {
        this.soilQuality = soilQuality;
    }
    public String getSoilQuality() {
        return soilQuality;
    }

    public double posibilityToGetStuck() {
        return 0;
    }
}












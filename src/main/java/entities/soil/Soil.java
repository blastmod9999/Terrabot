package entities.soil;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Entities;


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
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private String soilQuality;

    /**
     * Javadoc for method copy.
     * Se face deepcopy , referinta in AIR
     */
    public Soil copy() {
        try {
            return MAPPER.treeToValue(MAPPER.valueToTree(this), Soil.class);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Javadoc for method getNitrogen.
     */
    public double getNitrogen() {
        return nitrogen;
    }

    /**
     * Javadoc for method setNitrogen.
     */
    public void setNitrogen(final double nitrogen) {
        this.nitrogen = nitrogen;
    }

    /**
     * Javadoc for method getWaterRetention.
     */
    public double getWaterRetention() {
        return waterRetention;
    }

    /**
     * Javadoc for method setWaterRetention.
     */
    public void setWaterRetention(final double waterRetention) {
        this.waterRetention = waterRetention;
    }

    /**
     * Javadoc for method getSoilpH.
     */
    public double getSoilpH() {
        return soilpH;
    }

    /**
     * Javadoc for method setSoilpH.
     */
    public void setSoilpH(final double soilpH) {
        this.soilpH = soilpH;
    }

    /**
     * Javadoc for method getOrganicMatter.
     */
    public double getOrganicMatter() {
        return organicMatter;
    }

    /**
     * Javadoc for method setOrganicMatter.
     */
    public void setOrganicMatter(final double organicMatter) {
        this.organicMatter = organicMatter;
    }

    /**
     * Javadoc for method setSoilQualityScore.
     */
    public void setSoilQualityScore() {
    }

    /**
     * Javadoc for method getSoilQualityScore.
     */
    public double getSoilQualityScore() {
        return 0;
    }

    /**
     * Javadoc for method getSoilQuality.
     */
    public String getSoilQuality() {
        return soilQuality;
    }

    /**
     * Javadoc for method setSoilQuality.
     */
    public void setSoilQuality(final String soilQuality) {
        this.soilQuality = soilQuality;
    }

    /**
     * Javadoc for method posibilityToGetStuck.
     */
    public double posibilityToGetStuck() {
        return 0;
    }
}












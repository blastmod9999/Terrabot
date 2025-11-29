package entities.soil;

import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.ONE_HUNDRED_INT;

public final class DesertSoil extends Soil {
    private double salinity; //DesertSoil
    private double soilQualityScore;

    @Override
/**
 * Javadoc for method setSoilQualityScore.
 */
    public void setSoilQualityScore() {
        soilQualityScore = (getNitrogen() * MagicNumbers.POINT_FIVE) + (getWaterRetention()
                * MagicNumbers.POINT_THREE) - (salinity * 2);

        final double normalizeScore = Math.max(0, Math.min(ONE_HUNDRED_INT, soilQualityScore));
        final double finalResult =
                Math.round(normalizeScore * ONE_HUNDRED_DOUBLE) / ONE_HUNDRED_DOUBLE;
        soilQualityScore = finalResult;
        if (soilQualityScore >= MagicNumbers.GOOD) {
            setSoilQuality("good");
        } else if (soilQualityScore >= MagicNumbers.MODERATE) {
            setSoilQuality("moderate");
        } else {
            setSoilQuality("poor");
        }
    }

    /**
     * Javadoc for method getSalinity.
     */
    public double getSalinity() {
        return salinity;
    }

    /**
     * Javadoc for method setSalinity.
     */
    public void setSalinity(final double salinity) {
        this.salinity = salinity;
    }

    @Override
/**
 * Javadoc for method posibilityToGetStuck.
 */
    public double posibilityToGetStuck() {
        return ((ONE_HUNDRED_INT - getWaterRetention() + salinity) / ONE_HUNDRED_INT)
                * ONE_HUNDRED_INT;
    }
}

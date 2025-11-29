package entities.soil;

import utils.MagicNumbers;


import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.ONE_HUNDRED_INT;
import static utils.MagicNumbers.ONE_POINT_THREE;
import static utils.MagicNumbers.ONE_POINT_FIVE;
import static utils.MagicNumbers.FIFTY;
import static utils.MagicNumbers.SEVENTY_FIVE;

public final class GrasslandSoil extends Soil {
    private double rootDensity;  //GrasslandSoil
    private double soilQualityScore;

    @Override
/**
 * Javadoc for method setSoilQualityScore.
 */
    public void setSoilQualityScore() {
        soilQualityScore = (getNitrogen() * ONE_POINT_THREE) + (getOrganicMatter()
                * ONE_POINT_FIVE) + (rootDensity
                * MagicNumbers.POINT_EIGHT);

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

    @Override
/**
 * Javadoc for method posibilityToGetStuck.
 */
    public double posibilityToGetStuck() {
        return (((FIFTY - rootDensity) + getWaterRetention()
                * MagicNumbers.POINT_FIVE) / SEVENTY_FIVE) * ONE_HUNDRED_INT;
    }

    @Override
/**
 * Javadoc for method getSoilQualityScore.
 */
    public double getSoilQualityScore() {
        return soilQualityScore;
    }

    /**
     * Javadoc for method getRootDensity.
     */
    public double getRootDensity() {
        return rootDensity;
    }

    /**
     * Javadoc for method setRootDensity.
     */
    public void setRootDensity(final float rootDensity) {
        this.rootDensity = rootDensity;
    }
}

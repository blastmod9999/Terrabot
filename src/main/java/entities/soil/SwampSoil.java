package entities.soil;

import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_POINT_ONE;
import static utils.MagicNumbers.TWO_POINT_TWO;
import static utils.MagicNumbers.ONE_HUNDRED_INT;
import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.TEN;
import static utils.MagicNumbers.FIVE;


public final class SwampSoil extends Soil {
    private double waterLogging;  //SwampSoil
    private double soilQualityScore;

    @Override
    public void setSoilQualityScore() {
        soilQualityScore =
                (getNitrogen() * ONE_POINT_ONE) + (getOrganicMatter() * TWO_POINT_TWO)
                        - (getWaterLogging() * FIVE);

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
    public double posibilityToGetStuck() {
        return waterLogging * TEN;
    }

    @Override
    public double getSoilQualityScore() {
        return soilQualityScore;
    }

    public double getWaterLogging() {
        return waterLogging;
    }

    public void setWaterLogging(final double waterLogging) {
        this.waterLogging = waterLogging;
    }
}

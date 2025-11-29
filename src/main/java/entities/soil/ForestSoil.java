package entities.soil;

import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_HUNDRED_INT;
import static utils.MagicNumbers.ONE_POINT_TWO;
import static utils.MagicNumbers.ONE_POINT_FIVE;
import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.EIGHTY;

public final class ForestSoil extends Soil {
    private double leafLitter;  //ForestSoil
    private double soilQualityScore;

    @Override
    public void setSoilQualityScore() {
        soilQualityScore =
                (getNitrogen() * ONE_POINT_TWO) + (getOrganicMatter() * 2)
                        + (getWaterRetention() * ONE_POINT_FIVE)
                        + (leafLitter * MagicNumbers.POINT_THREE);

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
        return ((getWaterRetention() * MagicNumbers.POINT_SIX
                + leafLitter * MagicNumbers.POINT_FOUR) / EIGHTY) * ONE_HUNDRED_INT;
    }

    @Override
    public double getSoilQualityScore() {
        return soilQualityScore;
    }

    public double getLeafLitter() {
        return leafLitter;
    }

    public void setLeafLitter(final double leafLitter) {
        this.leafLitter = leafLitter;
    }
}


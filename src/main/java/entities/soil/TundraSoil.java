package entities.soil;

import lombok.Setter;
import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_HUNDRED_INT;
import static utils.MagicNumbers.ONE_POINT_FIVE;
import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.FIFTY;

public final class TundraSoil extends Soil {
    @Setter
    private double permafrostDepth; //TundraSoil
    private double soilQualityScore;

    @Override
    public void setSoilQualityScore() {
        soilQualityScore = (getNitrogen() * MagicNumbers.POINT_SEVEN) + (getOrganicMatter()
                * MagicNumbers.POINT_FIVE) - (permafrostDepth * ONE_POINT_FIVE);

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
        return ((FIFTY - permafrostDepth) / FIFTY) * ONE_HUNDRED_INT;
    }

    public double getPermafrostDepth() {
        return permafrostDepth;
    }

}

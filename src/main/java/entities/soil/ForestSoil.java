package entities.soil;

public class ForestSoil extends Soil {
    private float leafLitter;  //ForestSoil
    private double soilQualityScore;

    @Override
    public void setSoilQualityScore() {
        soilQualityScore = (getNitrogen() * 1.2) + (getOrganicMatter() * 2) + (getWaterRetention() * 1.5) + (leafLitter * 0.3);

        double normalizeScore = Math.max(0, Math.min(100, soilQualityScore));
        double finalResult = Math.round(normalizeScore * 100.0) / 100.0;
        soilQualityScore = finalResult;
        if(soilQualityScore >= 70)
            setSoilQuality("good");
        else if (soilQualityScore >= 40) {
            setSoilQuality("moderate");
        } else {
            setSoilQuality("poor");
        }
    }

//    public double getSoilQuality() {
//        return soilQuality;
//    }

    @Override
    public double getSoilQualityScore()
    {
        return soilQualityScore;
    }
    public float getLeafLitter() {
        return leafLitter;
    }

    public void setLeafLitter(float leafLitter) {
        this.leafLitter = leafLitter;
    }
}


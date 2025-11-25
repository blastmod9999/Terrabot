package entities.soil;

public class SwampSoil extends Soil {
    private float waterLogging;  //SwampSoil
    private double soilQualityScore;

    @Override
    public void setSoilQualityScore() {
        soilQualityScore = (getNitrogen() * 1.1) + (getOrganicMatter() * 2.2) - (getWaterLogging() * 5);

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
    public float getWaterLogging() {
        return waterLogging;
    }
    public void setWaterLogging(float waterLogging) {
        this.waterLogging = waterLogging;
    }
}

package entities.soil;

public class GrasslandSoil extends Soil {
    private float rootDensity;  //GrasslandSoil
    private double soilQualityScore;

    @Override
    public void setSoilQualityScore() {
        soilQualityScore = 		(getNitrogen() * 1.3) + (getOrganicMatter() * 1.5) + (rootDensity * 0.8);

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
    public float getRootDensity() {
        return rootDensity;
    }

    public void setRootDensity(float rootDensity) {
        this.rootDensity = rootDensity;
    }
}

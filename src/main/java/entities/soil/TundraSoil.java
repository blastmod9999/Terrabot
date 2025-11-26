package entities.soil;

public class TundraSoil extends Soil {
    private float permafrostDepth; //TundraSoil
    private double soilQualityScore;

    @Override
    public void setSoilQualityScore() {
        soilQualityScore = 		(getNitrogen() * 0.7) + (getOrganicMatter() * 0.5) - (permafrostDepth * 1.5);

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

    @Override
    public double posibilityToGetStuck(){
        return  ((50 - permafrostDepth) / 50) * 100;
    }

    public float getPermafrostDepth() {
        return permafrostDepth;
    }

    public void setPermafrostDepth(float permafrostDepth) {
        this.permafrostDepth = permafrostDepth;
    }
}

package entities.soil;

public class DesertSoil extends Soil {
    private float salinity; //DesertSoil
    private double soilQualityScore;

    @Override
    public void setSoilQualityScore() {
        soilQualityScore = 	(getNitrogen() * 0.5) + (getWaterRetention() * 0.3) - (salinity * 2);

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
    public float getSalinity() {
        return salinity;
    }

    public void setSalinity(float salinity) {
        this.salinity = salinity;
    }

    @Override
    public double posibilityToGetStuck(){
        return ((100 - getWaterRetention() + salinity) / 100) * 100;
    }
}

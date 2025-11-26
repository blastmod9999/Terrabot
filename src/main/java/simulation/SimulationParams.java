package simulation;

import map.TerritorySectionParams;

public class SimulationParams {

    private String territoryDim;
    private int energyPoints;
    private TerritorySectionParams territorySectionParams;

    public TerritorySectionParams getTerritorySectionParams() {
        return territorySectionParams;
    }

    public void setTerritorySectionParams(TerritorySectionParams territorySectionParams) {
        this.territorySectionParams = territorySectionParams;
    }

    public String getTerritoryDim() {
        return territoryDim;
    }

    public void setTerritoryDim(String territoryDim) {
        this.territoryDim = territoryDim;
    }

    public int getEnergyPoints() {
        return energyPoints;
    }
    public void setEnergyPoints(int energyPoints) {
        this.energyPoints = energyPoints;
    }
}


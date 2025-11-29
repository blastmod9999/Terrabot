package simulation;

import map.TerritorySectionParams;

public class SimulationParams {

    private String territoryDim;
    private int energyPoints;
    private TerritorySectionParams territorySectionParams;

    /**
     * Javadoc for method getTerritorySectionParams.
     */
    public TerritorySectionParams getTerritorySectionParams() {
        return territorySectionParams;
    }

    /**
     * Javadoc for method setTerritorySectionParams.
     */
    public void setTerritorySectionParams(final TerritorySectionParams territorySectionParams) {
        this.territorySectionParams = territorySectionParams;
    }

    /**
     * Javadoc for method getTerritoryDim.
     */
    public String getTerritoryDim() {
        return territoryDim;
    }

    /**
     * Javadoc for method setTerritoryDim.
     */
    public void setTerritoryDim(final String territoryDim) {
        this.territoryDim = territoryDim;
    }

    /**
     * Javadoc for method getEnergyPoints.
     */
    public int getEnergyPoints() {
        return energyPoints;
    }

    /**
     * Javadoc for method setEnergyPoints.
     */
    public void setEnergyPoints(final int energyPoints) {
        this.energyPoints = energyPoints;
    }
}


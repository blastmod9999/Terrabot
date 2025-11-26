package terraBot;

import map.InitializeMap;
import map.MapBox;


public class MoveDecider {
    private double possibilityToGetStuckInSoil;
    private double possibilityToGetDamagedByAir;
    private double possibilityToBeAttackedByAnimal;
    private double possibiltyToGetStuckInPlants;
    private double totalRisk;
    int calculatePossibilityToGetStuckInSoil(MapBox box)
    {
        if(box.getSoil()!=null)
        {
            possibilityToGetStuckInSoil = box.getSoil().posibilityToGetStuck();
            return 1;
        } else
        {
            possibilityToGetStuckInSoil = 0;
        }
        return 0;
    }

    int calculatePosibilityToGetDamagedByAir(MapBox box)
    {
        possibilityToGetDamagedByAir = box.getAir().getAirToxicity();
        return 1;
    }

    int calculatePosibilityToBeAttackedByAnimal(MapBox box)
    {
        int precent = 0;
        if(box.getAnimal()!=null)
        {
            if(box.getAnimal().type.equals("Herbivores")){
                precent = 85;
            } else if(box.getAnimal().type.equals("Carnivores")){
                precent = 30;
            } else if (box.getAnimal().type.equals("Omnivores")) {
                precent = 60;
            } else if (box.getAnimal().type.equals("Detritivores")) {
                precent = 90;
            } else if (box.getAnimal().type.equals("Parasites")) {
                precent = 10;
            }
            possibilityToBeAttackedByAnimal = (100.0 - precent) / 10.0;
            return 1;
        } else
        {
            precent = 0;
            possibilityToBeAttackedByAnimal = 0;
        }
        return 0;

    }

    int calculatePosibilityToGetStuckInPlants(MapBox box)
    {

        if(box.getPlant()!=null)
        {
            if(box.getPlant().type.equals("FloweringPlants")){
                possibiltyToGetStuckInPlants = 0.9;
            } else if(box.getPlant().type.equals("GymnospermsPlants")){
                possibiltyToGetStuckInPlants = 0.6;
            } else if (box.getPlant().type.equals("Ferns")) {
                possibiltyToGetStuckInPlants = 0.3;
            } else if (box.getPlant().type.equals("Mosses")) {
                possibiltyToGetStuckInPlants = 0.4;
            } else if (box.getPlant().type.equals("Algae")) {
                possibiltyToGetStuckInPlants = 0.2;
            }
            return 1;
        } else
        {
            possibiltyToGetStuckInPlants = 0;
        }
        return 0;

    }

    public int CalculateTotalRisk(MapBox box){
        int count = 0;
        count += calculatePossibilityToGetStuckInSoil(box);
        count += calculatePosibilityToBeAttackedByAnimal(box);
        count += calculatePosibilityToGetDamagedByAir(box);
        count += calculatePosibilityToGetStuckInPlants(box);

        totalRisk = (possibilityToBeAttackedByAnimal + possibilityToGetDamagedByAir + possibilityToGetStuckInSoil + possibiltyToGetStuckInPlants);

        double mean = Math.abs(totalRisk / count);
        int fn = (int)Math.round(mean);
        IO.println("COUNT " + count + " TOTAL = "+fn + " AIR : " + possibilityToGetDamagedByAir + " SOIL " + possibilityToGetStuckInSoil + " ANIMAL " + possibilityToBeAttackedByAnimal + " PLANTS " + possibiltyToGetStuckInPlants);
        return fn;
    }

}

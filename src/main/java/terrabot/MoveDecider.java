package terrabot;

import map.MapBox;
import utils.MagicNumbers;

import static utils.MagicNumbers.EIGHTY_FIVE;
import static utils.MagicNumbers.THIRTY;
import static utils.MagicNumbers.SIXTY;
import static utils.MagicNumbers.NINETY;
import static utils.MagicNumbers.TEN;
import static utils.MagicNumbers.TEN_POINT_ZERO;
import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;


public final class MoveDecider {
    private double possibilityToGetStuckInSoil;
    private double possibilityToGetDamagedByAir;
    private double possibilityToBeAttackedByAnimal;
    private double possibiltyToGetStuckInPlants;
    private double totalRisk;

    int calculatePossibilityToGetStuckInSoil(final MapBox box) {
        if (box.getSoil() != null) {
            possibilityToGetStuckInSoil = box.getSoil().posibilityToGetStuck();
            return 1;
        } else {
            possibilityToGetStuckInSoil = 0;
        }
        return 0;
    }

    int calculatePosibilityToGetDamagedByAir(final MapBox box) {
        possibilityToGetDamagedByAir = box.getAir().getAirToxicity();
        if (possibilityToGetDamagedByAir < 0) {
            possibilityToGetDamagedByAir = 0;
        }
        return 1;
    }

    int calculatePosibilityToBeAttackedByAnimal(final MapBox box) {
        int precent = 0;
        if (box.getAnimal() != null) {
            if (box.getAnimal().getType().equals("Herbivores")) {
                precent = EIGHTY_FIVE;
            } else if (box.getAnimal().getType().equals("Carnivores")) {
                precent = THIRTY;
            } else if (box.getAnimal().getType().equals("Omnivores")) {
                precent = SIXTY;
            } else if (box.getAnimal().getType().equals("Detritivores")) {
                precent = NINETY;
            } else if (box.getAnimal().getType().equals("Parasites")) {
                precent = TEN;
            }
            possibilityToBeAttackedByAnimal = (ONE_HUNDRED_DOUBLE - precent) / TEN_POINT_ZERO;
            return 1;
        } else {
            precent = 0;
            possibilityToBeAttackedByAnimal = 0;
        }
        return 0;

    }

    int calculatePosibilityToGetStuckInPlants(final MapBox box) {

        if (box.getPlant() != null) {
            if (box.getPlant().getType().equals("FloweringPlants")) {
                possibiltyToGetStuckInPlants = MagicNumbers.POINT_NINE;
            } else if (box.getPlant().getType().equals("GymnospermsPlants")) {
                possibiltyToGetStuckInPlants = MagicNumbers.POINT_SIX;
            } else if (box.getPlant().getType().equals("Ferns")) {
                possibiltyToGetStuckInPlants = MagicNumbers.POINT_THREE;
            } else if (box.getPlant().getType().equals("Mosses")) {
                possibiltyToGetStuckInPlants = MagicNumbers.POINT_FOUR;
            } else if (box.getPlant().getType().equals("Algae")) {
                possibiltyToGetStuckInPlants = MagicNumbers.POINT_TWO;
            }
            return 1;
        } else {
            possibiltyToGetStuckInPlants = 0;
        }
        return 0;

    }

    /**
     * Javadoc for method CalculateTotalRisk.
     */
    public int calculateTotalRisk(final MapBox box) {
        int count = 0;
        count += calculatePossibilityToGetStuckInSoil(box);
        count += calculatePosibilityToBeAttackedByAnimal(box);
        count += calculatePosibilityToGetDamagedByAir(box);
        count += calculatePosibilityToGetStuckInPlants(box);

        totalRisk = (possibilityToBeAttackedByAnimal + possibilityToGetDamagedByAir
                + possibilityToGetStuckInSoil + possibiltyToGetStuckInPlants);

        final double mean = Math.abs(totalRisk / count);
        final int fn = (int) Math.round(mean);
//        IO.println("COUNT " + count + " TOTAL = " + fn + " AIR : " + possibilityToGetDamagedByAir
//                           + " SOIL " + possibilityToGetStuckInSoil + " ANIMAL "
//                           + possibilityToBeAttackedByAnimal + " PLANTS "
//                           + possibiltyToGetStuckInPlants);
        return fn;
    }

}

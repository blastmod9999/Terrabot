package entities.plant;

import entities.air.Air;
import utils.MagicNumbers;

import static utils.MagicNumbers.ONE_HUNDRED_DOUBLE;
import static utils.MagicNumbers.ONE_HUNDRED_INT;

public final class Algae extends Plants {


/**
 * Javadoc for method UpdateBox.
 */
    @Override
    public void updateBox(final Air air) {
        final double o2 = MagicNumbers.POINT_FIVE;

        setGrowth(getGrowth() + MagicNumbers.POINT_TWO);
        double bonus = MagicNumbers.POINT_TWO;

        setGrowth(Math.round(getGrowth() * ONE_HUNDRED_INT) / ONE_HUNDRED_DOUBLE);
        if (getGrowth() >= 1.0) {
            bonus = MagicNumbers.POINT_SEVEN;
        }

        if (getGrowth() >= 2.0) {
            bonus = MagicNumbers.POINT_FOUR;
        }

        if (getGrowth() >= MagicNumbers.GROW_LIMIT) {
            return;
        }
        final double currentO2 = air.getOxygenLevel();
        double updated = currentO2 + o2 + bonus;

        updated = Math.round(updated * ONE_HUNDRED_INT) / ONE_HUNDRED_DOUBLE;


        air.setOxygenLevel(updated);
        air.setAirQualityScore();

    }

}

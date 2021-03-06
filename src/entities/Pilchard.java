package entities;

/**
 * Represents a pilchard.
 *
 * A sweet and simple fish which can be eaten by a {@link entities.Shark shark}.
 */
public class Pilchard extends Fish {
    private static final int DEATHCYCLE = 15;
    private static final int DEATHCYCLE_RANGE = DEATHCYCLE / 3;
    private static final int BREEDINGCYCLE = 1;
    private static final int BREEDINGCYCLE_RANGE = BREEDINGCYCLE / 3;
    private int breedingCycle;
    private int deathCycle;

    /**
     * The pilchard constructor
     * @param y The Y coordinate
     * @param x The X coordinate
     */
    public Pilchard(int y, int x) {
        super(y, x);

        this.deathCycle = Fish.getCycleAverage(DEATHCYCLE, DEATHCYCLE_RANGE);
        this.breedingCycle = Fish.getCycleAverage(BREEDINGCYCLE, BREEDINGCYCLE_RANGE);
    }

    @Override
    protected int getDeathCycle() {
        return this.deathCycle;
    }

    @Override
    protected int getBreedingCycle() {
        return this.breedingCycle;
    }

    @Override
    protected boolean isFamineDeath() {
        return false;
    }

    @Override
    public String toString() {
        return "*";
    }
}

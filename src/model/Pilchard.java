package model;

/**
 * Represents a pilchard.
 *
 * He can be eaten by a {@link model.Shark shark}.
 */
class Pilchard extends Fish {
    private static int DEATHCYCLE = 20;
    private static int DEATHCYCLE_RANGE = DEATHCYCLE / 3;
    private static int BREEDINGCYCLE = 2;
    private static int BREEDINGCYCLE_RANGE = BREEDINGCYCLE / 3;
    protected int breedingCycle;
    protected int deathCycle;

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
    public String toString() {
        return "*";
    }
}

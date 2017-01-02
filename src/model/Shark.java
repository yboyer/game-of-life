package model;

class Shark extends Fish {
/**
 * Represents a shark.
 *
 * He needs to eat {@link model.Pilchard pilchards} to survive ; otherwise, he starves.
 * He have three states: baby, teen and adult.
 * @see model.BabyShark
 * @see model.TeenShark
 * @see model.AdultShark
 */
    private static int DEATHCYCLE = 20;
    private static int DEATHCYCLE_RANGE = DEATHCYCLE / 3;
    private static int BREEDINGCYCLE = 4;
    private static int BREEDINGCYCLE_RANGE = BREEDINGCYCLE / 3;
    protected int breedingCycle;
    protected int deathCycle;

    /**
     * The shark constructor
     * @param y The Y coordinate
     * @param x The X coordinate
     */
    public Shark(int y, int x) {
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
        return "O";
    }
}

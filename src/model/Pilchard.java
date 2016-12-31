package model;

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
    public Pilchard(int x, int y) {
        super(x, y);

        this.deathCycle = Fish.getCycleAverage(DEATHCYCLE, DEATHCYCLE_RANGE);
        this.breedingCycle = Fish.getCycleAverage(BREEDINGCYCLE, BREEDINGCYCLE_RANGE);
    }

    /**
     * Get the death cycle
     * @return The death cycle
     */
    protected int getDeathCycle() {
        return deathCycle;
    }

    /**
     * Get the breed cycle
     * @return The breed cycle
     */
    protected int getBreedingCycle() {
        return breedingCycle;
    }

    /**
     * Return the display the fish
     * @return The display the fish
     */
    public String toString() {
        return "*";
    }
}

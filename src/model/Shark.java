package model;

class Shark extends Fish {
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
    public Shark(int x, int y) {
        super(x, y);

        this.deathCycle = Fish.getCycleAverage(DEATHCYCLE, DEATHCYCLE_RANGE);
        this.breedingCycle = Fish.getCycleAverage(BREEDINGCYCLE, BREEDINGCYCLE_RANGE);
    }

    /**
     * Get the death cycle
     * @return The death cycle
     */
    protected int getDeathCycle() {
        return this.deathCycle;
    }

    /**
     * Get the breed cycle
     * @return The breed cycle
     */
    protected int getBreedingCycle() {
        return this.breedingCycle;
    }

    /**
     * Return the display the fish
     * @return The display the fish
     */
    public String toString() {
        return "O";
    }
}

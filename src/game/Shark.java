package game;

import java.util.List;

/**
 * Represents a shark.
 *
 * He needs to eat {@link model.Pilchard pilchards} to survive ;
 * otherwise,he starves.
 * He have three states: baby, teen and adult.
 * @see model.BabyShark
 * @see model.TeenShark
 * @see model.AdultShark
 */
class Shark extends Fish implements FishStateContext {
    private static final int DEATHCYCLE = 20;
    private static final int DEATHCYCLE_RANGE = DEATHCYCLE / 3;
    private static final int BREEDINGCYCLE = 4;
    private static final int BREEDINGCYCLE_RANGE = BREEDINGCYCLE / 3;
    private static final int FAMINELIMIT = 4;
    private int breedingCycle;
    private int deathCycle;
    private int lastEatCycle;
    private FishState state;

    /**
     * The shark constructor
     * @param y The Y coordinate
     * @param x The X coordinate
     */
    public Shark(int y, int x) {
        super(y, x);

        this.state = new BabyShark();

        this.lastEatCycle = 0;

        this.deathCycle = Fish.getCycleAverage(DEATHCYCLE, DEATHCYCLE_RANGE);
        this.breedingCycle = Fish.getCycleAverage(BREEDINGCYCLE, BREEDINGCYCLE_RANGE);
    }

    @Override
    public void setState(FishState state) {
        this.state = state;
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    protected void grow() {
        super.grow();
        state.grow(this);
    }

    @Override
    protected void move(Sea sea) {
        state.move(this, sea);
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
        if (this.lastEatCycle == FAMINELIMIT) {
            return true;
        }
        this.lastEatCycle++;
        return false;
    }

    /**
     * Eat a pilchard
     * @param pilchard The pilchard to eat
     * @param sea The sea to interact with
     */
    protected void eat(Pilchard pilchard, Sea sea) {
        sea.transposeCells(this, pilchard);
        sea.kill((Fish) pilchard);
        resetLastEatCycle();
    }

    /**
     * Reset the last time he eats
     */
    protected void resetLastEatCycle() {
        this.lastEatCycle = 0;
    }

    /**
     * Filter the input list to remove all unreachable cells
     * @param cells The cells to filter
     * @return The filtered list
     */
    protected List<Cell> getReachableCells(List<Cell> cells) {
        // Remove sharks of the list
        for (int c = cells.size() - 1; c >= 0; c--) {
            if (cells.get(c) instanceof Shark) {
                cells.remove(c);
            }
        }
        return cells;
    }

    @Override
    public String toString() {
        return "O";
    }
}

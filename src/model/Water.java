package model;

/**
 * Represents a water cell.
 */
class Water extends Cell {
    /**
     * The water constructor
     * @param y The Y coordinate
     * @param x The X coordinate
     */
    public Water(int y, int x) {
        super(y, x);
    }

    @Override
    public String toString() {
        return "~";
    }
}

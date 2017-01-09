package game;

/**
 * Represents a cell.
 *
 * @see model.Sea
 */
abstract class Cell {
    private int x;
    private int y;

    /**
     * The cell constructor
     * @param y The Y coordinate
     * @param x The X coordinate
     */
    public Cell(int y, int x) {
        this.y = y;
        this.x = x;
    }

    /**
     * Set the X coordinate
     * @param x The X coordinate
     * @return The current cell
     */
    public Cell setX(int x) {
        this.x = x;
        return this;
    }

    /**
     * Get the X coordinate
     * @return The x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Set the Y coordinate
     * @param y The Y coordinate
     * @return The current cell
     */
    public Cell setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Get the Y coordinate
     * @return The Y coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Get a formated string of the coordinates
     * @return The formated coordinates
     */
    public String toFormatedCoordinates() {
        return "{" + getY() + "," + getX() + "}";
    }
}

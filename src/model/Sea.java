package model;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a sea.
 *
 * The sea is a grid of cells which may contain fishies and.. water.
 * @see model.Cell
 */
class Sea {
    private Cell[][] grid;
    private List<Fish> fishies;
    private int height;
    private int width;
    private int fishiesIndex;

    /**
     * Generate and populate the sea
     * @param height The height of the sea
     * @param width The width of the sea
     * @param nbSharks The number of sharks to add
     * @param nbPilchards The number of pilchards to add
     * @throws RuntimeException If the number of fish to add is greater than the grid size
     */
    public Sea(int height, int width, int nbSharks, int nbPilchards) throws RuntimeException {
        this(height, width);

        populate(nbSharks, nbPilchards);
    }

    /**
     * Generate the sea
     * @param height The height of the sea
     * @param width The width of the sea
     */
    public Sea(int height, int width) {
        this.height = height;
        this.width = width;

        this.grid = new Cell[getHeight()][getWidth()];
        this.fishies = new ArrayList<Fish>();
    }

    /**
     * Generate and populate the sea
     * @param defaultCells A custom set of cell
     */
    public Sea(Cell[][] defaultCells) {
        this(defaultCells.length, defaultCells[0].length);
        this.grid = defaultCells;

        // Add fishies into the list
        int x, y;
        for (int i = 0; i < getHeight() * getWidth(); i++) {
            y = i % getHeight();
            x = i / getHeight();

            if (grid[y][x] instanceof Fish) {
                this.fishies.add((Fish) grid[y][x]);
            }
        }
    }

    /**
     * Constructor with default values
     * A 50x22 sea with 10 sharks and 30 pilchards.
     */
    public Sea() {
        this(22, 50, 10, 30);
    }

    /**
     * Get the height
     * @return The height
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the width
     * @return The width
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Populate the whole sea
     * @param nbSharks Number of sharks
     * @param nbPilchards Number of pilchards
     */
    public void populate(int nbSharks, int nbPilchards) throws RuntimeException {
        int nbFish = nbSharks + nbPilchards;
        int nbCell = this.width * this.height;
        if (nbFish >= nbCell) {
            throw new RuntimeException("Too many fish (" + nbFish + ") to add on the sea (" + nbCell + ")");
        }
        int i, x, y;

        // Add pilchards
        for (i = 0; i < nbPilchards; i++) {
            y = i % getHeight();
            x = i / getHeight();
            this.grid[y][x] = new Pilchard(y, x);
            this.fishies.add((Fish) this.grid[y][x]);
        }

        // Add sharks
        for (i = nbPilchards; i < nbPilchards + nbSharks; i++) {
            y = i % getHeight();
            x = i / getHeight();
            this.grid[y][x] = new Shark(y, x);
            this.fishies.add((Fish) this.grid[y][x]);
        }

        // Add water
        for (i = nbPilchards + nbSharks; i < getHeight() * getWidth(); i++) {
            y = i % getHeight();
            x = i / getHeight();
            this.grid[y][x] = new Water(y, x);
        }

        // Randomize the whole sea
        for (y = 0; y < getHeight(); y++) {
            for (x = 0; x < getWidth(); x++) {
                // Get coordinates of a random cell
                int rX = (int) (Math.random() * this.getWidth());
                int rY = (int) (Math.random() * this.getHeight());

                // Swap current cell with a random one
                transposeCells(this.grid[y][x], this.grid[rY][rX]);
            }
        }
    }

    /**
     * Transpose two cells
     * @param c1 The first cell
     * @param c2 The second cell
     */
    public void transposeCells(Cell c1, Cell c2) {
        int c1Y = c1.getY();
        int c1X = c1.getX();
        int c2Y = c2.getY();
        int c2X = c2.getX();
        this.grid[c2Y][c2X] = c1.setY(c2Y).setX(c2X);
        this.grid[c1Y][c1X] = c2.setY(c1Y).setX(c1X);
    }

    /**
     * Apply a cycle for the whole sea
     */
    public void applyCycle() {
        for (this.fishiesIndex = 0; this.fishiesIndex < this.fishies.size(); this.fishiesIndex++) {
            this.fishies.get(this.fishiesIndex).act(this);
        }
    }

    /**
     * Return a list of nearby cells of a given cell
     * @param cell The cell to inspect
     * @return List of the nearby cells
     */
    public List<Cell> getNearbyCells(Cell cell) {
        return getCellsByRadius(cell, 1);
    }

    /**
     * Return a list of cells at a given radius of a given cell
     * @param cell The cell to inspect
     * @param radius The radius to iterate
     * @return List of the nearby cells
     */
    public List<Cell> getCellsByRadius(Cell cell, int radius) {
        List<Cell> cells = new ArrayList<Cell>();
        int minY = cell.getY() - radius;
        int minX = cell.getX() - radius;
        int maxY = cell.getY() + radius;
        int maxX = cell.getX() + radius;

        // Set the values into the sea bounds
        if (minX < 0) {
            minX = 0;
        }
        if (minY < 0) {
            minY = 0;
        }
        if (maxX > getWidth() - 1) {
            maxX = getWidth() - 1;
        }
        if (maxY > getHeight() - 1) {
            maxY = getHeight() - 1;
        }

        // Loop to nearby cells
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if ((y == minY || y == maxY) || (x == minX || x == maxX)) {
                    cells.add(this.grid[y][x]);
                }
            }
        }

        return cells;
    }

    /**
     * Get a cell from coordinates
     * @param int y The Y coordinate
     * @param int x The X coordinate
     * @return The cell if it exists
     */
    public Cell getCell(int y, int x) {
        if (y < 0 || y >= grid.length || x < 0 || x >= grid[0].length) {
            return null;
        }
        return grid[y][x];
    }

    /**
     * Kill a fish
     * @param fish The fish to kill
     */
    public void kill(Fish fish) {
        // Replace the `dead` fish by a water cell
        int y = fish.getY();
        int x = fish.getX();
        this.grid[y][x] = new Water(y, x);

        // Only decrease the fishies index if the dead fish is before it
        // -> for iteration process
        if (this.fishies.indexOf(fish) <= this.fishiesIndex) {
            this.fishiesIndex--;
        }
        this.fishies.remove(fish);

        System.out.println("   Death on {" + y + ", " + x + "} (" + fish + ")");
    }

    /**
     * Spawn a fish on the sea
     * @param fish The fish to spawn
     * @param parentFish The parent of the newborn fish
     */
    public void spawn(Fish fish, Fish parentFish) {
        // Replace the water by the newborn fish
        int y = fish.getY();
        int x = fish.getX();
        this.grid[y][x] = fish;

        // Add the new born fish right after the parent fish position
        // to prevent it to directly move after its born
        int parentFishIndex = this.fishies.indexOf(parentFish) + 1;
        this.fishies.add(parentFishIndex, fish);
        // Pass the new born fish
        this.fishiesIndex++;

        System.out.println("New born on {" + y + ", " + x + "} (" + fish + ")");
    }

    /**
     * Return the grid as a string
     * @return The grid
     */
    @Override
    public String toString() {
        String display = "";

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                display += this.grid[y][x].toString();
            }
            display += "\n";
        }

        return display;
    }
}

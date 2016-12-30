package model;

import java.util.*;


class Sea {
    private Cell[][] grid;
    private ArrayList<Fish> fishies;
    private int height;
    private int width;

    /**
     * Generate and populate the sea
     * @param height Height of the sea
     * @param width Width of the sea
     * @param nbSharks Count of sharks to add
     * @param nbPilchards Count of pilchards to add
     */
    public Sea(int height, int width, int nbSharks, int nbPilchards) throws Exception {
        this(height, width);

        populate(nbSharks, nbPilchards);
    }

    /**
     * Generate the sea
     * @param height Height of the sea
     * @param width Width of the sea
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
                fishies.add((Fish) grid[y][x]);
            }
        }
    }

    /**
     * Consctructor with default values
     */
    public Sea() {
        this(500, 500);
        try {
            populate(20, 10);
        } catch (Exception e) {}
    }

    /**
     * Get the height of the sea
     * @return The height of the sea
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the width of the sea
     * @return The width of the sea
     */
    public int getHeight() {
        return height;
    }

    /**
     * Populate the whole sea
     * @param nbSharks Number of sharks
     * @param nbPilchards Number of pilchards
     */
    public void populate(int nbSharks, int nbPilchards) throws Exception {
        int nbFish = nbSharks + nbPilchards;
        int nbCell = this.width * this.height;
        if (nbFish >= nbCell) {
            throw new Exception("Too many fish (" + nbFish + ") to add on the sea (" + nbCell + ")");
        }
        int i, x, y;

        // Add pilchards
        for (i = 0; i < nbPilchards; i++) {
            y = i % getHeight();
            x = i / getHeight();
            grid[y][x] = new Pilchard(y, x);
        }

        // Add sharks
        for (i = nbPilchards; i < nbPilchards + nbSharks; i++) {
            y = i % getHeight();
            x = i / getHeight();
            grid[y][x] = new Shark(y, x);
        }

        // Add water
        for (i = nbPilchards + nbSharks; i < getHeight() * getWidth(); i++) {
            y = i % getHeight();
            x = i / getHeight();
            grid[y][x] = new Water(y, x);
        }

        // Randomize the whole sea
        for (y = 0; y < getHeight(); y++) {
            for (x = 0; x < getWidth(); x++) {
                // Get coordinates of a random cell
                int rX = (int) (Math.random() * this.getWidth());
                int rY = (int) (Math.random() * this.getHeight());

                // Swap current cell with a random one
                Cell temp = grid[y][x];

                grid[y][x] = grid[rY][rX].setX(x).setY(y);
                grid[rY][rX] = temp.setX(rX).setY(rY);
            }
        }
    }

    /**
     * Return a list of nearby cells of a given cell
     * @param cell Cell to inspect
     * @return List of the nearby cells
     */
    private ArrayList<Cell> getNearbyCells(Cell cell) {
        ArrayList<Cell> cells = new ArrayList<Cell>();
        int minY = cell.getY() - 1;
        int minX = cell.getX() - 1;
        int maxY = cell.getY() + 1;
        int maxX = cell.getX() + 1;

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
                if (y != cell.getY() || x != cell.getX()) {
                    cells.add(grid[y][x]);
                }
            }
        }

        return cells;
    }

    /**
     * Return the grid as a string
     * @return The grid
     */
    public String toString() {
        String display = "\n";

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                display += grid[y][x].toString();
            }
            display += "\n";
        }

        return display;
    }
}

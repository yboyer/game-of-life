package model;

import java.util.ArrayList;


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
                transposeCells(grid[y][x], grid[rY][rX]);
            }
        }
    }

    /**
     * Transpose two cells
     * @param c1 The first cell
     * @param c1 The second cell
     */
    public void transposeCells(Cell c1, Cell c2) {
        int c1Y = c1.getY();
        int c1X = c1.getX();
        int c2Y = c2.getY();
        int c2X = c2.getX();
        grid[c2Y][c2X] = c1.setY(c2Y).setX(c2X);
        grid[c1Y][c1X] = c2.setY(c1Y).setX(c1X);
    }

    /**
     * Apply a cycle for the whole sea
     */
    public void applyCycle() {
        for (int i = 0; i < fishies.size(); i++) {
            int actValue = fishies.get(i).act(this);
            if (actValue == Fish.ACT_DEAD) {
                i--;
            } else if (actValue == Fish.ACT_BREED) {
                i++;
            }
        }
    }

    /**
     * Return a list of nearby cells of a given cell
     * @param cell Cell to inspect
     * @return List of the nearby cells
     */
    public ArrayList<Cell> getNearbyCells(Cell cell) {
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
     * Kill a fish on the sea
     * @param fish A fish to kill
     */
    public void kill(Fish fish) {
        fishies.remove(fish);

        // Replace the `dead` fish by a water cell
        int y = fish.getY();
        int x = fish.getX();
        grid[y][x] = new Water(y, x);

        System.out.println("Death of {" + y + ", " + x + "} (" + fish + ")");
    }

    /**
     *
     */
    public void spawn(Fish fish, Fish parentFish) {
        // Add the new born fish right after the parent fish position
        // to prevent it to directly move after its born
        int parentFishIndex = fishies.indexOf(parentFish) + 1;
        fishies.add(parentFishIndex, fish);

        // Replace the water by the newborn fish
        int y = fish.getY();
        int x = fish.getX();
        grid[y][x] = fish;

        System.out.println("New born in {" + y + ", " + x + "} (" + fish + ")");
    }

    /**
     * Return the grid as a string
     * @return The grid
     */
    public String toString() {
        String display = "";

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                display += grid[y][x].toString();
            }
            display += "\n";
        }

        return display;
    }
}

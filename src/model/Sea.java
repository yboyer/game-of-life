package model;

class Sea {
    private Cell[][] grid;
    private int sizeX;
    private int sizeY;

    /**
     * Generate and poluplate a sea
     * @param sizeX Width of the sea
     * @param sizeY Height of the sea
     * @param nbPilchards Count of pilchards to add
     * @param nbSharks Count of sharks to add
     */
    public Sea(int sizeX, int sizeY, int nbPilchards, int nbSharks) {
        this.grid = new Cell[sizeY][sizeX];

        populate(nbSharks, nbPilchards);
    }

    /**
     * Consctructor with default values
     */
    public Sea() {
        this(500, 500, 20, 100);
    }

    /**
     * Populate the whole sea
     * @param nbPilchards Number of pilchards
     * @param nbSharks Number of sharks
     */
    private void populate(int nbPilchards, int nbSharks) {
        int i;
        int x;
        int y;

        // Add pilchards
        for (i = 0; i < nbPilchards; i++) {
            x = i % sizeX;
            y = i / sizeY;
            grid[y][x] = new Pilchard(y, x);
        }

        // Add sharks
        for (i = nbPilchards; i < nbPilchards + nbSharks; i++) {
            x = i % sizeX;
            y = i / sizeY;
            grid[i / sizeY][i % sizeX] = new Shark(y, x);
        }

        // Randomize the whole sea
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                // Get coordinates of a random cell
                rX = Math.random() * this.sizeX();
                rY = Math.random() * this.sizeX();

                // Swap current cell with a random one
                temp = grid[y][x];
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
        ArrayList<Cell> cells = ArrayList<Cell>();
        int minY = cell.getY() - 1;
        int minX = cell.getX() - 1;
        int maxY = cell.getY() + 1;
        int maxX = cell.getX() + 1;

        // Reformat value for interation
        if (minX < 0) {
            minX = 0;
        }
        if (minY < 0) {
            minY = 0;
        }
        if (maxX > sizeX - 1) {
            maxX = sizeX - 1;
        }
        if (maxY > sizeY - 1) {
            maxY = sizeY - 1;
        }

        // Loop to nearby cells
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                cells.push(grid[y][x]);
            }
        }
    }
}

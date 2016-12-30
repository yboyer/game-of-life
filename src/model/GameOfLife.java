package model;

public class GameOfLife {
    private static Sea sea;

    public static void main(String[] args) {
        // try { sea = new Sea(10, 10, 3, 2); } catch (Exception e) { }
        sea = getDefault();
        startLife(500);
    }

    private static Sea getDefault() {
        int height = 5;
        int width = 10;

        Cell[][] grid = new Cell[height][width];

        grid[0][0] = new Pilchard(0, 0);
        grid[0][1] = new Shark(0, 1);

        int x, y;
        for (int i = (height * width) - 1; i >= 0; i--) {
            y = i % height;
            x = i / height;

            if (grid[y][x] == null) {
                grid[y][x] = new Water(y, x);
            }
        }

        return new Sea(grid);
    }

    /**
     * Start life for a defined time
     * @param nbCycle The number of cycle to apply
     */
    public static void startLife(int nbCycle) {
        int cycle;
        for (cycle = 0; cycle < nbCycle; cycle++) {
            System.out.printf("\n######### Cycle %02d #########\n", cycle);
            System.out.println(sea);
            sea.applyCycle();
        }

        System.out.printf("\n######### Cycle %02d #########\n", cycle);
        System.out.println(sea);
    }

    /**
     * Start life for an unlimited time
     */
    public static void startLife() {
        startLife((int) Double.POSITIVE_INFINITY);
    }
}

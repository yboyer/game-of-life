package model;

public class GameOfLife {

    public static void main(String[] args) {
        // Sea sea = new Sea(5, 5, 2, 10);
        System.out.println(sea);
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
}

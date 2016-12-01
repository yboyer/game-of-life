package model;

abstract class Cell {
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell setX(int x) {
        this.x = x;
        return this;
    }
    public int getX() {
        this.x = x;
    }

    public Cell setY(int y) {
        this.y = y;
        return this;
    }
    public int getY() {
        this.y = y;
    }
}

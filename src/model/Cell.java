package model;

abstract class Cell {
    private int x;
    private int y;

    public Cell(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public Cell setX(int x) {
        this.x = x;
        return this;
    }
    public int getX() {
        return x;
    }

    public Cell setY(int y) {
        this.y = y;
        return this;
    }
    public int getY() {
        return y;
    }
}

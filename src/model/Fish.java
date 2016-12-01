package model;

abstract class Fish extends Cell {
    private int age;
    private int breedingCycle;
    private int deathCycle;

    public Fish(int x, int y) {
        super(x, y);

        this.age = 0;
        this.deathCycle = Math.random() * 30;
        this.breedingCycle = Math.random() * 30;
    }
}

package model;

abstract class Fish extends Cell {
    private static int DEATHCYCLE = 20;
    private static int DEATHCYCLEAVG = DEATHCYCLE / 3;
    private static int BREEDINGCYCLE = 4;
    private static int BREEDINGCYCLEAVG = BREEDINGCYCLE / 3;
    private int age;
    private int breedingCycle;
    private int deathCycle;

    public Fish(int y, int x) {
        super(y, x);

        this.age = 0;

        int minDeathCycle = DEATHCYCLE - DEATHCYCLEAVG;
        int maxDeathCycle = DEATHCYCLE + DEATHCYCLEAVG;
        this.deathCycle = (int) (Math.random() * (maxDeathCycle - minDeathCycle)) + minDeathCycle;

        int minBreedingCycle = BREEDINGCYCLE - BREEDINGCYCLEAVG;
        int maxBreedingCycle = BREEDINGCYCLE + BREEDINGCYCLEAVG;
        this.breedingCycle = (int) (Math.random() * (maxBreedingCycle - minBreedingCycle)) + minBreedingCycle;
    }
}

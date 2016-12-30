package model;

import java.util.ArrayList;


abstract class Fish extends Cell {
    public static int DEAD = -1;
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

    /**
     * ..and Action !
     * @param sea The sea to interact with
     * @return The status code of the fish
     */
    public int act(Sea sea) {
        // Kill the fish if it deserves to die
        if (age == deathCycle) {
            sea.kill(this);
            return DEAD;
        }

        // TODO: It's time to breed ?

        ArrayList<Water> cells = getWaterCells(sea.getNearbyCells(this));
        int index = (int) (Math.random() * cells.size());
        sea.transposeCells(this, cells.get(index));

        age++;

        return 0;
    }

    /**
     * Filter the input list to retrieve the water cells
     * @param cells The list to filter
     * @return The list of water cells filtered from the input list
     */
    public ArrayList<Water> getWaterCells(ArrayList<Cell> cells) {
        ArrayList<Water> waterCells = new ArrayList<Water>();
        for (Cell c : cells) {
            if (c instanceof Water) {
                waterCells.add((Water) c);
            }
        }
        return waterCells;
    }
}

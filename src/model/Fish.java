package model;

import java.util.ArrayList;


abstract class Fish extends Cell {
    public static int ACT_OK = 0;
    public static int ACT_DEAD = -1;
    public static int ACT_BREED = 1;
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
     * Return a baby fish with the current fish type
     * @param y The Y coordinate
     * @param x The X coordinate
     * @return A baby fish
     */
    public Fish getBaby(int y, int x) {
        try {
            return this.getClass()
                .getConstructor(int.class, int.class)
                .newInstance(y, x);
        } catch (Exception e) {
            // NoSuchMethodException
            // InstantiationException
            return null;
        }
    }

    /**
     * ..and Action !
     * @param sea The sea to interact with
     * @return The status code of the fish
     */
    public int act(Sea sea) {
        int statusCode = ACT_OK;

        // Kill the fish if it deserves to die
        if (age == deathCycle) {
            sea.kill(this);
            return ACT_DEAD;
        }

        ArrayList<Water> cells = getWaterCells(sea.getNearbyCells(this));

        // Breed
        if (!cells.isEmpty()) {
            int breedIndex = (int) (Math.random() * cells.size());
            if (age == breedingCycle) {
                Water waterCell = cells.get(breedIndex);
                int y = waterCell.getY();
                int x = waterCell.getX();

                sea.spawn(getBaby(y, x), this);

                cells.remove(waterCell);

                statusCode = ACT_BREED;
            }
        }

        // Move
        if (!cells.isEmpty()) {
            int index = (int) (Math.random() * cells.size());
            sea.transposeCells(this, cells.get(index));
        }

        age++;

        return statusCode;
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

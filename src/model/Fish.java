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
    protected int age;
    protected int breedingCycle;
    protected int deathCycle;

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
    protected Fish giveBirth(int y, int x) {
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
     * Check if it needs to die because of its age
     * @return True if it need to die
     */
    protected boolean isAgeDeath() {
        if (age == deathCycle) {
            return true;
        }
        return false;
    }

    /**
     * Check if it needs to die because of its famine
     * @return True if it need to die
     */
    protected boolean isFamineDeath() {
        return false;
    }

    /**
     * Move action
     * @param sea The sea to move
     * @return True if TODO
     */
    protected void move(Sea sea) {
        ArrayList<Water> cells = getNearbyWaterCells(sea);

        // Move to a nearby water cell
        if (!cells.isEmpty()) {
            int index = (int) (Math.random() * cells.size());
            sea.transposeCells(this, cells.get(index));
        }
    };

    /**
     * Breed action
     * @param sea The sea to breed
     * @return True if the breed action is a success
     */
    protected void breed(Sea sea) {
        ArrayList<Water> cells = getNearbyWaterCells(sea);

        if (!cells.isEmpty()) {
            if (age == breedingCycle) {
                int breedIndex = (int) (Math.random() * cells.size());
                Water waterCell = cells.get(breedIndex);
                int y = waterCell.getY();
                int x = waterCell.getX();

                sea.spawn(giveBirth(y, x), this);

                cells.remove(waterCell);
            }
        }
    }

    /**
     * ..and Action !
     * @param sea The sea to interact with
     * @return The status code of the fish
     */
    public void act(Sea sea) {
        // Kill the fish if it deserves to die
        if (isAgeDeath()) {
            System.out.println("   *age*");
            sea.kill(this);
            return; // Too old to breed and move anyway..
        }

        breed(sea);

        move(sea);

        if (isFamineDeath()) {
            System.out.println("   *famine*");
            sea.kill(this);
        }

        age++;
    }

    /**
     * Filter the input list to retrieve the water cells
     * -Fish don't care about other fish-
     * @param cells The list to filter
     * @return The list of water cells filtered from the input list
     */
    protected ArrayList<Water> getNearbyWaterCells(Sea sea) {
        ArrayList<Cell> cells = sea.getNearbyCells(this);
        ArrayList<Water> waterCells = new ArrayList<Water>();
        for (Cell c : cells) {
            if (c instanceof Water) {
                waterCells.add((Water) c);
            }
        }
        return waterCells;
    }

    public abstract String toString();
}

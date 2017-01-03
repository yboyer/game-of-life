package model;

import java.util.ArrayList;

/**
 * Represents a state of shark. The teen shark.
 *
 * He eats first a nearby pilchard ; otherwise, he moves to a random nearby
 * water cell.
 */
class TeenShark implements FishState {
    private static final int AGE_STEP = 4;

    @Override
    public void move(Shark shark, Sea sea) {
        ArrayList<Cell> cells = sea.getNearbyCells(shark);
        cells = shark.getReachableCells(cells);

        // Check if there is a pilchard around him
        // -First seen first eaten-
        for (Cell cell : cells) {
            if (cell instanceof Pilchard) {
                System.out.println("  *eaten by {" + shark.getY() + ", " + shark.getX() + "}*" + " as o");
                sea.transposeCells(shark, cell);
                sea.kill((Fish) cell);
                shark.resetLastEatCycle();
                return;
            }
        }

        // Move to a random water cell
        if (!cells.isEmpty()) {
            int index = (int) (Math.random() * cells.size());
            sea.transposeCells(shark, cells.get(index));
        }
    }

    @Override
    public void grow(FishStateContext fish) {
        if (fish.getAge() == TeenShark.AGE_STEP) {
            System.out.println("Update to adult");
            // fish.setState(new AdultShark());
        }
    }
}

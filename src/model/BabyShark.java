package model;

import java.util.ArrayList;

/**
 * Represents a state of shark. The baby shark.
 *
 * He moves to a random nearby cell. If this cell contains a pilchard, he eats
 * him.
 */
class BabyShark implements FishState {
    private static final int AGE_STEP = 2;

    @Override
    public void move(Shark shark, Sea sea) {
        ArrayList<Cell> cells = sea.getNearbyCells(shark);
        cells = shark.getReachableCells(cells);

        // Move to a random cell
        // If this cell contains a pilchard, the baby shark eats him
        if (!cells.isEmpty()) {
            int index = (int) (Math.random() * cells.size());
            Cell destCell = cells.get(index);

            sea.transposeCells(shark, destCell);

            if (destCell instanceof Pilchard) {
                System.out.println("  *eaten by {" + shark.getY() + ", " + shark.getX() + "}*" + " as °");
                sea.kill((Fish) destCell);
                shark.resetLastEatCycle();
            }
        }
    }

    @Override
    public void grow(FishStateContext fish) {
        if (fish.getAge() == AGE_STEP) {
            System.out.println("Update to teen");
            fish.setState(new TeenShark());
        }
    }
}

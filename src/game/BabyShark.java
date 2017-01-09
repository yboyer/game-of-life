package game;

import java.util.List;

/**
 * Represents a state of shark. The baby shark.
 *
 * He moves to random nearby cells. If this cell contains a pilchard, he eats
 * him.
 */
class BabyShark implements FishState {
    private static final int AGE_STEP = 2;

    @Override
    public void move(Shark shark, Sea sea) {
        List<Cell> cells = sea.getNearbyCells(shark);
        cells = shark.getReachableCells(cells);

        // Move to a random cell
        if (!cells.isEmpty()) {
            int index = (int) (Math.random() * cells.size());
            Cell destCell = cells.get(index);

            // If this cell contains a pilchard, he eats him
            if (destCell instanceof Pilchard) {
                if (GameOfLife.debug){
                    System.out.println("     (eaten by " + shark.toFormatedCoordinates() + " as a baby");
                }
                shark.eat((Pilchard) destCell, sea);
            } else {
                sea.transposeCells(shark, destCell);
            }
        }
    }

    @Override
    public void grow(FishStateContext fish) {
        if (fish.getAge() == AGE_STEP) {
            if (GameOfLife.debug){
                System.out.println("Update to teen");
            }
            fish.setState(new TeenShark());
        }
    }
}

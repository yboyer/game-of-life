package states;

import entities.Shark;
import entities.Pilchard;
import entities.Cell;
import entities.Sea;
import game.GameOfLife;
import java.util.List;

/**
 * Represents a state of shark. The teen shark.
 *
 * He eats first a nearby pilchard ; otherwise, he moves to a random nearby
 * water cell.
 */
public class TeenShark implements FishState {
    private static final int AGE_STEP = 4;

    @Override
    public void move(Shark shark, Sea sea) {
        List<Cell> cells = sea.getNearbyCells(shark);
        cells = shark.getReachableCells(cells);

        // Check if there is a pilchard around him
        // -First seen first eaten-
        for (Cell cell : cells) {
            if (cell instanceof Pilchard) {
                if (GameOfLife.debug){
                    System.out.println("     (eaten by " + shark.toFormatedCoordinates() + ") as a teenager");
                }
                shark.eat((Pilchard) cell, sea);
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
            if (GameOfLife.debug){
                System.out.println("Update to adult");
            }
            fish.setState(new AdultShark());
        }
    }
}

package game;

import java.util.List;
import astar.*; // PathfinderContext, Pathfinder, Node

/**
 * Represents a state of shark. The adult shark.
 *
 * He chase the first pilchard he found ; otherwise, he moves to a random
 * nearby water cell.
 * @see astar.Pathfinder
 */
class AdultShark implements FishState {
    private final int MAX_RANGE = 40;
    private Pathfinder pathfinder = null;

    @Override
    public void move(Shark shark, Sea sea) {
        Pilchard pilchard = null;
        List<Cell> cells;

        // Iterate as a radar from the range 1 to MAX_RANGE until he finds
        // a pilchard
        for (int range = 1; (pilchard == null && range <= MAX_RANGE); range++) {
            cells = sea.getCellsByRadius(shark, range);
            pilchard = containsPilchard(cells);
        }

        // Pilchard found on the `range`'s range
        if (pilchard != null) {
            try {
                // Get the nearby cell to cross for the pilchard hunting
                Cell nextCell = chase(sea, shark, pilchard);

                // If the nearby cell is a pilchard he eats him
                if (nextCell instanceof Pilchard) {
                    if (GameOfLife.debug){
                        System.out.println("     (eaten by " + shark.toFormatedCoordinates() + " as an adult)");
                    }
                    shark.eat((Pilchard) nextCell, sea);
                    return;
                } else if (nextCell != null) {
                    // `nextCell` is a one-more-cell to cross between them him and the
                    // pilchard
                    sea.transposeCells(shark, nextCell);
                    return;
                }
            } catch (Exception e) {
                // The shark is surrounded and don't have a way to go
                // to the pilchard's position
                if (GameOfLife.debug){
                    System.out.println("For " + shark.toFormatedCoordinates() +
                        " to " + pilchard.toFormatedCoordinates() + ": " + e.getMessage());
                }
            }
        }

        // Move to a random water cell
        cells = shark.getReachableCells(sea.getNearbyCells(shark));
        if (!cells.isEmpty()) {
            int index = (int) (Math.random() * cells.size());
            sea.transposeCells(shark, cells.get(index));
        }
    }

    /**
     * Get the next cell to go for the shark chase of the pilchard
     * @param sea The sea to interact with
     * @param shark The hunting shark
     * @param pilchard The chased pilchard
     * @throws Exception If the shark is surrounded and no route was found
     */
    private Cell chase(Sea sea, Shark shark, Pilchard pilchard) throws Exception {
        // Create and store the path finder and avoid to recreate it each time
        if (pathfinder == null) {
            pathfinder = new Pathfinder(new PathfinderContext() {
                @Override
                public boolean isValidPoint(int y, int x) {
                    Cell cell = sea.getCell(y, x);

                    return cell != null && (cell instanceof Water || cell instanceof Pilchard);
                }
            });
        }

        Node start = new Node(shark.getY(), shark.getX());
        Node destination = new Node(pilchard.getY(), pilchard.getX());

        // Get the route
        List<Node> path = pathfinder.getPath(start, destination);
        // Get the next cell to cross
        // where path[0] is the shark and path[path.length - 1] is the pilchard
        Node nextNode = path.get(1);
        if (GameOfLife.debug) {
            System.out.println("Path found for " + start + ": " + path);
        }
        return sea.getCell(nextNode.getY(), nextNode.getX());
    }

    /**
     * Get one pilchard of a list
     * @param cells The cells to filter
     * @return The first pilchard found
     */
    private Pilchard containsPilchard(List<Cell> cells) {
        for (Cell c : cells) {
            if (c instanceof Pilchard) {
                return (Pilchard) c;
            }
        }
        return null;
    }

    @Override
    public void grow(FishStateContext fish) {
        // He doesn't need to grow anymore
    }
}

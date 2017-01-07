package astar;

/**
 * Represents the context and list of methods for the use of the path finder.
 *
 * @see astar.Pathfinder
 */
public interface PathfinderContext {
    /**
     * Check if the coordinates are valid
     * @param y The Y coordinate
     * @param x The X coordinate
     * @return True if the point is valid
     */
    public boolean isValidPoint(int y, int x);
}

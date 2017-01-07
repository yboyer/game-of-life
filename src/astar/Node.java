package astar;

/**
 * Represents a {@link astar.Pathfinder Pathfinder}'s node.
 *
 * @see astar.Pathfinder
 */
public class Node {
    /** Its parent node */
    public Node parent;

    // The path scoring
    /**
     * G represents the cost to move from the starting node to this node :
     *   With base of
     *     10 for a horizontal or vertical move (1*10),
     *     14 for a diagonal move ( (square root of 2 ~= 1.4)*10 ).
     */
    public int g;
    /**
     * H represents the estimated movement cost to move from this node to the
     * destination node. ( (Manhattan distance between the two nodes)*10 )
     * @see astar.Pathfinder#estimateDistance
     */
    public int h;
    /** F is the sum of G and H */
    public int f;

    // The Y and X coordinates
    private int y, x;
    /** The cost to go to this node ; the G base */
    public int cost;


    /**
     * The node constructor
     * @param y The Y coordinate
     * @param x The X coordinate
     * @param cost The cost of the node
     */
    public Node(int y, int x, int cost) {
        this.y = y;
        this.x = x;
        this.cost = cost;
    }

    /**
     * The default node constructor
     * @param y The Y coordinate
     * @param x The X coordinate
     */
    public Node(int y, int x) {
        this(y, x, 0);
    }

    /**
     * Get the Y coordinate
     * @return The Y coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Get the X coordinate
     * @return The X coordinate
     */
    public int getX() {
        return this.x;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Node)) return false;
        Node o = (Node) obj;
        return o.getY() == this.y && o.getX() == this.x;
    }

    @Override
    public String toString() {
        return "{" + getY() + "," + getX() + "}";
    }
}

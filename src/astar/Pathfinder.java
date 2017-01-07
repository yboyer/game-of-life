package astar;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a shortest path finder.
 *
 * Use of the A* algorithm to find the shortest path between two nodes.
 * A* search only to expands a node if it seems promising. It only focuses to
 * reach the destination node from the current node, not to reach every
 * other nodes. This needs to explore a lot less nodes than Dijkstra.
 *
 * @see astar.Node
 */
public class Pathfinder {
    private static final int MULTIPIER = 10;
    private static final int DIAG_MOVE = 14;   // Math.sqrt(2) * MULTIPIER
    private static final int DIRECT_MOVE = 10; // 1 * MULTIPIER

    private PathfinderContext context;

    /**
     * The pathfinder constructor
     * @param context The context of the search process
     */
    public Pathfinder(PathfinderContext context) {
        this.context = context;
    }

    /**
     * Return the shortest path between two nodes
     * @param start The starting node
     * @param destination The destination node
     * @throws Exception If there is no possible way to go to the destination
     */
    public List<Node> getPath(Node start, Node destination) throws Exception {
        List<Node> open = new ArrayList<Node>();
        List<Node> closed = new ArrayList<Node>();

        start.g = 0;
        start.h = estimateDistance(start, destination);
        start.f = start.h;

        open.add(start);

        while (true) {
            Node current = null;

            if (open.size() == 0) {
                throw new Exception("No route found");
            }

            // Take the lower node cost
            for (Node node : open) {
                if (current == null || node.f < current.f) {
                    current = node;
                }
            }

            // Break the loop
            if (current.equals(destination)) {
                destination = current;
                break;
            }

            open.remove(current);
            closed.add(current);

            List<Node> neighbors = getNeighbors(current);
            for (Node neighbor : neighbors) {
                int nextG = current.g + neighbor.cost;

                // Only keep the less expensive neighbor
                if (nextG < neighbor.g) {
                    open.remove(neighbor);
                    closed.remove(neighbor);
                }

                if (!open.contains(neighbor) && !closed.contains(neighbor)) {
                    neighbor.g = nextG;
                    neighbor.h = estimateDistance(neighbor, destination);
                    neighbor.f = neighbor.g + neighbor.h;
                    // neighbor.f = neighbor.g;
                    // ^ for Dijkstra: http://stackoverflow.com/a/16268762
                    neighbor.parent = current;
                    open.add(neighbor);
                }
            }
        }

        // Create the node list of the route
        List<Node> nodes = new ArrayList<Node>();
        Node current = destination;
        while (current.parent != null) {
            nodes.add(current);
            current = current.parent;
        }
        nodes.add(start);
        Collections.reverse(nodes);

        return nodes;
    }

    /**
     * Get the direct node neighbors
     * @see astar.PathfinderContext#isValidPoint
     * @param node The reference node
     * @return The list of the node neighbors
     */
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<Node>();

        // NW
        if(context.isValidPoint(node.getY() - 1, node.getX() - 1)) {
            neighbors.add(new Node(node.getY() - 1, node.getX() - 1, DIAG_MOVE));
        }
        // N
        if(context.isValidPoint(node.getY() - 1, node.getX())) {
            neighbors.add(new Node(node.getY() - 1, node.getX(), DIRECT_MOVE));
        }
        // NE
        if(context.isValidPoint(node.getY() - 1, node.getX() + 1)) {
            neighbors.add(new Node(node.getY() - 1, node.getX() + 1, DIAG_MOVE));
        }
        // E
        if(context.isValidPoint(node.getY(), node.getX() + 1)) {
            neighbors.add(new Node(node.getY(), node.getX() + 1, DIRECT_MOVE));
        }
        // SE
        if(context.isValidPoint(node.getY() + 1, node.getX() + 1)) {
            neighbors.add(new Node(node.getY() + 1, node.getX() + 1, DIAG_MOVE));
        }
        // S
        if(context.isValidPoint(node.getY() + 1, node.getX())) {
            neighbors.add(new Node(node.getY() + 1, node.getX(), DIRECT_MOVE));
        }
        // SW
        if(context.isValidPoint(node.getY() + 1, node.getX() - 1)) {
            neighbors.add(new Node(node.getY() + 1, node.getX() - 1, DIAG_MOVE));
        }
        // W
        if(context.isValidPoint(node.getY(), node.getX() - 1)) {
            neighbors.add(new Node(node.getY(), node.getX() - 1, DIRECT_MOVE));
        }

        return neighbors;
    }

    /**
     * Get the Manhattan distance between two nodes as a direct move (four axes)
     * @see <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">Taxicab geometry</a>
     * @param n1 The first node
     * @param n2 The second node
     * @return The number of cell
     */
    private int estimateDistance(Node n1, Node n2) {
        return (
            Math.abs(n1.getX() - n2.getX()) +
            Math.abs(n1.getY() - n2.getY())
        ) * MULTIPIER;
    }
}

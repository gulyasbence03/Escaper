package ai;

/** Represents a Node in the pathfinding algorithm (PathFinder class)
 */
public class Node {
    /** Backtrack previous node in the path
     */
    Node parent;
    /** Position of the node in the graph/matrix
     */
    public int col;
    public int row;
    /** Costing values of A* pathfinding algorithm (F,H,G)
     */
    int gCost;
    int hCost;
    int fCost;
    /** Different statuses of a node
     *  solid - not a walkable path, need to get around
     *  open - opened for evaluation (adjacent Nodes)
     *  checked - has used as best pass candidate, no need to evaluate anymore
     */
    boolean solid;
    boolean open;
    boolean checked;

    /** Creates a Node with the given position
     *
     * @param col - horizontal position of node
     * @param row - vertical position of node
     */
    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }
}

package ai;

import entity.Entity;
import main.GamePanel;
import java.util.ArrayList;

/** Holds the path finding algorithm and it's values
 */
public class PathFinder {
    /** The main game panel (will be given)
     */
    GamePanel gp;
    /** Holds each node, that represent the whole map of the game
     */
    Node[][] nodes;
    /** Stores the nodes that are currently being opened
     */
    ArrayList<Node> openList = new ArrayList<>();
    /** The final path to the goalNode from startNode
     */
    public ArrayList<Node> pathList = new ArrayList<>();

    Node startNode;
    Node currentNode;
    Node goalNode;
    /** The goalNode has been reached
     */
    boolean goalReached = false;
    /** Store how many steps have been made, to be able to limit it
     */
    int step = 0;

    /** Constructor adds the main game panel, and initialize nodes matrix
     *
     * @param gp - The main game panel
     */
    public PathFinder(GamePanel gp){
        this.gp = gp;
        innitNodes();
    }

    /** Initialize nodes matrix, with Nodes and their positions
     */
    public void innitNodes(){
        nodes = new Node[GamePanel.MAX_SCREEN_COL][GamePanel.MAX_SCREEN_ROW];

        int col = 0;
        int row = 0;

        // loop through whole map
        while(col < GamePanel.MAX_SCREEN_COL && row < GamePanel.MAX_SCREEN_ROW){
            nodes[col][row] = new Node(col,row);

            col++;
            // if side of map has been reached, go to next line
            if(col == GamePanel.MAX_SCREEN_COL){
                col = 0;
                row++;
            }
        }
    }

    /** Reset booleans and the position of all Nodes
     */
    public void resetNodes(){
        int col = 0;
        int row = 0;
        // loop through whole map
        while(col < GamePanel.MAX_SCREEN_COL && row < GamePanel.MAX_SCREEN_ROW){
            nodes[col][row].open = false;
            nodes[col][row].checked = false;
            nodes[col][row].solid = false;

            col++;
            // if map side has been reached, go to next line
            if(col == GamePanel.MAX_SCREEN_COL){
                col = 0;
                row++;
            }
        }
        // Clear lists, and reset
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    /** Set values to Node, such as Costs(F,G,H) and Solidness(solid)
     * The next parameters are given to count Costs
     * @param startCol - Column of startingNode
     * @param startRow - Row of startingNode
     * @param goalCol - Column of goalNode
     * @param goalRow -Row of goalNode
     */
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
        resetNodes();
        // set Start and Goal node
        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        // add current node to openList to store which nodes are opened
        openList.add(currentNode);

        int col = 0;
        int row = 0;
        // loop through whole map
        while(col < GamePanel.MAX_SCREEN_COL && row < GamePanel.MAX_SCREEN_ROW){
            // Set solid node
            // check tiles if is solid or
            // if cell or table on a tile it needs to be solid
            int tileNum = gp.tileM.mapTileNum[col][row];
            for(int i = 0; i <gp.objectArray.length; i++){
                if(gp.objectArray[i] != null &&
                    (gp.objectArray[i].type == Entity.Type.Cell || gp.objectArray[i].type == Entity.Type.Table) &&
                    gp.objectArray[i].x / GamePanel.TILE_SIZE == col &&
                    gp.objectArray[i].y / GamePanel.TILE_SIZE == row){
                    nodes[col][row].solid = true;
                }
            }
            if(gp.tileM.tile[tileNum].isSolidTile){
                nodes[col][row].solid = true;
            }

            // set cost (all 3 of them)
            getCost(nodes[col][row]);

            col++;
            // if side of map has been reached, go to next line
            if(col == GamePanel.MAX_SCREEN_COL){
                col = 0;
                row++;
            }
        }
    }

    /** Get all type of costs of a Node, using distance (A* pathfinding)
     * @param node - The current node that needs cost
     */
    private void getCost(Node node) {
        // G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F cost
        node.fCost = node.gCost + node.hCost;
    }

    /** Searching which path to take and if goal has been reached
     *
     * @return goalReached - if goal has been already reached or not
     */
    public boolean search(){
        // check if goal is not reached and number of steps is lower than limit
        while(!goalReached && step <500){
            int col = currentNode.col;
            int row = currentNode.row;

            // check the current node (remove from open, because it is now being checked)
            currentNode.checked = true;
            openList.remove(currentNode);

            // Check adjacent nodes
            // open the Up node
            if(row - 1 >= 0){
                openNode(nodes[col][row-1]);
            }
            // open the Left node
            if(col - 1 >= 0){
                openNode(nodes[col-1][row]);
            }
            // open the down node
            if(row+1<GamePanel.MAX_SCREEN_ROW){
                openNode(nodes[col][row+1]);
            }
            // open the right node
            if(col+1<GamePanel.MAX_SCREEN_COL){
                openNode(nodes[col+1][row]);
            }

            // find the best node
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for(int i = 0; i<openList.size();i++){
                // check if this tile's f Cost is better
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }

                // if f cost is equal, check the g cost
                else if(openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            // if there is no node in the openList end the loop
            if(openList.isEmpty()){
                break;
            }
            // after the loop, openList[bestNodeIndex] is the next step (=currentNode)
            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode){
                goalReached = true;
                // track back the final path
                trackThePath();
            }
            // a step has been made
            step++;
        }
        return goalReached;
    }

    /** Tracks back the final path using the parent of the Node
     */
    private void trackThePath() {
        Node current = goalNode;

        while(current != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }

    /** Open up a given node to evaluate costs
     *  Set open variable true
     * @param node - the given node that needs to be opened
     */
    private void openNode(Node node) {
        if(!node.open && !node.checked && !node.solid){
            node.open = true;
            // set parent to current for now
            node.parent = currentNode;
            openList.add(node);
        }
    }
}

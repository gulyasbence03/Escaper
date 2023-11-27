package ai;

import main.GamePanel;

import java.nio.file.Path;
import java.util.ArrayList;

public class PathFinder {
    GamePanel gp;
    Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode;
    Node currentNode;
    Node goalNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp){
        this.gp = gp;
        innitNodes();
    }
    public void innitNodes(){
        nodes = new Node[GamePanel.MAX_SCREEN_COL][GamePanel.MAX_SCREEN_ROW];

        int col = 0;
        int row = 0;

        while(col < GamePanel.MAX_SCREEN_COL && row < GamePanel.MAX_SCREEN_ROW){
            nodes[col][row] = new Node(col,row);

            col++;
            if(col == GamePanel.MAX_SCREEN_COL){
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes(){
        int col = 0;
        int row = 0;

        while(col < GamePanel.MAX_SCREEN_COL && row < GamePanel.MAX_SCREEN_ROW){
            nodes[col][row].open = false;
            nodes[col][row].checked = false;
            nodes[col][row].solid = false;

            col++;
            if(col == GamePanel.MAX_SCREEN_COL){
                col = 0;
                row++;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
        resetNodes();
        // set Start and Goal node
        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < GamePanel.MAX_SCREEN_COL && row < GamePanel.MAX_SCREEN_ROW){
            // Set solid node
            // check tiles
            int tileNum = gp.tileM.mapTileNum[col][row];
            if(gp.tileM.tile[tileNum].isSolidTile){
                nodes[col][row].solid = true;
            }

            // set cost
            getCost(nodes[col][row]);

            col++;
            if(col == GamePanel.MAX_SCREEN_COL){
                col = 0;
                row++;
            }
        }
    }

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

    public boolean search(){
        while(!goalReached && step <500){
            int col = currentNode.col;
            int row = currentNode.row;

            // check the current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // open the Up node
            if(row - 1 >= 0){
                openNode(nodes[col][row-1]);
            }
            // open the Left node
            if(col - 1 >= 0){
                openNode(nodes[col-1][row]);
            }
            // open the down node
            if(row+1<GamePanel.MAX_SCREEN_ROW && row + 1 >= 0){
                openNode(nodes[col][row+1]);
            }
            // open the right node
            if(col+1<GamePanel.MAX_SCREEN_COL &&  col + 1 >= 0){
                openNode(nodes[col+1][row]);
            }

            // Find the best node
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

            // After the loop, openList[bestNodeIndex] is the next step (=currentNode)
            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }

        return goalReached;
    }

    private void trackThePath() {
        Node current = goalNode;

        while(current != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }

    private void openNode(Node node) {
        if(!node.open && !node.checked && !node.solid){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
}

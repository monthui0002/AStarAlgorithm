package AStarAlgorithm;

import View.Main;
import View.Node;

import java.util.*;

public class AStarAlgorithm {
    Main main;

    public PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

    public List<Node> close = new ArrayList<>();

    public double index;

    public AStarAlgorithm(Main main) {
        this.main = main;
    }


    public void calculate(Node currentNode) {
        this.main.node[currentNode.pos_x][currentNode.pos_y].setState(Node.State.OPEN);
        if (currentNode.compare(this.main.targetNode)) {
            priorityQueue.clear();
            Node result = currentNode;
            while (result != null) {
                this.main.node[result.pos_x][result.pos_y].setState(Node.State.VISITED);
                result = result.parent;
            }
            return;
        }
        List<Node> neighbors = getNeighbors(currentNode);
        if (!(neighbors.isEmpty() && priorityQueue.isEmpty())) {
            Node next;
            if (!neighbors.isEmpty()) {
                neighbors.forEach(neighbor -> {
                    this.main.node[neighbor.pos_x][neighbor.pos_y].setState(Node.State.OPEN);
                    priorityQueue.add(neighbor);
                    if(!inClose(neighbor)) close.add(neighbor);
                });
            }
            if (!priorityQueue.isEmpty()) {
                next = priorityQueue.poll();
                calculate(next);
            }
        }
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (checkNeighbors(node, node.pos_x + i, node.pos_y + j) && !(i == 0 && j == 0)) {
                    Node neighbor = new Node(node.pos_x + i, node.pos_y + j);
                    neighbor.setParent(node);
                    neighbor.setH(this.main.targetNode);
                    neighbor.calculateG(node);
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }


    private boolean checkNeighbors(Node node, int x, int y) {
        return x >= 0 && x < Main.COLS &&
                y >= 0 && y < Main.ROWS &&
                this.main.node[x][y].getState() != Node.State.CLOSED &&
                betterG(node, x, y);
    }


    private boolean betterG(Node node, int x, int y) {
        if (inClose(this.main.node[x][y])) {
            if (node.getG() + Math.sqrt(Math.pow(node.pos_x - x, 2) + Math.pow(node.pos_y - y, 2)) < getMinG(this.main.node[x][y])) {
                return true;
            }
            return false;
        }
        return true;
    }


    private boolean inClose(Node node){
        for(int i = 0; i < close.size(); i++){
            index = i;
            if(close.get(i).compare(node)) return true;
        }
        return false;
    }


    private double getMinG(Node node){
        double min = 0;
        for(int i = 0; i < close.size(); i++){
            if(close.get(i).compare(node)){
                min = close.get(i).getG();
                for(int j = i;j < close.size(); j++){
                    if(close.get(i).compare(node)){
                        if(min > close.get(j).getG()) min = close.get(j).getG();
                    }
                }
                break;
            }
        }
        return min;
    }

}

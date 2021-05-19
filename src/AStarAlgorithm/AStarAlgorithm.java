package AStarAlgorithm;

import View.Main;
import View.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AStarAlgorithm {
    Main main;

    public PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

    public AStarAlgorithm(Main main) {
        this.main = main;
    }


    public boolean calculate(Node currentNode) {
        this.main.node[currentNode.pos_x][currentNode.pos_y].setState(Node.State.OPEN);
        if (currentNode.compare(this.main.targetNode)) {
            return true;
        }
        List<Node> neighbors = getNeighbors(currentNode);
        if (neighbors.isEmpty() && priorityQueue.isEmpty()) {
            return false;
        } else {
            Node next;
            if (!neighbors.isEmpty()) {
                neighbors.forEach(neighbor -> {
                    neighbor.setState(Node.State.OPEN);
                    neighbor.setParent(currentNode);
                    if (!priorityQueue.contains(neighbor)) {
                        priorityQueue.add(neighbor);
                    }
                });
            }
            if(!priorityQueue.isEmpty()){
                next = priorityQueue.poll();
                return calculate(next);
            }
            return false;
        }
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (node.pos_x + i >= 0 && node.pos_y + j >= 0
                        && node.pos_x + i <= Main.COLS - 1 && node.pos_y + j <= Main.ROWS - 1
                        && !(i == 0 && j == 0)
                        && this.main.node[node.pos_x + i][node.pos_y + j].getState() == Node.State.UNVISITED) {
                    neighbors.add(this.main.node[node.pos_x + i][node.pos_y + j]);
                }
            }
        }
        return neighbors;
    }

}

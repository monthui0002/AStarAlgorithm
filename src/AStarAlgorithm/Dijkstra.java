package AStarAlgorithm;

import View.Main;
import View.Node;

import java.util.PriorityQueue;

public class Dijkstra implements Runnable {
    Main main;

    PriorityQueue<Node> open = new PriorityQueue<>();
    PriorityQueue<Node> close = new PriorityQueue<>();

    public Dijkstra(Main main) {
        this.main = main;
    }

    public void calculate(Node startNode) {
        for (int i = 0; i < Main.COLS; i++) {
            for (int j = 0; j < Main.ROWS; j++) {
                this.main.node[i][j].setH(0);
            }
        }

        open.add(startNode);
        while (!open.isEmpty()) {
            startNode = open.poll();
            startNode.setState(Node.State.VISITED);
            if (startNode.compare(this.main.targetNode)) {
                this.main.result = startNode;
                return;
            } else {
                for (Node node : startNode.list) {
                    if (node.getState() != Node.State.CLOSED) {
                        double weight = startNode.getG() + Math.sqrt(Math.pow(startNode.pos_x - node.pos_x, 2) + Math.pow(startNode.pos_y - node.pos_y, 2));
                        if (!open.contains(node) && !close.contains(node)) {
                            node.parent = startNode;
                            node.setG(weight);
                            try {
                                Thread.sleep(100);
                                this.main.repaint();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            open.add(node);
                            node.setState(Node.State.OPEN);
                        } else {
                            if (node.getG() > weight) {
                                node.parent = startNode;
                                node.setG(weight);
                                if (close.contains(node)) {
                                    close.remove(node);
                                    open.add(node);
                                    try {
                                        Thread.sleep(100);
                                        this.main.repaint();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    node.setState(Node.State.OPEN);
                                }
                            }
                        }
                    }
                }
            }
            close.add(startNode);
            open.remove(startNode);
        }
    }

    @Override
    public void run() {
        calculate(this.main.startNode);
    }
}

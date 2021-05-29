package AStarAlgorithm;

import View.Main;
import View.Node;

import javax.swing.*;
import java.util.PriorityQueue;

public class Dijkstra implements Runnable {
    Main main;

    PriorityQueue<Node> open = new PriorityQueue<>();

    PriorityQueue<Node> close = new PriorityQueue<>();

    double startTime = System.currentTimeMillis();

    public Dijkstra(Main main) {
        this.main = main;
    }

    public void dijkstra(Node startNode) {
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
                            open.add(node);
                            setState();
                            node.setState(Node.State.OPEN);
                        } else {
                            if (node.getG() > weight) {
                                node.parent = startNode;
                                node.setG(weight);
                                if (close.contains(node)) {
                                    close.remove(node);
                                    open.add(node);
                                    setState();
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
        JOptionPane.showMessageDialog(this.main,"No path to the destination!","",JOptionPane.ERROR_MESSAGE);
    }


    private void setState() {
        try {
            Thread.sleep(this.main.speed);
            this.main.time = (System.currentTimeMillis() - startTime)/1000;
            this.main.repaint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.main.time = 0;
        dijkstra(this.main.startNode);
        this.main.time = (System.currentTimeMillis() - startTime)/1000;
        this.main.addMouseListener(this.main.mouseHandler);
    }
}

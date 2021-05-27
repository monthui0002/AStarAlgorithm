package AStarAlgorithm;

import View.Main;
import View.Node;

import java.util.*;

public class AStarAlgorithm implements Runnable {
    Main main;

    public PriorityQueue<Node> open = new PriorityQueue<>();

    public PriorityQueue<Node> close = new PriorityQueue<>();

    public AStarAlgorithm(Main main) {
        this.main = main;
    }


    public void aStar(Node currentNode) {
        open.add(currentNode);
        while (!open.isEmpty()) {
            currentNode = open.peek();
            currentNode.setState(Node.State.VISITED);
            if (currentNode.compare(this.main.targetNode)) {
                this.main.result = currentNode;
                open.clear();
                close.clear();
                return;
            }
            for(Node node : currentNode.list){
                if(node.getState() != Node.State.CLOSED){
                    double weight = currentNode.getG() + Math.sqrt(Math.pow(currentNode.pos_x - node.pos_x, 2) + Math.pow(currentNode.pos_y - node.pos_y, 2));
                    if(!open.contains(node) && !close.contains(node)){
                        node.parent = currentNode;
                        node.calculateG(currentNode);
                        open.add(node);
                        try {
                            Thread.sleep(100);
                            this.main.repaint();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        node.setState(Node.State.OPEN);
                    }
                    else {
                        if(node.getG() > weight){
                            node.parent = currentNode;
                            node.setG(weight);
                            if(close.contains(node)){
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
            close.add(currentNode);
            open.remove(currentNode);
        }
    }

    @Override
    public void run() {
        aStar(this.main.startNode);
    }
}

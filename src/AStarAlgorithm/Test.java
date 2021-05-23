package AStarAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Test {
    public static int m = 30;
    public static int n = 30;
    public static Node[][] node = new Node[m][n];
    public static int[][] maze = new int[m][n];

    public static Node startNode;
    public static Node targetNode;

    public static PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

    public static void AStar(Node currentNode) {
        System.out.print("(" + currentNode.pos_x + ", " + currentNode.pos_y + ")");
        maze[currentNode.pos_x][currentNode.pos_y] = 1;
//        printMaze();
        if (currentNode.compare(targetNode)) {
            System.out.println("done---------------------");
            return;
        }
        List<Node> neighbors = currentNode.getNeighbors();
        if (neighbors.isEmpty() && priorityQueue.isEmpty()) {
            System.out.println("no way to win");
        } else {
            Node next;
            if (!neighbors.isEmpty()) {
                neighbors.forEach(neighbor -> {
                    neighbor.setParent(currentNode);
                    if (!priorityQueue.contains(neighbor)) {
                        priorityQueue.add(neighbor);
                    }
                });
            }
            next = priorityQueue.poll();
            AStar(next);
        }
    }


    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maze[i][j] = scn.nextInt();
            }
        }


        startNode = new Node(7, 18);
        targetNode = new Node(17, 18);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                node[i][j] = new Node(i, j);
                node[i][j].state = maze[i][j];
                node[i][j].calculateH();
            }
        }

        AStar(startNode);
        Node n = node[17][18];
        while (n.parent != null){
            System.out.print("(" + n.parent.pos_x + ", " + n.parent.pos_y + ")" + "   <-   ");
            n = n.parent;
        }
        System.out.print("start");
    }

    public static void printMaze(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(maze[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("--------------------------------------------------");
    }

    static class Node implements Comparable<Node> {
        private double h = 0;
        private double g = 0;

        public int state;

        public int pos_x;
        public int pos_y;

        public Node parent = null;

        Node(int pos_x, int pos_y) {
            this.pos_x = pos_x;
            this.pos_y = pos_y;

        }

        void calculateH() {
            this.h = Math.sqrt(Math.pow(targetNode.pos_x - this.pos_x, 2) + Math.pow(targetNode.pos_y - this.pos_y, 2));
        }

        void getG(Node node) {
            this.g = Math.sqrt(Math.pow(node.pos_x - this.pos_x, 2) + Math.pow(node.pos_y - this.pos_y, 2)) + node.g;
        }

        double getF() {
            return this.parent != null ? this.g + this.h : 0;
        }

        void setParent(Node node) {
            this.parent = node;
        }


        List<Node> getNeighbors() {
            List<Node> neighbors = new ArrayList<>();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (this.pos_x + i >= 0 && this.pos_y + j >= 0 && this.pos_x + i <= m - 1 && this.pos_y + j <= n - 1 && !(i == 0 && j == 0) && maze[this.pos_x + i][this.pos_y + j] != 1) {
                        node[this.pos_x + i][this.pos_y + j].getG(this);
                        neighbors.add(node[this.pos_x + i][this.pos_y + j]);
                    }
                }
            }
            return neighbors;
        }


        boolean compare(Node node){
            return this.pos_x == node.pos_x && this.pos_y == node.pos_y;
        }


        @Override
        public int compareTo(Node o) {
            return Double.compare(this.getF(), o.getF());
        }
    }
}

/*      input
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 */

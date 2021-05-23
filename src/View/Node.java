package View;


public class Node implements Comparable<Node> {

    public enum State {OPEN, CLOSED, UNVISITED, VISITED}

    public int pos_x;
    public int pos_y;

    private double h = 0;
    private double g = 0;

    private State state = State.UNVISITED;

    public Node parent = null;

    public Node(int pos_x, int pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }


    public State getState() {
        return state;
    }


    public void setState(State state) {
        this.state = state;
    }


    public void setH(Node targetNode) {
        this.h = Math.sqrt(Math.pow(targetNode.pos_x - this.pos_x, 2) + Math.pow(targetNode.pos_y - this.pos_y, 2));
    }


    public void setParent(Node node) {
        this.parent = node;
    }


    public void calculateG(Node node) {
        this.g = Math.sqrt(Math.pow(node.pos_x - this.pos_x, 2) + Math.pow(node.pos_y - this.pos_y, 2)) + node.g;
    }


    double getF() {
        return this.parent != null ? this.g + this.h : 0;
    }


    public boolean compare(Node node) {
        return this.pos_x == node.pos_x && this.pos_y == node.pos_y;
    }


    @Override
    public int compareTo(Node o) {
        return Double.compare(this.getF(), o.getF());
    }
}

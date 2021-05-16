public class Node {
    public enum State {OPEN, CLOSED}

    public int pos_x;
    public int pos_y;

    private State state = State.OPEN;

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


    public boolean compare(Node node) {
        return this.pos_x == node.pos_x && this.pos_y == node.pos_y;
    }
}

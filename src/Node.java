public class Node{
    public enum State {OPEN, CLOSED, UNVISITED};

    public int pos_x;
    public int pos_y;

    private State state = State.UNVISITED;

     Node(int pos_x, int pos_y) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }


    public State getState(){
         return state;
    }

    public void setState(State state){
         this.state = state;
    }

    public void printNode(){
        System.out.println(this.pos_x + " " + this.pos_y);
    }
}

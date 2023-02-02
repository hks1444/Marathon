import java.util.*;

public class Node {
    public boolean isFlag = false;
    public String value;
    private PriorityQueue<Tuple> connections = new PriorityQueue<>(new CustomComparator());
    public Node(String val, boolean flg){
        this.isFlag = flg;
        this.value = val;
    }
    public boolean isVisited1 = false;
    public boolean isVisited2 = false;
    public int dist1 = Integer.MAX_VALUE;
    public int dist2 = Integer.MAX_VALUE;
    public void addCon(Node nod,int dist){
        connections.add(new Tuple(nod,dist));
    }

    public PriorityQueue<Tuple> getConnections() {
        return connections;
    }
    public void add_SpecialConnection(Node other){
        connections.add(new Tuple(other,0));
    }
}
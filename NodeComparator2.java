import java.util.Comparator;

public class NodeComparator2 implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(o1.dist2,o2.dist2);
    }
}

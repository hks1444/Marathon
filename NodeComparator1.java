import java.util.Comparator;

public class NodeComparator1 implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(o1.dist1,o2.dist1);
    }
}

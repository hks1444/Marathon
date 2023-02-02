import java.util.Comparator;

public class CustomComparator implements Comparator<Tuple> {
    @Override
    public int compare(Tuple o1, Tuple o2) {
        if (o1.second != o2.second) {
            return Integer.compare(o1.second, o2.second);
        }
        return o1.first.value.compareTo(o2.first.value);
    }
}

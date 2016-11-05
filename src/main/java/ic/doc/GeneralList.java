package ic.doc;

import java.util.Iterator;
import java.util.List;

public class GeneralList<T> implements Iterable<T> {

    protected final List<T> list;

    public GeneralList(List<T> list) {
        this.list = list;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public T initialValue() {
        if (list.size() == 0) {
            throw new IndexOutOfBoundsException("list size must be more than 0");
        }
        return list.get(0);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

}



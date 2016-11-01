package ic.doc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;


public class MappableList<T> implements Iterable<T> {

    private final List<T> list;

    public MappableList(List<T> list) {
        this.list = list;
    }

    public MappableList(T... list) {
        this.list = asList(list);
    }

    public List<T> map(Function<T> f) {
        List<T> newlist = new ArrayList<T>();
        for(T elem: list) {
            newlist.add(f.applyTo(elem));
        }
        return newlist;
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }


}

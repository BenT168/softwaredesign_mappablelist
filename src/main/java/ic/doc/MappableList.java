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

    public List<T> map(Function<T> mapper) {
        List<T> result = new ArrayList<T>();
        for(T elem: list) {
            result.add(mapper.applyTo(elem));
        }
        return result;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public T initialValue() {
        if (list.size() == 0 ) {
            throw new IndexOutOfBoundsException("list size must be more than 0");
        }
        return list.get(0);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

}

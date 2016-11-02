package ic.doc;

import java.util.ArrayList;
import java.util.List;

public class MappableList<T> extends ListGenerator<T> {

    public MappableList(List<T> list) {
        super(list);
    }

    public List<T> map(UnaryFunction<T> mapper) {
        List<T> result = new ArrayList<T>();
        for(T elem: list) {
            result.add(mapper.applyTo(elem));
        }
        return result;
    }

}


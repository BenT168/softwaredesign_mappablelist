package ic.doc;

import java.util.List;

public class FoldableList<T> extends ListGenerator<T> {

    public FoldableList(List<T> list) {
        super(list);
    }

    public T map(BinaryFunction<T> mapper) {
        if (list.size() < 2){
            throw new IllegalStateException("Foldable list must have size more than 2");
        }
        T result = super.initialValue();
        boolean initial = true;

        for(T elem: list) {
            if (!initial) {
                result = mapper.applyTo(result, elem);
            } else {
                initial = false;
            }
        }
        return result;
    }

}


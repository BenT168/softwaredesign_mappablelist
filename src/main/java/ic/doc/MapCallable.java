package ic.doc;

import java.util.concurrent.Callable;

public class MapCallable<T> implements Callable<T> {
    private UnaryFunction<T> func;
    private T val;

    public MapCallable(UnaryFunction<T> func, T val) {
        this.func = func;
        this.val = val;
    }

    @Override
    public T call() throws Exception {
        return func.applyTo(val);
    }
}


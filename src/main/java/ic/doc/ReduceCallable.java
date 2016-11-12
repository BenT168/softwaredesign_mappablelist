package ic.doc;

import java.util.concurrent.Callable;

public class ReduceCallable<T> implements Callable<T> {
    private BinaryFunction<T> binFunc;
    private T fst;
    private T snd;

    public ReduceCallable(BinaryFunction<T> binFunc, T fst, T snd) {
        this.binFunc = binFunc;
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public T call() throws Exception {
        return binFunc.applyTo(fst, snd);
    }

}




package ic.doc;

import java.util.*;
import java.util.concurrent.*;

public class MapReduceList<T> implements Iterable<T> {

    protected List<T> list = new ArrayList<T>();

    public MapReduceList(Collection<T> elems) {
        list.addAll(elems);
    }

    public MapReduceList(T... elems) {
        this(Arrays.asList(elems));
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public T initialValue() {
        if (size() == 0) {
            throw new IndexOutOfBoundsException("list size must be more than 0");
        }
        return list.get(0);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    public MapReduceList<T> map(UnaryFunction<T> func) {
        ExecutorService executorService = Executors.newFixedThreadPool(list.size());
        List<Future<T>> futures = new ArrayList<>();
        List<T> resultElem = new ArrayList<>();

        for(T elem : list) {
            futures.add(executorService.submit(new MapCallable<T>(func, elem)));
        }

        executorService.shutdown();
        mapHelper(futures, resultElem);

        return new MapReduceList<T>(resultElem);
    }

    private void mapHelper(List<Future<T>> futures, List<T> elems) {
        for(Future<T> elem : futures) {
            try {
                elems.add(elem.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public T reduce(BinaryFunction<T> binFunc, T init) {
        ExecutorService executorService = Executors.newFixedThreadPool(list.size());
        List<Callable<T>> listOfTasks = new ArrayList<>();
        List<Future<T>> futures = new ArrayList<>();

        while (size() > 1) {
            for (int i = 0; i < size(); i++) {
                if (size() % 2 == 1 && i == size() - 1) {
                    listOfTasks.add(new ReduceCallable<T>(binFunc, list.get(i), init));
                } else {
                    listOfTasks.add(new ReduceCallable<T>(binFunc, list.get(i), list.get(++i)));
                }
            }
            reduceHelper(futures, listOfTasks, executorService);
        }

        return list.get(0);
    }

    private void reduceHelper(List<Future<T>> futures, List<Callable<T>> listOfTasks, ExecutorService executor) {
        try {
            futures = executor.invokeAll(listOfTasks);
            list = new ArrayList<T>();
            for (Future<T> future : futures) {
                list.add(future.get());
            }
            listOfTasks.clear();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}





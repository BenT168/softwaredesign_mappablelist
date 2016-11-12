package ic.doc;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.lessThan;

public class PerformanceTest {

    private static final int SLEEPING_TIME = 200;

    private UnaryFunction<Integer> slowSquare = new UnaryFunction<Integer>() {
        @Override
        public Integer applyTo(Integer input) {
            try{
                Thread.sleep(SLEEPING_TIME);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            return input * input;
        }
    };

    @Test
    public void longRunningTimeForConcurrentMappingTest() {
        long startTime = System.currentTimeMillis();
        Iterable<Integer> slowSquares =
                new MapReduceList(1, 2, 3, 4).map(slowSquare);
        assertThat(slowSquares, contains(1, 4, 9, 16));
        long estimatedTime = System.currentTimeMillis() - startTime;
        assertThat(estimatedTime, lessThan(Long.valueOf(2 * SLEEPING_TIME)));

    }

    private BinaryFunction<Integer> slowSum = new BinaryFunction<Integer>() {
        @Override
        public Integer applyTo(Integer inputA, Integer inputB) {
            try{
                Thread.sleep(SLEEPING_TIME);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            return inputA + inputB;
        }
    };

    @Test
    public void longRunningTimeForConcurrentReducingTest() {
        long startTime = System.currentTimeMillis();
        Integer slowTotal =
                new MapReduceList<Integer>(1, 2, 3, 4, 5, 6).reduce(slowSum, 0);
        assertThat(slowTotal, is(21));
        long estimatedTime = System.currentTimeMillis() - startTime;
        assertThat(estimatedTime, lessThan(Long.valueOf(slowTotal * SLEEPING_TIME)));
    }

    @Test
    public void longRunningTimeForConcurrentMappingReducingTest() {
        long startTime = System.currentTimeMillis();
        Integer result = new MapReduceList<Integer>(1, 2, 3, 4).
                map(slowSquare).reduce(slowSum, 0);
        Assert.assertThat(result, is(30));
        long estimatedTime = System.currentTimeMillis() - startTime;
        assertThat(estimatedTime, lessThan(Long.valueOf(result * SLEEPING_TIME)));
    }

}

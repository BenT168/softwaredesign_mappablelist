package ic.doc;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MapReduceListTest {

    @Test
    public void isListInitialisedEmpty() {
        assertTrue(new MapReduceList().isEmpty());
    }

    @Test
    public void canBeInitialisedFromAnotherList() {
        assertFalse(new MapReduceList(1, 2, 3).isEmpty());
    }

    @Test
    public void isIterable() {
        Iterable<Integer> iterable = new MapReduceList<>(1, 2, 3);
        assertThat(iterable, contains(1, 2, 3));
    }

    private UnaryFunction<Integer> square = new UnaryFunction<Integer>() {
        @Override
        public Integer applyTo(Integer input) {
            return input * input;
        }
    };

    @Test
    public void allowsFunctionToBeMappedOverElements() {
        Iterable<Integer> squares = new MapReduceList(1, 2, 3, 4).map(square);
        assertThat(squares, contains(1, 4, 9, 16));
    }

    @Test
    public void allowsTwoMapFunctionsToBeMappedOverElements() {
        UnaryFunction<Integer> cube = new UnaryFunction<Integer>() {
            @Override
            public Integer applyTo(Integer input) {
                return input * input * input;
            }
        };
        Iterable<Integer> triSquares = new MapReduceList(1, 2, 3, 4).map(square).map(cube);
        assertThat(triSquares, contains(1, 64, 729, 4096));
    }

    private BinaryFunction<Integer> sum = new BinaryFunction<Integer>() {
        @Override
        public Integer applyTo(Integer inputA, Integer inputB) {
            return inputA + inputB;
        }
    };

    @Test
    public void allowsElementsToBeReducedToSingleElement() {
        Integer total = new MapReduceList<Integer>(1, 2, 3, 4).reduce(sum, 0);
        assertThat(total, is(10));
    }

    @Test
    public void canMapReduceFunctionsBeAppliedToList() {
        Integer result = new MapReduceList<Integer>(1, 2, 3, 4).map(square).reduce(sum, 0);
        Assert.assertThat(result, is(30));
    }

    @Test
    public void getInitialValueFromEmptyListThrowIndexOutOfBoundsException() {
        MapReduceList newList = new MapReduceList();
        try {
            newList.initialValue();
            fail("the list is empty, index out of bounds");
        } catch (IndexOutOfBoundsException e) {
            assertThat(e.getMessage(), is("list size must be more than 0"));
        }
    }

}

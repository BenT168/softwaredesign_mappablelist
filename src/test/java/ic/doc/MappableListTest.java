package ic.doc;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class MappableListTest {

    MappableList<Integer> newMappableList = new MappableList<Integer>(new ArrayList<Integer>());

    Function<Integer> square = new Function<Integer>() {
        @Override
        public Integer applyTo(Integer input) {
            return input * input;
        }
    };

    @Test
    public void isListInitialisedAsEmpty() {
        assertTrue(newMappableList.isEmpty());
    }

    @Test
    public void canSquareFunctionAppliedOnEmptyList() {
        assertThat(new MappableList<Integer>().map(square), IsEqual.<List<Integer>>equalTo(new ArrayList<Integer>()));
    }

    @Test
    public void canSquareFunctionAppliedOnElemList() {
        MappableList<Integer> intMappableList = new MappableList<Integer>(3, 6, 9, 12);
        List<Integer> result = intMappableList.map(square);
        assertListElementEquals(result, asList(9, 36, 81, 144));

    }

    @Test
    public void canTwoMapsBeMappedToMapplableList() {
        MappableList<Integer> intMappableList = new MappableList<Integer>(1, 2, 3, 4);
        Function<Integer> cube = new Function<Integer>() {
            @Override
            public Integer applyTo(Integer input) {
                return input * input * input;
            }
        };
        List<Integer> result = intMappableList.map(cube);
        assertListElementEquals(result, asList(1, 8, 27, 64));
    }

    @Test
    public void getInitialValueFromEmptyListThrowIndexOutOfBoundsException(){
        try {
            newMappableList.initialValue();
            fail("the list is empty, index out of bounds");
        } catch (IndexOutOfBoundsException e) {
            assertThat(e.getMessage(), is("list size must be more than 0"));
        }
    }

    private void assertListElementEquals(List<Integer> actual, List<Integer> expected) {
        Iterator<Integer> iter = expected.iterator();
        for (Integer num: actual) {
                assertEquals(num, iter.next());
        }
    }

}
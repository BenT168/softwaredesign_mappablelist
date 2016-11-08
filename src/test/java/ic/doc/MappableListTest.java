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
import static org.junit.matchers.JUnitMatchers.hasItems;

public class MappableListTest {

    MappableList<Integer> newMappableList =
            new MappableList<Integer>(new ArrayList<Integer>());
    MappableList<Integer> intMappableList =
            new MappableList<Integer>(asList(1, 2, 3, 4));

    UnaryFunction<Integer> square = new UnaryFunction<Integer>() {
        @Override
        public Integer applyTo(Integer input) {

            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                //interrupted
            }
            return input * input;
        }
    };

    @Test
    public void doesMappableListContainGivenElement() {
        assertThat(intMappableList, hasItems(1, 2, 3, 4));
    }

    @Test
    public void canSquareFunctionBeAppliedOnEmptyList() {
        assertThat(newMappableList.map(square),
                IsEqual.<List<Integer>>equalTo(new ArrayList<Integer>()));
    }

    @Test
    public void canSquareFunctionBeAppliedOnElemList() {
        List<Integer> result = intMappableList.map(square);
        assertListElementEquals(result, asList(1, 4, 9, 16));

    }

    @Test
    public void canTwoMapsBeMappedToMapplableList() {
        UnaryFunction<Integer> cube = new UnaryFunction<Integer>() {
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

    private void assertListElementEquals(List<Integer> actual,
                                         List<Integer> expected) {
        Iterator<Integer> iter = expected.iterator();
        for (Integer num: actual) {
                assertEquals(num, iter.next());
        }
    }

}

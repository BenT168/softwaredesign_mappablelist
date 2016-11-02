package ic.doc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.fail;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class FoldableListTest {

    FoldableList<Integer> newFoldableList = new FoldableList<Integer>(new ArrayList<Integer>());
    FoldableList<Integer> intFoldableList = new FoldableList<Integer>(asList(1, 2, 3, 4, 5));

    BinaryFunction<Integer> sum = new BinaryFunction<Integer>() {
        @Override
        public Integer applyTo(Integer inputA, Integer inputB) {
            return inputA + inputB;
        }
    };

    @Test
    public void isFoldableListInitialisedAsEmpty() {
        assertTrue(newFoldableList.isEmpty());
    }

    @Test
    public void isFoldableListContainGivenElement() {
        assertThat(intFoldableList, hasItems(1, 2, 3, 4, 5));
    }

    @Test
    public void canBinaryFunctionSumUpElemInList() {
        assertThat(intFoldableList.map(sum), is(15));
    }

    @Test
    public void emptyFoldableListThrowsAssertionErrorWhenBinaryFunctionApplied() {
        testIfAssertionErrorIsRaised();
    }

    @Test
    public void foldableListOfOneElementThrowsAssertionErrorWhenBinaryFunctionApplied() {
        newFoldableList = new FoldableList<>(Arrays.asList(1));
        testIfAssertionErrorIsRaised();
    }

    private void testIfAssertionErrorIsRaised() {
        try {
            newFoldableList.map(sum);
            fail("foldableList of one element should throw assertionError when map applied");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), is("Foldable list must have size more than 2"));
        }
    }


}

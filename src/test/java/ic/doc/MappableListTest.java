package ic.doc;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static sun.nio.cs.Surrogate.is;

public class MappableListTest {

    List<Integer> intList = new ArrayList<Integer>();
    MappableList<Integer> mapList = new MappableList(intList);

    Function<Integer> f = new Function<Integer>() {
        @Override
        public Integer applyTo(Integer input) {
            return input*input;
        }
    };

    @Test
    public void isInitialisedAsEmpty() {
        assertTrue(mapList.isEmpty());
    }

    @Test
    public void canBeMappedOnEmptyClass() {
        assertThat(new MappableList<Integer>().map(f), IsEqual.<List<Integer>>equalTo(new ArrayList<Integer>()));
    }

    @Test
    public void canBeMappedBetweenEmptyClass() {
        MappableList<Integer> expect = new MappableList<Integer>(intList);
        Iterator<Integer> iter = expect.iterator();
        for (Integer elem: mapList) {
            assertThat(String.valueOf(elem), is(iter.next()));
        }
    }

    @Test
    public void containListElem() {
        MappableList<Integer> newList = new MappableList<Integer>(1, 2, 3);
        ArrayList<Integer> l = (ArrayList<Integer>) newList.map(f);
        assertEquals(l.get(0), Integer.valueOf(1));
        assertEquals(l.get(1), Integer.valueOf(4));
        assertEquals(l.get(2), Integer.valueOf(9));
    }

}
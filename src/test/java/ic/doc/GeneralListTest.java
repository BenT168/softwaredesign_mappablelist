package ic.doc;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class GeneralListTest {

    final GeneralList<Integer> newList = new GeneralList<Integer>(new ArrayList<Integer>());

    @Test
    public void isListInitialisedAsEmpty() {
        assertTrue(newList.isEmpty());
    }


}

package ru.ifmo.ads.romashkina.treap;

import org.junit.Before;
import org.junit.Test;
import ru.ifmo.ads.romashkina.utils.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

import static org.junit.Assert.*;
import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.*;
import static ru.ifmo.ads.romashkina.treap.ImplicitTreapTestUtility.*;


public class ImplicitTreapSmallTest {

    @Before
    public void setUp(){
        smallSetUp();
    }

    @Test
    public void nullSizeTest() {
        assertNull(nullT);
        assertEquals(0, size(nullT));
    }

    @Test
    public void simpleSizeTest() {
        assertEquals(1, size(simpleT));
    }

    @Test
    public void simpleIteratorTest(){
        StringJoiner sj = new StringJoiner(" ");
        ImplicitTreap<Integer> tree = merge(simpleT, simpleT1);
        Iterator i = tree.iterator();
        while (i.hasNext()) {
            sj.add(i.next().toString());
        }
        assertEquals("0 1", sj.toString());
    }

    @Test
    public void simpleIteratorTest2(){
        List<Integer> array = createArray(100);
        ImplicitTreap<Integer> tree = makeFromArray(array);
        List<Integer> result = new ArrayList<>(100);
        for (Integer aTree : tree) {
            result.add(aTree);
        }
        assertEquals(array, result);
    }

    @Test
    public void simpleGetRootTest() {
        assertEquals(simpleT, getRoot(simpleT));
    }

    @Test
    public void getRootTest() {
        merge(simpleT, simpleT1);
        assertTrue(getRoot(simpleT) == getRoot(simpleT1));
    }

    @Test
    public void nullMergeTest() {
        ImplicitTreap<Integer> testMerge = merge(nullT, null);
        assertEquals("Size of merging is not what expected", 0, size(testMerge));
        assertNull("Should remain null", testMerge);
    }

    @Test
    public void simpleNullMergeTest() {
        ImplicitTreap<Integer> testMerge = merge(simpleT, nullT);
        assertEquals("Size of merging is not what expected", 1, size(testMerge));
    }

    @Test
    public void simpleMergeTest() {
        ImplicitTreap<Integer> testMerge = merge(simpleT, simpleT1);
        assertEquals("Wrong merging size", 2, size(testMerge));
    }

    @Test
    public void simpleFindIndexTest() {
        assertEquals(1, findIndex(simpleT));
        ImplicitTreap<Integer> testMerge = merge(simpleT, simpleT1);
        assertEquals(2, findIndex(testMerge));
    }

    @Test
    public void simpleValueByIndexTest() {
        assertEquals(null, getValueByIndex(nullT, 1));
        assertEquals(Integer.valueOf(0), getValueByIndex(simpleT, 1));
        assertEquals(Integer.valueOf(1), getValueByIndex(simpleT1, 1));
        ImplicitTreap<Integer> testMerge = merge(simpleT, simpleT1);
        assertEquals(Integer.valueOf(1), getValueByIndex(testMerge, 2));
    }

    @Test
    public void nullSplitByIndexTest() {
        Pair<ImplicitTreap<Integer>> testPair = split(nullT, 0);
        assertNull(testPair.getFirst());
        assertEquals("Wrong Size of splitted treap 1", 0, size(testPair.getFirst()));
        assertNull(testPair.getSecond());
        assertEquals("Wrong Size of splitted treap 2", 0, size(testPair.getSecond()));

        testPair = split(nullT, 1);
        assertNull(testPair.getFirst());
        assertEquals("Wrong Size of splitted treap 1", 0, size(testPair.getFirst()));
        assertNull(testPair.getSecond());
        assertEquals("Wrong Size of splitted treap 2", 0, size(testPair.getSecond()));
    }

    @Test
    public void simpleSplitByIndexTest() {
        Pair<ImplicitTreap<Integer>> testPair = split(simpleT, 0);
        assertNull(testPair.getFirst());
        assertEquals("Wrong Size of splitted treap 1", 0, size(testPair.getFirst()));
        assertEquals("Wrong Size of splitted treap 2", 1, size(testPair.getSecond()));

        testPair = split(simpleT, 1);
        assertEquals("Wrong Size of splitted treap 1", 1, size(testPair.getFirst()));
        assertNull(testPair.getSecond());
        assertEquals("Wrong Size of splitted treap 2", 0, size(testPair.getSecond()));
    }

    @Test
    public void nullSplitByNodeTest() {
        Pair<ImplicitTreap<Integer>> testPair = split(nullT);
        assertNull(testPair.getFirst());
        assertEquals("Wrong Size of splitted treap 1", 0, size(testPair.getFirst()));
        assertNull(testPair.getSecond());
        assertEquals("Wrong Size of splitted treap 2", 0, size(testPair.getSecond()));
    }

    @Test
    public void simpleSplitByNodeTest() {
        Pair<ImplicitTreap<Integer>> testPair = split(simpleT);
        assertEquals("Wrong Size of splitted treap 1", 1, size(testPair.getFirst()));
        assertNull(testPair.getSecond());
        assertEquals("Wrong Size of splitted treap 2", 0, size(testPair.getSecond()));
    }

    @Test
    public void simpleMergeSplitTest() {
        ImplicitTreap<Integer> test = merge(simpleT, simpleT1);
        Pair<ImplicitTreap<Integer>> testPair = split(test, 1);
        assertEquals(simpleT.getY(), testPair.getFirst().getY());
        assertEquals(simpleT1.getY(), testPair.getSecond().getY());
    }

    @Test
    public void simpleSplitMergeTest() {
        ImplicitTreap<Integer> res1 = merge(simpleT, simpleT1);
        Pair<ImplicitTreap<Integer>> testPair = split(merge(simpleT, simpleT1), 1);
        ImplicitTreap<Integer> res2 = merge(testPair.getFirst(), testPair.getSecond());
        assertEquals("Sizes not equal", size(res1), size(res2));
        assertEquals("Priority changed", res1.getY(), res2.getY());
    }

//    @Test
//    public void simpleAddByIndexTest() {
//        ImplicitTreap<Integer> res = add(simpleT, 0, simpleT1);
//        assertEquals(2, size(res));
//    }
//
//    @Test
//    public void addRemoveTest() {
//        ImplicitTreap<Integer> addT = add(simpleT, 0, simpleT1);
//        ImplicitTreap<Integer> removeT = remove(addT, 0);
//        assertEquals(simpleT.getY(), removeT.getY());
//    }
}

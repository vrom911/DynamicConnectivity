package ru.ifmo.ads.romashkina.treap;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.*;
import static ru.ifmo.ads.romashkina.treap.ImplicitTreapTestUtility.*;

/*
 * 1. Сделать findIndexTest разумным.
 * Создать список размера MAX_RUN * MAX_SIZE из чисел. Затем сделать из всех одно дерево.
 * Затем сконвертировать в список вершин.
 * Затем пройтись по списку и от каждой вершины вызвать findIndex, сравнивая с положением вершины в списке.
 */
public class ImplicitTreapTest {

    @Test
    public  void splitMergeTestBig() {
        final int TREAP_SIZE = 10000;
        final int RUN = 200;
        Random r = new Random();

        List<Integer> array = createArray(TREAP_SIZE);
        ImplicitTreap<Integer> t = makeFromArray(array);

        for (int i = 0; i < RUN; i++) {
            int index = r.nextInt(TREAP_SIZE);
            ImplicitTreapPair<Integer> tPair = split(t, index);
            assertEquals(array.subList(0, index), makeValueList(tPair.getFirst()));
            assertEquals(array.subList(index, array.size()), makeValueList(tPair.getSecond()));
            t = merge(tPair.getFirst(), tPair.getSecond());
            assertEquals(array, makeValueList(t));
        }
    }

    @Test
    public void mergeTestBig() {
        for (int i = 0; i < MAX_RUN; i++) {
            List<Integer> array1 = createArray(MAX_TREAP_SIZE);
            List<Integer> array2 = createArray(MAX_TREAP_SIZE);
            ImplicitTreap<Integer> t1 = makeFromArray(array1);
            ImplicitTreap<Integer> t2 = makeFromArray(array2);
            ImplicitTreap<Integer> res = merge(t1, t2);
            array1.addAll(array2);
            assertEquals(array1, makeValueList(res));
        }
    }

    @Test
    public void findIndexTest() {
        List<Integer> list = createArray(MAX_TREAP_SIZE);
        ImplicitTreap<Integer> test = makeFromArray(list);
        List<ImplicitTreap<Integer>> treaps = makeNodeList(test);
        for (int i = 0; i < MAX_TREAP_SIZE; i++) {
            assertEquals(i + 1, findIndex(treaps.get(i)));
        }
    }

    @Test
    public void addByIndexTest() {
        ImplicitTreap<Integer> res = null;
        for (int i = 0; i < MAX_TREAP_SIZE; i++) {
            res = add(res, i, i);
        }
        assertEquals(MAX_TREAP_SIZE, size(res));
        List<Integer> values = makeValueList(res);
        for (int i = 0; i < MAX_TREAP_SIZE; i++) {
            assertEquals((Integer) i, values.get(i));
        }
    }

    @Test
    public void addByIndexRandomPriorityTest() {
        ImplicitTreap<Integer> res = null;
        List<Integer> array = createArray(MAX_TREAP_SIZE);
        for (int i = 0; i < MAX_TREAP_SIZE; i++) {
            res = add(res, i, array.get(i));
        }
        assertEquals(MAX_TREAP_SIZE, size(res));
        assertEquals(array, makeValueList(res));
    }

    @Test
    public void addByRandomIndexTest() {
        Random r = new Random();
        ImplicitTreap<Integer> res = null;
        List<Integer> array = createArray(MAX_TREAP_SIZE);
        List<Integer> curValues = new ArrayList<>(MAX_TREAP_SIZE);
        for (int i = 0; i < MAX_TREAP_SIZE; i++) {
            int index = r.nextInt(i + 1);
            res = add(res, index, array.get(i));
            curValues.add(index, array.get(i));
        }
        assertEquals(MAX_TREAP_SIZE, size(res));
        assertEquals(curValues, makeValueList(res));
    }

    @Test
    public void makeTreapTestBig() {
        List<Integer> array = createArray(100000);
        ImplicitTreap<Integer> t = makeFromArray(array);
        assertEquals(array, makeValueList(t));
    }

    @Test
    public void  getValueByIndexTestBig() {
        List<Integer> array = createArray(MAX_TREAP_SIZE);
        ImplicitTreap<Integer> tree = makeFromArray(array);
        for (int i = 0; i < MAX_TREAP_SIZE; i++) {
            assertEquals(array.get(i), getValueByIndex(tree, i + 1));
        }

    }
}
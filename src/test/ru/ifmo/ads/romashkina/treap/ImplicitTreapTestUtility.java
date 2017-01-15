package ru.ifmo.ads.romashkina.treap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.makeFunctionList;

public class ImplicitTreapTestUtility {
    public static ImplicitTreap<Integer> nullT;
    public static ImplicitTreap<Integer> simpleT;
    public static ImplicitTreap<Integer> simpleT1;
    public static final int MAX_RUN = 100;
    public static final int MAX_TREAP_SIZE = 1000;

    public static void smallSetUp() {
        simpleT = new ImplicitTreap<>(0, 0);
        simpleT1 = new ImplicitTreap<>(1, 1);
        nullT = null;
    }

    public static List<Integer> createArray(int n) {
        List<Integer> result = new ArrayList<>(n);
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            result.add(r.nextInt(n + 1));
        }
        return  result;
    }

    public static <E> List<ImplicitTreap<E>> makeNodeList(ImplicitTreap<E> tree) {
        return makeFunctionList(tree, Function.identity());
    }
}

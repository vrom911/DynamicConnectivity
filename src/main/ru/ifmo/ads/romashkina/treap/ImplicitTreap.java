package ru.ifmo.ads.romashkina.treap;

import ru.ifmo.ads.romashkina.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;


public class ImplicitTreap<T> {
    private long y;
    private ImplicitTreap<T> left;
    private ImplicitTreap<T> right;
    private ImplicitTreap<T> parent;
    private int treeSize;
    private T value;

    public  ImplicitTreap(T value) {
        this(new Random().nextLong(), value);
    }

    public ImplicitTreap(long y){
        this(y, null);
    }

    public ImplicitTreap(long y, T value) {
        this(y, value, null, null, null);
    }

    private ImplicitTreap(long y, T value, ImplicitTreap<T> left, ImplicitTreap<T> right, ImplicitTreap<T> parent) {
        this.y = y;
        this.value = value;
        setLeft(left);
        setRight(right);
        this.parent = parent;
        updateSize();
    }

    public long getY() {
        return y;
    }

    public ImplicitTreap<T> getLeft() {
        return left;
    }

    public void setLeft(ImplicitTreap<T> left) {
        this.left = left;
        if (this.left != null) {
            this.left.parent = this;
        }
        updateSize();
    }

    public ImplicitTreap<T> getRight() {
        return right;
    }

    public void setRight(ImplicitTreap<T> right) {
        this.right = right;
        if (this.right != null) {
            this.right.parent = this;
        }
        updateSize();
    }

    public ImplicitTreap<T> getParent() {
        return parent;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static <E> int size(ImplicitTreap<E> t) {
        return t == null ? 0 : t.treeSize;
    }

    public static <E> int fullSize(ImplicitTreap<E> t) {
        return size(getRoot(t));
    }

    private void updateSize() {
        treeSize = size(left) + size(right) + 1;
    }

    @Override
    public String toString() {
        return "{ value = " + value + " y = " + y + ", treeSize = " + treeSize + " " + isRoot(this) + '}';
    }

    public static <E> ImplicitTreap<E> merge(ImplicitTreap<E> l, ImplicitTreap<E> r) {
        if (l == null) return r;
        if (r == null) return l;

        if (l.y < r.y) {
            r.setLeft(merge(l, r.left));
            return r;
        } else {
            l.setRight(merge(l.right, r));
            return l;
        }
    }

    public static <E> ImplicitTreap<E> mergeTreapList(List<ImplicitTreap<E>> treaps) {
        ImplicitTreap<E> result = null;
        for (ImplicitTreap<E> t : treaps) {
            result = merge(result, t);
        }
        return result;
    }

    public static <E> Pair<ImplicitTreap<E>> split(ImplicitTreap<E> t, int k) {
        Pair<ImplicitTreap<E>> result = new Pair<>(null, null);
        if (t == null) return result;

        int ind = size(t.left) + 1;
        if (ind <= k) {
            result = split(t.right, k - ind);
            t.setRight(result.getFirst());
            t.parent = null;
            result.setFirst(t);
        } else {
            result = split(t.left, k);
            t.setLeft(result.getSecond());
            t.parent = null;
            result.setSecond(t);
        }

        return result;
    }

    private static <E> boolean isRoot(ImplicitTreap<E> node) {
        return node == null || node.parent == null;
    }

    public static <E> int findIndex(ImplicitTreap<E> node) {
        if (node == null) return 0;

        int result = size(node.left) + 1;
        while (!isRoot(node)) {
            if (node.parent.right == node) {
                result += size(node.parent.left) + 1;
            }
            node = node.parent;
        }
        return result;
    }

    private static <E> ImplicitTreap<E> getByIndexFromRoot(ImplicitTreap<E> tree, int i) {
        int ind = size(tree.getLeft()) + 1;
        if (ind == i) {
            return tree;
        } else if (ind < i) {
            return getByIndexFromRoot(tree.getRight(), i - ind);
        } else {
            return getByIndexFromRoot(tree.getLeft(), i);
        }
    }

    public static <E, R> R getByIndex(ImplicitTreap<E> tree, Function<ImplicitTreap<E>, R> f, int i) {
        if (tree == null || fullSize(tree) < i) return null;
        // TODO: проверить в границах i
        ImplicitTreap<E> res = getByIndexFromRoot(getRoot(tree), i);
        return res == null ? null : f.apply(res);
    }

    public  static <E> ImplicitTreap<E> getTreapByIndex(ImplicitTreap<E> tree, int i) {
        return getByIndex(tree, Function.identity(), i);
    }

    public  static <E> E getValueByIndex(ImplicitTreap<E> tree, int i) {
        return getByIndex(tree, ImplicitTreap::getValue, i);
    }

    public static <E> ImplicitTreap<E> getRoot(ImplicitTreap<E> node) {
        return isRoot(node) ? node : getRoot(node.parent);
    }

    public static <E> Pair<ImplicitTreap<E>> split(ImplicitTreap<E> node) {
        return split(getRoot(node), findIndex(node));
    }

    public static <E> ImplicitTreap<E> add(ImplicitTreap<E> t, int k, E value) {
        Pair<ImplicitTreap<E>> splitRes = split(t, k);
        return merge(merge(splitRes.getFirst(), new ImplicitTreap<>(value)), splitRes.getSecond());
    }

    public static <E> ImplicitTreap<E> remove(ImplicitTreap<E> tree, int k) {
        Pair<ImplicitTreap<E>> splitRes = split(tree, k);
        return merge(splitRes.getFirst(), split(splitRes.getSecond(), 1).getSecond());
    }

    public static <E> void inOrderPrint(ImplicitTreap<E> t) {
        if (t == null) return;
        inOrderPrint(t.left);
        System.out.println(t);
        inOrderPrint(t.right);
    }

    public static <E> ImplicitTreap<E> makeFromArray(List<E> array) {
        ImplicitTreap<E> t = null;
        for (int i = 0; i < array.size(); i++) {
            t = add(t, i, array.get(i));
        }
        return t;
    }

    public static <E> List<E> makeValueList(ImplicitTreap<E> tree) {
        return makeFunctionList(tree, ImplicitTreap::getValue);
    }

    public static <E, R> List<R> makeFunctionList(ImplicitTreap<E> tree, Function<ImplicitTreap<E>, R> f) {
        List<R> result = new ArrayList<>(size(tree));
        makeArrayWithList(tree, f, result);
        return result;
    }

    private static <E, R> void makeArrayWithList(ImplicitTreap<E> tree, Function<ImplicitTreap<E>, R> f, List<R> res) {
        if (tree == null) return;
        makeArrayWithList(tree.left, f, res);
        res.add(res.size(), f.apply(tree));
        makeArrayWithList(tree.right, f, res);
    }
}

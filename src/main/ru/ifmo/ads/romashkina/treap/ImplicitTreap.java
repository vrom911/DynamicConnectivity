package ru.ifmo.ads.romashkina.treap;

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

    public void setY(long y) {
        this.y = y;
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

        ImplicitTreap<E> result;
        if (l.y < r.y) {
            result = new ImplicitTreap<>(r.y, r.value, merge(l, r.left), r.right, r.parent);
        } else {
            result = new ImplicitTreap<>(l.y, l.value, l.left, merge(l.right, r), l.parent);
        }
        return result;
    }

    public static <E> ImplicitTreapPair<E> split(ImplicitTreap<E> t, int k) {
        ImplicitTreapPair<E> result = new ImplicitTreapPair<>(null, null);
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

    // Написать оптимальней, если захочется использовать
    public static <E> E valueByIndex(ImplicitTreap<E> tree, int i) {
        if (tree == null) return null;

        int curIndex = findIndex(tree);
        if (i == curIndex) return tree.getValue();
        if (i < curIndex) return valueByIndex(tree.getLeft(), i);
        return valueByIndex(tree.getRight(), i);
    }

    private static <E> ImplicitTreap<E> getRoot(ImplicitTreap<E> node) {
        return isRoot(node) ? node : getRoot(node.parent);
    }

    public static <E> ImplicitTreapPair<E> split(ImplicitTreap<E> node) {
        return split(getRoot(node), findIndex(node));
    }

    public static <E> ImplicitTreap<E> add(ImplicitTreap<E> t, int k, ImplicitTreap<E> toAdd) {
        ImplicitTreapPair<E> splitRes = split(t, k);
        return merge(merge(splitRes.getFirst(), toAdd), splitRes.getSecond());
    }

    public static <E> ImplicitTreap<E> add(ImplicitTreap<E> t, int k, E value) {
        return add(t, k, new ImplicitTreap<>(value));
    }

    public static <E> ImplicitTreap<E> remove(ImplicitTreap<E> tree, int k) {
        ImplicitTreapPair<E> splitRes = split(tree, k);
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

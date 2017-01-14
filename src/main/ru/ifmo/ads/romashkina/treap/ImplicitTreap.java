package ru.ifmo.ads.romashkina.treap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImplicitTreap<T> {
    private long y;
    private ImplicitTreap<T> left;
    private ImplicitTreap<T> right;
    private ImplicitTreap<T> parent;
    private int size;
    private T value;

    public  ImplicitTreap() {
        this(new Random().nextLong());
    }

    public  ImplicitTreap(T value) {
        this(new Random().nextLong());
        this.value = value;
    }

    public  ImplicitTreap(long y, T value) {
        this(y);
        this.value = value;
    }

    public ImplicitTreap(long y){
        this.y = y;
        this.value = null;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.updateSize();
    }

    private ImplicitTreap(long y, T value, ImplicitTreap<T> left, ImplicitTreap<T> right, ImplicitTreap<T> parent) {
        this.y = y;
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
        if (left != null) this.left.parent = this;
        if (right != null) this.right.parent = this;
        this.updateSize();
    }


    public static <E> int size(ImplicitTreap<E> t) {
        return t == null ? 0 : t.size;
    }

    private void updateSize() {
        this.size = size(left) + size(right) + 1;
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
        if (t == null) {
            return result;
        }
        int ind = size(t.left) + 1;
        if (ind <= k) {
            result = split(t.right, k - ind);
            t.right = result.getFirst();
            if (t.right != null) t.right.setParent(t);
            t.setParent(null);
            t.updateSize();
            result.setFirst(t);
            return result;
        } else {
            result = split(t.left, k);
            t.left = result.getSecond();
            if (t.left != null) t.left.setParent(t);
            t.setParent(null);
            t.updateSize();
            result.setSecond(t);

            return result;
        }
    }

    public static <E> ImplicitTreapPair<E> split(ImplicitTreap<E> node) {
        return split(getRoot(node), findIndex(node));
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

    private static <E> boolean isRoot(ImplicitTreap<E> node) {
        return (node == null) || node.parent == null;
    }

    private static <E> ImplicitTreap<E> getRoot(ImplicitTreap<E> node) {
        return (isRoot(node)) ? node : getRoot(node.parent);
    }

    public ImplicitTreap<T> add(int k, long y) {
        return add(k, new ImplicitTreap<>(y));
    }

    public ImplicitTreap<T> add(int k, ImplicitTreap<T> toAdd) {
        ImplicitTreapPair<T> splitRes = split(this, k);
        return merge(merge(splitRes.getFirst(), toAdd), splitRes.getSecond());
    }

    public ImplicitTreap<T> remove(int k) {
        ImplicitTreapPair<T> splitRes = split(this, k);
        return merge(splitRes.getFirst(), split(splitRes.getSecond(), 1).getSecond());
    }

    public static <E> void inOrderPrint(ImplicitTreap<E> t) {
        if (t == null) return;
        inOrderPrint(t.left);
        System.out.println(t);
        inOrderPrint(t.right);
    }

    public static <E> ImplicitTreap<E> makeFromArray(List<E> array) {
        ImplicitTreap<E> t = new ImplicitTreap<>(array.get(0));
        for (int i = 1; i < array.size(); i++) {
            t = t.add(i - 1, new ImplicitTreap<>(array.get(i)));
        }
        return t;
    }

    public List<T> makeArray(List<T> res) {
        if (this.left != null) res = this.left.makeArray(res);
        res.add(this.value);
        if (this.right != null) res = this.right.makeArray(res);
        return res;
    }

//    public List<ImplicitTreap<T>> makeTreapsArray(List<ImplicitTreap<T>> res) {
//        if (this.left != null) res = this.left.makeTreapsArray(res);
//        res.add(this);
//        if (this.right != null) res = this.right.makeTreapsArray(res);
//        return res;
//    }

    public static List<Integer> createArray(int n) {
        List<Integer> result = new ArrayList<>(n);
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int toAdd;
            while (true) {
                if (!result.contains(toAdd = r.nextInt(n + 1))) {
                    result.add(toAdd);
                    break;
                }
            }
        }
        return  result;
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
    }

    public ImplicitTreap<T> getRight() {
        return right;
    }

    public void setRight(ImplicitTreap<T> right) {
        this.right = right;
    }

    public ImplicitTreap<T> getParent() {
        return parent;
    }

    public void setParent(ImplicitTreap<T> parent) {
        this.parent = parent;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{ value = " + value + " y = " + y + ", size = " + size + " " + parent + '}';
    }
}

package ru.ifmo.ads.romashkina.treap;

import ru.ifmo.ads.romashkina.graph.Vertex;

import java.util.List;
import java.util.Random;

public class ImplicitTreap {

    private long y;
    private ImplicitTreap left;
    private ImplicitTreap right;
    private ImplicitTreap parent;
    private int size;
    private Vertex vertex;

    public  ImplicitTreap() {
        this(new Random().nextInt());
    }

    public ImplicitTreap(long y){
        this.y = y;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.updateSize();
    }

    public ImplicitTreap(long y, ImplicitTreap left, ImplicitTreap right) {
        this(y, left, right, null);
    }

    private ImplicitTreap(long y, ImplicitTreap left, ImplicitTreap right, ImplicitTreap parent) {
        this.y = y;
        this.left = left;
        this.right = right;
        this.parent = parent;
        if (left != null) this.left.parent = this;
        if (right != null) this.right.parent = this;
        this.updateSize();
    }


    public static int size(ImplicitTreap t) {
        return t == null ? 0 : t.size;
    }

    private void updateSize() {
        this.size = size(left) + size(right) + 1;
    }

    public static ImplicitTreap merge(ImplicitTreap l, ImplicitTreap r) {
        if (l == null) return r;
        if (r == null) return l;

        ImplicitTreap result;
        if (l.y < r.y) {
            result = new ImplicitTreap(r.y, merge(l, r.left), r.right, r.parent);
        } else {
            result = new ImplicitTreap(l.y, l.left, merge(l.right, r), l.parent);
        }
        return result;
    }

    public static ImplicitTreapPair split(ImplicitTreap t, int k) {
        ImplicitTreapPair result = new ImplicitTreapPair(null, null);
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

    public static ImplicitTreapPair split(ImplicitTreap node) {
        return split(getRoot(node), findIndex(node));
    }

    private static int findIndex(ImplicitTreap node) {
        int result = size(node.left) + 1;
        while (!isRoot(node)) {
            if (node.parent.right == node) {
                result += size(node.parent.left) + 1;
            }
            node = node.parent;
        }
        System.out.println(result);
        return result;
    }

    private static boolean isRoot(ImplicitTreap node) {
        return node.parent == null;
    }

    private static ImplicitTreap getRoot(ImplicitTreap node) {
        return (isRoot(node)) ? node : getRoot(node.parent);
    }

    public ImplicitTreap add(int k, long y) {
        ImplicitTreap toAdd = new ImplicitTreap(y);
        ImplicitTreapPair splitRes = split(this, k);
        return merge(merge(splitRes.getFirst(), toAdd), splitRes.getSecond());
    }

    public ImplicitTreap remove(int k) {
        ImplicitTreapPair splitRes = split(this, k);
        return merge(splitRes.getFirst(), split(splitRes.getSecond(), 1).getSecond());
    }

    public static void inOrderPrint(ImplicitTreap t) {
        if (t == null) return;
        inOrderPrint(t.left);
        System.out.println(t);
        inOrderPrint(t.right);
    }

    public static ImplicitTreap makeFromArray(List<Vertex> list) {
//        ru.ifmo.ads.romashkina.treap.ImplicitTreap t = new ru.ifmo.ads.romashkina.treap.ImplicitTreap(list.get(0));
        for (int i = 1; i < list.size(); i++) {
//            t.add(i, list.get(i));
        }
        return null;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public ImplicitTreap getLeft() {
        return left;
    }

    public void setLeft(ImplicitTreap left) {
        this.left = left;
    }

    public ImplicitTreap getRight() {
        return right;
    }

    public void setRight(ImplicitTreap right) {
        this.right = right;
    }

    public ImplicitTreap getParent() {
        return parent;
    }

    public void setParent(ImplicitTreap parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "{ y = " + y + ", size = " + size + " " + parent + '}';
    }
}

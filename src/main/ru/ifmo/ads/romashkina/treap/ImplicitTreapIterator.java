package ru.ifmo.ads.romashkina.treap;

import java.util.Iterator;

import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.getRoot;

public class ImplicitTreapIterator<T> implements Iterator<T> {
    private ImplicitTreap<T> t;

    public ImplicitTreapIterator(ImplicitTreap<T> t) {
        ImplicitTreap<T> root = getRoot(t);
        while (root.getLeft() != null) root = root.getLeft();
        this.t = root;
    }

    private void goNext() {
        if (t == null) return;
        if (t.getRight() != null) {
            t = t.getRight();
            ImplicitTreap<T> left;
            while ((left = t.getLeft()) != null) {
                t = left;
            }
        } else {
            ImplicitTreap<T> parent = t.getParent();
            while (parent != null && t == parent.getRight()) {
                t = parent;
                parent = parent.getParent();
            }
            t = parent;
        }
    }

    @Override
    public boolean hasNext() {
        return t != null;
    }

    @Override
    public T next() {
        T value = t.getValue();
        goNext();
        return value;
    }
}

package ru.ifmo.ads.romashkina.treap;

public class ImplicitTreapPair<T> {
    public ImplicitTreap<T> first;
    public ImplicitTreap<T> second;

    public ImplicitTreapPair(ImplicitTreap<T> first, ImplicitTreap<T> second) {
        this.first = first;
        this.second = second;
    }

    public ImplicitTreap<T> getFirst() {
        return first;
    }

    public ImplicitTreap<T> getSecond() {
        return second;
    }

    protected void setFirst(ImplicitTreap<T> first) {
        this.first = first;
    }

    protected void setSecond(ImplicitTreap<T> second) {
        this.second = second;
    }
}

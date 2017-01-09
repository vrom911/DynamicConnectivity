package ru.ifmo.ads.romashkina.treap;

public class ImplicitTreapPair {
    public ImplicitTreap first;
    public ImplicitTreap second;

    public ImplicitTreapPair(ImplicitTreap first, ImplicitTreap second) {
        this.first = first;
        this.second = second;
    }

    public ImplicitTreap getFirst() {
        return first;
    }

    public ImplicitTreap getSecond() {
        return second;
    }

    protected void setFirst(ImplicitTreap first) {
        this.first = first;
    }

    protected void setSecond(ImplicitTreap second) {
        this.second = second;
    }
}

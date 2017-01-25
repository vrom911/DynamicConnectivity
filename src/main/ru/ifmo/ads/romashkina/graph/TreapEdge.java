package ru.ifmo.ads.romashkina.graph;

import ru.ifmo.ads.romashkina.treap.ImplicitTreap;

public class TreapEdge {
    private Vertex from;
    private Vertex to;
    private ImplicitTreap<TreapEdge> link;

    public TreapEdge(Vertex from, Vertex to) {
        this(from, to, null);
    }

    public TreapEdge(Vertex from, Vertex to, ImplicitTreap<TreapEdge> link) {
        this.from = from;
        this.to = to;
        this.link = link;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public ImplicitTreap<TreapEdge> getLink() {
        return link;
    }

    public void setLink(ImplicitTreap<TreapEdge> link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "(" + from.getLabel() +
                ", " + to.getLabel() + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;
        return from.getLabel().equals(edge.getFrom()) && to.getLabel().equals(edge.getTo());
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }
}

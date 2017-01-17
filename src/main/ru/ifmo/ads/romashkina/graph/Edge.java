package ru.ifmo.ads.romashkina.graph;

import ru.ifmo.ads.romashkina.treap.ImplicitTreap;

public class Edge {
    private Vertex from;
    private Vertex to;
    private ImplicitTreap<Vertex> fromNode;
    private ImplicitTreap<Vertex> toNode;

    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
    }

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    public ImplicitTreap<Vertex> getFromNode() {
        return fromNode;
    }

    public void setFromNode(ImplicitTreap<Vertex> fromNode) {
        this.fromNode = fromNode;
    }

    public ImplicitTreap<Vertex> getToNode() {
        return toNode;
    }

    public void setToNode(ImplicitTreap<Vertex> toNode) {
        this.toNode = toNode;
    }

    @Override
    public String toString() {
        return "(" + from.getLabel() +
                ", " + to.getLabel() + ')';
    }
}

package ru.ifmo.ads.romashkina.graph;

import ru.ifmo.ads.romashkina.treap.ImplicitTreap;

public class Edge {
    private Vertex from;
    private Vertex to;
    private ImplicitTreap<Vertex> fromNode;
    private ImplicitTreap<Vertex> toNode;
    private int level;

    public Edge(Vertex from, Vertex to) {
        this(from, to, null, null);
    }

    public Edge(Vertex from, Vertex to, ImplicitTreap<Vertex> fromNode, ImplicitTreap<Vertex> toNode) {
        this.from = from;
        this.to = to;
        this.level = 0;
        setLinksToNodes(fromNode, toNode);
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

    public void setLinksToNodes(ImplicitTreap<Vertex> fromNode, ImplicitTreap<Vertex> toNode) {
        setFromNode(fromNode);
        setToNode(toNode);
    }

    @Override
    public String toString() {
        return "(" + from.getLabel() +
                ", " + to.getLabel() + ')';
    }
}

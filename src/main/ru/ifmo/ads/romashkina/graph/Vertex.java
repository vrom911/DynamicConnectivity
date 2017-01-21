package ru.ifmo.ads.romashkina.graph;

import ru.ifmo.ads.romashkina.treap.ImplicitTreap;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    private String label;
    private Map<Vertex, Edge> edges;
    private ImplicitTreap<Vertex> in;

    public Vertex(String label) {
        this.label = label;
        this.edges = new HashMap<>();
    }

    public ImplicitTreap<Vertex> getIn() {
        return in;
    }

    public void setIn(ImplicitTreap<Vertex> in) {
        this.in = in;
    }

    public String getLabel() {
        return label;
    }

    public boolean hasLabel(String l) {
        return label.equals(l);
    }

    public Map<Vertex, Edge> getEdges() {
        return edges;
    }

    public int getEdgesNumber() {
        return edges.size();
    }

    public void addEdge(Vertex to) {
        edges.put(to, new Edge(this, to));
    }

    public void addEdge(Edge edge) {
        edges.put(edge.getTo(), edge);
    }

    public boolean hasEdge(Vertex u) {
        return edges.containsKey(u);
    }

    public Edge getEdgeTo(Vertex to) {
        return edges.get(to);
    }

    public void removeEdgeTo(Vertex to) {
        edges.remove(to);
    }

    @Override
    public String toString() {
        return "{ " + label + ", edges=" + edges.values() + " }\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;
        return label.equals(vertex.label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }
}
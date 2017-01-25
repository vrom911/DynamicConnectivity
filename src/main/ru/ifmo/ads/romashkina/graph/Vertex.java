package ru.ifmo.ads.romashkina.graph;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
    private String label;
    private Map<Vertex, TreapEdge> edges;

    public Vertex(String label) {
        this.label = label;
        this.edges = new HashMap<>();
    }

    public String getLabel() {
        return label;
    }

    public boolean hasLabel(String l) {
        return label.equals(l);
    }

    public Map<Vertex, TreapEdge> getEdges() {
        return edges;
    }

    public int getEdgesNumber() {
        return edges.size();
    }

    public TreapEdge getRandomTreapEdge() {
        return edges.isEmpty() ? null : edges.entrySet().iterator().next().getValue();
    }

    public TreapEdge addTreapEdge(Vertex to) {
        TreapEdge e = new TreapEdge(this, to);
        edges.put(to, e);
        return e;
    }

    public void addTreapEdge(TreapEdge treapEdge) {
        edges.put(treapEdge.getTo(), treapEdge);
    }

    public boolean hasTreapEdge(Vertex u) {
        return edges.containsKey(u);
    }

    public TreapEdge getTreapEdgeTo(Vertex to) {
        return edges.get(to);
    }

    public void removeTreapEdgeTo(Vertex to) {
        edges.remove(to);
    }

    @Override
    public String toString() {
        return edges.values() + " \n";
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

package ru.ifmo.ads.romashkina.graph;

import java.util.*;
import java.util.function.Function;

import static ru.ifmo.ads.romashkina.graph.GraphUtility.addOrientedEdge;
import static ru.ifmo.ads.romashkina.graph.GraphUtility.removeOrientedEdge;

public class Graph {
    private Map<String, Vertex> vertex;

    public Graph(Map<String, Vertex> vertex) {
        this.vertex = vertex;
    }

    public Graph(List<Edge> edges) {
        vertex = new HashMap<>();
        for (Edge e : edges) {
            addEdge(e.getFrom(), e.getTo());
        }
    }

    public Map<String, Vertex> getVertexMap() {
        return vertex;
    }

    public Vertex getVertex(String label) {
        return vertex.get(label);
    }

    public Vertex getRandomVertex() {
        Random r = new Random();
        Vertex[] v = (Vertex[]) vertex.values().toArray();
        return v[r.nextInt(getVertexNum())];
    }

    public int getVertexNum() {
        return vertex.size();
    }

    public void addEdge(String vLabel, String uLabel) {
        Function<String, Vertex> f = Vertex::new;
        Vertex v = vertex.computeIfAbsent(vLabel, f);
        if (!uLabel.equals("")) {
            Vertex u = vertex.computeIfAbsent(uLabel, f);
            addOrientedEdge(v, u);
        }
    }

    public void removeEdge(String vLabel, String uLabel) {
        Vertex v = vertex.get(vLabel);
        Vertex u = vertex.get(uLabel);
        removeOrientedEdge(v, u);
    }

    @Override
    public String toString() {
        return vertex.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Graph o = (Graph) obj;

        return vertex.equals(o.vertex);
    }
}

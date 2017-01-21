package ru.ifmo.ads.romashkina.graph;

import java.util.*;

import static ru.ifmo.ads.romashkina.graph.GraphUtility.addOrientedEdge;

public class Graph {
    private Map<String, Vertex> vertex;

    public Graph(Map<String, Vertex> vertex) {
        this.vertex = vertex;
    }

    public Graph(List<UndirectedEdge> edges) {
        vertex = new HashMap<>();
        for (UndirectedEdge e : edges) {
            addDirectedEdge(e.getFrom(), e.getTo());
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

    public void addAllVertices(Graph g) {
        vertex.putAll(g.getVertexMap());
    }

    public void addDirectedEdge(String vLabel, String uLabel) {
        Vertex v = vertex.putIfAbsent(vLabel, new Vertex(vLabel));
        if (!uLabel.equals("")) {
            Vertex u = vertex.putIfAbsent(uLabel, new Vertex(uLabel));
            addOrientedEdge(v, u);
        }
    }
    @Override
    public String toString() {
        return "Graph{ " + "vertex = " + vertex + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Graph o = (Graph) obj;

        return vertex.equals(o.vertex);
    }
}

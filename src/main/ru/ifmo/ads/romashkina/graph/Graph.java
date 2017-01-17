package ru.ifmo.ads.romashkina.graph;

import java.util.List;
import java.util.Random;

import static ru.ifmo.ads.romashkina.graph.GraphUtility.readGraph;

public class Graph {
    private List<Vertex> vertex;

    public Graph(String fileName) {
        this(readGraph(fileName));
    }

    public Graph(List<Vertex> vertex) {
        this.vertex = vertex;
    }

    public List<Vertex> getVertex() {
        return this.vertex;
    }

    public Vertex getVertex(int i) {
        return this.vertex.get(i);
    }

    public Vertex getRandomVertex() {
        Random r = new Random();
        return this.vertex.get(r.nextInt(getVertexNum()));
    }

    public int getVertexNum() {
        return vertex.size();
    }

    @Override
    public String toString() {
        return "Graph{ " + "vertex = " + vertex + '}';
    }
}

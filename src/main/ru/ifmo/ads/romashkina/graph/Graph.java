package ru.ifmo.ads.romashkina.graph;

import java.util.List;
import java.util.Random;

public class Graph {
    private List<Vertex> vertex;

    public Graph(List<Vertex> vertex) {
        this.vertex = vertex;
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
        return "ru.ifmo.ads.romashkina.graph.Graph{ " + "vertex = " + vertex + '}';
    }
}

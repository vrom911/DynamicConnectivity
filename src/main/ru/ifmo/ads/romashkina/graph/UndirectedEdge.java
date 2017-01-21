package ru.ifmo.ads.romashkina.graph;

public class UndirectedEdge {
    private final String from;
    private final String to;

    public UndirectedEdge(String edge) {
        String[] vertices = edge.split(" ");
        from = vertices[0];
        if (vertices.length > 1) {
            to = vertices[1];
        } else {
            to = "";
        }
    }

    public UndirectedEdge(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return '(' + from + ", " + to + ')';
    }
}

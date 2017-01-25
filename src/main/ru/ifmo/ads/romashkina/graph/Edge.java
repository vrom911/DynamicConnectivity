package ru.ifmo.ads.romashkina.graph;

public class Edge implements Comparable<Edge> {
    private final String from;
    private final String to;

    public Edge(String edge) {
        String[] vertices = edge.split(" ");
        from = vertices[0];
        if (vertices.length > 1) {
            to = vertices[1];
        } else {
            to = "";
        }
    }

    public Edge(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Edge reverseEdge() {
        return new Edge(to, from);
    }

    @Override
    public String toString() {
        return '(' + from + ", " + to + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (!from.equals(edge.from)) return false;
        return to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

    @Override
    public int compareTo(Edge edge) {
        int res = from.compareTo(edge.from);
        return (res == 0) ? to.compareTo(edge.to) : res;
    }
}

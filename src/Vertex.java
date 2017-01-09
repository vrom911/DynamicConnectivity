import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable {
    private int number;
    private String label;
    private List<Edge> edges;
    private ImplicitTreap firstIn;
    private ImplicitTreap lastIn;

    public Vertex(String label, int number) {
        this.number = number;
        this.label = label;
        this.edges = new ArrayList<>();
    }

    public ImplicitTreap getFirstIn() {
        return firstIn;
    }

    public void setFirstIn(ImplicitTreap firstIn) {
        this.firstIn = firstIn;
    }

    public ImplicitTreap getLastIn() {
        return lastIn;
    }

    public void setLastIn(ImplicitTreap lastIn) {
        this.lastIn = lastIn;
    }

    public String getLabel() {
        return this.label;
    }

    public boolean hasLabel(String l) {
        return this.label.equals(l);
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public int getNumber() {
        return number;
    }

    public int getEdgesNumber() {
        return this.edges.size();
    }

    public void addEdge(Vertex to) {
        this.edges.add(new Edge(this, to));
    }

    public void addEdge(Edge e) {
        this.edges.add(e);
    }

    @Override
    public String toString() {
        return "Vertex{ " + label + ", num: " + number +
                ", edges=" + edges +
                " }\n";
    }

    @Override
    public int compareTo(Object o) {
        Vertex v = (Vertex) o;
        return Integer.valueOf(number).compareTo(v.getNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        if (number != vertex.number) return false;
        return label.equals(vertex.label);

    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + label.hashCode();
        return result;
    }
}

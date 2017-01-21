package ru.ifmo.ads.romashkina.graph;

public class GraphWithMST {
    private Graph graphMST;
    Graph graphOther;

    public GraphWithMST(Graph graphMST, Graph graphOther) {
        this.graphMST = graphMST;
        this.graphOther = graphOther;
    }

    public Graph getGraphMST() {
        return graphMST;
    }

    public Graph getGraphOther() {
        return graphOther;
    }

    @Override
    public String toString() {
        return "GraphWithMST{" + graphMST + " | " + graphOther + '}';
    }
}

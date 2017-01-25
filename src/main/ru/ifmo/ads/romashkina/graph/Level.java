package ru.ifmo.ads.romashkina.graph;

public class Level {
    private Graph graphMST;
    private Graph graphOther;

    public Level(Graph graphMST, Graph graphOther) {
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
        return "{" + graphMST + "\n  ----|---- \n" + graphOther + '}';
    }
}

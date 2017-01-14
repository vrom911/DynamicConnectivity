package ru.ifmo.ads.romashkina.euler;

import ru.ifmo.ads.romashkina.graph.*;
import ru.ifmo.ads.romashkina.treap.ImplicitTreap;

import java.util.ArrayList;
import java.util.List;

public class EulerTourTree {
    private ImplicitTreap<Vertex> tree;
    private List<Vertex> tour;

    EulerTourTree(Graph graph) {
        this.tour = new ArrayList<>(GraphUtility.findEulerPath(graph.getRandomVertex(), graph.getVertexNum()));
        this.tree = ImplicitTreap.makeFromArray(this.tour);
    }

    @Override
    public String toString() {
        StringBuilder t = new StringBuilder();
        for (Vertex v : tour) {
            t.append(v.getLabel()).append(" ");
        }
        return "ET Tree{ \n" +
                "tree = " + tree +
                "\ntour = " + t.toString() +
                "\n}";
    }
}

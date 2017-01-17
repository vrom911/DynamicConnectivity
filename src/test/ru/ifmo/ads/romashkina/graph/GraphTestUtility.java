package ru.ifmo.ads.romashkina.graph;

import ru.ifmo.ads.romashkina.treap.ImplicitTreap;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;
import static ru.ifmo.ads.romashkina.euler.EulerTourTree.link;
import static ru.ifmo.ads.romashkina.euler.EulerUtility.findEulerPath;
import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.*;

public class GraphTestUtility {

    public static ImplicitTreap<Vertex> makeEulerPathTreeFromList(List<Vertex> vertices) {
        List<ImplicitTreap<Vertex>> vertexTreaps = new ArrayList<>(vertices.size());
        for (Vertex v : vertices) {
            ImplicitTreap<Vertex> curVertexTreap = new ImplicitTreap<>(v);
            vertexTreaps.add(curVertexTreap);
            v.setIn(curVertexTreap);
        }
        return mergeTreapList(vertexTreaps);
    }

    public static ImplicitTreap<Vertex> makeEulerPathTree(Graph graph, Vertex u) {
        return makeEulerPathTreeFromList(findEulerPath(u, graph.getVertexNum()));
    }

    public static ImplicitTreap<Vertex> makeEulerPathTreeRandomVertex(Graph graph) {
        return  makeEulerPathTree(graph, graph.getRandomVertex());
    }

    public static List<Vertex> createVerticesFromLabels(String[] labels) {
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            vertices.add(new Vertex(labels[i], i));
        }
        return vertices;
    }

    public static String labelsFromVertices(List<Vertex> vertices) {
        StringJoiner labels = new StringJoiner(" ");
        for (Vertex v : vertices) {
            labels.add(v.getLabel());
        }
        return labels.toString();
    }

    public static String labelsFromTour(ImplicitTreap<Vertex> tour) {
        return labelsFromVertices(makeValueList(tour));
    }

    public static void handTestTemplate(String l1, String l2, int v1, int v2, String result) {
        List<Vertex> eulerTour1 = createVerticesFromLabels(l1.split(" "));
        List<Vertex> eulerTour2 = createVerticesFromLabels(l2.split(" "));
        makeEulerPathTreeFromList(eulerTour1);
        makeEulerPathTreeFromList(eulerTour2);
        assertEquals(result, labelsFromTour(link(eulerTour1.get(v1), eulerTour2.get(v2))));
    }
}

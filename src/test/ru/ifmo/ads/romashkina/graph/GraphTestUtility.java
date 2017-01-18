package ru.ifmo.ads.romashkina.graph;

import ru.ifmo.ads.romashkina.euler.EulerTourTree;
import ru.ifmo.ads.romashkina.treap.ImplicitTreap;
import ru.ifmo.ads.romashkina.treap.ImplicitTreapPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;
import static ru.ifmo.ads.romashkina.euler.EulerTourTree.cut;
import static ru.ifmo.ads.romashkina.euler.EulerTourTree.link;
import static ru.ifmo.ads.romashkina.euler.EulerUtility.findEulerPath;
import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.*;

public class GraphTestUtility {

    public static ImplicitTreap<Vertex> makeEulerPathTreeFromList(List<Vertex> vertices) {
        List<ImplicitTreap<Vertex>> vertexTreaps = new ArrayList<>(vertices.size());
        for (Vertex v : vertices) {
            ImplicitTreap<Vertex> curVertexTreap = new ImplicitTreap<>(EulerTourTree.random.nextLong(), v);
            vertexTreaps.add(curVertexTreap);
            v.setIn(curVertexTreap);

            int  curTreapSize = vertexTreaps.size();
            if (curTreapSize > 1) {
                Vertex previousVertex = vertices.get(curTreapSize - 2);
                Edge edge = new Edge(previousVertex, v, vertexTreaps.get(curTreapSize - 2), curVertexTreap);
                previousVertex.addEdge(edge);
            }
        }
        return mergeTreapList(vertexTreaps);
    }

    public static ImplicitTreap<Vertex> makeEulerPathTree(Graph graph, Vertex u) {
        return findEulerPath(u, graph.getVertexNum());
    }

    public static ImplicitTreap<Vertex> makeEulerPathTreeRandomVertex(Graph graph) {
        return  makeEulerPathTree(graph, graph.getRandomVertex());
    }

    public static ImplicitTreap<Vertex> makeEulerFromString(String l) {
        return makeEulerPathTreeFromList(createVerticesFromLabels(l.split(" ")));
    }

    public static List<Vertex> createVerticesFromLabels(String[] labels) {
        HashMap<String, Vertex> vertexMap = new HashMap<>();
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            String label = labels[i];
            Vertex v;
            if (vertexMap.containsKey(label)) {
                v = vertexMap.get(label);
            } else {
                v = new Vertex(label, i);
                vertexMap.put(label, v);
            }
            vertices.add(v);
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

    public static ImplicitTreap<Vertex> handLinkTest(String l1, String l2, int v1, int v2) {
        List<Vertex> eulerTour1 = createVerticesFromLabels(l1.split(" "));
        List<Vertex> eulerTour2 = createVerticesFromLabels(l2.split(" "));
        makeEulerPathTreeFromList(eulerTour1);
        makeEulerPathTreeFromList(eulerTour2);
        return  link(eulerTour1.get(v1), eulerTour2.get(v2));
    }

    public static String handLinkTestToString(String l1, String l2, int v1, int v2) {
        return  labelsFromTour(handLinkTest(l1, l2, v1, v2));
    }

    public static void handCutTest(int edge, String l, String result1, String result2) {
        List<Vertex> eulerTour1 = createVerticesFromLabels(l.split(" "));
        makeEulerPathTreeFromList(eulerTour1);
        ImplicitTreapPair<Vertex> cutResult = cut(eulerTour1.get(edge), eulerTour1.get(edge + 1));
        assertEquals(result1, labelsFromTour(cutResult.getFirst()));
        assertEquals(result2, labelsFromTour(cutResult.getSecond()));
    }
}
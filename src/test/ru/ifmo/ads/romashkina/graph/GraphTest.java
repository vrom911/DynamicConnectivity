package ru.ifmo.ads.romashkina.graph;

import org.junit.Before;
import org.junit.Test;
import ru.ifmo.ads.romashkina.treap.ImplicitTreap;

import static org.junit.Assert.assertEquals;
import static ru.ifmo.ads.romashkina.euler.EulerTourTree.link;
import static ru.ifmo.ads.romashkina.euler.EulerUtility.findEulerPath;
import static ru.ifmo.ads.romashkina.graph.GraphTestUtility.*;
import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.makeValueList;

public class GraphTest {

    @Before
    public void setUp() {
    }

    @Test
    public void GraphInitTest() {
        Graph graph1 = new Graph("graphTest.txt");
        Graph graph2 = new Graph("graphTest1.txt");

        ImplicitTreap<Vertex> res1 = makeEulerPathTree(graph1, graph1.getVertex(2));
        ImplicitTreap<Vertex> res2 = makeEulerPathTree(graph2, graph2.getVertex(0));

        assertEquals("c d c b c a c g e g f g h g c", labelsFromTour(link(graph1.getVertex(2), graph2.getVertex(2))));

    }

    @Test
    public void eulerTourTest() {
        Graph graph1 = new Graph("graphTest.txt");
        assertEquals("a c d c b c a", labelsFromVertices(findEulerPath(graph1.getVertex(0), graph1.getVertexNum())));
    }

    @Test
    public void oneVertexLinkTest() {
        Vertex v = new Vertex("v", 0);
        ImplicitTreap<Vertex> vTree = new ImplicitTreap<>(v);
        v.setIn(vTree);
        Vertex u = new Vertex("u", 0);
        ImplicitTreap<Vertex> uTree = new ImplicitTreap<>(u);
        u.setIn(uTree);
        ImplicitTreap<Vertex> testVtoU = link(v, u);
        assertEquals("v u v", labelsFromVertices(makeValueList(testVtoU)));
    }

    @Test
    public void oneVertexLinkHandTest() {
        handTestTemplate("a", "b", 0, 0, "a b a");
    }

    @Test
    public void oneVertexToManyLinkHandTest() {
        handTestTemplate("a", "b c b", 0, 0, "a b c b a");
        handTestTemplate("a b a", "c", 0, 0, "a c a b a");
    }

    @Test
    public void paperExampleLinkHandTest() {
        handTestTemplate("c a c b c d c", "e g f g h g e", 6, 5, "c a c b c d c g e g f g h g c");
    }

    @Test
    public void handLinkHandTest() {
        handTestTemplate("a b d b c e c b a", "f g j k j i j g h g f", 6, 1, "a b d b c e c g j k j i j g h g f g c b a");
    }
}
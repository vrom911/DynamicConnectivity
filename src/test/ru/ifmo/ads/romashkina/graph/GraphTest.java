package ru.ifmo.ads.romashkina.graph;

import org.junit.Before;
import org.junit.Test;
import ru.ifmo.ads.romashkina.treap.ImplicitTreap;

import static org.junit.Assert.assertEquals;
import static ru.ifmo.ads.romashkina.euler.EulerTourTree.link;
import static ru.ifmo.ads.romashkina.euler.EulerUtility.findEulerPathForVertex;
import static ru.ifmo.ads.romashkina.graph.GraphTestUtility.*;
import static ru.ifmo.ads.romashkina.graph.GraphUtility.makeGraphFromFile;
import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.makeValueList;

public class GraphTest {

    @Before
    public void setUp() {
    }

    @Test
    public void GraphInitTest() {
        Graph graph1 = makeGraphFromFile("graphTest.txt");
        Graph graph2 = makeGraphFromFile("graphTest1.txt");

        makeEulerPathTree(graph1, graph1.getVertex("c"));
        makeEulerPathTree(graph2, graph2.getVertex("e"));

        assertEquals("c d c b c g e g h g f g c a c", labelsFromTour(link(graph1.getVertex("c"), graph2.getVertex("g"))));

    }

    @Test
    public void eulerTourTest() {
        Graph graph1 = makeGraphFromFile("graphTest.txt");
        assertEquals("a c d c b c a", labelsFromEdges(makeValueList(findEulerPathForVertex(graph1.getVertex("a"), graph1.getVertexNum()))));
    }

    @Test
    public void oneVertexLinkTest() {
        Vertex v = new Vertex("v");
        Vertex u = new Vertex("u");

        ImplicitTreap<TreapEdge> testVtoU = link(v, u);
        assertEquals("v u v", labelsFromEdges(makeValueList(testVtoU)));
    }

    @Test
    public void oneVertexLinkHandTest() {
        String result = handLinkTestToString("a", "b", 0, 0);
        assertEquals("a b a", result);
    }

    @Test
    public void oneVertexToManyLinkHandTest() {
        String result = handLinkTestToString("a", "b c b", 0, 0);
        assertEquals("a b c b a", result);

        result = handLinkTestToString("a b a", "c", 0, 0);
        assertEquals("a c a b a", result);
    }

    @Test
    public void paperExampleLinkHandTest() {
        String result = handLinkTestToString("c a c b c d c", "e g f g h g e", 6, 5);
        assertEquals("c g e g f g h g c a c b c d c", result);
    }

    @Test
    public void linkHandTest() {
        String result = handLinkTestToString("a b d b c e c b a", "f g j k j i j g h g f", 6, 1);
        assertEquals("a b d b c e c g f g j k j i j g h g c b a", result);
    }

    @Test
    public void doubleLinkTest() {
        ImplicitTreap<TreapEdge> linkTree = handLinkTest("a", "b", 0, 0);
        assertEquals("a b a", labelsFromTour(linkTree));
        String result = labelsFromTour(link(linkTree.getValue().getTo(), new Vertex("c")));
        assertEquals("a b c b a", result);
    }

    @Test
    public void oneEdgeCutTest() {
        handCutTest(0, "a b a", "", "");
    }

    @Test
    public void oneEdgeReverseCutTest() {
        handCutTest(1, "a b a", "", "");
    }

    @Test
    public void simpleCutTest() {
        handCutTest(0, "a c b c a", "", "c b c");
        handCutTest(3, "a c b c a", "", "c b c");
    }

    @Test
    public void lectureCutTest() {
        handCutTest(3, "c e c g j k j i j g h g f g c", "c e c g h g f g c", "j k j i j");
    }

    @Test
    public void otherCutTest() {
        handCutTest(4, "c a c b c g h g e g f g c d c", "c a c b c d c", "g h g e g f g");
    }
}

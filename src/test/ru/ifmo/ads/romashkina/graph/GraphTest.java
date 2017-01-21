package ru.ifmo.ads.romashkina.graph;

import org.junit.Before;
import org.junit.Test;
import ru.ifmo.ads.romashkina.euler.EulerTourTree;
import ru.ifmo.ads.romashkina.treap.ImplicitTreap;
import ru.ifmo.ads.romashkina.utils.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.ifmo.ads.romashkina.euler.EulerTourTree.cut;
import static ru.ifmo.ads.romashkina.euler.EulerTourTree.link;
import static ru.ifmo.ads.romashkina.euler.EulerUtility.findEulerPathForVertex;
import static ru.ifmo.ads.romashkina.graph.GraphTestUtility.*;
import static ru.ifmo.ads.romashkina.graph.GraphUtility.*;
import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.makeValueList;

public class GraphTest {

    @Before
    public void setUp() {
    }

    @Test
    public void GraphInitTest() {
        Graph graph1 = makeGraphFromFile("graphTest.txt");
        Graph graph2 = makeGraphFromFile("graphTest1.txt");

        ImplicitTreap<Vertex> res1 = makeEulerPathTree(graph1, graph1.getVertex("c"));
        ImplicitTreap<Vertex> res2 = makeEulerPathTree(graph2, graph2.getVertex("e"));

        assertEquals("c d c b c a c g e g h g f g c", labelsFromTour(link(graph1.getVertex("c"), graph2.getVertex("g"))));

    }

    @Test
    public void eulerTourTest() {
        Graph graph1 = makeGraphFromFile("graphTest.txt");
        assertEquals("a c d c b c a", labelsFromVertices(makeValueList(findEulerPathForVertex(graph1.getVertex("a"), graph1.getVertexNum()))));
    }

    @Test
    public void oneVertexLinkTest() {
        Vertex v = new Vertex("v");
        ImplicitTreap<Vertex> vTree = new ImplicitTreap<>(v);
        v.setIn(vTree);
        Vertex u = new Vertex("u");
        ImplicitTreap<Vertex> uTree = new ImplicitTreap<>(u);
        u.setIn(uTree);
        ImplicitTreap<Vertex> testVtoU = link(v, u);
        assertEquals("v u v", labelsFromVertices(makeValueList(testVtoU)));
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
        assertEquals("a b a c a", result);
    }

    @Test
    public void paperExampleLinkHandTest() {
        String result = handLinkTestToString("c a c b c d c", "e g f g h g e", 6, 5);
        assertEquals("c a c b c d c g e g f g h g c", result);
    }

    @Test
    public void linkHandTest() {
        String result = handLinkTestToString("a b d b c e c b a", "f g j k j i j g h g f", 6, 1);
        assertEquals("a b d b c e c g f g j k j i j g h g c b a", result);
    }

    @Test
    public void doubleLinkTest() {
        ImplicitTreap<Vertex> linkTree = handLinkTest("a", "b", 0, 0);
        assertEquals("a b a", labelsFromTour(linkTree));
        ImplicitTreap<Vertex> addTree = makeEulerFromString("c");
        String result = labelsFromTour(link(linkTree.getValue(), addTree.getValue()));
        assertEquals("a c a b a", result);
    }

    @Test
    public void oneEdgeCutTest() {
        handCutTest(0, "a b a", "a", "b");
    }

    @Test
    public void oneEdgeReverseCutTest() {
        handCutTest(1, "a b a", "a", "b");
    }

    @Test
    public void simpleCutTest() {
        handCutTest(0, "a c b c a", "a", "c b c");
        handCutTest(3, "a c b c a", "a", "c b c");
    }

    @Test
    public void lectureCutTest() {
        handCutTest(3, "c e c g j k j i j g h g f g c", "c e c g h g f g c", "j k j i j");
    }

    @Test
    public void otherCutTest() {
        handCutTest(4, "c a c b c g h g e g f g c d c", "c a c b c d c", "g h g e g f g");
    }

    @Test
    public void linkCutLinkTest() {
        Vertex a = new Vertex("a");
        a.setIn(new ImplicitTreap<>(EulerTourTree.random.nextLong(), a));
        Vertex b = new Vertex("b");
        b.setIn(new ImplicitTreap<>(EulerTourTree.random.nextLong(), b));
        ImplicitTreap<Vertex> firstLink = link(a, b);
        List<Vertex> firstLinkVerticies = new ArrayList<>(makeValueList(firstLink));
        Pair<ImplicitTreap<Vertex>> cutResult = cut(a, b);
        assertEquals(a, cutResult.getFirst().getValue());
        assertEquals(b, cutResult.getSecond().getValue());
        ImplicitTreap<Vertex> secondLink = link(cutResult.getFirst().getValue(), cutResult.getSecond().getValue());
        assertEquals(firstLinkVerticies, makeValueList(secondLink));
//        assertTrue(firstLink.getValue().getEdges().keySet().contains(b));
    }

    @Test
    public void undirectedEdgesTest() {
        List<UndirectedEdge> edges = readGraphEdges("undirectedEdgesTest");
        System.out.println(kruskalFindMST(edges));
//        System.out.println(Integer.highestOneBit(15));

    }
}

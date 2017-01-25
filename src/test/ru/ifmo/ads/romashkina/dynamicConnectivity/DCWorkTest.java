package ru.ifmo.ads.romashkina.dynamicConnectivity;

import org.junit.Before;
import org.junit.Test;
import ru.ifmo.ads.romashkina.graph.Edge;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.ifmo.ads.romashkina.graph.GraphUtility.readGraphEdges;

public class DCWorkTest {
    private List<Edge> edges;
    private FastDynamicConnectivity fdc;
    private NaiveDynamicConnectivity ndc;

    @Before
    public void setUp() {
        edges = readGraphEdges("undirectedEdgesTest");
        ndc = new NaiveDynamicConnectivity(edges);
        fdc = new FastDynamicConnectivity(edges);
    }

    @Test
    public void simpleFastDCinitTest() {
        List<Edge> revEdges = new ArrayList<>(edges);
        for (Edge e : edges) {
            revEdges.add(e.reverseEdge());
        }
        assertTrue(revEdges.containsAll(fdc.getEdgeLevel().keySet()));
    }

//    @Test
//    public void simpleNaiveDCinitTest() {
//        List<Edge> revEdges = new ArrayList<>(edges);
//        for (Edge e : edges) {
//            revEdges.add(e.reverseEdge());
//        }
//        assertTrue(revEdges.containsAll(fdc.getEdgeLevel().keySet()));
//    }

    @Test
    public void simpleAreConnectedFastTest() {
        assertTrue(fdc.areConnected("a", "b"));
        assertTrue(fdc.areConnected("a", "c"));
        assertFalse(fdc.areConnected("b", "v"));
    }

    @Test
    public void simpleAreConnectedNaiveTest() {
        assertTrue(ndc.areConnected("a", "b"));
        assertTrue(ndc.areConnected("a", "c"));
        assertFalse(ndc.areConnected("b", "v"));
    }

    @Test
    public void simpleLinkFastTest(){
//        System.out.println(fdc.getBasicLevel());
        fdc.link("a", "v");
//        System.out.println(fdc.getBasicLevel());
    }

    @Test
    public void simpleLinkNaiveTest(){
//        System.out.println(ndc);
        ndc.link("a", "v");
//        System.out.println(ndc);
    }

    @Test
    public void simpleCutFastTest() {
        fdc.cut("b", "a");
//        System.out.println(fdc.getBasicLevel() + "\n");
//        System.out.println(fdc.getLevel(1));
    }

    @Test
    public void simpleCutNaiveTest() {
        ndc.cut("a", "b");
//        System.out.println(ndc);
    }

    @Test
    public void levelTest() {
        fdc.cut("a", "b");
        fdc.cut("c", "d");
        fdc.cut("a", "d");
        System.out.println(fdc);
    }
}

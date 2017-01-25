package ru.ifmo.ads.romashkina.dynamicConnectivity;

import ru.ifmo.ads.romashkina.euler.EulerTourTree;
import ru.ifmo.ads.romashkina.graph.Edge;
import ru.ifmo.ads.romashkina.graph.Graph;
import ru.ifmo.ads.romashkina.graph.Vertex;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class DCTestUtility {
    private static List<String> vertices;
    public static List<Edge> edges;
    public static FastDynamicConnectivity fdc;
    public static NaiveDynamicConnectivity ndc;
    public static Map<Integer, int[]> verEdge = new HashMap<>();

    public static void setting() {
        verEdge.put(5, new int[]{5, 7, 10, 13, 25, 50});
        verEdge.put(6, new int[]{6, 13, 25, 37, 50, 100, 120});
        verEdge.put(7, new int[]{7, 25, 50, 100});
        verEdge.put(10, new int[]{10, 25, 50, 100});
        verEdge.put(100, new int[]{5, 100, 1000, 2000});
        verEdge.put(1000, new int[]{100, 1000, 5000, 10000, 100000});
        verEdge.put(100000, new int[]{100, 1000, 10000, 500000});
    }

    public static void settingUp(int v, int e) {
        edges = generateEdges(v, e);
        ndc = new NaiveDynamicConnectivity(edges);
        fdc = new FastDynamicConnectivity(edges);
//        edges = generateOperatingEdges(v, e);
    }

//    public static List<Edge> generateEmptyGraph(int v) {
//        for (int i = 0; i < v; i++) {
//            edges.add(new Edge("v" + i, ""));
//        }
//    }

    public static List<Edge> generateEdges(int v, int e) {
        List<Edge> es = new ArrayList<>(v + e);

        vertices = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            vertices.add("v" + i);
            es.add(new Edge("v" + i, ""));
        }
        for (int i = 0; i < e; i++) {
            String from = vertices.get(EulerTourTree.random.nextInt(v));
            String to = vertices.get(EulerTourTree.random.nextInt(v));
            if (!from.equals(to)) {
                Edge ed = new Edge(from, to);
                es.add(ed);
            }
        }
        return es;
    }

    public static List<Edge> generateOperatingEdges(int n, int m) {
        List<Edge> e = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            String from = vertices.get(EulerTourTree.random.nextInt(n));
            String to = vertices.get(EulerTourTree.random.nextInt(n));
            if (!from.equals(to)) {
                e.add(new Edge(from, to));
            } else {
                i--;
            }
        }
        return e;
    }

    private static List<Edge> makeEdgeListFromGraph(Graph g) {
        List<Edge> es = new ArrayList<>();
        for (Vertex v : g.getVertexMap().values()) {
            for (Vertex treapE : v.getEdges().keySet()) {
                Edge e = new Edge(v.getLabel(), treapE.getLabel());
                es.add(e);
            }
        }
        return es;
    }

    public static List<Edge> makeEdgeListDC(FastDynamicConnectivity fdc) {
        List<Edge> list = makeEdgeListFromGraph(fdc.getBasicLevel().getGraphMST());
        list.addAll(makeEdgeListFromGraph(fdc.getBasicLevel().getGraphOther()));
        Comparator<Edge> c = Edge::compareTo;
        list.sort(c);
        return list;
    }

    public static List<Edge> makeEdgeListDC(NaiveDynamicConnectivity ndc) {
        List<Edge> list = makeEdgeListFromGraph(ndc.getGraph());
        Collections.sort(list);
        return list;
    }

    public static void nextOperationBoth(String v, String u) {
        Operation o = Operation.values()[EulerTourTree.random.nextInt(3)];
//        System.out.println(o + " " + v + " " + u);
        switch (o) {
            case ARECONNECTED:
                boolean expected = ndc.areConnected(v, u);
                boolean actual = fdc.areConnected(v, u);
                assertEquals("areConnected", expected, actual);
                break;
            case LINK:
                ndc.link(v, u);
                fdc.link(v, u);
                break;
            case CUT:
                ndc.cut(v, u);
                fdc.cut(v, u);
                break;
        }
    }

    public static boolean nextOperationDC(DynamicConnectivity dc, String v, String u, int i) {
        Operation o = Operation.values()[i];
        switch (o) {
            case ARECONNECTED:
                return dc.areConnected(v, u);
            case LINK:
                dc.link(v, u);
                break;
            case CUT:
                dc.cut(v, u);
                break;
        }
        return true;
    }

    public static void checkFromMap(int v) {
        checkFromMap(v, 20_000);
    }

    public static void checkFromMap(int v, int ops) {
        for (int eNum : verEdge.get(v)) {
            settingUp(v, eNum);
            System.out.println(eNum);
            for (Edge e : edges) {
                if (!e.getTo().equals("")) {
                    nextOperationBoth(e.getFrom(), e.getTo());
                }
            }
            assertEquals(makeEdgeListDC(ndc), makeEdgeListDC(fdc));
        }
    }

//    public static List<Edge> benchmarkTest(int n) {
//        return  generateOperatingEdges(n, 3 * n);
//
//    }

}

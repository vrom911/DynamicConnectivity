package ru.ifmo.ads.romashkina.dynamicConnectivity;

import ru.ifmo.ads.romashkina.graph.Edge;

import java.util.List;

import static ru.ifmo.ads.romashkina.dynamicConnectivity.DCTestUtility.*;

public class Benchmark {

    public static void main(String[] args) {
        int[] num = new int[]{10, 100, 1000, 10000};
        for (int n : num) {
            System.out.println("Vertex: " + n + " edges: " + 5 * n + " operations: 30000");
            benchmarkTest(n);
        }
    }

    public static void benchmarkTest(int n) {
        List<Edge> edges = generateEdges(n, 5 * n);
        NaiveDynamicConnectivity ndc = new NaiveDynamicConnectivity(edges);
        FastDynamicConnectivity fdc = new FastDynamicConnectivity(edges);
        List<Edge> eList = generateOperatingEdges(n, 30000);

        System.out.println("FDC: " + lotsOperations(fdc, eList, 30000));
        System.out.println("NDC: " + lotsOperations(ndc, eList, 30000));
    }

    private static long lotsOperations(DynamicConnectivity dc, List<Edge> eList, int m) {
        long before = System.currentTimeMillis();
        for (int i = 0; i < m; i++) {
            Edge e = eList.get(i);
            nextOperationDC(dc, e.getFrom(), e.getTo(), i % 3);
        }
        long after = System.currentTimeMillis();
        return after - before;
    }
}

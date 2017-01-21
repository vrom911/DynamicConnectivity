package ru.ifmo.ads.romashkina.dynamicConnectivity;

import org.junit.Test;
import ru.ifmo.ads.romashkina.graph.Graph;
import ru.ifmo.ads.romashkina.graph.Vertex;

import java.util.HashMap;
import java.util.Map;

public class DynamicConnectivityTest {

    @Test
    public void kruskalAlgoTest() {
        Vertex a = new Vertex("a");
        Vertex b = new Vertex("b");
        a.addEdge(b);
        Map<String, Vertex> list = new HashMap<>();
        list.put("a", a);
        list.put("b", b);
        Graph graph = new Graph(list);
//        System.out.println(kruskalFindMST(graph));
    }

}
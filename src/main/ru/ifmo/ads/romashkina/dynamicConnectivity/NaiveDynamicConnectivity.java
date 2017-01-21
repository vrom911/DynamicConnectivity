package ru.ifmo.ads.romashkina.dynamicConnectivity;

import ru.ifmo.ads.romashkina.graph.Graph;
import ru.ifmo.ads.romashkina.graph.UndirectedEdge;
import ru.ifmo.ads.romashkina.graph.Vertex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.ifmo.ads.romashkina.graph.GraphUtility.addOrientedEdge;
import static ru.ifmo.ads.romashkina.graph.GraphUtility.removeOrientedEdge;

public class NaiveDynamicConnectivity implements DynamicConnectivity {
    private Graph graph;

    public NaiveDynamicConnectivity(Graph graph) {
        this.graph = graph;
    }

    public NaiveDynamicConnectivity(List<UndirectedEdge> edges) {
        this(new Graph(edges));
    }

    @Override
    public void link(String v, String  u) {
        addOrientedEdge(graph.getVertex(v), graph.getVertex(u));
    }

    @Override
    public void cut(String v, String  u) {
        removeOrientedEdge(graph.getVertex(v), graph.getVertex(u));
    }

    public boolean dfs(Set<Vertex> set, Vertex v, Vertex u) {
        if (v.equals(u)) return true;
        set.add(v);
        for (Vertex vVer : v.getEdges().keySet()) {
            if (!set.contains(vVer) && dfs(set, vVer, u)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areConnected(String v, String u) {
        Set<Vertex> set = new HashSet<>();
        return dfs(set, graph.getVertex(v), graph.getVertex(u));
    }
}

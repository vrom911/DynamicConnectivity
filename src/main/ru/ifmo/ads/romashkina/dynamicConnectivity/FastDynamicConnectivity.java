package ru.ifmo.ads.romashkina.dynamicConnectivity;

import ru.ifmo.ads.romashkina.euler.EulerTourTree;
import ru.ifmo.ads.romashkina.graph.*;
import ru.ifmo.ads.romashkina.treap.ImplicitTreap;
import ru.ifmo.ads.romashkina.utils.Pair;

import java.util.*;

import static ru.ifmo.ads.romashkina.euler.EulerUtility.findEulerPathForGraph;
import static ru.ifmo.ads.romashkina.graph.GraphUtility.kruskalFindMST;
import static ru.ifmo.ads.romashkina.graph.GraphUtility.removeOrientedEdge;
import static ru.ifmo.ads.romashkina.utils.Stuff.log;

public class FastDynamicConnectivity implements DynamicConnectivity{
    private List<Level> levels;
    private Map<Edge, Integer> edgeLevel;

    public FastDynamicConnectivity(List<Edge> edges) {
        edgeLevel = new HashMap<>();
        List<Edge> pureEdges = new ArrayList<>();
        for (Edge e : edges) {
            if (addNewEdge(e)) {
                pureEdges.add(e);
            }
        }
        Level level = kruskalFindMST(pureEdges);
        findEulerPathForGraph(level.getGraphMST());
        int n = log(level.getGraphMST().getVertexNum());
        levels = new ArrayList<>(n);
        levels.add(level);

        for (int i = 1; i < n; i++) {
            levels.add(new Level(new Graph(makeVertices(level)), new Graph(makeVertices(level))));
        }
    }

    public Map<Edge, Integer> getEdgeLevel() {
        return edgeLevel;
    }

    private Map<String, Vertex> makeVertices(Level l) {
        Map<String, Vertex> vertices = new HashMap<>();
        for (String v : l.getGraphMST().getVertexMap().keySet()) {
            vertices.put(v, new Vertex(v));
        }
        return vertices;
    }

    private void incEdgeLevel(String v, String u) {
        Edge newEdge = new Edge(v, u);
        edgeLevel.computeIfPresent(newEdge, (key,value) -> ++value);
        edgeLevel.computeIfPresent(newEdge.reverseEdge(), (key,value) -> ++value);
    }

    public Level getBasicLevel() {
        return levels.get(0);
    }

    public Level getLevel(int i) {
        return i >= levels.size() ? null : levels.get(i);
    }

    private void addNewEdge(String v, String u) {
        addNewEdge(new Edge(v, u));
    }

    private boolean addNewEdge(Edge e) {
        if (!edgeLevel.containsKey(e)) {
            edgeLevel.put(e, 0);
            edgeLevel.put(e.reverseEdge(), 0);
            return true;
        } else {
            return false;
        }

    }

    private void removeEdge(String v, String u) {
        Edge edge = new Edge(v, u);
        edgeLevel.remove(edge);
        edgeLevel.remove(edge.reverseEdge());
    }

    private int getEdgeLevel(String v, String u) {
        Edge edge = new Edge(v, u);
        return edgeLevel.getOrDefault(edge, -1);
    }

    private boolean hasEdge(String v,String u) {
        return getEdgeLevel(v, u) >= 0;
    }

    @Override
    public void link(String v, String  u) {
        if (hasEdge(v, u)) return;
        Level level = getBasicLevel();
        Graph g = level.getGraphMST();
        Graph gOther = level.getGraphOther();
        ImplicitTreap<TreapEdge> link = EulerTourTree.link(g.getVertex(v), g.getVertex(u));
        if (link == null) {
            gOther.addEdge(v, u);
        }
        addNewEdge(v, u);
    }

    private void cutFromMSTsWithReplacement(String v, String u, int levelNum, Edge replacementEdge) {
        for (int i = levelNum; i >= 0; i--) {
            Graph g = getLevel(i).getGraphMST();
            EulerTourTree.cut(g.getVertex(v), g.getVertex(u));
            EulerTourTree.link(g.getVertex(replacementEdge.getFrom()), g.getVertex(replacementEdge.getTo()));
            getLevel(i).getGraphOther().removeEdge(replacementEdge.getFrom(), replacementEdge.getTo());
        }
    }

    @Override
    public void cut(String v, String  u) {
        int levelNum = getEdgeLevel(v, u);
        // если такого ребра нет в графе
        if (levelNum < 0) return;

        // удалить ребро из edgeLevel
        removeEdge(v, u);

        Graph gO = getBasicLevel().getGraphOther();
        if (gO.getVertex(v).hasTreapEdge(gO.getVertex(u))) {
            // ребро не в остовном дереве, удаляем с каждого уровня
            for (int i = 0; i <= levelNum; i++) {
                Graph g = getLevel(i).getGraphOther();
                g.removeEdge(v, u);
            }
        } else {
            Edge replacementEdge = null;
            // ребро в остовном дереве
            while (replacementEdge == null && levelNum >= 0) {
                Graph g = getLevel(levelNum).getGraphMST();

                Pair<ImplicitTreap<TreapEdge>> pair = EulerTourTree.cut(g.getVertex(v), g.getVertex(u));
                ImplicitTreap<TreapEdge> firstCut = pair.getFirst();
                ImplicitTreap<TreapEdge> secondCut = pair.getSecond();

                Graph gPlusMST = getLevel(levelNum + 1).getGraphMST();
                Graph gPlusOther = getLevel(levelNum + 1).getGraphOther();
                Set<String> vers = new HashSet<>();

                if (firstCut == null || secondCut == null) {
                    vers.add(g.getVertex(v).getEdges().isEmpty() ? v : u);
                } else {
                    ImplicitTreap<TreapEdge> smallTreap = firstCut.isBigger(secondCut) ? secondCut : firstCut;
                    for (TreapEdge e : smallTreap) {
                        String fromL = e.getFrom().getLabel();
                        String toL = e.getTo().getLabel();
                        if (getEdgeLevel(fromL, toL) == levelNum) {
                            // увеличить уровень
                            // делать Link на уровне выше
                            incEdgeLevel(fromL, toL);
                            EulerTourTree.link(gPlusMST.getVertex(fromL), gPlusMST.getVertex(toL));
                        }
                        vers.add(fromL);
                        vers.add(toL);
                    }
                }

                Graph gOther = levels.get(levelNum).getGraphOther();
                TreapEdge eToRemove = null;
                outer: for (String from : vers) {
                    for (TreapEdge e : gOther.getVertex(from).getEdges().values()) {
                        String to = e.getTo().getLabel();
                        if (getEdgeLevel(from, to) == levelNum) {
                            if (!vers.contains(to)) {
                                // запомнить replacementEdge
                                eToRemove = e;
                                replacementEdge = new Edge(from, to);
                                EulerTourTree.link(g.getVertex(from), g.getVertex(to));
                                break outer;
                            } else {
                                gPlusOther.getVertex(from).addTreapEdge(gPlusOther.getVertex(to));
                                gPlusOther.getVertex(to).addTreapEdge(gPlusOther.getVertex(from));
                                incEdgeLevel(from, to);
                            }
                        }
                    }
                }
                if (eToRemove != null) {
                    // удалить из неостовного дерева
                    removeOrientedEdge(eToRemove.getFrom(), eToRemove.getTo());
                }
                levelNum--;
            }
            //  когда нашла replacementEdge, то добавить его на каждый уровень (Link)
            if (replacementEdge != null) {
                cutFromMSTsWithReplacement(v, u, levelNum, replacementEdge);
            }
        }
    }

    @Override
    public boolean areConnected(String v, String  u) {
        Graph g = levels.get(0).getGraphMST();
        return EulerTourTree.areConnected(g.getVertex(v), g.getVertex(u));
    }

    @Override
    public String toString() {
        return "levels=" + levels;
    }
}

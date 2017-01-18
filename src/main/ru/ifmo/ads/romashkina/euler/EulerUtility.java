package ru.ifmo.ads.romashkina.euler;

import ru.ifmo.ads.romashkina.graph.Edge;
import ru.ifmo.ads.romashkina.graph.Graph;
import ru.ifmo.ads.romashkina.graph.Vertex;
import ru.ifmo.ads.romashkina.treap.ImplicitTreap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static ru.ifmo.ads.romashkina.graph.GraphUtility.readGraph;
import static ru.ifmo.ads.romashkina.treap.ImplicitTreap.mergeTreapList;

public class EulerUtility {

    public static List<Graph> readGraphList(String fileName) {
        List<Graph> graphs = null;

        try (FileReader fInput = new FileReader(fileName);
             BufferedReader inputBuf = new BufferedReader(fInput)
        ) {
            graphs = new ArrayList<>();
            String row;
            while ((row = inputBuf.readLine()) != null) {
                graphs.add(new Graph(readGraph(row)));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return graphs;
    }

    public static ImplicitTreap<Vertex> findEulerPath(Vertex u, int vertexNum) {
        Deque<Vertex> tour = new ArrayDeque<>();
        tour.addLast(u);
        List<Vertex> result = new ArrayList<>();
        List<ImplicitTreap<Vertex>> resTreaps = new ArrayList<>();
        int[] edgeCounters = new int[vertexNum];
        while (!tour.isEmpty()) {
            Vertex w = tour.peekLast();
            int wNum = w.getNumber();
            int i = edgeCounters[wNum];

            if (i < w.getEdgesNumber()) {
                Vertex to = w.getEdges().keySet().toArray(new Vertex[w.getEdgesNumber()])[i];
                tour.addLast(to);
                edgeCounters[wNum]++;
            }

            if (w.equals(tour.peekLast())) {
                tour.pollLast();
                result.add(w);
                ImplicitTreap<Vertex> e = new ImplicitTreap<>(EulerTourTree.random.nextLong(), w);
                resTreaps.add(e);
                w.setIn(e);
                int curResTreapsSize = resTreaps.size() - 1;
                if (curResTreapsSize > 0) {
                    Edge edge = result.get(curResTreapsSize - 1).getEdgeTo(w);
                    edge.setLinksToNodes(resTreaps.get(curResTreapsSize - 1), e);
                }
            }
        }
        return mergeTreapList(resTreaps);
    }

}
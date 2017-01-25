package ru.ifmo.ads.romashkina.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GraphUtility {

    public static void addOrientedEdge(Vertex from, Vertex to) {
        if (from != null && to != null) {
            from.addTreapEdge(to);
            to.addTreapEdge(from);
        }
    }

    public static void removeOrientedEdge(Vertex from, Vertex to) {
        if (from != null && to != null) {
            from.removeTreapEdgeTo(to);
            to.removeTreapEdgeTo(from);
        }
    }

    public static List<Edge> readGraphEdges(String fileName) {
        List<Edge> edges = new ArrayList<>();
        try (FileReader fInput = new FileReader(fileName);
             BufferedReader inputBuf = new BufferedReader(fInput)
        ) {
            String row;
            while ((row = inputBuf.readLine()) != null) {
                edges.add(new Edge(row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return edges;
    }

    public static Map<String, Vertex> readGraph(String fileName) {
        Map<String, Vertex> vertex;
        try (FileReader fInput = new FileReader(fileName);
             BufferedReader inputBuf = new BufferedReader(fInput)
        ) {
//            read number of vertex and add them in the list
            String row = inputBuf.readLine();
            int vSize = Integer.parseInt(row);
            vertex = new HashMap<>(vSize);
            Map<String, Integer> labelToNumber = new HashMap<>();
            int vertexCounter = 0;
            for (int i = 0; i < vSize; i++) {
                String label = inputBuf.readLine();
                if (!labelToNumber.containsKey(label)) {
                    labelToNumber.put(label, vertexCounter);
                    vertexCounter++;
                }
                vertex.put(label, new Vertex(label));
            }

//            read and add edges for each vertex
            while ((row = inputBuf.readLine()) != null) {
                String[] edge = row.split(" ");
                Vertex from = vertex.get(edge[0]);
                Vertex to = vertex.get(edge[1]);
                addOrientedEdge(from, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return vertex;
    }

    public static Graph makeGraphFromFile(String filename) {
        Map<String, Vertex> vertex = readGraph(filename);
        return vertex == null ? null : new Graph(vertex);
    }

    public static Map<String, Vertex> makeVertexMap(Map<String, Integer> vertex) {
        Map<String, Vertex> gVertex = new HashMap<>();
        for (String label : vertex.keySet()) {
            gVertex.put(label, new Vertex(label));
        }
        return gVertex;
    }

    public static Level kruskalFindMST(List<Edge> edges) {
        Map<String, Integer> vertex = verticesFromEdges(edges);

        Graph mst = new Graph(makeVertexMap(vertex));
        Graph other = new Graph(makeVertexMap(vertex));

        DSU dsu = new DSU(vertex.size());

        for (Edge e : edges) {
            if (!e.getTo().equals("")) {
                int from = vertex.get(e.getFrom());
                int to = vertex.get(e.getTo());
                if (!dsu.connected(from, to)) {
                    mst.addEdge(e.getFrom(), e.getTo());
                    dsu.union(from, to);
                } else {
                    other.addEdge(e.getFrom(), e.getTo());
                }
            }

        }
        return new Level(mst, other);
    }

    public static Map<String, Integer> verticesFromEdges(List<Edge> edges) {
        Map<String, Integer> v = new HashMap<>();
        int[] count = new int[1];
        Function<String, Integer> counter = value -> count[0]++;
        for (Edge e : edges) {
            v.computeIfAbsent(e.getFrom(), counter);
            if (!e.getTo().equals("")) {
                v.computeIfAbsent(e.getTo(), counter);
            }
        }
        return v;
    }
}

package ru.ifmo.ads.romashkina.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphUtility {

    public static void addOrientedEdge(Vertex from, Vertex to) {
        if (from != null && to != null) {
            from.addEdge(to);
            to.addEdge(from);
        }
    }

    public static List<Vertex> readGraph(String fileName) {
        List<Vertex> vertex;
        try (FileReader fInput = new FileReader(fileName);
             BufferedReader inputBuf = new BufferedReader(fInput)
        ) {
//            read number of vertex and add them in the list
            String row = inputBuf.readLine();
            int vSize = Integer.parseInt(row);
            vertex = new ArrayList<>(vSize);
            Map<String, Integer> labelToNumber = new HashMap<>();
            int vertexCounter = 0;
            for (int i = 0; i < vSize; i++) {
                String label = inputBuf.readLine();
                if (!labelToNumber.containsKey(label)) {
                    labelToNumber.put(label, vertexCounter);
                    vertexCounter++;
                }

                int number = labelToNumber.get(label);
                vertex.add(new Vertex(label, number));
            }

//            read and add edges for each vertex
            while ((row = inputBuf.readLine()) != null) {
                String[] edge = row.split(" ");
                Vertex from = vertex.get(labelToNumber.get(edge[0]));
                Vertex to = vertex.get(labelToNumber.get(edge[1]));
                addOrientedEdge(from, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return vertex;
    }

    // Лучше перенести в eulerUtility

}

package ru.ifmo.ads.romashkina.euler;

import ru.ifmo.ads.romashkina.graph.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EulerUtility {

    public static List<Graph> readGraphList(String fileName) {
        List<Graph> graphs = null;

        try (FileReader fInput = new FileReader(fileName);
             BufferedReader inputBuf = new BufferedReader(fInput)
        ) {
            graphs = new ArrayList<>();
            String row;
            while ((row = inputBuf.readLine()) != null) {
                graphs.add(new Graph(row));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return graphs;
    }
}

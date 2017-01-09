import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GraphUtility {

    public static Graph readGraph(String fileName) {
        List<Vertex> vertex = new ArrayList<>();
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
                if (from != null && to != null) {
                    from.addEdge(to);
                    to.addEdge(from);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Graph(vertex);
    }

    public static List<Vertex> findEulerPath(Vertex u, int vertexNum) {
        Deque<Vertex> tour = new ArrayDeque<>();
        tour.addLast(u);
        List<Vertex> result = new ArrayList<>();
        int[] edgeCounters = new int[vertexNum];
        while (!tour.isEmpty()) {
            Vertex w = tour.peekLast();
            int wNum = w.getNumber();
            int i = edgeCounters[wNum];
            if (i < w.getEdgesNumber()) {
                tour.addLast(w.getEdges().get(i).getTo());
                edgeCounters[wNum]++;
            }
            if (w.equals(tour.peekLast())) {
                tour.pollLast();
                result.add(w);
            }
        }
        return result;
    }
}

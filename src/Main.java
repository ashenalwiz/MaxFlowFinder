//20222335,w2053241 - R.W.S Ashen Alwis

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <input_file>");
            return;
        }

        String filename = args[0];
        try {
            long startTime = System.currentTimeMillis();

            // Parse the input file and create the graph
            Graph graph = parseInputFile(filename);

            System.out.println("Graph loaded with " + graph.getVertices() + " nodes and " + graph.countEdges() + " edges");

            // Find the maximum flow
            MaxFlow maxFlow = new MaxFlow(graph, 0, graph.getVertices() - 1);
            int flow = maxFlow.findMaxFlow();

            long endTime = System.currentTimeMillis();

            // Print the results
            System.out.println("Total iterations: " + maxFlow.getIterations());
            System.out.println("Maximum flow from 0 to " + (graph.getVertices() - 1) + ": " + flow);
            System.out.println("Time elapsed: " + (endTime - startTime) + " ms");

            // Print detailed results if graph is small enough
            if (graph.getVertices() < 100) {
                maxFlow.printDetailedResult();
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (OutOfMemoryError e) {
            System.out.println("Memory error: The graph is too large to process with available memory.");
            System.out.println("Consider using a more memory-efficient implementation or increasing JVM heap size.");
        }
    }

    private static Graph parseInputFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Read the number of nodes
            int nodes = Integer.parseInt(reader.readLine().trim());
            Graph graph = new Graph(nodes);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 3) {
                    int from = Integer.parseInt(parts[0]);
                    int to = Integer.parseInt(parts[1]);
                    int capacity = Integer.parseInt(parts[2]);
                    graph.addEdge(from, to, capacity);
                }
            }

            return graph;
        }
    }
}
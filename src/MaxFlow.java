//20222335,w2053241 - R.W.S Ashen Alwis


import java.util.*;

class MaxFlow {
    private final Graph graph;
    private final int source;
    private final int sink;
    private final List<String> steps;
    private int iterations;
    private final Set<String> flowAssignments;

    public MaxFlow(Graph graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
        this.steps = new ArrayList<>();
        this.iterations = 0;
        this.flowAssignments = new HashSet<>();
    }

    public int findMaxFlow() {
        int maxFlow = 0;

        // While there's an augmenting path, add flow along the path
        int pathFlow;
        while ((pathFlow = findAugmentingPath()) > 0) {
            maxFlow += pathFlow;
            iterations++;

            // Only store detailed steps for smaller graphs
            if (graph.getVertices() < 100) {
                steps.add("Path " + iterations + ": Added flow of " + pathFlow + ". Total flow now: " + maxFlow);
            }
        }

        if (graph.getVertices() < 100) {
            steps.add("Final maximum flow: " + maxFlow);
        }
        return maxFlow;
    }

    private int findAugmentingPath() {
        // Use BFS to find the shortest augmenting path
        int[] parent = new int[graph.getVertices()];
        Arrays.fill(parent, -1);
        Edge[] edgeOnPath = new Edge[graph.getVertices()];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        parent[source] = source;

        while (!queue.isEmpty() && parent[sink] == -1) {
            int current = queue.poll();

            for (Edge edge : graph.getAdjacencyList(current)) {
                int next = edge.getTo();
                if (parent[next] == -1 && edge.getResidualCapacity() > 0) {
                    parent[next] = current;
                    edgeOnPath[next] = edge;
                    queue.add(next);
                }
            }
        }

        // If we didn't reach the sink, there's no augmenting path
        if (parent[sink] == -1) {
            return 0;
        }

        // Find the bottleneck capacity
        int pathFlow = Integer.MAX_VALUE;
        StringBuilder pathStr = new StringBuilder("Found path: ");
        List<Edge> pathEdges = new ArrayList<>();

        for (int v = sink; v != source; v = parent[v]) {
            Edge edge = edgeOnPath[v];
            pathFlow = Math.min(pathFlow, edge.getResidualCapacity());
            pathEdges.add(0, edge);
        }

        // Augment flow along the path
        for (Edge edge : pathEdges) {
            if (graph.getVertices() < 100) {
                pathStr.append(edge.getFrom()).append(" -> ");
            }

            // Find the reverse edge
            Edge reverseEdge = graph.findEdge(edge.getTo(), edge.getFrom());

            edge.addFlow(pathFlow);
            reverseEdge.addFlow(-pathFlow); // Add negative flow to the reverse edge
        }

        if (graph.getVertices() < 100) {
            pathStr.append(sink).append(" with flow ").append(pathFlow);
            steps.add(pathStr.toString());
        }

        return pathFlow;
    }

    public int getIterations() {
        return iterations;
    }

    public void printDetailedResult() {
        System.out.println("Flow Assignment:");

        // Track which edges we've already displayed
        Set<String> displayedEdges = new HashSet<>();

        // First, iterate through all nodes to find edges with positive flow
        for (int i = 0; i < graph.getVertices(); i++) {
            for (Edge edge : graph.getAdjacencyList(i)) {
                // Only print original edges (capacity > 0) with positive flow
                if (edge.getCapacity() > 0 && edge.getFlow() > 0) {
                    String edgeKey = edge.getFrom() + "," + edge.getTo();
                    if (!displayedEdges.contains(edgeKey)) {
                        System.out.println("f(" + edge.getFrom() + "," + edge.getTo() + ") = " + edge.getFlow());
                        displayedEdges.add(edgeKey);
                    }
                }
            }
        }

        System.out.println("Detailed Steps:");
        for (String step : steps) {
            System.out.println(step);
        }
    }
}
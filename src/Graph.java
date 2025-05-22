//20222335,w2053241 - R.W.S Ashen Alwis


import java.util.*;

class Graph {
    private final int vertices;
    private final List<Edge>[] adjacencyList;
    private final Map<Integer, Map<Integer, Edge>> edgeMap; // Maps (from, to) to edge
    private int edgeCount;

    @SuppressWarnings("unchecked")
    public Graph(int vertices) {
        this.vertices = vertices;
        this.edgeCount = 0;
        adjacencyList = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        edgeMap = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            edgeMap.put(i, new HashMap<>());
        }
    }

    public void addEdge(int from, int to, int capacity) {
        // Check if the edge already exists
        Edge existingEdge = findEdge(from, to);
        if (existingEdge != null) {
            // Edge already exists, just update capacity if needed
            return;
        }

        // Add forward edge
        Edge forwardEdge = new Edge(from, to, capacity);
        adjacencyList[from].add(forwardEdge);
        edgeMap.get(from).put(to, forwardEdge);

        // Add reverse edge for residual network
        Edge reverseEdge = new Edge(to, from, 0);
        adjacencyList[to].add(reverseEdge);
        edgeMap.get(to).put(from, reverseEdge);

        edgeCount++;
    }

    public Edge findEdge(int from, int to) {
        if (edgeMap.containsKey(from) && edgeMap.get(from).containsKey(to)) {
            return edgeMap.get(from).get(to);
        }
        return null;
    }

    public int getVertices() {
        return vertices;
    }

    public List<Edge> getAdjacencyList(int vertex) {
        return adjacencyList[vertex];
    }

    public int countEdges() {
        return edgeCount;
    }

    // Get all original edges (not residual)
    public List<Edge> getOriginalEdges() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            for (Edge edge : adjacencyList[i]) {
                if (edge.getCapacity() > 0) {  // Only original edges, not residual
                    edges.add(edge);
                }
            }
        }
        return edges;
    }
}
//20222335,w2053241 - R.W.S Ashen Alwis


class Edge {
    private final int from;
    private final int to;
    private final int capacity;
    private int flow;

    public Edge(int from, int to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFlow() {
        return flow;
    }

    public int getResidualCapacity() {
        return capacity - flow;
    }

    public void addFlow(int amount) {
        flow += amount;
    }

    @Override
    public String toString() {
        return from + " -> " + to + " (" + flow + "/" + capacity + ")";
    }
}
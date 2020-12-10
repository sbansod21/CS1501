import java.io.*;
import java.util.*;

public class Digraph {
    public final int v;
    public int e;
    public LinkedList<DirectedEdge>[] adj;
    public boolean[] marked; // marked[v] = is there an s-v path
    public int[] edgeTo; // edgeTo[v] = previous edge on shortest s-v path
    public int[] distTo; // distTo[v] = number of edges shortest s-v path
    private static final int INFINITY = Integer.MAX_VALUE;

    /**
     * Create an empty digraph with v vertices.
     */
    public Digraph(int v) {
        if (v < 0)
            throw new RuntimeException("Number of vertices must be nonnegative");
        this.v = v;
        this.e = 0;
        @SuppressWarnings("unchecked")
        LinkedList<DirectedEdge>[] temp = (LinkedList<DirectedEdge>[]) new LinkedList[v];
        adj = temp;
        for (int i = 0; i < v; i++)
            adj[i] = new LinkedList<DirectedEdge>();
    }

    /**
     * Add the edge e to this digraph.
     */
    public void addEdge(DirectedEdge edge) {
        int from = edge.from();
        adj[from].add(edge);
        e++;
    }

    public int v() {
        return this.v;
    }

    /**
     * Return the edges leaving vertex v as an Iterable. To iterate over the edges
     * leaving vertex v, use foreach notation:
     * <tt>for (DirectedEdge e : graph.adj(v))</tt>.
     */
    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public void bfs(int source) {
        marked = new boolean[this.v];
        distTo = new int[this.v];
        edgeTo = new int[this.v];

        Queue<Integer> q = new LinkedList<Integer>();
        for (int i = 0; i < v; i++) {
            distTo[i] = INFINITY;
            marked[i] = false;
        }
        distTo[source] = 0;
        marked[source] = true;
        q.add(source);

        while (!q.isEmpty()) {
            int v = q.remove();
            for (DirectedEdge w : adj(v)) {
                if (!marked[w.to()]) {
                    edgeTo[w.to()] = v; // reverse
                    distTo[w.to()] = distTo[v] + 1;
                    marked[w.to()] = true;
                    q.add(w.to());
                }
            }
        }
    }

}

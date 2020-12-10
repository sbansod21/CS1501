import java.io.*;
import java.util.*;

public class DirectedEdge {
    private int v;
    private int w;
    private double cost;
    private int distance;
    private final double weight;

    /**
     * Create a directed edge from v to w with given weight.
     */

    public DirectedEdge(int v, int w, int distance, double cost) {
        if (v < 0)
            throw new IndexOutOfBoundsException("Must be postive integers!");
        if (w < 0)
            throw new IndexOutOfBoundsException("Must be postive integers!");
        if (Double.isNaN(cost))
            throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.setCost(cost);
        this.setDistance(distance);
    }

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public void setCost(double c) {
        this.cost = c;
    }

    public double cost() {
        return cost;
    }

    public void setDistance(int d) {
        this.distance = d;
    }

    public int distance() {
        return distance;
    }

    public int other(int vertex) {
        if (vertex == v)
            return w;
        else if (vertex == w)
            return v;
        else
            throw new RuntimeException("Illegal endpoint");
    }

    public static void assignValues(DirectedEdge e, DirectedEdge f) {
        e.v = f.v;
        e.w = f.w;
        e.distance = f.distance;
        e.cost = f.cost;
    }

    public int either() {
        return v;
    }
}
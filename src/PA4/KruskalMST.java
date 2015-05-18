package pa4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class KruskalMST {

    /**
     * @param args
     */
    public static void main(String[] args) {

        double graph[][] = {
                {0, 1, 4, 4, 5},
                {1, 0, 3, 7, 5},
                {4, 3, 0, 6, 0},
                {4, 7, 6, 0, 2},
                {5, 5, 0, 2, 0}
        };
        double tree[][] = minimumSpanningTree(graph);
        if (tree == null) {
            System.out.println("no spanning tree");
            System.exit(0);
        }
        PriorityQueue<Edge> edgeList = generateEdgeList(tree);
        for (Edge e : edgeList) {
            System.out.println(e);
        }

    }

    /**
     *
     * @param graph
     * @return
     */
    public static double[][] minimumSpanningTree(double[][] graph) {

        int number = graph.length;
        PriorityQueue<Edge> edgeList;
        edgeList = generateEdgeList(graph);
        double tree[][] = new double[number][number];
        /**
         */
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                tree[i][j] = 0;
            }
        }

        // use disjoint set to check a cycle
        Map<Integer, Integer> map = new HashMap<>();

        // initialize set
        for (int i = 0; i < number; i++) {
            map.put(i, i);
        }

        int edgeCount = 0;
        while (edgeCount < number - 1 && !edgeList.isEmpty()) {
            Edge e = edgeList.poll();

            int repU = map.get(e.u);
            int repV = map.get(e.v);

            if (repU != repV) {
                // make u as the representation
                map.put(e.v, repU);
            } else {
                // a cycle!!!
                continue;
            }

            tree[e.u][e.v] = e.weight;
            tree[e.v][e.u] = e.weight;
            edgeCount++;

        }

        if (edgeCount == number - 1)
            return tree;
        else
            return null;
    }

    /**
     *
     * @param graph
     * @return
     */
    private static PriorityQueue<Edge> generateEdgeList(double[][] graph) {
        int number = graph.length;
        PriorityQueue<Edge> edgeList = new PriorityQueue<Edge>();
        for (int i = 0; i < number; i++) {
            for (int j = i + 1; j < number; j++) {
                if (graph[i][j] > 0) {
                    Edge e = new Edge(i, j, graph[i][j]);
                    edgeList.add(e);
                }
            }
        }
        return edgeList;
    }


}

class Edge implements Comparable<Edge> {
    int u;
    int v;
    double weight;


    public Edge(int u, int v, double weight) {
        super();
        this.u = u;
        this.v = v;
        this.weight = weight;
    }


    @Override
    public int compareTo(Edge e) {
        if (e.weight == weight)
            return 0;
        else if (weight < e.weight)
            return -1;
        else
            return 1;

    }

    public String toString() {
        return u + "--" + v + ":" + weight;
    }

}

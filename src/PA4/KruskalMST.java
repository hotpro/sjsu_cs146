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

        double graph[][] = {{0, 1, 4, 4, 5},
                {1, 0, 3, 7, 5},
                {4, 3, 0, 6, Double.MAX_VALUE},
                {4, 7, 6, 0, 2},
                {5, 5, Double.MAX_VALUE, 2, 0}};
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

        int rlength = graph.length;
        int clength = graph[0].length;
        PriorityQueue<Edge> edgeList;
        edgeList = generateEdgeList(graph);
        double tree[][] = new double[rlength][clength];
        /**
         */
        for (int i = 0; i < rlength; i++) {
            for (int j = 0; j < clength; j++) {
                if (i == j)
                    tree[i][j] = 0;
                else
                    tree[i][j] = Double.MAX_VALUE;
            }
        }

        /**
         */
        Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
        int edgeCount = 0;
        while (edgeCount < rlength - 1 && !edgeList.isEmpty()) {
            Edge e = edgeList.poll();


            Set<Integer> setU = map.get(e.u);
            Set<Integer> setV = map.get(e.v);
            //System.out.println(e);
            if (setU == null && setV == null) {
                Set<Integer> set = new HashSet<Integer>();
                set.add(e.u);
                set.add(e.v);
                map.put(e.u, set);
                map.put(e.v, set);
            }
            else if (setU == null && setV != null) {
                map.put(e.u, setV);
                setV.add(e.u);
            } else if (setU != null && setV == null) {
                map.put(e.v, setU);
                setU.add(e.v);
            }
            else if (setU != setV) {
                for (int v : setV) {
                    map.put(v, setU);

                }

                setU.addAll(setV);
            }
            else {
                continue;
            }
            tree[e.u][e.v] = e.weight;
            tree[e.v][e.u] = e.weight;
            edgeCount++;

        }


        if (edgeCount == rlength - 1)
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
        PriorityQueue<Edge> edgeList = new PriorityQueue<Edge>();
        int rlength = graph.length;
        int clength = graph[0].length;
        for (int i = 0; i < rlength; i++) {
            for (int j = i + 1; j < clength; j++) {
                if (graph[i][j] > 0 & graph[i][j] < Double.MAX_VALUE) {
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

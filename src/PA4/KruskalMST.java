package pa4;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

//        int graph[][] = {
//                {0, 1, 4, 4, 5},
//                {1, 0, 3, 7, 5},
//                {4, 3, 0, 6, 0},
//                {4, 7, 6, 0, 2},
//                {5, 5, 0, 2, 0}
//        };
        int graph[][] = buildAdjMatrixFromFile();
        int tree[][] = minimumSpanningTree(graph);
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
    public static int [][] minimumSpanningTree(int[][] graph) {

        int number = graph.length;
        PriorityQueue<Edge> edgeList;
        edgeList = generateEdgeList(graph);
        int tree[][] = new int[number][number];
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

            int repU = find(map, e.u);
            int repV = find(map, e.v);

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

    private static int find(Map<Integer, Integer> map, int x) {
        int rep = map.get(x);

        while (rep != x) {
            x = rep;
            rep = map.get(x);
        }

        return rep;
    }

    /**
     *
     * @param graph
     * @return
     */
    private static PriorityQueue<Edge> generateEdgeList(int[][] graph) {
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


    public static int[][] buildAdjMatrixFromFile() {

        int[][] adjMatrix = null;

        BufferedReader fRead = null;
        try {
            fRead = new BufferedReader(new FileReader("edgeData.txt"));
            String line;
            line = fRead.readLine();
            if (line != null) {
                int numVertices = Integer.valueOf(line);
                adjMatrix = new int[numVertices][numVertices];
                // Initialize all to 0;
                for (int i = 0; i < numVertices; i++) {
                    for (int j = 0; j < numVertices; j++) {
                        adjMatrix[i][j] = 0;
                    }
                }

                while ((line = fRead.readLine()) != null) {
                    String[] splitLine = line.split(" ");
                    String weight = splitLine[1];
                    String edge = splitLine[0];
                    String[] vertices = edge.split(",");
                    int u = Integer.parseInt(vertices[0]) - 1;
                    int v = Integer.parseInt(vertices[1]) - 1;
                    adjMatrix[u][v] = Integer.parseInt(weight);
                    adjMatrix[v][u] = Integer.parseInt(weight);
                }

                for (int i = 0; i < numVertices; i++) {
                    for (int j = 0; j < numVertices; j++) {
                        System.out.print(adjMatrix[i][j] + " ");
                    }
                    System.out.println("");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adjMatrix;
    }
}


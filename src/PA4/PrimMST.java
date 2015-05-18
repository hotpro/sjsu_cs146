package pa4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 */
public class PrimMST {

    static int m = Integer.MAX_VALUE;

    /** adjacency matrix */
    static int[][] weight;

    /** number of node*/
    static int verNum;

    /** the lowest weight in the known node V to every unknown node*/
    static int[] lowerW;

    /** edge that is selected, e.g. edge[3] = 4, means from node to node 4 is selected*/
    static int[] edge;

    /** selected node */
    static boolean[] checked = new boolean[verNum];

    public void prim(int n, int[][] w) {
        checked[1] = true;

        for (int i = 1; i <= n; i++) {
            lowerW[i] = w[1][i];
            edge[i] = 1;
            checked[i] = false;
        }

        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            int j = 1;
            for (int k = 2; k <= n; k++) {
                if (lowerW[k] < min && (!checked[k])) {
                    min = lowerW[k];
                    j = k;
                }
            }
            if (i < n)
                System.out.println(j + "-->" + edge[j] + ":" + lowerW[j]);

            checked[j] = true;

            for (int k = 2; k <= n; k++) {
                if ((w[j][k] < lowerW[k]) && (!checked[k])) {
                    lowerW[k] = weight[j][k];
                    edge[k] = j;
                }
            }
        }
    }

    public static int[][] buildAdjMatrixFromFile() {

        int[][] adjMatrix = null;

        BufferedReader fRead = null;
        try {
            fRead = new BufferedReader(new FileReader("edgeData2.txt"));
            String line;
            line = fRead.readLine();
            if (line != null) {
                int numVertices = Integer.valueOf(line);
                adjMatrix = new int[numVertices + 1][numVertices + 1];
                // Initialize all to m Integer.MAX_VALUE;
                for (int i = 0; i <= numVertices; i++) {
                    for (int j = 0; j <= numVertices; j++) {
                        if (i == 0 || j == 0) {
                            adjMatrix[i][j] = 0;
                        } else {
                            adjMatrix[i][j] = m;
                        }
                    }
                }

                while ((line = fRead.readLine()) != null) {
                    String[] splitLine = line.split(" ");
                    String weight = splitLine[1];
                    String edge = splitLine[0];
                    String[] vertices = edge.split(",");
                    int u = Integer.parseInt(vertices[0]);
                    int v = Integer.parseInt(vertices[1]);
                    adjMatrix[u][v] = Integer.parseInt(weight);
                    adjMatrix[v][u] = Integer.parseInt(weight);
                }

                for (int i = 0; i <= numVertices; i++) {
                    for (int j = 0; j <= numVertices; j++) {
                        if (adjMatrix[i][j] == m) {
                            System.out.print("m ");
                        } else {
                            System.out.print(adjMatrix[i][j] + " ");
                        }
                    }
                    System.out.println("");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adjMatrix;
    }

    public static void main(String[] args) {
        PrimMST p = new PrimMST();
        weight = buildAdjMatrixFromFile();
        verNum = weight.length;
        lowerW = new int[verNum];
        edge = new int[verNum];
        checked = new boolean[verNum];
        p.prim(verNum - 1, weight);
    }
}

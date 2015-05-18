package pa4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Graph {

	static HashSet<Vertex> graphVertices;
	static HashSet<Edge2> graphEdges;
	static PriorityQueue<Vertex> vertexPriQueue;
	
	private static final String fileName = "edgeData.txt";

	// Does something. Not sure if it is still needed. Not completed code
	public static void buildAdjMatrixFromFile() {
	
		int numVertices = graphVertices.size() + 1;
		int[][] adjMatrix = new int[numVertices][numVertices];
		// Initialize all to 0;
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				adjMatrix[i][j] = 0;
			}
		}

		BufferedReader fRead = null;
		try {
			fRead = new BufferedReader(new FileReader(fileName));
			String line;
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				System.out.print(adjMatrix[i][j] + " ");
			}
			System.out.println("");
		}

	}
	
	// Get vertices in the file.
	public static void getVerticesAndEdges() {

		graphVertices = new HashSet<Vertex>();
		graphEdges = new HashSet<Edge2>();
		
		BufferedReader fRead = null;
		try {
			fRead = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = fRead.readLine()) != null) {
				System.out.println(line);
				String[] splitLine = line.split(" ");
				String edge = splitLine[0];
				int wt = Integer.parseInt(splitLine[1]);
				String[] vertices = edge.split(",");
				Vertex x = new Vertex(vertices[0], wt);
				Vertex y = new Vertex(vertices[1], wt);
				if (getVertex(x) == null) {
					graphVertices.add(x);
				} else {
					getVertex(x).setWeight(wt);
				}
				if (getVertex(y) == null) {
					graphVertices.add(y);
				} else {
					getVertex(y).setWeight(wt);
				}
				Edge2 e = new Edge2(x, y, wt);
				graphEdges.add(e);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fRead.close();
			} catch (IOException e) {
				System.out.println("Unable to close the file ");
				e.printStackTrace();
			}
		}
		System.out.println("DEBUG: Read " + graphVertices.size() + " vertices and " + graphEdges.size() + " from the file.");
	}
	
	// Returns the vertex reference if it exists in the graph else null
	public static Vertex getVertex (Vertex v) {
		
		Iterator<Vertex> itr = graphVertices.iterator();
		while(itr.hasNext()) {
			Vertex x = itr.next();
			if (x.name.equals(v.name)) {
				return x;
			}
		}
		return null;
	}

	// Returns the edge reference if it exists in the graph else null
	public static Edge2 getEdge (Edge2 e) {
		
		Iterator<Edge2> itr = graphEdges.iterator();
		while(itr.hasNext()) {
			Edge2 d = itr.next();
			if ((d.u.name.equals(e.u.name) && d.v.name.equals(e.v.name)) || (d.u.name.equals(e.v.name) && d.v.name.equals(e.u.name))) {  
				return d;
			}
		}
		return null;
	}

	public void buildVertexPriQ() {
		
		Comparator<Vertex> comparator = new VertexWeightComparator();
        vertexPriQueue = new PriorityQueue<Vertex>(graphVertices.size(), comparator);
		Iterator<Vertex> itr = graphVertices.iterator();
		while (itr.hasNext()) {
			Vertex v = itr.next();
			vertexPriQueue.add(v);
		}
	}
}

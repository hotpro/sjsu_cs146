package pa4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Utils {

	static HashSet<String> graphVertices;
	private static final String fileName = "edgeData.txt";

	// Does something. Not sure if it is still needed. Not completed code
	public static void buildAdjMatrixFromFile() {
	
		getVertices();
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
	public static void getVertices() {

		graphVertices = new HashSet<String>();
		BufferedReader fRead = null;
		try {
			fRead = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = fRead.readLine()) != null) {
				String[] splitLine = line.split(" ");
				String edge = splitLine[0];
				String[] vertices = edge.split(",");
				for (String v : vertices) {
					graphVertices.add(v);
				}
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
		System.out.println("DEBUG: Read " + graphVertices.size() + " vertices from the file.");
	}

}

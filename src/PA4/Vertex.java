package pa4;

import java.util.HashSet;

public class Vertex {
	// The least weight edge connecting to this vertex
	int lWeight = -1;
	String name;
	int key;
	HashSet<Edge> connectedEdges;
	boolean visited;

	public Vertex(String name, int weight) {
		this.name = name;
		if (weight < this.lWeight) {
			this.lWeight = weight;
		}
	}

	public void setWeight (int weight) {
		if (weight < this.lWeight) {
			this.lWeight = weight;
		}
	}

	public int getWeight () {
		return lWeight;
	}

}
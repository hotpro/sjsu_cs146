package pa4;

public class Edge2 {
	Vertex u;
	Vertex v;
	int weight;
	
	public Edge2 (Vertex u, Vertex v, int weight) {
		this.u = u;
		this.v = v;
		this.weight = weight;
	}
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
}

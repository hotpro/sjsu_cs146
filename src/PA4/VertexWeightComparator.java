package pa4;

import java.util.Comparator;

public class VertexWeightComparator implements Comparator<Vertex> {
	@Override
	public int compare(Vertex x, Vertex y) {

		//Assume no null vertices are passed.
		if (x.getWeight() < y.getWeight()) {
			return -1;
		}
		if (x.getWeight() > y.getWeight()) {
			return 1;
		}
		return 0;
	}
}
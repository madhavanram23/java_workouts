package co.interview.datastructures.graph.undirected;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BasicGraph {

	private Map<Integer, UUVertex> vertexMap;

	public BasicGraph() {
		this.vertexMap = new HashMap<>();
	}

	public BasicGraph(Map<Integer, UUVertex> vertexMap, Map<Integer, List<Integer>> vertexEdgeListMap) {
		super();
		this.vertexMap = vertexMap;
	}

	public BasicGraph(String[] sItems, String[] vertexPairs) {
		this(sItems.length, sItems, vertexPairs.length, vertexPairs);
	}

	public BasicGraph(int v, String[] sItems, int e, String[] vertexPairs) {
		Map<Integer, UUVertex> vertexMap = new HashMap<>();
		for (int i = 0; i < v; i++) {
			vertexMap.put(i, new UUVertex(sItems[i]));
		}

		for (int i = 0; i < e; i++) {
			String vv = vertexPairs[i]; // 0-1
			String[] vvArray = vv.split("-");
			/**
			 * Get the vertex for the 0th index and set element at 1st index as adjacency
			 * vertex.
			 */
			Integer v0 = Integer.parseInt(vvArray[0]);
			Integer v1 = Integer.parseInt(vvArray[1]);
			vertexMap.get(v0).getAdjacenyList().add(v1);
			vertexMap.get(v1).getAdjacenyList().add(v0);
		}
		this.setVertexMap(vertexMap);
	}

	public Map<Integer, UUVertex> getVertexMap() {
		return vertexMap;
	}

	public void setVertexMap(Map<Integer, UUVertex> vertexMap) {
		this.vertexMap = vertexMap;
	}

	/**
	 * Breadth First Search
	 * 
	 * @param startIdx
	 */
	public void bfsTraversal(final int startIdx) {
		Queue<UUVertex> queue = new LinkedList<>();
		UUVertex startVertex = vertexMap.get(startIdx);
		startVertex.setIs_visited(true);
		queue.add(startVertex);

		while (!queue.isEmpty()) {
			UUVertex traversed = queue.poll();
			if (null != traversed) {
				System.out.println(traversed.getData());
			}

			// Add the neighbours;
			for (int i = 0; i < traversed.getAdjacenyList().size(); i++) {
				Integer vertexKey = traversed.getAdjacenyList().get(i);
				UUVertex v = vertexMap.get(vertexKey);
				if (!v.isIs_visited()) {
					v.setIs_visited(true);
					queue.add(v);
				}
			}
		}
	}

	/**
	 * Depth First search
	 * 
	 * @param startIdx
	 */
	
	public void dfsTraversal(final int startIdx) {
		UUVertex traversed = vertexMap.get(startIdx);
		this.dfsTraversalRecursive(traversed);
	}

	private void dfsTraversalRecursive(UUVertex vertex) {
		System.out.println(vertex);
		vertex.setIs_visited(true);
		System.out.println(vertex.getData());
		List<Integer> adjacenyList = vertex.getAdjacenyList();
		for (int i = 0; i < adjacenyList.size(); i++) {
			Integer key = adjacenyList.get(i);
			UUVertex temp = vertexMap.get(key);
			if (!temp.isIs_visited()) {
				dfsTraversalRecursive(temp);
			}
		}
	}

	public boolean isCyclic(int startIdx) {
		boolean[] isVisitedArray = new boolean[vertexMap.size()];
		return isCyclicUtil(startIdx, -1, isVisitedArray);
	}

	private boolean isCyclicUtil(int itself, int parent, boolean[] isVisitedArray) {
		UUVertex ver = vertexMap.get(itself);
		isVisitedArray[itself] = true;
		List<Integer> neighbours = ver.getAdjacenyList();
		for (int i = 0; i < neighbours.size(); i++) {
			int key = neighbours.get(i);
			UUVertex temp = vertexMap.get(key);
			if (!temp.isIs_visited()) {
				temp.setIs_visited(true);
				isVisitedArray[key] = true;
				isCyclicUtil(key, itself, isVisitedArray);
			} else if (key != parent && isVisitedArray[key]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Override to String method
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BasicGraph [vertexMap=");
		builder.append(vertexMap);
		builder.append("]");
		return builder.toString();
	}

}

import java.util.*;

class Node implements Comparable<Node> {
	public char id;
	public int distance;

	public Node(char id, int distance) {
		this.id = id;
		this.distance = distance;
	}

	@Override
	public int compareTo(Node other) {
		return Integer.compare(this.distance, other.distance);
	}
}

class Graph {
	Map<Character, List<Node>> adjList = new HashMap<>();

	public void addEdge(char source, char dest, int weight) {
		adjList.putIfAbsent(source, new ArrayList<>());
		adjList.putIfAbsent(dest, new ArrayList<>());

		adjList.get(source).add(new Node(dest, weight));
		adjList.get(dest).add(new Node(source, weight));
	}

	public List<Node> getNeighbors(char node) {
		return adjList.get(node);
	}
}

class Dijkstra {
	public static Map<Character, Integer> shortestPath(Graph graph, char start) {
		PriorityQueue<Node> queue = new PriorityQueue<>();
		Map<Character, Integer> distances = new HashMap<>();
		Set<Character> processed = new HashSet<>();

		for (char node : graph.adjList.keySet()) {
			distances.put(node, Integer.MAX_VALUE);
		}
		distances.put(start, 0);
		queue.add(new Node(start, 0));

		while (!queue.isEmpty()) {
			Node current = queue.poll();
			if (processed.contains(current.id))
				continue;

			for (Node neighbor : graph.getNeighbors(current.id)) {
				int newDist = distances.get(current.id) + neighbor.distance;
				if (newDist < distances.get(neighbor.id)) {
					distances.put(neighbor.id, newDist);
					queue.add(new Node(neighbor.id, newDist));
				}
			}
			processed.add(current.id);
		}

		return distances;
	}
}

public class RTable {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Graph graph = new Graph();
		graph.addEdge('A', 'B', 2);
		graph.addEdge('A', 'D', 3);
		graph.addEdge('B', 'C', 5);
		graph.addEdge('B', 'E', 4);
		graph.addEdge('C', 'G', 3);
		graph.addEdge('C', 'F', 4);
		graph.addEdge('G', 'F', 1);
		graph.addEdge('F', 'E', 2);
		graph.addEdge('E', 'D', 5);
		String rootNode = sc.next();
		for (char node : graph.adjList.keySet()) {
			if (rootNode.charAt(0) == node) {
				Map<Character, Integer> result = Dijkstra.shortestPath(graph, node);
				System.out.println("Routing table for node " + node + ": " + result);
			}
		}
	}

}

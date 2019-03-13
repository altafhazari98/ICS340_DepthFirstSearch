
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Creates an Edge Node Graph
 * 
 * @author Mike Stein
 * @author Austin Weigel
 */
public class Graph implements Iterable<Node> {

	ArrayList<Node> nodeList;
	ArrayList<Edge> edgeList;

	/**
	 * Creates a new Graph.
	 * 
	 * @author Mike Stein
	 */
	public Graph() {
		nodeList = new ArrayList<Node>();
		edgeList = new ArrayList<Edge>();
	}

	/**
	 * Gets the ArrayList of all the Nodes in the Graph.
	 * 
	 * @author Mike Stein
	 * @return The ArrayList of all the Nodes in the Graph.
	 */
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}

	/**
	 * Gets the ArrayList of all the Edges in the Graph.
	 * 
	 * @author Mike Stein
	 * @return The ArrayList of all the Edges in the Graph.
	 */
	public ArrayList<Edge> getEdgeList() {
		return edgeList;
	}

	/**
	 * Adds a Node to the Graph.
	 * 
	 * @author Mike Stein
	 * @param n The node to add to the Graph.
	 */
	public void addNode(Node n) {
		nodeList.add(n);
	}

	/**
	 * Adds an Edge to the Graph.
	 * 
	 * @author Mike Stein
	 * @param e The edge to add to the Graph.
	 */
	public void addEdge(Edge e) {
		edgeList.add(e);
	}

	@Override
	/**
	 * Gets name, abbreviation, and value as string.
	 */
	public String toString() {
		String s = "Graph g.\n";
		if (nodeList.size() > 0) {
			for (Node n : nodeList) {
				// Print node info
				String t = "\nNode " + n.getName() + ", abbrev " + n.getAbbrev() + ", value " + n.getVal() + "\n";
				s = s.concat(t);
			}
			s = s.concat("\n");
		}

		return s;
	}

	/**
	 * Sets the 'edgeClass' char value of the Graph based off of the current start
	 * and end times.
	 * 
	 * @author Austin Weigel
	 */
	public void setEdgeClass() {
		for (Edge e : edgeList) {
			if ((e.getTail().getStartTime() < e.getHead().getStartTime())
					&& (e.getHead().getStartTime() < e.getHead().getEndTime())
					&& (e.getHead().getEndTime() < e.getTail().getEndTime()) && e.getEdgeClass() != 'T') {
				e.setEdgeClass('F');
			}
			if ((e.getHead().getStartTime() <= e.getTail().getStartTime())
					&& (e.getTail().getStartTime() <= e.getTail().getEndTime())
					&& (e.getTail().getEndTime() <= e.getHead().getEndTime())) {
				e.setEdgeClass('B');
			}
			if ((e.getHead().getStartTime() < e.getHead().getEndTime())
					&& (e.getHead().getEndTime() < e.getTail().getStartTime())
					&& (e.getTail().getStartTime() < e.getTail().getEndTime())) {
				e.setEdgeClass('C');
			}
		}
	}

	@Override
	/**
	 * Iterates through the nodes of a depth first search. Will skip root node start
	 * but will still set correct root node start time.
	 * 
	 * @author Austin Weigel
	 */
	public Iterator<Node> iterator() {
		return new DepthFirstSearch();
	}

	/**
	 * Iterates through the nodes in depth first search. Chooses the node with the
	 * lowest end time set on parameter arrival. Overwrites new start and end times
	 * to each Node.
	 * 
	 * @author Austin Weigel
	 * @return
	 */
	public Iterator<Node> kosarajuIterator() {
		return new KosarajuSearch();
	}

	/**
	 * Creates a new DepthFirstSearch Iterator. Traverses to next node on start and
	 * end times.
	 * 
	 * @author Austin Weigel
	 */
	private class DepthFirstSearch implements Iterator<Node> {

		Stack<Node> path;
		int time;
		boolean edgesAreInt;

		/**
		 * Creates a new DepthFirstSearchIterator.
		 */
		public DepthFirstSearch() {
			path = new Stack<Node>();
			time = 1;
			edgesAreInt();
			Node startNode = startNode();
			startNode.setStartTime(time);
			path.push(startNode);
		}

		@Override
		/**
		 * Checks if there is another node in the DFS traversal of the Graph
		 */
		public boolean hasNext() {
			return path.isEmpty() == false;
		}

		@Override
		/**
		 * The next node by T in the DFS.
		 */
		public Node next() {
			time++;
			// Get the 'next' node from the top of the stack then find the node after the
			// next node or next next node to push into the Stack.
			Node nextNode = path.peek();
			Node nextNextNode = null;

			// Finding the next next Node in forest
			if (path.isEmpty() == false) { // Used to search for outgoing white nodes, if none
											// are found pop top node off stack and repeat
				Edge nextWhiteEdge = path.peek().nextOutgoingWhiteEdge(edgesAreInt);
				if (nextWhiteEdge != null) {// searching for outgoing edges to white nodes from top node on the path.
					nextWhiteEdge.setEdgeClass('T');
					nextNextNode = nextWhiteEdge.getHead();
					nextNextNode.setStartTime(time);
					path.push(nextNextNode);
				} else {
					path.peek().setEndTime(time);
					path.pop();
				}
			}
			if (path.isEmpty()) {
				nextNextNode = nextForestRootNode();
				if (nextNextNode != null) {
					time++;
					nextNextNode.setStartTime(time);
					path.push(nextNextNode);
				}
			}

			return nextNode;

		}

		/**
		 * Sets the variable edgesAreInt that is true is all the edges have integer
		 * values; false otherwise.
		 */
		private void edgesAreInt() {
			edgesAreInt = true;
			for (Edge e : edgeList) {
				try {
					Integer.parseInt(e.getLabel());
				} catch (NumberFormatException nfe) {
					edgesAreInt = false;
					return;
				}
			}
		}

		/**
		 * Determines the next Node to search from or the 'root'. Set by the next
		 * unexplored node, that has the lowest lexicographical order of it's name.
		 * 
		 * @return An unexplored node with the lowest lexicographical order of it's name
		 *         of all the unexplored nodes.
		 */
		private Node nextForestRootNode() {
			ArrayList<Node> whiteNodes = new ArrayList<Node>();
			for (Node n : nodeList) {
				if (n.getStartTime() == 0) {
					whiteNodes.add(n);
				}
			}
			if (whiteNodes.isEmpty()) {
				return null;
			}
			Node nextRootNode = whiteNodes.get(0);
			for (Node n : whiteNodes) {
				if (nextRootNode.compareTo(n) > 0) {
					nextRootNode = n;
				}
			}
			return nextRootNode;
		}

		/**
		 * Finds the node with the val 's' not case sensitive.
		 * 
		 * @return The node with val 's' not case sensitive.
		 */
		private Node startNode() {
			for (Node n : nodeList) {
				if (n.getVal().equalsIgnoreCase("S")) {
					return n;
				}
			}
			return null;
		}
	}

	/**
	 * Creates a KosarajuSearch Iterator. Traverses to next node on start and end
	 * times.
	 * 
	 * @author Austin Weigel
	 */
	private class KosarajuSearch implements Iterator<Node> {
		Stack<Node> path;
		int time;
		boolean edgesAreInt;
		Stack<Node> rootNodeStack;

		/**
		 * Creates a new KosarajuSearch Iterator.
		 */
		public KosarajuSearch() {
			path = new Stack<Node>();
			time = 1;
			edgesAreInt();
			setRootNodeStack();
			clearStartEndTimes();
			Node rootNode = rootNodeStack.pop();
			rootNode.setStartTime(time);
			path.push(rootNode);
		}

		/**
		 * Checks there is an unexplored node.
		 */
		@Override
		public boolean hasNext() {
			return path.isEmpty() == false;
		}

		/**
		 * The next node by start or end time in the search.
		 */
		@Override
		public Node next() {
			time++;
			// Get the 'next' node from the top of the stack then find the node after the
			// next node or next next node to push into the Stack.
			Node nextNode = path.peek();
			Node nextNextNode = null;

			// Finding the next next Node in forest
			if (path.isEmpty() == false) { // Used to search for outgoing white nodes, if none
											// are found pop top node off stack and repeat
				Edge nextWhiteEdge = path.peek().nextOutgoingWhiteEdge(edgesAreInt);
				if (nextWhiteEdge != null) {// searching for outgoing edges to white nodes from top node on the path.
					nextWhiteEdge.setEdgeClass('T');
					nextNextNode = nextWhiteEdge.getHead();
					nextNextNode.setStartTime(time);
					path.push(nextNextNode);
				} else {
					path.peek().setEndTime(time);
					path.pop();
				}
			}
			if (path.isEmpty()) {
				nextNextNode = nextForestRootNode();
				if (nextNextNode != null) {
					time++;
					nextNextNode.setStartTime(time);
					path.push(nextNextNode);
				}
			}

			return nextNode;

		}

		/**
		 * Sets the variable edgesAreInt that is true is all the edges have integer
		 * values; false otherwise.
		 */
		private void edgesAreInt() {
			edgesAreInt = true;
			for (Edge e : edgeList) {
				try {
					Integer.parseInt(e.getLabel());
				} catch (NumberFormatException nfe) {
					edgesAreInt = false;
					return;
				}
			}
		}

		/**
		 * Finds the unexplored Node with the lowest end time to set as the next forest
		 * root Node.
		 * 
		 * @return The unexplored Node with the lowest end time.
		 */
		private Node nextForestRootNode() {
			Node nextRootNode = null;
			while (rootNodeStack.isEmpty() == false && rootNodeStack.peek().getStartTime() > 0) {
				rootNodeStack.pop();
			}
			if (rootNodeStack.isEmpty() == false) {
				nextRootNode = rootNodeStack.pop();
			}
			return nextRootNode;
		}

		/**
		 * Fills the rootNodeStack with all nodes or the Graph by original end times
		 * with lowest end time on top.
		 */
		private void setRootNodeStack() {
			ArrayList<Node> rootNodeList = new ArrayList<Node>();
			for (Node rootNode : nodeList) {
				rootNodeList.add(rootNode);
			}
			Node minEndTimeNode;
			rootNodeStack = new Stack<Node>();
			while (rootNodeList.isEmpty() == false) {
				minEndTimeNode = rootNodeList.get(0);
				for (Node rootNode : rootNodeList) {
					if (minEndTimeNode.getEndTime() > rootNode.getEndTime()) {
						minEndTimeNode = rootNode;
					}
				}
				rootNodeList.remove(minEndTimeNode);
				rootNodeStack.push(minEndTimeNode);
			}
		}

		/**
		 * Clears the original start and end times as they are used in the graph
		 * traversal.
		 */
		private void clearStartEndTimes() {
			for (Node n : nodeList) {
				n.setStartTime(0);
				n.setEndTime(0);
			}
		}
	}

	/**
	 * Reverses the direction of all edges in the Graph.
	 */
	public void inverse() {
		for (Edge e : edgeList) {
			Node tail = e.getTail();
			e.setTail(e.getHead());
			e.setHead(tail);
		}
		for (Node n : nodeList) {
			n.getOutgoingEdges().clear();
			n.getIncomingEdges().clear();
		}
		for (Edge e : edgeList) {
			e.getHead().addIncomingEdge(e);
			e.getTail().addOutgoingEdge(e);
		}
	}

	/**
	 * Creates a new Graph with the same Node values and Edge values. Deep clone of
	 * Nodes, Edges, in Graph.
	 * 
	 * @return A new Graph with the same Node values and Edge values.
	 */
	public Graph cloneGraph() {
		Graph clonedGraph = new Graph();

		// Adding cloned nodes without connecting edges to cloned graph.
		for (Node origNode : nodeList) {
			clonedGraph.addNode(origNode.cloneWithoutEdges());
		}

		for (Edge origEdge : edgeList) {
			Node clonedTailNode = null;
			Node clonedHeadNode = null;
			for (Node clonedNode : clonedGraph.getNodeList()) {
				if (clonedNode.equalsIgnoreEdges(origEdge.getTail())) {
					clonedTailNode = clonedNode;
				}
				if (clonedNode.equalsIgnoreEdges(origEdge.getHead())) {
					clonedHeadNode = clonedNode;
				}
			}
			Edge clonedEdge = new Edge(clonedTailNode, clonedHeadNode, origEdge.getLabel());
			clonedGraph.addEdge(clonedEdge);
		}

		for (Edge clonedEdge : clonedGraph.getEdgeList()) {
			clonedEdge.getTail().addOutgoingEdge(clonedEdge);
			clonedEdge.getHead().addIncomingEdge(clonedEdge);
		}

		return clonedGraph;
	}

	/**
	 * Computes the strongly connected components of a graph that has already been
	 * cloned, inverted, and traversed using KosarajuSearch to set the correct start
	 * and end times.
	 * 
	 * @return An ArrayList of ArrayLists of the nodes in all the strongly connected
	 *         components.
	 */
	public ArrayList<ArrayList<Node>> getStronglyConnectedNodes() {
		int rootNodeStartTime = 1;
		ArrayList<ArrayList<Node>> stronglyConnectedNodes = new ArrayList<ArrayList<Node>>();
		while (rootNodeStartTime < nodeList.size() * 2) {
			ArrayList<Node> forest = new ArrayList<Node>();
			Node rootNode = null;
			for (Node n : nodeList) {
				if (n.getStartTime() == rootNodeStartTime) {
					rootNode = n;
				}
			}
			forest.add(rootNode);
			for (Node n : nodeList) {
				if (n.getStartTime() > rootNode.getStartTime() && n.getEndTime() < rootNode.getEndTime()) {
					forest.add(n);
				}
			}
			rootNodeStartTime = rootNode.getEndTime() + 1;
			stronglyConnectedNodes.add(forest);
		}
		return stronglyConnectedNodes;
	}
}

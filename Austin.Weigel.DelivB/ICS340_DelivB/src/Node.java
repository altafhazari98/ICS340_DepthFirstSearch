
import java.util.ArrayList;

/**
 * A node of a graph for the Spring 2018 ICS 340 program.
 * 
 * @author Mike Stein
 * @author Austin Weigel
 *
 */
public class Node implements Comparable<Node> {

	String name; // The name of the Node
	String val; // The value of the Node
	String abbrev; // The abbreviation for the Node
	ArrayList<Edge> outgoingEdges; // All the connected edges leading away from this node.
	ArrayList<Edge> incomingEdges; // All the connected edges leading towards this node.
	int startTime; // Used to keep track of when a node was first visitied during a DFS or Kosaraju
					// search of it's containing graph.
	int endTime; // Used to keep track of when a node was last visitied during a DFS or Kosaraju
					// search of it's containing graph.

	/**
	 * Creates a new node with the abbreviation set as passed arg.
	 * 
	 * @author Mike Stein
	 * @param theAbbrev The abbreviaiton of the Name of the node.
	 */
	public Node(String theAbbrev) {
		setAbbrev(theAbbrev);
		val = null;
		name = null;
		outgoingEdges = new ArrayList<Edge>();
		incomingEdges = new ArrayList<Edge>();
		startTime = 0;
		endTime = 0;
	}

	/**
	 * Gets the abbreviation of the name of the node.
	 * 
	 * @author Mike Stein
	 * @return The abbreviation of the name of the node.
	 */
	public String getAbbrev() {
		return abbrev;
	}

	/**
	 * Gets he name of the node.
	 * 
	 * @author Mike Stein
	 * @return The name of the node.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the value of the node.
	 * 
	 * @author Mike Stein
	 * @return The value of the node.
	 */
	public String getVal() {
		return val;
	}

	/**
	 * Gets all edges connected to and leading away from current node.
	 * 
	 * @author Mike Stein
	 * @return All edges connected to and leading away from current node.
	 */
	public ArrayList<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}

	/**
	 * Gets all edges connected to and leading to current node.
	 * 
	 * @author Mike Stein
	 * @return All edges connected to and leading to current node.
	 */
	public ArrayList<Edge> getIncomingEdges() {
		return incomingEdges;
	}

	/**
	 * Sets the abbreviation of the name of node according to parameters.
	 * 
	 * @author Mike Stein
	 * @param theAbbrev The new abbreviation of the name of the node.
	 */
	public void setAbbrev(String theAbbrev) {
		abbrev = theAbbrev;
	}

	/**
	 * Sets the name of the node
	 * 
	 * @author Mike Stein
	 * @param theName The name of the node.
	 */
	public void setName(String theName) {
		name = theName;
	}

	/**
	 * Sets the value of the node according to parameter.
	 * 
	 * @author Mike Stein
	 * @param theVal The new value to set for the node.
	 */
	public void setVal(String theVal) {
		val = theVal;
	}

	/**
	 * Adds an outgoing edge to the list of connected outgoing edges.
	 * 
	 * @author Mike Stein
	 * @param e The edge to add as a new outgoing edge.
	 */
	public void addOutgoingEdge(Edge e) {
		outgoingEdges.add(e);
	}

	/**
	 * Adds an incoming edge to the list of connected incoming edges.
	 * 
	 * @author Mike Stein
	 * @param e The edge to add as a new incoming edge.
	 */
	public void addIncomingEdge(Edge e) {
		incomingEdges.add(e);
	}

	/**
	 * Gets the time the node was first traveled to in a DFS or kosaraju search
	 * 
	 * @author Austin Weigel
	 * @return Start time of the node.
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Sets when the node was first traveled to during a DFS or kosaraju search.
	 * 
	 * @author Austin Weigel
	 * @param startTime The start time to set.
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets when the node was last traveled to during a DFS or kosaraju search.
	 * 
	 * @author Austin Weigel
	 * @return The end time of the node.
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets when the node was last traveled to during a DFS or kosaraju search.
	 * 
	 * @param endTime The end time of the node.
	 */
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	/**
	 * Determines the next edge in a DFS traversal has a head to a white, or not
	 * started, edge.
	 * 
	 * @param edgesAreInt States if all edges in the graph being searched have
	 *                    integer label values.
	 * @return The next white node in the graph traversal.
	 */
	public Edge nextOutgoingWhiteEdge(boolean edgesAreInt) {
		Edge nextWhiteEdge = null;
		for (Edge e : outgoingEdges) {
			if (e.getHead().getStartTime() < 1) {
				if (nextWhiteEdge == null) {
					nextWhiteEdge = e;
				} else if (edgesAreInt && nextWhiteEdge.compareTo(e) > 0) {
					nextWhiteEdge = e;
				} else if (((edgesAreInt && nextWhiteEdge.compareTo(nextWhiteEdge) == 0) || edgesAreInt == false)
						&& nextWhiteEdge.getHead().compareTo(e.getHead()) > 0) {
					nextWhiteEdge = e;
				}
			}
		}
		return nextWhiteEdge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abbrev == null) ? 0 : abbrev.hashCode());
		result = prime * result + ((incomingEdges == null) ? 0 : incomingEdges.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((outgoingEdges == null) ? 0 : outgoingEdges.hashCode());
		result = prime * result + ((val == null) ? 0 : val.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (abbrev == null) {
			if (other.abbrev != null)
				return false;
		} else if (!abbrev.equals(other.abbrev))
			return false;
		if (incomingEdges == null) {
			if (other.incomingEdges != null)
				return false;
		} else if (!incomingEdges.equals(other.incomingEdges))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (outgoingEdges == null) {
			if (other.outgoingEdges != null)
				return false;
		} else if (!outgoingEdges.equals(other.outgoingEdges))
			return false;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}

	/**
	 * Determines if the object is a node, and the node has the same values for the
	 * name, abbreviation, and value.
	 * 
	 * @author Austin Weigel
	 * @param obj The node or object to compare with.
	 * @return True if the passed object is a node with the same name, abbreviation,
	 *         and value variables as the node called upon. False otherwise.
	 */
	public boolean equalsIgnoreEdges(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (abbrev == null) {
			if (other.abbrev != null)
				return false;
		} else if (!abbrev.equals(other.abbrev))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Node [name=" + name + ", outgoingEdges=" + outgoingEdges + ", incomingEdges=" + incomingEdges + "]";
	}

	/**
	 * Creates a new node with the same name, abbreviation, value, startTime, and
	 * endTime values as the node called upon. Will not copy info on incoming or
	 * outgoing edges.
	 * 
	 * @author Austin Weigel
	 * @return a new node with the same name, abbreviation, value, startTime, and
	 *         endTime values as the node called upon. Will not copy info on
	 *         incoming or outgoing edges.
	 */
	public Node cloneWithoutEdges() {
		Node clone = new Node(abbrev);
		clone.setName(name);
		clone.setVal(val);
		clone.setStartTime(startTime);
		clone.setEndTime(endTime);
		return clone;
	}

	/**
	 * The difference between the called upon node's endTime and the parameter
	 * node's endTime.
	 * 
	 * @author Austin Weigel
	 */
	@Override
	public int compareTo(Node otherNode) {
		return endTime - otherNode.getEndTime();
	}
}


//import java.util.*;

/**
 * Edge between two Nodes.
 * 
 * @author Mike Stein
 * @author Austin Weigel
 *
 */
public class Edge implements Comparable<Edge> {

	String label; // Label of the edge
	Node tail; // The outgoing node or non-arrow side node.
	Node head; // The incoming node or arrow side node.
	char edgeClass; // The type of edge based off of start and end times of tail and head nodes.

	/**
	 * Creates a new edge.
	 * 
	 * @author Mike Stein
	 * @param tailNode The outgoing node or non-arrow side node.
	 * @param headNode The incoming node or arrow side node.
	 * @param theLabel The label of the edge.
	 */
	public Edge(Node tailNode, Node headNode, String theLabel) {
		setLabel(theLabel);
		setTail(tailNode);
		setHead(headNode);
		edgeClass = '!';
	}

	/**
	 * Gets the type of edge based off of start and end times of tail and head
	 * nodes. If setEdgeClass is not run first the edgeClass's may not be correct.
	 * 
	 * @author Austin Weigel
	 * @return The type of edge represented as a char value.
	 */
	public char getEdgeClass() {
		return edgeClass;
	}

	/**
	 * Sets the edge class of the edge based off of the start and end times of the
	 * head and tail nodes.
	 * 
	 * @author Austin Weigel
	 * @param edgeClass A char representation of what class of edge this is.
	 */
	public void setEdgeClass(char edgeClass) {
		this.edgeClass = edgeClass;
	}

	public String getLabel() {
		return label;
	}

	/**
	 * Gets the tail node of the edge.
	 * 
	 * @author Mike Stein
	 * @return The tail node of the edge.
	 */
	public Node getTail() {
		return tail;
	}

	/**
	 * Gets the head node of the edge.
	 * 
	 * @author Mike Stein
	 * @return The head node of the edge
	 */
	public Node getHead() {
		return head;
	}

	/**
	 * Sets the label of the edge.
	 * 
	 * @author Mike Stein
	 * @param The label of the edge.
	 */
	public void setLabel(String s) {
		label = s;
	}

	/**
	 * Sets the tail node of the edge.
	 * 
	 * @author Mike Stein
	 * @param n The new tail node of the edge.
	 */
	public void setTail(Node n) {
		tail = n;
	}

	/**
	 * Sets the head node of the edge.
	 * 
	 * @author Mike Stein
	 * @param n The new head node of the edge.
	 */
	public void setHead(Node n) {
		head = n;
	}

	@Override
	/**
	 * Compares two edges based off of the edge's label's lexicographical order.
	 */
	public int compareTo(Edge e) {
		return label.compareTo(e.getLabel());
	}

	@Override
	/**
	 * The label, tail and head of the edge in string values.
	 */
	public String toString() {
		return "Edge [label=" + label + ", tail=" + tail + ", head=" + head + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((tail == null) ? 0 : tail.hashCode());
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
		Edge other = (Edge) obj;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (tail == null) {
			if (other.tail != null)
				return false;
		} else if (!tail.equals(other.tail))
			return false;
		return true;
	}
}

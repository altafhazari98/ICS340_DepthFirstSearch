import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class DelivB does the work for deliverable DelivB of the Prog340
 * 
 * @author Mike Stein
 * @author Austin Weigel
 *
 */
public class DelivB {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;

	public DelivB(File in, Graph gr) {
		// Start given code from Mike Stein.
		inputFile = in;
		g = gr;

		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring(0, inputFileName.length() - 4); // Strip off ".txt"
		String outputFileName = baseFileName.concat("_out.txt");
		outputFile = new File(outputFileName);
		if (outputFile.exists()) { // For retests
			outputFile.delete();
		}

		try {
			output = new PrintWriter(outputFile);
		} catch (Exception x) {
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		// End given code from Mike Stein.

		// Creating an iterator over the original selected input graph
		Iterator<Node> gDFSIterator = g.iterator();

		// Iterating through the graph. Start and end times, which are the important
		// data, are being set as we iterate through the graph.
		while (gDFSIterator.hasNext()) {
			gDFSIterator.next();
		}
		// With start and end times set, the class of edge can now be determined and is
		// set in each edge's edgeClass variable.
		g.setEdgeClass();

		// Printing all nodes of the graph with their start and end times to output
		// file.
		output.println("Node Start Time End Time");
		for (Node n : g.getNodeList()) {
			output.println(n.getName() + " " + n.getStartTime() + " " + n.getEndTime());
		}
		output.println();

		// Printing all edges in the graph with the type of edge that they are to output
		// file.
		output.println("Edge Type");
		for (Edge e : g.getEdgeList()) {
			output.println(e.getTail().getAbbrev() + "-" + e.getHead().getAbbrev() + " " + e.getEdgeClass());
		}

		// Cloning and inverting the original graph. Clone will be used to determine
		// strongly connected components using Kosaraju's algorithm.
		Graph inverseG = g.cloneGraph();
		inverseG.inverse();

		// Kosaraju iterator sets start and end times of the cloned graph. These start
		// and end times can then be used to determine strongly connected components.
		Iterator<Node> inverseGKosarajuIterator = inverseG.kosarajuIterator();
		while (inverseGKosarajuIterator.hasNext()) {
			inverseGKosarajuIterator.next();
		}

		// Determining the strongly connected components of the graph and printing to
		// output text file.
		ArrayList<ArrayList<Node>> stronglyConnectedNodes = inverseG.getStronglyConnectedNodes();
		output.println();
		output.println("Strongly connected components:");
		for (ArrayList<Node> forest : stronglyConnectedNodes) {
			String stronglyConnectedString = "{";
			for (Node n : forest) {
				stronglyConnectedString += n.getAbbrev() + ", ";
			}
			if (stronglyConnectedString.length() > 0) {
				stronglyConnectedString = stronglyConnectedString.substring(0, stronglyConnectedString.length() - 2);
			}
			stronglyConnectedString += "}\n";
			output.println(stronglyConnectedString);
		}
		output.close();
	}
}

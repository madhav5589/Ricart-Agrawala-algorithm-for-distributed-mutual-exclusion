

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TCPRunner {
	static List<Node> nodeinfo;
	static int id;
	static int totalNodes;
	
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		
		CriticalSectionRequests csrequest;
		nodeinfo = readConfigFile();  //extract all config file info to arraylist of nodes
		Map<Integer,Integer> ports = new TCPRunner().getServerPorts(nodeinfo);  //get port info to host the server to listen for connections
		Server server = new Server(ports,id);  //start the server
		server.start(); 
		Client client = new Client();
		client.sendRequests(nodeinfo, id);
		CriticalSectionRequests.sendReadyStatusToNodeZero(id);
//		csrequest = new CriticalSectionRequests();
//		csrequest.sendRequests(id); Qc
	}


	private static List<Node> readConfigFile() throws FileNotFoundException,
			IOException {
		ParseFile pf = new ParseFile();
		Scanner in = new Scanner(System.in);
		System.out.println("Enter node id");
		id = in.nextInt();
		nodeinfo = pf.readConfigFile(id);
		return nodeinfo;
	}
	
	
	public Map<Integer,Integer> getServerPorts(List<Node> nodeinfo){
		Map<Integer,Integer> ports = new HashMap<>();
		for(Node n : nodeinfo){
			ports.put(n.id, n.portno);
		}
		return ports;
	}
}

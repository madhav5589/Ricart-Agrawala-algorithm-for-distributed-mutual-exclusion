

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseFile {
	
//	static int totalNodes;
		
//	public static void main(String[] args) throws IOException {
//		readConfigFile();
//		
//	}

	public static List<Node> readConfigFile(int id) throws FileNotFoundException,
			IOException {
		List<Node> nodeinfo = new ArrayList<Node>();
//		System.out.println("Please enter the node id");
//		Scanner in = new Scanner(System.in);
//		int id = in.nextInt();
		String[] data;
		
		BufferedReader readfile = new BufferedReader(new FileReader("/home/rohit/indigo_workspace/AosTest/Config"));
		
		String line;
		
		while ((line = readfile.readLine()) != null) {
			if (!line.startsWith("#")) {
				data = line.split(" ");
				if (data[1].equals("#")) {
					TCPRunner.totalNodes = Integer.parseInt(data[0]);
					System.out.println("Total nodes : "+TCPRunner.totalNodes);
				} else {
					Node n = new Node();
					int nodeid = Integer.parseInt(data[0]);
					String ip = data[1];
					int port = Integer.parseInt(data[2]);
					n.id = nodeid;
					n.ipaddr = ip;
					n.portno = port;
					nodeinfo.add(n);
				}
			}
		}
//		new ParseFile().displayNodes(nodeinfo,id);
		return nodeinfo;
	}
	
	public void displayNodes(List<Node> nodeinfo,int id){
		++id;
		for(int i=id;i<nodeinfo.size();i++){
			System.out.println("Id is : "+nodeinfo.get(i).id);
			System.out.println("IP addr is : "+nodeinfo.get(i).ipaddr);
			System.out.println("Port no is : "+nodeinfo.get(i).portno);
			}
		}
		
	}

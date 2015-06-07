

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Client {

	MessageReceiver msgReceive;
	CriticalSectionRequests csrequest;


	int globalid;
	public void sendRequests(List<Node> nodeinfo,int id) throws UnknownHostException, IOException, InterruptedException{
		this.globalid = id;
		++id;  //incrementing the id because the client needs to send requests only to its predecessing nodes ex. 2 will send requests only to 3 and 4
		System.out.println("Bring them up in 15 secs...");
		Thread.sleep(15000);
		for(int i=id;i<nodeinfo.size();i++){
//			System.out.println("***Request sent to node number*** : "+i);
			Socket client = new Socket(nodeinfo.get(i).ipaddr,nodeinfo.get(i).portno);
			//SocketConnections.sockets.add(nodeinfo.get(i).id,client);
			System.out.println("~~~~~Adding "+nodeinfo.get(i).id+" to the socket connections");
			SocketConnections.sockets.put(nodeinfo.get(i).id, client);
//			System.out.println("added to sock connections : "+nodeinfo.get(i).id+" "+client.getPort());
//			System.out.println("****Total sockets : "+SocketConnections.getSocketConnections().size()+" ****");
			msgReceive = new MessageReceiver(client,globalid);
			msgReceive.start();
			
		}
		
//		csrequest = new CriticalSectionRequests();
//		csrequest.sendRequests(globalid);
	}
	

}

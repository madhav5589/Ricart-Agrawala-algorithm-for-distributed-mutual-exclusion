

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MessageReceiver extends Thread{
	static int totalMessages;
	DataInputStream dis;
	Socket server;
	static int totalConnections;
	static int permit;
	MessageSender msgSender;
	CriticalSectionRequests csrequest;
	RicartAgrawala ricartwins = new RicartAgrawala();
	int id;
	int guestId;
	StringTokenizer stringTokens ;
	
	
	public MessageReceiver(Socket s,int id) {
		this.id = id;
		this.server = s;
		totalConnections++;
	}
	
//	starting this thread from Server class
	public void run() {
		try {
			System.out.println("Client connection successful with..."+server.getInetAddress().getHostName());
			System.out.println("Total connections : "+totalConnections);

			while(true){
				dis = new DataInputStream(server.getInputStream());
				String msg = dis.readUTF().toString();
				List<String> recvdMsgTokens = new ArrayList<String>();
				StringTokenizer msgTokens = new StringTokenizer(msg,",");
				while(msgTokens.hasMoreTokens()){
					recvdMsgTokens.add(msgTokens.nextToken());
				}
//				System.out.println(server.getInetAddress().getHostName()+" : "+msg);
				if(recvdMsgTokens.get(0).equals("READY")){
//					if(id!=0)
					totalMessages++;
					if(totalMessages==TCPRunner.totalNodes-1){
//						System.out.println("Received READY from all of the nodes expected...");
						csrequest=new CriticalSectionRequests();
//						msgSender.sendToAll(Message.INITIATE.toString());
						msgSender = new MessageSender(Message.INITIATE.toString());
						msgSender.start();
						csrequest.sendCSRequests(0);
//						csrequest.join();
//						csrequest.start();
//						Socket nodezero = new Socket("localhost", port)
//						csrequest.start();
						
					}
				}
				if(recvdMsgTokens.get(0).equals("INITIATE")){
//					System.out.println("**** Received INITIATE ****");
					csrequest = new CriticalSectionRequests();
//					csrequest.sendRequests(id);
					csrequest.sendCSRequests(id);
				}
				if(recvdMsgTokens.get(0).equals("PERMIT")){
					System.out.println("Received PERMIT from "+recvdMsgTokens.get(1));
					permit++;
//					System.out.println("--------- PERMIT COUNT IS : "+permit);
					if(permit==TCPRunner.totalNodes-1){
//						ricartwins = new RicartAgrawala();
						permit=0;
						ricartwins.criticalSection();
					}
					
					
				}
				else {
//					ricartwins = new RicartAgrawala();
					stringTokens = new StringTokenizer(msg,",");
					List<String> tokens = new ArrayList<String>();
					while(stringTokens.hasMoreElements()){
						tokens.add(stringTokens.nextToken());
					}
//					this.guestId = Integer.parseInt(tokens.get(2));
//					System.out.println("////////// Guest id : "+guestId);
//					tokens will be Message(REQUEST),TimeStamp
					if(tokens.get(0).equals(Message.REQUEST.toString())){
					this.guestId=Integer.parseInt(tokens.get(2));
					ricartwins.determineCriticalSectionEntry(server, tokens, id,guestId);
					
					}
//					ricartwins.printQSize();
//					System.out.println("$$$$$$$$$$$$$$$ Deffered size is : "+DeferredRequests.getSize());
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}



import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Server extends Thread{
	DataOutputStream dos;
	MessageSender msgSender;
	int port;
	int id;
	static int identity; //identifying the node numbers as I get the request to connect
		public Server(Map<Integer,Integer> ports,Integer id) {
			
			this.id = id;
			this.port = ports.get(id);
			
		}
	
		
		public void run() {
		ServerSocket ss;
		try {
		System.out.println("Starting server on port no ^: "+port);
		ss = new ServerSocket(port);
		
		while(true){
		System.out.println("Waiting for connections...");
		Socket s = ss.accept();
//		SocketConnections.sockets.add(s);
		
//		System.out.println("~~~~~Adding "+identity+" to the socket connections");
		SocketConnections.sockets.put(identity++, s);
	//	identity++;
//		System.out.println("****Total sockets : "+SocketConnections.getSocketConnections().size()+" ****");
		MessageReceiver m = new MessageReceiver(s,id); //start thread to listen for incoming data
		m.start();
		dos = new DataOutputStream(s.getOutputStream());
//		dos.writeUTF("Thanks for the connect...");
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		};
	}

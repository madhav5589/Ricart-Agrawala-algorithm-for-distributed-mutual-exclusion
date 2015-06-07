

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class MessageSender extends Thread{

	DataOutputStream dos;
	int id;
	CriticalSectionRequests csrequests;
	String msg;
	public MessageSender(String m) {
	
		this.msg = m;
	}
	
	public MessageSender(){
		
	}
	
	public void createOutputStream(Socket socket,int id,Message m){
		this.id = id;
		try {
			dos= new DataOutputStream(socket.getOutputStream());
//			Timestamp ts = new Timestamp(new Date().getTime());
			RicartAgrawala.setMyTimeStamp(TimeStamp.getInstance());
//			dos.writeUTF("Let me try to enter critical section, id : "+id);
//			m=REQUEST,ts=timestamp i.e System time
			dos.writeUTF(m.toString()+","+TimeStamp.getInstance().toString()+","+id);
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public void run() {
		
		Iterator it = SocketConnections.sockets.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pairs = (Map.Entry) it.next();
//			System.out.println(pairs.getKey() + " = " + pairs.getValue());
			System.out.println("Iterating in sendToAll ^^THREAD^^ method now.............................");
			try {
				dos = new DataOutputStream(((Socket) pairs.getValue()).getOutputStream());
				dos.writeUTF(msg);
			} catch (IOException e) {
				System.out.println("Error in run() of MessageSender class");
				e.printStackTrace();
			}
		}
		
		/*while(true){
			try {
				dos.writeUTF("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Thanks***for***connecting...^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}
	
	public void sendMessage(Socket socket,int id,Message msg){
		try {
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(msg.toString()+","+id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void sendToAll(String m) throws IOException{
		
		Iterator it = SocketConnections.sockets.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pairs = (Map.Entry) it.next();
//			System.out.println(pairs.getKey() + " = " + pairs.getValue());
			System.out.println("Iterating in sendToAll method now.............................");
			dos = new DataOutputStream(((Socket) pairs.getValue()).getOutputStream());
			dos.writeUTF(m);
//			if(m.equals(Message.INITIATE.toString()) && id==0){
//				csrequests.sendCSRequests(0);
//			}
			
		}
		
//		for(int i=0;i<=SocketConnections.sockets.size();i++){
			/*if(i==0){ //***left here
//				csrequests = new CriticalSectionRequests();
				continue;
				}
			if(SocketConnections.sockets.get(i)==null)
				System.out.println("############################Found NULL############################ "+i);
			dos = new DataOutputStream(SocketConnections.sockets.get(i).getOutputStream());
			dos.writeUTF(m);
		}*/
}
	}

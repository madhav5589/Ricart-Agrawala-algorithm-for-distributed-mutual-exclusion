import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class RicartAgrawala {

	private static boolean requestingCS;
	private static Timestamp myTimeStamp;
//	MessageSender msgSender=new MessageSender();
	MessageSender msgSender;
	Timestamp guestTimestamp;
	static int permitreplies;
	static int cscount;
	static int noofrequests;
	static int id;
	
//	PriorityQueue<Timestamp> pq = new PriorityQueue<Timestamp>();
//	PriorityQ pq = new PriorityQ<>();
	

	public static Timestamp getMyTimeStamp() {
		return myTimeStamp;
	}

	public static void setMyTimeStamp(Timestamp myTimeStamp) {
		RicartAgrawala.myTimeStamp = myTimeStamp;
	}

	public synchronized static boolean isRequestingCS() {
		return requestingCS;
	}

	public synchronized static void setRequestingCS(boolean requestingCS) {
		RicartAgrawala.requestingCS = requestingCS;
	}

	public synchronized void determineCriticalSectionEntry(Socket s,List message,int id,int guestid){
		this.id=id;
		this.guestTimestamp = Timestamp.valueOf((String)message.get(1));
//		if I'm requesting for CS,let me compare timestamps to determine if I should permit the host or defer the reply
		if(RicartAgrawala.isRequestingCS()){
			msgSender = new MessageSender();
			if(myTimeStamp.compareTo(guestTimestamp)>0){  //guest timestamp has higher priority
				msgSender.sendMessage(s, id, Message.PERMIT);
//				System.out.println("--MyTimeStamp :   "+myTimeStamp);
//				System.out.println("--Requesting TS : "+guestTimestamp);
//				System.out.println("--                  PERMIT sent*");
				
			} 
			if(myTimeStamp.compareTo(guestTimestamp)<0){ //my timestamp has higher priority
				DeferredRequests.add(guestid, s);
				System.out.println("<<<<< Request deferred for "+guestid);
//				System.out.println("MyTimeStamp :   "+myTimeStamp);
//				System.out.println("Requesting TS : "+guestTimestamp);
//				System.out.println(".........................Reply has been deffered...");
				
			}
			if(myTimeStamp.compareTo(guestTimestamp)==0){
//				System.out.println("[[[[[[[[[[[  we are into a complex situation ]]]]]]]] "+id);
//				System.out.println("]]]]]]       Guest id : "+guestid);
				if(guestid<id){
					msgSender.sendMessage(s, id, Message.PERMIT);
					System.out.println("########                  PERMIT sent*");
				}else{
					DeferredRequests.add(guestid, s);
//				System.out.println(".........................Reply has been deffered...");
				}
			}
		}else{
			msgSender = new MessageSender();
			msgSender.sendMessage(s, id, Message.PERMIT);
			
		}
		
	}
	
//	public void printQSize(){
//		System.out.println("####################### Queue size is : "+pqueue.getSize());
//	}
	
	public synchronized void criticalSection(){
//		System.out.println("Time entered C*S : "+TimeStamp.getStartTime());
		System.out.println("----------------------------------------------- Time entered "+TimeStamp.getTime());
//		System.out.println(" ----- Time elapsed to request and enter : "+timeElapsed);
		if(true){
			System.out.println("******** Entered critical section!!!!!! ********* "+ ++cscount);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		writeToFile();
		exitCriticalSection();
		
	}
	
	
	public static void exitCriticalSection(){
		CriticalSectionRequests csr = new CriticalSectionRequests();
		RicartAgrawala.setRequestingCS(false);
		TimeStamp.setInstancetoNull();
		sendReplyToDeferredRequests();
//		System.out.println("Time exited C*S : "+TimeStamp.getEndTime());
		long timeElapsed=TimeStamp.getEndTime()-TimeStamp.getStartTime();
		System.out.println("----------------------------------------------- Time exited  "+TimeStamp.getTime());
		++noofrequests;
		if(noofrequests<20){
			csr.sendCSRequests(id);
		}
	}
	
	public static void sendReplyToDeferredRequests(){
		MessageSender msgSender=new MessageSender();
		Iterator it = DeferredRequests.deferredlist.entrySet().iterator();
		while(it.hasNext()){
//			System.out.println("Iterating deffered list......................");
			if(!isRequestingCS()){
//				System.out.println("Actually sending permits to deferred requests...!!!!!!!!");
			Map.Entry pairs = (Map.Entry) it.next();
//			msgSender.sendMessage((Socket)it.next(), 1, Message.PERMIT);
//			DeferredRequests.toRemove.add(pairs.getKey());
			System.out.println(">>>> Sent deferred PERMIT to : "+pairs.getKey());
			msgSender.sendMessage((Socket)pairs.getValue(), id, Message.PERMIT);
//			System.out.println("***************PERMIT REPLY SENT**************** "+ ++permitreplies);
//			it.remove();
			}
		}
		DeferredRequests.deferredlist = new HashMap<Integer, Socket>();
		}
	
	
	public void writeToFile() {
		try {
			File file = new File("/home/rohit/Desktop/ricartoutput.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);

			synchronized (this) {
				bw.write("Node no. "+id+ " Entered critical section..." + "\n"); // write to file
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
	

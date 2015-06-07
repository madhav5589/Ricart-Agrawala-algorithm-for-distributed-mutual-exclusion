import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


public class CriticalSectionRequests extends Thread {
	 static int nodeid;
		 
	public void sendCSRequests(int id) {
		int cscount = 0;

		// while(cscount<20){

		MessageSender m = new MessageSender();
		Iterator it = SocketConnections.sockets.entrySet().iterator();
		while (it.hasNext()) {
			try {
				Thread.sleep(randInt(10, 100));
			} catch (InterruptedException e) {
				System.out
						.println("********** Interuppted in SendCSRequests method *********");
				e.printStackTrace();
			}

			Map.Entry pairs = (Map.Entry) it.next();
			// System.out.println("=== "+pairs.getKey()+" "+pairs.getValue()+" ===");
			RicartAgrawala.setRequestingCS(true);
			TimeStamp.setStartTime();
			m.createOutputStream((Socket) pairs.getValue(), id, Message.REQUEST);
			// System.out.println(pairs.getKey() + " = " + pairs.getValue());
		}
		cscount++;
		// System.out.println("Now cscount is : "+cscount);
	}
		
//	 }
	
	public static void sendReadyStatusToNodeZero(int nodeid){
		Message msg = Message.READY;
		MessageSender m = new MessageSender(Message.READY.toString());
		if(nodeid!=0)
		m.sendMessage(SocketConnections.sockets.get(0), nodeid, msg);
		
	}
	
	
	public static int randInt(int min, int max) {

	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
}

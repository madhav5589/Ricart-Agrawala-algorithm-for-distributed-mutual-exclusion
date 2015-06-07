import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeferredRequests {

	static Map<Integer,Socket> deferredlist = new HashMap<Integer, Socket>();
	static List toRemove=new ArrayList<>();
	
	
	public static void add(Integer id,Socket s){
		deferredlist.put(id, s);
	}
	
	public static int getSize(){
		return deferredlist.size();
	}
	
}

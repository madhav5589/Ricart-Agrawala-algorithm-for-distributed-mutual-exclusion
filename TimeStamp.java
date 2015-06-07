import java.sql.Timestamp;
import java.util.Date;


public class TimeStamp {
    private static Timestamp instance;
    private static long startTime;
    private static long endTime;
    private static long time;
    
    public static long getTime(){
    	return new Date().getTime();
    }
    

    public static long getStartTime() {
		return startTime;
	}

	public static void setStartTime() {
		TimeStamp.startTime = new Date().getTime();
	}

	public static long getEndTime() {
		return new Date().getTime();
	}

	public static void setEndTime(long endTime) {
		TimeStamp.endTime = endTime;
	}

	public static synchronized Timestamp getInstance() {
        if (instance == null) {
            instance = new Timestamp(new Date().getTime());
        }
        return instance;
    }
    
    public static void setInstancetoNull(){
    	instance=null;
    }
}

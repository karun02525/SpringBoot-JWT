package alti.utils;

import java.sql.Timestamp;
import java.time.Instant;

public class Utils {
	
	 public static String timeStamp(){
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	        Instant instant = timestamp.toInstant();
	        long times=instant.toEpochMilli();
	        return String.valueOf(times);
	    }
}

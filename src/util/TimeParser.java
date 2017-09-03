package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeParser {

	public static long getTimestamp(String time){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S");
		Date date = null;
		try {
			date = simpleDateFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
		 
		long timestamp = date.getTime();
		return timestamp;
	}

	public static String getDateTime(String ts){
		Timestamp timestamp = new Timestamp(Long.valueOf(ts));
		Date date = new Date(timestamp.getTime());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S");
		return simpleDateFormat.format(date);
		
//		System.out.println(simpleDateFormat.format(timestamp));
//		System.out.println(simpleDateFormat.format(date));
	}
	
	public static void getCurrentHourTime(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Date date = new Date(timestamp.getTime());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:S");
		System.out.println(simpleDateFormat.format(date));
//		return simpleDateFormat.format(date);
	}
}

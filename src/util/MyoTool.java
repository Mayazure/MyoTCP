package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyoTool {
	
	public static String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		return date;
	}
	
}

package eu.nanairo_reader.business.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NanairoDateUtils {
	public static String convertDateToString(Date date) {
		if(date == null){
			return null;
		}
		return (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(date);
	}
}

package fcmpush.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.annotation.PropertyKey;

public class DateUtil {

	public Date todayFourAm() {	
		LocalDateTime nowDateTime = LocalDateTime.now();
		LocalDateTime startTime = LocalDateTime.of(
				  nowDateTime.getYear()
				, nowDateTime.getMonth()
				, nowDateTime.getDayOfMonth()
				, 11
				, 0
				, 0);
		
		Date date = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
	 
		return date; 
	}
	
	public static String todayHourMinute() {	
		LocalDateTime nowDateTime = LocalDateTime.now();
		LocalDateTime startTime = LocalDateTime.of(
				  nowDateTime.getYear()
				, nowDateTime.getMonth()
				, nowDateTime.getDayOfMonth()
				, nowDateTime.getHour()
				, nowDateTime.getMinute()
				, 0);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmm00");
		
		startTime.format(formatter);
	 
		return startTime.format(formatter); 
	}
}

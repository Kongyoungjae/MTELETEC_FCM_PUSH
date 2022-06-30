package fcmpush.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

	public static Date todayFourAm() {	
		LocalDateTime nowDateTime = LocalDateTime.now();
		LocalDateTime startTime = LocalDateTime.of(
				  nowDateTime.getYear()
				, nowDateTime.getMonth()
				, nowDateTime.getDayOfMonth()
				, 04
				, 00
				, 00);
		
		Date date = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
	 
		return date; 
	}
	
	public static Date tomorrowFourAm() {	
		LocalDateTime nowDateTime = LocalDateTime.now().plusDays(1);
		LocalDateTime startTime = LocalDateTime.of(
				  nowDateTime.getYear()
				, nowDateTime.getMonth()
				, nowDateTime.getDayOfMonth()
				, 04
				, 00
				, 00);
		
		Date date = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
	 
		return date; 
	}
	
	public static Date nowDateTime() {	
		LocalDateTime nowDateTime = LocalDateTime.now();
		LocalDateTime startTime = LocalDateTime.of(
				  nowDateTime.getYear()
				, nowDateTime.getMonth()
				, nowDateTime.getDayOfMonth()
				, nowDateTime.getHour()
				, 56
				, 00);
		
		Date date = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
	 
		return date; 
	}
	
	public static String getNowHourMinuateStr() {	
		LocalDateTime nowDateTime = LocalDateTime.now();
		LocalDateTime startTime = LocalDateTime.of(
				  nowDateTime.getYear()
				, nowDateTime.getMonth()
				, nowDateTime.getDayOfMonth()
				, nowDateTime.getHour()
				, nowDateTime.getMinute()
				, 0);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm00");
			 
		return startTime.format(formatter); 
	}
}

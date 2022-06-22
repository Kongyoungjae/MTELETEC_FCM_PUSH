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
				, 11
				, 0
				, 0);
		
		Date date = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
	 
		return date; 
	}
	
	public static String getNowHourMinuate() {	
		LocalDateTime nowDateTime = LocalDateTime.now();
		LocalDateTime startTime = LocalDateTime.of(
				  nowDateTime.getYear()
				, nowDateTime.getMonth()
				, nowDateTime.getDayOfMonth()
				, nowDateTime.getHour()
				, nowDateTime.getMinute()
				, 0);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm00");
		
		startTime.format(formatter);
	 
		return startTime.format(formatter); 
	}
	
	public static String todayNoonThisMorning() {	
		LocalDateTime nowDateTime = LocalDateTime.now();
		LocalDateTime startTime = LocalDateTime.of(
				  nowDateTime.getYear()
				, nowDateTime.getMonth()
				, nowDateTime.getDayOfMonth()
				, nowDateTime.getHour()
				, nowDateTime.getMinute()
				, 0);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm00");
		
		startTime.format(formatter);
	 
		return startTime.format(formatter); 
	}
}

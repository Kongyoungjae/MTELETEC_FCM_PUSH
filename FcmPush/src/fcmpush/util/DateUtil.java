package fcmpush.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

	public Date getStartTime() {
	  
	  LocalDateTime localTime = LocalDateTime.now();
	  LocalDateTime runTime = LocalDateTime.of(localTime.getYear(),localTime.getMonth(),localTime.getDayOfMonth(),5,4,40);
	
	  Instant instant = runTime.atZone(ZoneId.systemDefault()).toInstant();
		  
	  DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	  System.out.println(runTime.format(dateFormat));
	  
	  Date date = java.sql.Timestamp.valueOf(runTime.format(dateFormat));
	  
	  return date; 
	}
	
//	public Date getStartTime() {
//		  
//		  LocalDateTime localTime = LocalDateTime.now();
//		  LocalDateTime runTime = LocalDateTime.of(localTime.getYear(),localTime.getMonth(),localTime.getDayOfMonth(),4,53,0);
//		
//		  Instant instant = runTime.atZone(ZoneId.systemDefault()).toInstant();
//			  
//		  DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
//
//		  Date date = java.sql.Timestamp.valueOf(runTime.format(dateFormat));
//		  return date; 
//		}

}

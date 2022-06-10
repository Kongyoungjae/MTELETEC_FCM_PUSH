package fcmpush.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceLogUtil {
	public StackTraceLogUtil() {		
	}
	
	// return "Exception.PrintStackTrace" to String
	public static String getStackTraceString(Exception e) {
		StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
         
        return errors.toString();
	}
}

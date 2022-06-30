package fcmpush.thread;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.messaging.FirebaseMessagingException;

import fcmpush.exception.ErrorHandler;
import fcmpush.exception.ExceptionHandler;
import fcmpush.service.PushGroupService;

public class PushGroupThread {
	private static final Logger logger = LogManager.getLogger();
	
	public void run() {		
		
		try {
			PushGroupService service = new PushGroupService();
			service.groupProcess();
			logger.info("PushGroupThread 완료");
		} catch (Error e) {
			throw new ErrorHandler(e);
		} catch (RuntimeException e) {
			throw new ExceptionHandler(e);
		} catch (IOException e) {
			throw new ExceptionHandler(e);
		} catch (FirebaseMessagingException e) {
			throw new ExceptionHandler(e);
		} 
	}
}

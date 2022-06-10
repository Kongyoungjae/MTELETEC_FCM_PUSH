package fcmpush.thread;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.FirebaseException;
import com.google.firebase.messaging.FirebaseMessagingException;

import fcmpush.config.FireBaseConfig;
import fcmpush.exception.ErrorHandler;
import fcmpush.exception.ExceptionHandler;
import fcmpush.service.FireBasePushService;
import fcmpush.service.MakeGroupService;

public class MakeGroupThread {
	private static final Logger logger = LogManager.getLogger();
	
	public void run() {		
		
		try {
			MakeGroupService service = new MakeGroupService();
			service.makeReceiveUserGroup();
		} catch (Error e) {
			throw new ErrorHandler(e);
		} catch (RuntimeException e) {
			throw new ExceptionHandler(e);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FirebaseMessagingException e) {
			throw new ExceptionHandler(e);
		} 
	}
	
	public void run2() {		
		
		try {
			MakeGroupService service = new MakeGroupService();
			service.makeReceiveUserGroup2();
		} catch (Error e) {
			throw new ErrorHandler(e);
		} catch (RuntimeException e) {
			throw new ExceptionHandler(e);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FirebaseMessagingException e) {
			throw new ExceptionHandler(e);
		} 
	}
}

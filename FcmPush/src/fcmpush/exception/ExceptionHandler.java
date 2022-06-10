package fcmpush.exception;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.messaging.FirebaseMessagingException;

import fcmpush.main.Main;

public class ExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 8564871074216674628L;
	private static final Logger logger = LogManager.getLogger();

	public ExceptionHandler(RuntimeException e) {
		logger.error("@@@RuntimeException 익셉션 @@@");
		e.printStackTrace();
		Main.run();
	}

	public ExceptionHandler(InterruptedException e) {
		logger.error("@@@InterruptedException 익셉션 @@@");
		e.printStackTrace();
		Main.run();
	}

	public ExceptionHandler(FirebaseMessagingException e) {
		logger.error("@@@FirebaseMessagingException 익셉션 @@@");
		e.printStackTrace();
		Main.run();
	}

	public ExceptionHandler(IOException e) {
		logger.error("@@@FirebaseMessagingException 익셉션 @@@");
		e.printStackTrace();
		Main.run();	
	}
}

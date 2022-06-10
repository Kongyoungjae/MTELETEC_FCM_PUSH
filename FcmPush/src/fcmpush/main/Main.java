package fcmpush.main;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;

import fcmpush.enumeration.FireBaseIntervalEnum;
import fcmpush.exception.ErrorHandler;
import fcmpush.exception.ExceptionHandler;
import fcmpush.test.FireBaseTest;
import fcmpush.thread.FireBasePushThread;
import fcmpush.thread.MakeGroupThread;
import fcmpush.util.DateUtil;

public class Main {
	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		
		logger.info("@@@MAIN실행@@@");
		
		run();
		run2();

	}

	private static void run2() {
		logger.info("run2@@@");

		try {
			Thread.sleep(1000);
			Timer pushTimer = new Timer();
			TimerTask pushTask = new TimerTask() {			
				@Override
				public void run() {
					
					FireBasePushThread pushThread = new FireBasePushThread();
					pushThread.run();
				}
			};
			pushTimer.scheduleAtFixedRate(pushTask, 0, 1000);
		}
		catch(RuntimeException e) {	
			throw new ExceptionHandler(e);	
		} catch (InterruptedException e) {
			throw new ExceptionHandler(e);
		} catch(Error e) {	
			throw new ErrorHandler(e);
		}
	}

	public static void run() {
		try {
			Thread.sleep(1000);
						
			// 매일 오전4시에 실행되는 그룹만들기 타이머
		
				Timer receiveGroupTimer = new Timer();
				TimerTask makeReceiveGroupTask = new TimerTask() {			
					@Override
					public void run() {
						MakeGroupThread groupThread = new MakeGroupThread();
						groupThread.run();
					}
				};
				DateUtil date = new DateUtil();
				logger.info("run@");
				while(true) {
					receiveGroupTimer.scheduleAtFixedRate(makeReceiveGroupTask, date.getStartTime().getTime(), FireBaseIntervalEnum.CHECK_PUSH_TIME_INTERVAL.getInterval()); // 1분
				}
		} catch(RuntimeException e) {	
			throw new ExceptionHandler(e);	
		} catch (InterruptedException e) {
			throw new ExceptionHandler(e);
		} catch(Error e) {	
			throw new ErrorHandler(e);
		}
	}
}


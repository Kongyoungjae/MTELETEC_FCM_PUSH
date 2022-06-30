package fcmpush.main;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.internal.FirebaseService;

import fcmpush.config.DataBaseConfig;
import fcmpush.config.FireBaseConfig;
import fcmpush.enumeration.FireBaseEnum;
import fcmpush.enumeration.IntervalEnum;
import fcmpush.exception.ErrorHandler;
import fcmpush.exception.ExceptionHandler;
import fcmpush.service.FireBasePushService;
import fcmpush.thread.FireBasePushThread;
import fcmpush.thread.PushGroupThread;
import fcmpush.util.DateUtil;

public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);
	public static void main(String[] args) {
		
		logger.info("@@@MAIN실행!!@@@");
		run();
	}

	public static void run () {
		
		try {
			DataBaseConfig.init();
			FireBaseConfig.init();
			
//			// 수신그룹 만들기 스레드
			Timer receiveGroupTimer = new Timer();
			TimerTask makeReceiveGroupTask = new TimerTask() {			
				@Override
				public void run() {
					PushGroupThread groupThread = new PushGroupThread();
					groupThread.run();
				}			
			};
			receiveGroupTimer.scheduleAtFixedRate(makeReceiveGroupTask, DateUtil.todayFourAm() , IntervalEnum.All_GROUP_CREATE_INTERVAL.getInterval());
//			receiveGroupTimer.scheduleAtFixedRate(makeReceiveGroupTask, DateUtil.tomorrowFourAm() , IntervalEnum.All_GROUP_CREATE_INTERVAL.getInterval());
			
			//푸쉬 스레드
//			Timer pushTimer = new Timer();
//			TimerTask pushTask = new TimerTask() {			
//				@Override
//				public void run() {					
//					FireBasePushThread pushThread = new FireBasePushThread();
//					pushThread.run();
//
//				}
//			};
//			pushTimer.scheduleAtFixedRate(pushTask, 0 , 1000 * 10 ); // IntervalEnum.PUSH_CHECK_INTERVAL.getInterval()
			Thread.sleep(1000);
		}		
		catch(RuntimeException e) {	
			throw new ExceptionHandler(e);	
		} 
		catch (InterruptedException e) {
			throw new ExceptionHandler(e);
		} 	
		catch(Error e) {	
			throw new ErrorHandler(e);
		} catch (IOException e) {
			throw new ExceptionHandler(e);
		}
	}
}


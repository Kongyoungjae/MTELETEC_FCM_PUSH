package fcmpush.main;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fcmpush.enumeration.FireBaseIntervalEnum;
import fcmpush.exception.ErrorHandler;
import fcmpush.exception.ExceptionHandler;
import fcmpush.thread.FireBasePushThread;
import fcmpush.thread.MakeGroupThread;
import fcmpush.util.DateUtil;

public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) {
		
		logger.info("@@@MAIN실행!!@@@");
		run();
	}

	public static void run () {
		
		try {
			// 수신그룹 만들기 스레드
//			Timer receiveGroupTimer = new Timer();
//			TimerTask makeReceiveGroupTask = new TimerTask() {			
//				@Override
//				public void run() {
//					MakeGroupThread groupThread = new MakeGroupThread();
//					groupThread.run();
//				}			
//			};
//			DateUtil dateUtil = new DateUtil();			
//			receiveGroupTimer.scheduleAtFedRate(makeReceiveGroupTask, dateUtil.todayFourAm() , FireBaseIntervalEnum.All_GROUP_CREATE_INTERVAL.getInterval());
			
			//푸쉬 스레드
			Timer pushTimer = new Timer();
			TimerTask pushTask = new TimerTask() {			
				@Override
				public void run() {
					
					FireBasePushThread pushThread = new FireBasePushThread();
					pushThread.run();

				}
			};
			pushTimer.scheduleAtFixedRate(pushTask, 0, 1000);
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
		}
	}
}


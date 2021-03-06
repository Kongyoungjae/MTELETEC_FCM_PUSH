package fcmpush.main;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fcmpush.config.DataBaseConfig;
import fcmpush.config.FireBaseConfig;
import fcmpush.config.PropertiesConfig;
import fcmpush.enumeration.FireBaseEnum;
import fcmpush.enumeration.IntervalEnum;
import fcmpush.exception.ErrorHandler;
import fcmpush.exception.ExceptionHandler;
import fcmpush.thread.FireBasePushThread;
import fcmpush.thread.PushGroupThread;
import fcmpush.util.DateUtil;

public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);
	private static DatagramSocket isRun;

	public static void main(String[] args) throws SocketException {

		isRun = new DatagramSocket(31997);
		logger.info("@@@MAIN실행!!@@@");
		run();
	}

	public static void run() {

		try {	
			FireBaseConfig.init();
			Thread.sleep(1000);
			
			// 수신그룹 만들기 스레드
			Timer receiveGroupTimer = new Timer();
			TimerTask makeReceiveGroupTask = new TimerTask() {			
				@Override
				public void run() {
					DataBaseConfig.init();					
					PushGroupThread groupThread = new PushGroupThread();
					groupThread.run();
				}			
			};
			receiveGroupTimer.scheduleAtFixedRate(makeReceiveGroupTask, DateUtil.tomorrowFourAm() , IntervalEnum.All_GROUP_CREATE_INTERVAL.getInterval());
			
			// 푸쉬 스레드			
			Timer pushTimer = new Timer();
			TimerTask pushTask = new TimerTask() {
				@Override
				public void run() {
					FireBasePushThread pushThread = new FireBasePushThread();
					pushThread.run();
				}
			};
			pushTimer.scheduleAtFixedRate(pushTask, DateUtil.nowDateTime(), IntervalEnum.PUSH_CHECK_INTERVAL.getInterval());			
		}

		catch (RuntimeException e) {
			throw new ExceptionHandler(e);
		} catch (InterruptedException e) {
			throw new ExceptionHandler(e);
		} catch (IOException e) {
			throw new ExceptionHandler(e);
		} catch (Error e) {
			throw new ErrorHandler(e);
		} 
	}
}

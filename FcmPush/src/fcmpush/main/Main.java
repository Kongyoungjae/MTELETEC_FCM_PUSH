package fcmpush.main;

import java.io.IOException;
import java.io.InputStream;
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

public class Main {
	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		
		logger.info("@@@MAIN실행@@@");
		
		run();

	}

	public static void run() {
		
		try {
			Thread.sleep(1000);
			
			List<String> tokens = new LinkedList<String>();
			
			for(int i=0; i <= 100_000; i++) {
				 tokens.add("f_OoEVscSkCshbtP5cOFhh:APA91bH04xQltAKlGxcWcoQ_StyUkwXxbwenb4-fRViof424Vm5X5VXKGer7gGgxsbXRslkSZzC_hdPyoTYQu92SwtdKJ_Wknn1UgnjJhM0YxTlTp5q4VR3iMLUmwAuLrldHUc5xhU9a");
				 tokens.add("dmDjI3yUS929jlJz1r3egf:APA91bFPsDWCS2UAAHGjynz8wpk3gii2ejWoSUs6iY19TnH818DpKrQpf3PJldWwc3D9ZrrpVt4C2t2K2R9ulDauwteSaMjzyMBZY3xtYudTAKSFM8-cPR8gOCwaSGfx4bOn4ldwrArj");
				 tokens.add("d4ezIS3MQ2-ah0A2axdLa0:APA91bGHsL5LuPexm-OHsWG9UT1mQfm1i6cdGVArns0R-NFxCLRL4oqholKP6Jtu_BRvbVX4oYKNrDP6_n9JNv9LmQhN2DkWPXUzM2jTF6ACymFp3Nd8Fh9DvOuXIp1q1TgQY69cLXbI");
				 
			}
			
			// 그룹 만들기 스레드 (1~10)
			Timer receiveGroupTimer = new Timer();
			TimerTask makeReceiveGroupTask = new TimerTask() {			
				@Override
				public void run() {
					MakeGroupThread groupThread = new MakeGroupThread();
					groupThread.run();
				}
			};
			receiveGroupTimer.scheduleAtFixedRate(makeReceiveGroupTask, 0, FireBaseIntervalEnum.CHECK_PUSH_TIME_INTERVAL.getInterval());

			// 그룹 만들기 스레드 (1~10)
			Thread.sleep(1000);
			Timer receiveGroupTimer2 = new Timer();
			TimerTask makeReceiveGroupTask2 = new TimerTask() {			
				@Override
				public void run() {
					MakeGroupThread groupThread = new MakeGroupThread();
					groupThread.run2();
				}
			};
			receiveGroupTimer2.scheduleAtFixedRate(makeReceiveGroupTask2, 0, FireBaseIntervalEnum.CHECK_PUSH_TIME_INTERVAL.getInterval());
			
			
			// 푸쉬 발송 스레드
//			Timer pushTimer = new Timer();
//			TimerTask pushTask = new TimerTask() {			
//				@Override
//				public void run() {
//					
//					FireBasePushThread pushThread = new FireBasePushThread();
//					pushThread.run();
//				}
//			};
//			pushTimer.scheduleAtFixedRate(pushTask, 0, FireBaseIntervalEnum.CHECK_PUSH_TIME_INTERVAL.getInterval());
			
		} catch(RuntimeException e) {	
			throw new ExceptionHandler(e);	
		} catch (InterruptedException e) {
			throw new ExceptionHandler(e);
		} catch(Error e) {	
			throw new ErrorHandler(e);
		}
	}
}


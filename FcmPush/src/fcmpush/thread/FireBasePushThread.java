package fcmpush.thread;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.internal.FirebaseService;

import fcmpush.main.Main;
import fcmpush.service.FireBasePushService;
import fcmpush.service.MakeGroupService;

public class FireBasePushThread {
	private static final Logger logger = LogManager.getLogger();
	
	public void run() {

		try {
			logger.info("타이머 스레드@@@@@");
			logger.info("그룹의"+MakeGroupService.getGroupCount());
			
			FireBasePushService service = new FireBasePushService();
			service.push();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}

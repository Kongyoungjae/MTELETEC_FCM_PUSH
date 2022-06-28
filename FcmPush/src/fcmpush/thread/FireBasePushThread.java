package fcmpush.thread;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fcmpush.service.FireBasePushService;
import fcmpush.util.DateUtil;

public class FireBasePushThread {
	private static final Logger logger = LogManager.getLogger();
	
	public void run() {

		try {
			HashMap<String, Object> nowDateTime = new HashMap<String, Object>();
			nowDateTime.put("nowDateTime", DateUtil.getNowHourMinuateStr());
					
			FireBasePushService service = new FireBasePushService();
			service.pushProcess(nowDateTime);				
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}

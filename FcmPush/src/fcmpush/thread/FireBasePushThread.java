package fcmpush.thread;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fcmpush.service.FireBasePushService;
import fcmpush.util.DateUtil;

public class FireBasePushThread {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

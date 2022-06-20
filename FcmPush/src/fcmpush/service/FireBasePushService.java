package fcmpush.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import fcmpush.config.FireBaseConfig;
import fcmpush.main.Main;
import fcmpush.repository.FireBaseRepository;


public class FireBasePushService {
	private static final Logger logger = LogManager.getLogger();
	
	private FireBaseConfig fireBaseConfig;
	private FireBaseRepository repository;
	
	public FireBasePushService() {
		fireBaseConfig = new FireBaseConfig();
		repository = new FireBaseRepository();	
	}
	

	public void push(HashMap<String, Object> nowDateTime) throws InterruptedException {
		
		List<HashMap<String, Object>> pushList = repository.selectPushInfoByDateTime(nowDateTime);

		//푸쉬 시간이면서 중복발송이 아닌경우
		if(isPushTime(pushList) && notDuplicatePush(pushList)) {
			if("all".equals(checkPushTarget(pushList))) {
				
			} 
		}		
	}
	
	private boolean isPushTime(List<HashMap<String, Object>> pushList) {	
		if (pushList.size() != 0) {
			return true;
		}
		return false;
	}
	
	
	//같은 시간에 여러 PUSH건에 대해 한건이라도 HIST테이블에 데이터가 있으면 False
	private boolean notDuplicatePush(List<HashMap<String, Object>> pushList) {
		for(HashMap<String, Object> pushID: pushList) {		
			List<HashMap<String,Object>> resultList = repository.selectPushHistByPushID(pushID);
			if(resultList.size() != 0 ) {
				return false;
			}
		}
		return true;
	}
	
	
//  TODO(발송 타입별(Single . All) 메세지전송) 
//	추후에 작성예정 발송타깃값에 따라서 RETURN TYPE이 달라질거임
	private String checkPushTarget(List<HashMap<String, Object>> pushList) {	
		
		// single
		// muiti
		// all
		return "all";
	}
	
	private void groupPushProcess() throws IOException, FirebaseMessagingException {

//		Message message = Message.builder()
//				.setTopic("allUsers")
		
		FirebaseMessaging fireMessaing = FirebaseMessaging.getInstance(fireBaseConfig.initFireBaseApp());
		fireMessaing.send(null);
	}
	
	private void singlePushProcess() throws IOException, FirebaseMessagingException {

//		Message message = Message.builder()
//				.setTopic("allUsers")
		
		FirebaseMessaging fireMessaing = FirebaseMessaging.getInstance(fireBaseConfig.initFireBaseApp());
		fireMessaing.send(null);
	}

	
	
}
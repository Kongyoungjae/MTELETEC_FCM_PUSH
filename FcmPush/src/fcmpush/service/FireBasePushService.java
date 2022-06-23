package fcmpush.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.messaging.FirebaseMessagingException;

import fcmpush.config.FireBaseConfig;
import fcmpush.repository.FireBaseRepository;
import fcmpush.target.PushTarget;
import fcmpush.target.PushTargetFactory;
import fcmpush.util.DateUtil;


public class FireBasePushService {
	private static final Logger logger = LogManager.getLogger();
	
	private FireBaseConfig fireBaseConfig;
	private FireBaseRepository repository;
	
	public FireBasePushService() {
		fireBaseConfig = new FireBaseConfig();
		repository = new FireBaseRepository();	
	}
	

	public void push(HashMap<String, Object> nowDateTime) throws InterruptedException, FirebaseMessagingException, IOException {

		
		List<HashMap<String, Object>> pushList = repository.selectTodayPushInfoByNowDateTime(nowDateTime);
		
		if(isPushTime(pushList) && notDuplicatePush(pushList)) {
			logger.info("푸쉬 시간이면서 중복발송이 아닌경우");
			PushGroupService pushGroupService = new PushGroupService();		
			if(todayFristPush()) {
				List<String> tokens = repository.selectUsersTokenAfter4AM();						
				pushGroupService.createReceiveUserGroupJoinedToday(tokens);
			} 		
			else {
				HashMap<String, Object> lastPushTime  = repository.selectPushHistLastPushTime();				
				List<String> tokens = repository.selectJoinUsersTokenAfterLastPushTime(lastPushTime);
				pushGroupService.createReceiveUserGroupJoinedToday(tokens);
			}
			PushTargetFactory factory = new PushTargetFactory();
			PushTarget target = factory.createPushTarget(pushList);
			target.targetPush();
			//good
	
			
//			// PushTarget target = PushTargetFactory.getTarget(pushList);		
		}		
	}
	
	//현재시간 = DB 발송시간
	private boolean isPushTime(List<HashMap<String, Object>> pushList) {	
		if (pushList.size() != 0) {
			return true;
		}
		return false;
	}
	
	
	//같은 시간에 여러 PUSH건에 대해 한건이라도 HIST테이블에 데이터가 있으면 False
	private boolean notDuplicatePush(List<HashMap<String, Object>> pushList) {
		for(HashMap<String, Object> pushID: pushList) {		
			int count = repository.selectPushHistCountByPushID(pushID);
			if(count != 0 ) { 
				logger.info("TB_PUSH_HIST에 푸쉬내역이 있음");
				return false;
			}
		}
		return true;
	}
	
	//오늘 처음발송
	private boolean todayFristPush() {
		
		if(0 == repository.selectTodayPushHistCount()) {
			return true;
		}
		return false;
	}
	
	
//  TODO(발송 타입별(Single . All) 메세지전송) 
//	추후에 작성예정 발송타깃값에 따라서 RETURN TYPE이 달라질거임
	private String checkPushTarget(List<HashMap<String, Object>> pushList) {	
		
		// single
		// muiti
		// all
		return "all";
	}

	
	
	
	
	
	
//	private void groupPushProcess() throws IOException, FirebaseMessagingException {
//
////		Message message = Message.builder()
////				.setTopic("allUsers")
//		
//		FirebaseMessaging fireMessaing = FirebaseMessaging.getInstance(fireBaseConfig.initFireBaseApp());
//		fireMessaing.send(null);
//	}
//	
//	private void singlePushProcess() throws IOException, FirebaseMessagingException {
//
////		Message message = Message.builder()
////				.setTopic("allUsers")
//		
//		FirebaseMessaging fireMessaing = FirebaseMessaging.getInstance(fireBaseConfig.initFireBaseApp());
//		fireMessaing.send(null);
//	}

	
	
}
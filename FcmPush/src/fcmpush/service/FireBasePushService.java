package fcmpush.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Stopwatch;
import com.google.firebase.messaging.FirebaseMessagingException;

import fcmpush.repository.FireBaseRepository;
import fcmpush.service.target.PushTarget;
import fcmpush.service.target.PushTargetFactory;


public class FireBasePushService {
	private static final Logger logger = LogManager.getLogger();
	private FireBaseRepository repository;
	
	public FireBasePushService() {
		repository = new FireBaseRepository();	
	}
	
	public void pushProcess(HashMap<String, Object> nowDateTime) throws InterruptedException, FirebaseMessagingException, IOException {		
		if(0 == repository.selectPushGroupCount()) {
			PushGroupService pushGroupService = new PushGroupService();
			pushGroupService.groupProcess();
		}
		
		List<HashMap<String, Object>> pushList = repository.selectTodayPushInfoByNowDateTime(nowDateTime);	
		
		if(isPushTime(pushList) && notDuplicatePush(pushList)) {
			PushGroupService pushGroupService = new PushGroupService();		
			if(isFristPushToday()) {
				logger.info("오늘 1번쨰 푸쉬");
				List<String> tokens = repository.selectTodayUsersTokenAfter4AM();						
				pushGroupService.createReceiveGroupJoinedAfter4amToday(tokens);
			} else {
				logger.info("오늘 n번쨰 푸쉬");
				HashMap<String, Object> lastPushTime  = repository.selectPushHistLastPushTime();				
				List<String> tokens = repository.selectJoinUsersTokenAfterLastPushTime(lastPushTime);
				logger.info("마지막 푸쉬 시간이후 등록된 토큰수:"+tokens.size());
				pushGroupService.createReceiveGroupJoinedAfter4amToday(tokens);
			}	
			for(HashMap<String, Object> pushInfo : pushList) {
				PushTarget target = PushTargetFactory.createPushTarget(pushInfo);
				target.push(pushInfo);			
			}
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
		for(HashMap<String, Object> pushID : pushList) {		
			int count = repository.selectPushHistCountByPushID(pushID);
			if(count != 0 ) { 
				return false;
			}
		}
		return true;
	}
	
	//오늘 첫 푸쉬발송
	private boolean isFristPushToday() {
		if(0 == repository.selectTodayPushHistCount()) {
			return true;
		}
		return false;
	}	
}
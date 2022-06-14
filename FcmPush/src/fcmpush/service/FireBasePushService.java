package fcmpush.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.apache.ibatis.ognl.ASTThisVarRef;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fcmpush.config.FireBaseConfig;
import fcmpush.repository.FireBaseRepository;


public class FireBasePushService {
	private static final Logger logger = LogManager.getLogger();

	
	private FireBaseConfig fireBaseConfig;
	private FireBaseRepository repository;
	
	public FireBasePushService() {
		fireBaseConfig = new FireBaseConfig();
		repository = new FireBaseRepository();	
	}
	
	public void push(HashMap<String, Object> nowDateTime) {
		
		logger.info("push");
		List<HashMap<String, Object>> pushList = repository.selectPushInfoByDateTime(nowDateTime);
		logger.info(pushList.toString());
		
		//푸쉬 시간이면서 중복 발송이 아닌경우(DB에서 HISTORY 테이블에서 확인)
		if(isPushTime(pushList) && notDuplicatePush(pushList)) {
			
			// push type 구분하고 발송
			logger.info("푸쉬 시간이면서 중복 발송이 아닌경우");
		}
				
	}
	
	private boolean isPushTime(List<HashMap<String, Object>> pushList) {	
		
		if (pushList.size() != 0) {
			return true;
		}
		return false;
	}
	
	private boolean notDuplicatePush(List<HashMap<String, Object>> pushList) {
		
		for(HashMap<String, Object> pushID: pushList) {		
			List<HashMap<String,Object>> resultList = repository.selectPushHistByPushID(pushID);
			if(resultList.size() != 0 ) {
				return false;
			}
		}
		return true;
	}
	
	
//  TODO(발송 타입별 메세지전송) 추후에 작성예정 발송타깃값에 따라서 RETURN TYPE이 달라질거임
//	private String checkPushTarget(List<HashMap<String, Object>> pushList) {	
//		
//		for(HashMap<String, Object> map : pushList) {
//			
//		}
//	}

	
	
}
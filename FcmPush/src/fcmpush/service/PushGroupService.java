package fcmpush.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;

import fcmpush.config.FireBaseConfig;
import fcmpush.enumeration.FireBasePushEnum;
import fcmpush.repository.FireBaseRepository;

public class PushGroupService {
	private static final Logger logger = LogManager.getLogger();

	private FireBaseConfig fireBaseConfig;
	private FireBaseRepository repository;
	private static FirebaseMessaging fireMessaing;
	private static final int GROUP_SIZE = FireBasePushEnum.GROUP_SIZE.getSize();

	public PushGroupService() {
		fireBaseConfig = new FireBaseConfig();
		repository = new FireBaseRepository();
		init();
	}
	
	private void init() {
		
		if(fireMessaing == null) {
			try {
				fireMessaing = FirebaseMessaging.getInstance(fireBaseConfig.initFireBaseApp());
			} catch (IOException e) {
				logger.error("ADC 인증 오류");
			}
		}

	}
	
	// todo 트랜잭션 처리 해야함
	public void makeReceiveUserGroup() throws IOException, FirebaseMessagingException {
		
		
		// 금일 4시 이전까지 가입한 토큰 가져오기
		List<String> tokens = repository.selectUsersTokenBefore4AM();
		// deleteAllReceiveGroups();
		// unSubscribeUserGroup(tokens);
		long currentTime = System.currentTimeMillis();
		int groupSeq = 1;
    	
    	for(int i = 0; i < tokens.size(); i += GROUP_SIZE) {
    		logger.info(groupSeq + "번째 그룹 생성");
    		List<String> newTokens = new LinkedList<String>();

    		//마지막 그룹 만들때
    		if(i + GROUP_SIZE > tokens.size()) {
    			newTokens = tokens.subList(i, tokens.size());
    		} 
    		else {
        		newTokens = tokens.subList(i, i+GROUP_SIZE);
    		}
    		    	
    		// 템플릿 메서드 패턴???
    		// 구독하는건 똑같다
    		// 메서드로 뺴자 걍
    		// okok 
			TopicManagementResponse response = fireMessaing.subscribeToTopic(newTokens, FireBasePushEnum.GROUP_NAME.getValue() + groupSeq);

    		HashMap<String, Object> map = new HashMap<String, Object>();
    		map.put("GROUP_SEQ", groupSeq);
    		map.put("GROUP_ID", FireBasePushEnum.GROUP_NAME.getValue() + groupSeq);
    		map.put("REG_SUCCESS_CNT", response.getSuccessCount());
    		map.put("REG_FAIL_CNT", response.getFailureCount());
    		
    		insertPushGroup(map);
    		
    		groupSeq += 1;
    	}
    	
		long afterTime = System.currentTimeMillis();
		logger.info((afterTime - currentTime) / 1000);
    }
	
	
	//금일 4시 이후 가입한 사람들 그룹핑
	public void createReceiveUserGroupJoinedToday(List<String> tokens) throws IOException, FirebaseMessagingException {
		
		int groupSeq = repository.selectMaxGroupSEQ();
		
		// 1000개인데
    	for(int i = 0; i < tokens.size(); i += GROUP_SIZE) {

    		List<String> newTokens = new LinkedList<String>();

    		//마지막 그룹 만들때
    		if(i + GROUP_SIZE > tokens.size()) {
    			newTokens = tokens.subList(i, tokens.size());
    		} 
    		else {
        		newTokens = tokens.subList(i, i+GROUP_SIZE);
    		}
    		    	
			TopicManagementResponse response = fireMessaing.subscribeToTopic(newTokens, FireBasePushEnum.GROUP_NAME.getValue() + groupSeq);
    		HashMap<String, Object> map = new HashMap<String, Object>();
    		map.put("GROUP_SEQ", groupSeq);
    		map.put("GROUP_ID", FireBasePushEnum.GROUP_NAME.getValue() + groupSeq);
    		map.put("REG_SUCCESS_CNT", response.getSuccessCount());
    		map.put("REG_FAIL_CNT", response.getFailureCount());
    		
    		insertPushGroup(map);
    		
    		groupSeq += 1;
    	}   	
    }
	
	
	
	//  todo 구독취소..? 꼭해야하나? 수신그룹을 만들면 덮어씌워지긴하는데.. 
//	private void unSubscribeUserGroup(List<String> tokens) throws FirebaseMessagingException { 
//		
//		logger.info("unSubscribeUserGroup Call");
//		int groupSeq  = (int) Math.ceil(((double)tokens.size()) / GROUP_SIZE);
//			
//    	for(int i = 0; i < groupSeq; i ++) {
//    		TopicManagementResponse response = fireMessaing.unsubscribeFromTopic(tokens, null);
//    		logger.info("구독취소 요청 성공수"+response.getSuccessCount());
//    		logger.info("구독취소 요청 실패수"+response.getFailureCount());
//    	}
//	}
	
	
	private int deleteAllReceiveGroups() { 	
		return repository.deleteAllReceiveGroups();				
	}
	
	private void insertPushGroup(HashMap<String, Object> param) { 
		 repository.insertPushGroup(param);		
	}
}

package fcmpush.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;

import fcmpush.config.FireBaseConfig;
import fcmpush.enumeration.FireBaseEnum;
import fcmpush.repository.FireBaseRepository;

public class PushGroupService {
	private static final Logger logger = LogManager.getLogger();
	
	private FireBaseRepository repository;
	private static final int GROUP_SIZE = FireBaseEnum.GROUP_SIZE.getSize();
	private FirebaseMessaging fireBaseMessaing;
	
	public PushGroupService() {
		repository = new FireBaseRepository();
		fireBaseMessaing = FirebaseMessaging.getInstance(FireBaseConfig.getInstanceFireBaseApp());
	}
	
	// todo 트랜잭션 처리 해야함
	public void makeReceiveUserGroup() throws IOException, FirebaseMessagingException {
	
		deleteAllPushGroups();
		
		// 매일 오전 03:59:59 까지 가입한 유저 토큰
		List<String> tokens = repository.selectUsersTokenBefore4AM();
		
		//주제 구독 취소
		unSubScribeFcmTopic(tokens);
		
		int groupSeq = 1;	
    	for(int i = 0; i < tokens.size(); i += GROUP_SIZE) {
    		List<String> regTokens = splitTokenListByGroupSize(i, tokens);
    		
			TopicManagementResponse response = fireBaseMessaing.subscribeToTopic(regTokens, FireBaseEnum.GROUP_NAME.getValue() + groupSeq);
    		logger.info(groupSeq+"번쨰 그룹등록");
    		logger.info("등록 성공 카운트:"+response.getSuccessCount());
    		logger.info("등록 실패 카운트:"+response.getFailureCount());
    		
    		HashMap<String, Object> map = new HashMap<String, Object>();
    		map.put("GROUP_SEQ", groupSeq);
    		map.put("GROUP_ID", FireBaseEnum.GROUP_NAME.getValue() + groupSeq);
    		map.put("REG_SUCCESS_CNT", response.getSuccessCount());
    		map.put("REG_FAIL_CNT", response.getFailureCount());
    		
    		insertPushGroup(map);
    		
    		groupSeq += 1;
    	}
    }
	//오늘 오전 가입한 사람들 그룹핑
	public void createReceiveUserGroupJoinedToday(List<String> tokens) throws IOException, FirebaseMessagingException {
		
		int groupSeq = repository.selectMaxGroupSEQ() + 1;
				
    	for(int i = 0; i < tokens.size(); i += GROUP_SIZE) {
    		List<String> regTokens = splitTokenListByGroupSize(i, tokens);
    		    	
			TopicManagementResponse response = fireBaseMessaing.subscribeToTopic(regTokens, FireBaseEnum.GROUP_NAME.getValue() + groupSeq);
    		logger.info(groupSeq+"번쨰 그룹등록");
    		logger.info("등록 성공 카운트:"+response.getSuccessCount());
    		logger.info("등록 실패 카운트:"+response.getFailureCount());
    		
    		HashMap<String, Object> map = new HashMap<String, Object>();
    		map.put("GROUP_SEQ", groupSeq);
    		map.put("GROUP_ID", FireBaseEnum.GROUP_NAME.getValue() + groupSeq);
    		map.put("REG_SUCCESS_CNT", response.getSuccessCount());
    		map.put("REG_FAIL_CNT", response.getFailureCount());
    		
    		insertPushGroup(map);
    		groupSeq += 1;
    		
    	}   	
    }
	
	private List<String> splitTokenListByGroupSize(int index, List<String> tokens) {
		if(index + GROUP_SIZE > tokens.size()) {
			tokens = tokens.subList(index, tokens.size());
		} else {
			tokens = tokens.subList(index, index + GROUP_SIZE);
		}	
		return tokens;
	}

	private void unSubScribeFcmTopic(List<String> tokens) throws FirebaseMessagingException { 	
    	int groupSeq = 1;
    	for(int i = 0; i < tokens.size(); i += GROUP_SIZE) {
    		logger.info(groupSeq+"번쨰 그룹 삭제");
    		
    		List<String> deleteTokens = splitTokenListByGroupSize(i, tokens); 		    	
    		TopicManagementResponse response = fireBaseMessaing.unsubscribeFromTopic(deleteTokens, FireBaseEnum.GROUP_NAME.getValue() + groupSeq);
    		logger.info(groupSeq+"번쨰 그룹구독 취소");
    		logger.info("구독 취소성공 카운트:"+response.getSuccessCount());
    		logger.info("구독 취소실패 카운트:"+response.getFailureCount());
    		
    		groupSeq++;
    	}   
	}
    	
	private void deleteAllPushGroups() { 	
		repository.deleteAllPushGroups();				
	}
		
	private void insertPushGroup(HashMap<String, Object> param) { 
		 repository.insertPushGroup(param);		
	}
}

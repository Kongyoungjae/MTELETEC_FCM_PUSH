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

import fcmpush.config.DataBaseConfig;
import fcmpush.config.FireBaseConfig;
import fcmpush.enumeration.FireBaseEnum;
import fcmpush.enumeration.IntervalEnum;
import fcmpush.repository.FireBaseRepository;

public class PushGroupService {
	private static final Logger logger = LogManager.getLogger();
	private static final int GROUP_SIZE = FireBaseEnum.GROUP_SIZE.getSize();
	
	private FireBaseRepository repository;
	private FirebaseMessaging fireBaseMessaging;
	
	public PushGroupService() {	
		repository = new FireBaseRepository();	
		fireBaseMessaging = FirebaseMessaging.getInstance(FireBaseConfig.getInstanceFireBaseApp());
	}
	
	public void groupProcess() throws IOException, FirebaseMessagingException {		
	   unSubScribeFcmGroup();	 
	   deleteAllPushGroups();			
	   createReceiveGroupJoinedBefore4amToday();
    }
	public void createReceiveGroupJoinedAfter4amToday(List<String> tokensTodayAfter4am) throws IOException, FirebaseMessagingException {	
		logger.info("오늘 04:00 이후 가입한 사람들 그룹핑");
		
		if(tokensTodayAfter4am.size() !=0 ) {
			int groupNo = repository.selectMaxGroupNo() + 1;
			for(int i = 0; i < tokensTodayAfter4am.size(); i += GROUP_SIZE) {
	    		List<String> regTokens = splitTokenListByGroupSize(i, tokensTodayAfter4am);
				TopicManagementResponse response = fireBaseMessaging.subscribeToTopic(regTokens, FireBaseEnum.GROUP_NAME.getValue() + groupNo);
	    		
				logger.info(groupNo+"번쨰 그룹등록");
	    		logger.info("등록 성공 카운트:"+response.getSuccessCount());
	    		logger.info("등록 실패 카운트:"+response.getFailureCount());
	    		
	    		HashMap<String, Object> map = new HashMap<String, Object>();
	    		map.put("GROUP_NO", groupNo);
	    		map.put("GROUP_ID", FireBaseEnum.GROUP_NAME.getValue() + groupNo);
	    		map.put("REG_SUCCESS_CNT", response.getSuccessCount());
	    		map.put("REG_FAIL_CNT", response.getFailureCount());
	    		insertPushGroup(map);
	    		groupNo += 1;
	    	}
		}
	}
	
	private void createReceiveGroupJoinedBefore4amToday () throws FirebaseMessagingException { 
		logger.info("@@@@createReceiveGroupJoinedBefore4amToday Start@@@");
		List<String> allToken= repository.selectUserTokenByFcmGroupId(null); //모든토큰이 담김
		
		int groupNo = 1;
		for(int i = 0; i < allToken.size(); i += GROUP_SIZE) {
			String topicName = FireBaseEnum.GROUP_NAME.getValue()+groupNo;
			logger.info("topicName:"+topicName);
			List<String> regToken = splitTokenListByGroupSize(i, allToken);
			
			TopicManagementResponse response = fireBaseMessaging.subscribeToTopic(regToken, topicName);		
			
			logger.info(groupNo+"번쨰그룹 등록");
			logger.info("등록 성공 카운트:"+response.getSuccessCount());
			logger.info("등록 실패 카운트:"+response.getFailureCount());
    		
    		HashMap<String, Object> map = new HashMap<String, Object>();
    		map.put("GROUP_NO", groupNo);
    		map.put("GROUP_ID", topicName);
    		map.put("REG_SUCCESS_CNT", response.getSuccessCount());
    		map.put("REG_FAIL_CNT", response.getFailureCount());
    		map.put("REG_DT", "03:59:59");
    		
    		insertPushGroup(map);   
    		updateUserFcmGroup(regToken, topicName);
    		
    		groupNo++;
    	}
		logger.info("@@@@createReceiveGroupJoinedBefore4amToday End@@@");
	}

	// 전체 그룹에 대한 구독 취소
	private void unSubScribeFcmGroup() throws FirebaseMessagingException {
		logger.info("@@@@unSubScribeFcmGroup Start@@@");
		
		List<String> groupIdList = repository.selectUserGroupIdList();
		
		for (int i = 0; i < groupIdList.size(); i++) {
			String fcm_group_id = groupIdList.get(i).toString();
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("FCM_GROUP_ID", fcm_group_id);

			List<String> tokenList = repository.selectUserTokenByFcmGroupId(map);
			fireBaseMessaging.unsubscribeFromTopic(tokenList, fcm_group_id);
		}
		
		logger.info("@@@@unSubScribeFcmGroup End@@@");
	}
	
	private List<String> splitTokenListByGroupSize(int index, List<String> tokens) {
		if(index + GROUP_SIZE > tokens.size()) {
			tokens = tokens.subList(index, tokens.size());
		} else {
			tokens = tokens.subList(index, index + GROUP_SIZE);
		}	
		return tokens;
	}
	
	
	private void updateUserFcmGroup(List<String> regToken, String topicName) {
		HashMap<String, Object> pamMap = new HashMap<String, Object>();
		pamMap.put("FCM_TOKEN", regToken);
		pamMap.put("FCM_GROUP_ID", topicName);
				
		repository.updateUserFcmGroup(pamMap);
	}

	private void deleteAllPushGroups() {
		repository.deleteAllPushGroups();				
	}
	
	private void insertPushGroup(HashMap<String, Object> param) {
		 repository.insertPushGroup(param);		
	}
}

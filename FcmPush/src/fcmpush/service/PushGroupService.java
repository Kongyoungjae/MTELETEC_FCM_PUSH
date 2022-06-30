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
	private static final int GROUP_SIZE = FireBaseEnum.GROUP_SIZE.getSize();
	
	private FireBaseRepository repository;
	private FirebaseMessaging fireBaseMessaing;
	
	public PushGroupService() {
		repository = new FireBaseRepository();
		fireBaseMessaing = FirebaseMessaging.getInstance(FireBaseConfig.getInstanceFireBaseApp());
	}
	
	// todo 트랜잭션 처리 해야함
	public void groupProcess() throws IOException, FirebaseMessagingException {

		// 매일 오전 03:59:59 까지 가입한 유저 토큰
		List<String> allTokensBefore4am = repository.selectUsersAllTokenBefore4AM();
		List<String> tokensTodayAfter4am = repository.selectTodayUsersTokenAfter4AM();
		
		//주제 구독 취소
		unSubScribeFcmTopicByAllToken(allTokensBefore4am);
		unSubScribeFcmTopicByTodayAfter4AM(tokensTodayAfter4am);
	
		// DB 모든 그룹정보 삭제
		deleteAllPushGroups();
		
		//FCM 푸쉬 그룹 생성
		createReceiveGroupJoinedBefore4amToday(allTokensBefore4am);
    }
	
	private void createReceiveGroupJoinedBefore4amToday (List<String> allTokensBefore4am) throws FirebaseMessagingException { 
		logger.info("오늘 04:00 이전에 가입한 유저들 그룹핑");	
		int groupSeq = 1;
		for(int i = 0; i < allTokensBefore4am.size(); i += GROUP_SIZE) {
    		List<String> regTokens = splitTokenListByGroupSize(i, allTokensBefore4am);
			TopicManagementResponse response = fireBaseMessaing.subscribeToTopic(regTokens, FireBaseEnum.GROUP_NAME.getValue() + groupSeq);
    		
			logger.info(groupSeq+"번쨰 그룹등록");
    		logger.info("등록 성공 카운트:"+response.getSuccessCount());
    		logger.info("등록 실패 카운트:"+response.getFailureCount());
    		
    		HashMap<String, Object> map = new HashMap<String, Object>();
    		map.put("GROUP_SEQ", groupSeq);
    		map.put("GROUP_ID", FireBaseEnum.GROUP_NAME.getValue() + groupSeq);
    		map.put("REG_SUCCESS_CNT", response.getSuccessCount());
    		map.put("REG_FAIL_CNT", response.getFailureCount());
    		map.put("REG_DT", "03:59:59");
    		insertPushGroup(map);
    		
    		groupSeq += 1;
    	}
	}
	
	public void createReceiveGroupJoinedAfter4amToday(List<String> tokensTodayAfter4am) throws IOException, FirebaseMessagingException {	
		logger.info("오늘 04:00 이후 가입한 사람들 그룹핑");
		if(tokensTodayAfter4am.size()!=0) {
			int groupSeq = repository.selectMaxGroupSEQ() + 1;
			
			for(int i = 0; i < tokensTodayAfter4am.size(); i += GROUP_SIZE) {
	    		List<String> regTokens = splitTokenListByGroupSize(i, tokensTodayAfter4am);
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
    }

	// 전체 그룹에 대한 구독 취소
	private void unSubScribeFcmTopicByAllToken(List<String> allTokensBefore4am) throws FirebaseMessagingException {
		logger.info("매일 오전 4시 이전 가입자들 그룹에 대한 구독취소");
		
		int groupSeq = 1;   	
    	for(int i = 0; i < allTokensBefore4am.size(); i += GROUP_SIZE) {		
    		List<String> unSubscribeTokens = splitTokenListByGroupSize(i, allTokensBefore4am); 		    	
    		TopicManagementResponse response = fireBaseMessaing.unsubscribeFromTopic(unSubscribeTokens, FireBaseEnum.GROUP_NAME.getValue() + groupSeq);
    		logger.info(groupSeq+"번쨰 그룹구독 취소");
    		logger.info("구독 취소성공 카운트:"+response.getSuccessCount());
    		logger.info("구독 취소실패 카운트:"+response.getFailureCount());
    		groupSeq++;
    	}   	
	}
    //매일 4시이후 가입자들 그룹에 대한 구독취소
	//4시 이후 가입자들은 그룹핑이 완벽하게 되어있지않음 (A-10명, B-500명)
	private void unSubScribeFcmTopicByTodayAfter4AM(List<String> tokensAfter4am) throws FirebaseMessagingException { 	
    	logger.info("매일 오전4시이후 가입자들 그룹에 대한 구독취소");    	
    	logger.info(tokensAfter4am.size());
    	
		int minGroupSeqTodayAfter4am = repository.selectTodayMinGroupSeqAfter4am();
    	int maxGroupSeq = repository.selectMaxGroupSEQ();
    	int count = 0;
		for(int i = 0; i < tokensAfter4am.size(); i += GROUP_SIZE) {
    		List<String> unbScribeTokens = splitTokenListByGroupSize(i, tokensAfter4am);  		
    		for(int j = minGroupSeqTodayAfter4am; j <= maxGroupSeq; j++) {
    			TopicManagementResponse response = fireBaseMessaing.unsubscribeFromTopic(unbScribeTokens, FireBaseEnum.GROUP_NAME.getValue() + j);
        		logger.info(j+"번쨰 그룹구독 취소");
        		logger.info("구독 취소성공 카운트:"+response.getSuccessCount());
        		logger.info("구독 취소실패 카운트:"+response.getFailureCount());
        		count ++;
    		}
		logger.info("루프돈횟수!!:"+count);
    	}
	}
	//오늘 4시 이후 푸쉬 그룹이 생성된 적이 있으면
//	public boolean isTodayCreatedPushGroupAfter4AM() {		
//	   int count = repository.selectTodayCreatedPushGroupCountAfter4AM(); 
//	   if(count != 0) {		 
//		   return true;
//	   }
//	   return false;
//	}
    
	private List<String> splitTokenListByGroupSize(int index, List<String> tokens) {
		if(index + GROUP_SIZE > tokens.size()) {
			tokens = tokens.subList(index, tokens.size());
		} else {
			tokens = tokens.subList(index, index + GROUP_SIZE);
		}	
		return tokens;
	}

	private void deleteAllPushGroups() { 	
		repository.deleteAllPushGroups();				
	}
		
	private void insertPushGroup(HashMap<String, Object> param) { 
		 repository.insertPushGroup(param);		
	}
}

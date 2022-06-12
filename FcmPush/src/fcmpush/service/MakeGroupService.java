package fcmpush.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.FirebaseException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;

import fcmpush.config.DataBaseConfig;
import fcmpush.config.FireBaseConfig;
import fcmpush.enumeration.FireBasePushEnum;
import fcmpush.exception.ExceptionHandler;
import fcmpush.repository.FireBaseRepository;

public class MakeGroupService {
	private static final Logger logger = LogManager.getLogger();

	private FireBaseConfig fireBaseConfig;
	private FireBaseRepository repository;
	private static FirebaseMessaging fireMessaing;
	private int groupSize = FireBasePushEnum.GROUP_SIZE.getSize();

	public MakeGroupService() {
		fireBaseConfig = new FireBaseConfig();
		repository = new FireBaseRepository();
		init();
	}
	
	private void init() {
		
		if(fireMessaing ==null) {
			try {
				fireMessaing = FirebaseMessaging.getInstance(fireBaseConfig.getFireBaseApp());
			} catch (IOException e) {
				logger.error("ADC 인증 오류");
			}
		}
	}
	
	public void makeReceiveUserGroup() throws IOException, FirebaseMessagingException {
    	
		// todo push_group 삭제
		
		// List<String> tokens = repository.selectUsersToken();
		List<String> tokens = new LinkedList<String>();
		
		for(int i=0; i < 10000; i++) {
			 tokens.add("f_OoEVscSkCshbtP5cOFhh:APA91bH04xQltAKlGxcWcoQ_StyUkwXxbwenb4-fRViof424Vm5X5VXKGer7gGgxsbXRslkSZzC_hdPyoTYQu92SwtdKJ_Wknn1UgnjJhM0YxTlTp5q4VR3iMLUmwAuLrldHUc5xhU9a");
			 tokens.add("dmDjI3yUS929jlJz1r3egf:APA91bFPsDWCS2UAAHGjynz8wpk3gii2ejWoSUs6iY19TnH818DpKrQpf3PJldWwc3D9ZrrpVt4C2t2K2R9ulDauwteSaMjzyMBZY3xtYudTAKSFM8-cPR8gOCwaSGfx4bOn4ldwrArj2");
			 tokens.add("d4ezIS3MQ2-ah0A2axdLa0:APA91bGHsL5LuPexm-OHsWG9UT1mQfm1i6cdGVArns0R-NFxCLRL4oqholKP6Jtu_BRvbVX4oYKNrDP6_n9JNv9LmQhN2DkWPXUzM2jTF6ACymFp3Nd8Fh9DvOuXIp1q1TgQY69cLXbI3");
			 
		}
		long currentTime = System.currentTimeMillis();

		int groupSeq = 1;
    	
    	for(int i = 0; i < 10000; i += groupSize) {
    		logger.info(groupSeq + "번째 그룹 생성");
    		
    		List<String> newTokens = new LinkedList<String>();

    		//마지막 그룹 만들때
    		if(i + groupSize > tokens.size()) {
    			newTokens = tokens.subList(i, tokens.size());
    		} 
    		
    		else {
        		newTokens = tokens.subList(i, i+groupSize);
    		}
    		    	
			TopicManagementResponse response = fireMessaing.subscribeToTopic(newTokens, FireBasePushEnum.GROUP_NAME.getValue() + groupSeq);
    		
			logger.info("토큰 등록 성공수:"+ response.getSuccessCount());
    		logger.info("토큰 등록 실패수:"+ response.getFailureCount());
    		
    		groupSeq += 1;logger.info(response.getErrors().toString() + " tokens were subscribed Fail");  
    		
    		//todo 
    		//tb_push_group , tb_push_group_hist insert 
    		
    	}
    	
		long afterTime = System.currentTimeMillis();
		logger.info((afterTime - currentTime) / 1000);
    }
}

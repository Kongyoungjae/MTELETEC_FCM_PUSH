package fcmpush.service.target;

import java.io.IOException;
import java.util.HashMap;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import fcmpush.enumeration.FireBaseEnum;

public class GroupTarget extends PushTarget {

	@Override
	public void push(HashMap<String, Object> pushInfo) throws FirebaseMessagingException {
		
		String title = pushInfo.get("TITLE").toString();
		String body = pushInfo.get("BODY").toString();
		String img =  pushInfo.get("IMG").toString();
		int groupseq = repository.selectMaxGroupSEQ();
		
		logger.info("그룹푸쉬 그룹숫자:"+groupseq);
		logger.info("푸쉬정보:" + pushInfo.toString());
		
		for(int i=0; i < groupseq; i++) {
			Message message = Message.builder()
					.setNotification(Notification.builder()
							.setTitle(title)
							.setBody(body)
							.setImage(img)
							.build())
					.setTopic(FireBaseEnum.GROUP_NAME.getValue()+i)
					.build();
			fireBaseMessaging.send(message);
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("PUSH_ID", pushInfo.get("PUSH_ID"));
		paramMap.put("REL_DT", pushInfo.get("REL_DT"));
		paramMap.put("SEND_TARGET", pushInfo.get("SEND_TARGET"));
		
		repository.insertPushHist(paramMap);	
		
		logger.info("그룹발송완료!");

	}
}

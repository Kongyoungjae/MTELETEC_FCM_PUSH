package fcmpush.service.target;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;

import fcmpush.config.FireBaseConfig;
import fcmpush.repository.FireBaseRepository;

public abstract class PushTarget {
	protected static final Logger logger = LogManager.getLogger();
	
	protected FireBaseRepository repository;
	protected FirebaseMessaging fireBaseMessaging;
	
	public PushTarget() {		
		fireBaseMessaging = FirebaseMessaging.getInstance(FireBaseConfig.getInstanceFireBaseApp());
		repository = new FireBaseRepository();		
	}
	
	public abstract void push(HashMap<String, Object> pushInfo) throws FirebaseMessagingException;
	
}

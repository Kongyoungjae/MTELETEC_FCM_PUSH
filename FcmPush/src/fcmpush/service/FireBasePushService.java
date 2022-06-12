package fcmpush.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.firebase.messaging.FirebaseMessaging;

import fcmpush.config.DataBaseConfig;
import fcmpush.config.FireBaseConfig;
import fcmpush.repository.FireBaseRepository;
import fcmpush.util.DateUtil;
import fcmpush.util.StackTraceLogUtil;


public class FireBasePushService {
	private static final Logger logger = LogManager.getLogger();

	private FireBaseConfig fireBaseConfig;
	private FireBaseRepository repository;
	
	public FireBasePushService() {
		fireBaseConfig = new FireBaseConfig();
		repository = new FireBaseRepository();	
	}

	public void push() {

		logger.info("push");
		logger.info(repository.selectPushInfoByDateTime());
	}
	
	public boolean checkPushTime() {	
		return true;	
	}
	
}
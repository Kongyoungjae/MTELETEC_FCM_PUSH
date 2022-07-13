package fcmpush.config;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import fcmpush.enumeration.FireBaseEnum;

public class FireBaseConfig  {
	private static final Logger logger = LogManager.getLogger();
	private static FirebaseApp fireBaseApp = null;
	private static FirebaseMessaging firebaseMessaging = null;
	
	public static void init() throws IOException { 
		logger.info("FireBaseConfig Init");
		
		if(fireBaseApp == null) 
			initFireBaseApp();
		
		if(firebaseMessaging == null)  
			firebaseMessaging = FirebaseMessaging.getInstance(fireBaseApp);
	}
	
	private static void initFireBaseApp() throws IOException {
		InputStream resource = Resources.getResourceAsStream(FireBaseEnum.KEY_PATH.getValue());
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(resource))
				.build();
		fireBaseApp = FirebaseApp.initializeApp(options); 

	}
	
	public static synchronized FirebaseApp getInstanceFireBaseApp(){
		return fireBaseApp; 
	}
	
	public static synchronized FirebaseMessaging getInstanceFireBaseMessaging(){
		return firebaseMessaging; 
	}
}
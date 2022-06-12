package fcmpush.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.IOUtils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import fcmpush.enumeration.FireBasePushEnum;


public class FireBaseConfig  {
	private final Logger logger = LogManager.getLogger();
	private static FirebaseApp fireBaseApp;
	
	//ADC 인증
	public FirebaseApp getFireBaseApp() throws IOException  {
		
		if(fireBaseApp == null) {
			logger.info("FirebaseApp 초기화");
			InputStream resource = this.getClass().getClassLoader().getResourceAsStream(FireBasePushEnum.KEY_PATH.getValue());
			
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(resource))
					.build();
			fireBaseApp = FirebaseApp.initializeApp(options); 
		} 
		 return fireBaseApp; 
	}
	
	// 사용자 인증 정보를 사용하여 액세스 토큰 발급
	public String getAccessToken() throws IOException {

		InputStream resource = this.getClass().getClassLoader().getResourceAsStream(FireBasePushEnum.KEY_PATH.getValue());
		
		GoogleCredentials googleCredentials = GoogleCredentials
				.fromStream(resource)
				.createScoped(FireBasePushEnum.SEND_URL.getValue());
		googleCredentials.refreshIfExpired();
		
		return googleCredentials.getAccessToken().getTokenValue();

	}
}
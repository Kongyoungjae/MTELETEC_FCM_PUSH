package fcmpush.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.TopicManagementResponse;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.HttpHeaders;

public class FireBaseTest  {
	private final Logger logger = LogManager.getLogger();
	public void runPushMessage() throws IOException {
		
		logger.info("LOG warn");
		logger.warn("LOG warn");
		logger.debug("LOG debug");
		logger.error("LOG error");
		logger.fatal("LOG fatal");
		
		


	}
	
	public void test() throws IOException {
		logger.info("LOG info");
	}
	
	public void test2() throws IOException {
		logger.info("LOG info");
	}
	
	
	//ADC 인증
//	private FirebaseApp initFireBaseApp() throws IOException {
//		final String KEY_PATH = "fireBase/serviceAccountKey.json";		
//		ClassPathResource resource = new ClassPathResource(KEY_PATH);
//		
//		FirebaseOptions options = FirebaseOptions.builder()
//				.setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
//				.build();
//		
//		return FirebaseApp.initializeApp(options);
//
//	}
//	
//	// 사용자 인증 정보를 사용하여 액세스 토큰 발급
//	private String getAccessToken() throws IOException {
//		final String KEY_PATH = "fireBase/serviceAccountKey.json";
//		final String SEND_URL = "https://www.googleapis.com/auth/cloud-platform";
//		
//		ClassPathResource resource = new ClassPathResource(KEY_PATH);
//		
//		
//		GoogleCredentials googleCredentials = GoogleCredentials
//				.fromStream(resource.getInputStream())
//				.createScoped(SEND_URL);
//		googleCredentials.refreshIfExpired();
//		
//		return googleCredentials.getAccessToken().getTokenValue();
//
//	}
//    
//	/* 전송 로직 */
//	private void sendMessageTo(String targetToken, String title, String body) throws IOException {
//    	final String API_URL = "https://fcm.googleapis.com/v1/projects/mteletec-test/messages:send";
//    	
//        String message = makeSinglePushMessage(targetToken, title, body);
//        
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), message);
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .post(requestBody)
//                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
//                .build();
//        
//        Response response = client.newCall(request).execute();
//        
//        System.out.println("결과::"+response.body().string());
//   }
//    
//    /* 주제발송(1000명) 테스트 */
//    private String sendMultiUserMessage (String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {
//    	
//        TopicPushFormat fcmMessage = TopicPushFormat.builder()
//        		.message(TopicPushFormat.Message.builder()
//        					.topic("allUser")
//        					.notification(TopicPushFormat.Notification.builder()
//    		        			  .title("하나한텐 안가야함GGGGG")
//    		        			  .body("가지마GG!")
//    							  .image("https://t1.daumcdn.net/cfile/tistory/18014C4D516D5C5A26")
//    		        			  .build()
//        					).data(TopicPushFormat.Data.builder()
//        						  .title("DATA TITLE")
//        						  .body("DATA BODY")
//        						  .build()
//        					).build()
//        				).build();
//        		
//        
//        ObjectMapper objectMapper = new ObjectMapper();  
//        System.out.println(objectMapper.writeValueAsString(fcmMessage));
//        return objectMapper.writeValueAsString(fcmMessage);
//    }
//    
//     /* 단일 발송 테스트 */  
//	  private String makeSinglePushMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {
//		  
//		  SinglePushFormat fcmMessage = SinglePushFormat.builder()
//		  		.message(SinglePushFormat.Message.builder()
//		  				.notification(SinglePushFormat.Notification.builder()
//		  							  .title("단일전송")
//		  							  .body("단일전송")
//		          					  .image("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F96SGZ%2Fbtq0PctNnW3%2FEsa3EpBe3pbX8vAjKW572K%2Fimg.png")
//		  						 	  .build()
//		  							 )
//		  				.data(SinglePushFormat.Data.builder()
//		  						.title("BABABA")
//		  						.body("BAOAOA")
//		  						.build()
//		  					  )
//		  				.token(targetToken)
//		  				.build()
//		  				).build();
//		  		
//		
//		  ObjectMapper objectMapper = new ObjectMapper();  
//		  System.out.println(objectMapper.writeValueAsString(fcmMessage));
//		  return objectMapper.writeValueAsString(fcmMessage);
//	}
//	
//	
//    public void makeReceiveUserGroup() throws IOException {
//    	final List<String> tokens = new ArrayList<String>();
//    	FirebaseMessaging gg =FirebaseMessaging.getInstance(initFireBaseApp());
//    	
//    	tokens.add("f_OoEVscSkCshbtP5cOFhh:APA91bH04xQltAKlGxcWcoQ_StyUkwXxbwenb4-fRViof424Vm5X5VXKGer7gGgxsbXRslkSZzC_hdPyoTYQu92SwtdKJ_Wknn1UgnjJhM0YxTlTp5q4VR3iMLUmwAuLrldHUc5xhU9a");
//    	tokens.add("d4ezIS3MQ2-ah0A2axdLa0:APA91bGHsL5LuPexm-OHsWG9UT1mQfm1i6cdGVArns0R-NFxCLRL4oqholKP6Jtu_BRvbVX4oYKNrDP6_n9JNv9LmQhN2DkWPXUzM2jTF6ACymFp3Nd8Fh9DvOuXIp1q1TgQY69cLXbI");
//    	tokens.add("dmDjI3yUS929jlJz1r3egf:APA91bFPsDWCS2UAAHGjynz8wpk3gii2ejWoSUs6iY19TnH818DpKrQpf3PJldWwc3D9ZrrpVt4C2t2K2R9ulDauwteSaMjzyMBZY3xtYudTAKSFM8-cPR8gOCwaSGfx4bOn4ldwrArj");
//    	   
//    	try {
//    		
//			TopicManagementResponse response = gg.subscribeToTopic(tokens, "allUser");
//			System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
//			System.out.println(response.getErrors().toString() + " tokens were subscribed Fail");
//		
//    	} catch (FirebaseMessagingException e) {
//
//			e.printStackTrace();
//		}
//    }
    
}

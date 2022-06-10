package fcmpush.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TopicPushFormat {	
	private  Message message;
	
    @Getter
    @Builder
    public static class Message {
    	private String topic;
    	private Notification notification;
        private Data data;

    }
    
    @Getter
    @Builder
    public static class Notification {
        private String title;
    	private String body;
    	private String image;
    }
     
    @Getter
    @Builder
    public static class Data {
        private String title;
    	private String body;
    	private String image;
    	
    }

}

package fcmpush.data;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class SinglePushFormat {
	private  Message message;
	
    @Getter
    @Builder
    public static class Message {
    	private String token;
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

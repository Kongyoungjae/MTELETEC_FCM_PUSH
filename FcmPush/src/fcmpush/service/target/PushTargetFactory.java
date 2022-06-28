package fcmpush.service.target;

import java.io.IOException;
import java.util.HashMap;

public class PushTargetFactory {
			
	public static PushTarget createPushTarget(HashMap<String, Object> pushInfo) throws IOException {
		String sendTarget = pushInfo.get("SEND_TARGET").toString();
		
		switch (sendTarget) {
		case "all":
			return new GroupTarget();
		default:
			return new SingleTarget();
		}	
	}
}

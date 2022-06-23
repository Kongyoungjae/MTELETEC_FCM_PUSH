package fcmpush.target;

import java.util.HashMap;
import java.util.List;

public class PushTargetFactory {

	public PushTarget createPushTarget(List<HashMap<String, Object>> pushList) {
	
		for(HashMap<String, Object> pushInfo: pushList) {
			String sendTarget = pushInfo.get("SEND_TARGET").toString();
			
			switch (sendTarget) {
			case "all":
				return new GroupTarget();
			default:
				return new SingleTarget();
			}
		}
		return null;
	}
}

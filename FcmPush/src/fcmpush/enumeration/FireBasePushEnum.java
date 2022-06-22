package fcmpush.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum FireBasePushEnum {

	 KEY_PATH("fireBase/serviceAccountKey.json")
	,SEND_URL("https://www.googleapis.com/auth/cloud-platform")
	,GROUP_NAME("UserGroup")
	,GROUP_SIZE(1000);
	
	
	private String value;
	private int size;

	FireBasePushEnum(String value) {
		this.value = value;
	}
	
	FireBasePushEnum(int size) {
		this.size = size;
	}
}

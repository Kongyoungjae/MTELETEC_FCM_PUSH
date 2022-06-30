package fcmpush.enumeration;

import lombok.Getter;

@Getter
public enum FireBaseEnum {

	 KEY_PATH("fireBase/serviceAccountKey.json")
	,SEND_URL("https://www.googleapis.com/auth/cloud-platform")
	,GROUP_NAME("UserGroup")
	,GROUP_SIZE(1000);

	private String value;
	private int size;

	FireBaseEnum(String value) {
		this.value = value;
	}
	
	FireBaseEnum(int size) {
		this.size = size;
	}
}

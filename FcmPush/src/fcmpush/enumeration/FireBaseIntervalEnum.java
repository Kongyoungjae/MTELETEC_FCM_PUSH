package fcmpush.enumeration;

import lombok.Getter;

@Getter
public enum FireBaseIntervalEnum {
	
	 All_GROUP_CREATE_INTERVAL(1000 * 60 * 60 * 24) //하루마다
	,All_GROUP_START_HOUR(4)
	,All_GROUP_START_MINUTE(0)
	
	
	,CREATE_GROUP_INTERVAL(1000 * 10);

	private int interval = 0;

	FireBaseIntervalEnum(int interval) {
		this.interval = interval;
	}
}

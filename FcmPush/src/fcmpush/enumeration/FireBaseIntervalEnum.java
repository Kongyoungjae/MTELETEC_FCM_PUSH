package fcmpush.enumeration;

import lombok.Getter;

@Getter
public enum FireBaseIntervalEnum {
	
	 MAKE_All_GROUP_CREATE_INTERVAL(1000 * 60 * 60 * 24) //하루마다
	,MAKE_All_GROUP_START_HOUR(4)
	,MAKE_All_GROUP_START_MINUTE(0)
	
	
	,MAKE_GROUP_INTERVAL(1000 * 10)
	,CHECK_PUSH_TIME_INTERVAL(1000*60); 

	private int interval = 0;

	FireBaseIntervalEnum(int interval) {
		this.interval = interval;
	}
}

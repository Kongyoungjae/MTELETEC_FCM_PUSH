package fcmpush.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FireBaseIntervalEnum {
	
	 All_GROUP_CREATE_INTERVAL(1000 * 60 * 60 * 24) //하루마다
	,All_GROUP_CREATE_START_HOUR(4)
	,All_GROUP_CREATE_START_MINUTE(0);

	private int interval = 0;
}

package fcmpush.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntervalEnum {
	
	 All_GROUP_CREATE_INTERVAL(1000 * 60 * 60 * 24) //하루마다
	,PUSH_CHECK_INTERVAL(1000 * 60); // 1분
	
	

	private int interval = 0;
}

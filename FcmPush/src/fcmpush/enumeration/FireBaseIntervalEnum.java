package fcmpush.enumeration;

public enum FireBaseIntervalEnum {
	
//	MAKE_GROUP_PERIOD(1000 * 60 * 3); //3분마다
	MAKE_GROUP_INTERVAL(1000 * 10), 
	CHECK_PUSH_TIME_INTERVAL (1000);

	private int interval = 0;

	FireBaseIntervalEnum(int interval) {
		this.interval = interval;
	}
	
	public int getInterval() {
		return this.interval;
	}
}

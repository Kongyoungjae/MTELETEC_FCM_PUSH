package fcmpush.target;

public class SingleTarget extends PushTarget {

	@Override
	void targetPush() {
		logger.info("targetPush 입니다");
	}
}

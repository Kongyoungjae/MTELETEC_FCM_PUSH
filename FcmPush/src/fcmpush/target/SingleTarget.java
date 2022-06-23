package fcmpush.target;

public class SingleTarget extends PushTarget {

	@Override
	public void targetPush() {
		logger.info("targetPush 입니다");
	}
}

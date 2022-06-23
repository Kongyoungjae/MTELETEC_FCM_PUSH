package fcmpush.target;

public class GroupTarget extends PushTarget {

	@Override
	void targetPush() {
		logger.info("GroupTarget 입니다");
	}
}

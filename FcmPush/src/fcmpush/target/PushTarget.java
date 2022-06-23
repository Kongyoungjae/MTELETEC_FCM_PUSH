package fcmpush.target;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PushTarget {
	protected static final Logger logger = LogManager.getLogger();
	
	public abstract void targetPush();
	
}

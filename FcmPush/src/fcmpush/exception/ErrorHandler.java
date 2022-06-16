package fcmpush.exception;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fcmpush.main.Main;

public class ErrorHandler extends Error{
	private static final long serialVersionUID = -5152992671302980747L;
	private static final Logger logger = LogManager.getLogger();

	public ErrorHandler(Error e) {
		logger.fatal("@@@에러핸들러@@@");
		e.printStackTrace();
		Main.run();	
	}

}

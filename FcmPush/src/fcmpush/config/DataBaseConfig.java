package fcmpush.config;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fcmpush.util.StackTraceLogUtil;


public class DataBaseConfig {
	private static final Logger logger = LogManager.getLogger();
	
	private static SqlSessionFactory adminFactory;
	private static SqlSessionFactory serviceFactory;
	
	static {
		if(sqlFactoryNull()) {
			logger.info("databaseConfig Init");
			init();
		}			
	}
	
	public static void init() {
		
		try {
			String resource = "mybatis-config.xml";
			InputStream adminInputStream = Resources.getResourceAsStream(resource);
			InputStream serviceInputStream = Resources.getResourceAsStream(resource);

			adminFactory   = new SqlSessionFactoryBuilder().build(adminInputStream, "admin");
			serviceFactory = new SqlSessionFactoryBuilder().build(serviceInputStream,"service");
			
		
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao()"+StackTraceLogUtil.getStackTraceString(e));
		}
	}
	
	public SqlSession getAdminSession() {
		SqlSession adminSession = null;

		try {
			
			adminSession = adminFactory.openSession();
			
		} catch( Exception e ) {
			logger.error("exception/ SMSdao::getAdminSession()"+StackTraceLogUtil.getStackTraceString(e));

		}
		return adminSession;
	}
	
	public SqlSession getServiceSession() {
		SqlSession serviceSession = null;

		try {			
			serviceSession = serviceFactory.openSession();
		} catch( Exception e ) {
			logger.error("exception/ SMSdao::getServiceSession()"+StackTraceLogUtil.getStackTraceString(e));
		}
		
		return serviceSession;
	}
	
	private static boolean sqlFactoryNull() {
		
		if(serviceFactory == null || adminFactory == null ) {
			return true;
		} 
		return false;
	}
}
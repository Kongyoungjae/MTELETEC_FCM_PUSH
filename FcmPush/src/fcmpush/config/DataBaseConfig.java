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

	public DataBaseConfig() {		
		if(!notSqlFactoryNull()) {
			initSqlFactory();
		}		
	}
	
	private void initSqlFactory() {
		
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
			if(notSqlFactoryNull()) {
				adminSession = adminFactory.openSession();
			} else {
				return null;
			}
		} catch( Exception e ) {
			logger.error("exception/ SMSdao::getAdminSession()"+StackTraceLogUtil.getStackTraceString(e));

		}
		return adminSession;
	}
	
	public SqlSession getServiceSession() {
		SqlSession serviceSession = null;

		try {
			if(notSqlFactoryNull()) {
				serviceSession = serviceFactory.openSession();
			} else {
				return null;
			}
		} catch( Exception e ) {
			logger.error("exception/ SMSdao::getServiceSession()"+StackTraceLogUtil.getStackTraceString(e));
		}
		
		return serviceSession;
	}
	
	private boolean notSqlFactoryNull() {
		
		if(serviceFactory == null || adminFactory == null ) {
			return false;
		} 
		return true;
	}

}
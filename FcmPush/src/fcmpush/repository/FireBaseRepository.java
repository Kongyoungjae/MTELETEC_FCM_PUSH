package fcmpush.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fcmpush.config.DataBaseConfig;
import fcmpush.service.FireBasePushService;
import fcmpush.util.DateUtil;
import fcmpush.util.StackTraceLogUtil;

public class FireBaseRepository {

	private static final Logger logger = LogManager.getLogger(FireBasePushService.class);
	private DataBaseConfig dbconfig;

	public FireBaseRepository() {
		dbconfig = new DataBaseConfig();
	}
	
	// select. result. single
	public List<HashMap<String, Object>> selectPushInfoByDateTime(HashMap<String, Object> nowDateTime) {
		final String query = "selectPushInfoByDateTime";
		
		List<HashMap<String, Object>> resultMap = new ArrayList<HashMap<String,Object>>();
			
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nowDateTime", DateUtil.getDateTime());
		
		SqlSession s = dbconfig.getAdminSession();
		if( s == null ) {
			logger.info("sqlSession is null. not running query.");
			return null;
		}
		
		try {
			resultMap = s.selectList(query, paramMap);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			s.close();
		}
		
		return resultMap;
	}
	
	public List<HashMap<String, Object>> selectPushHistByPushID(HashMap<String, Object> pushID) {
		final String query = "selectPushHistByPushID";
		
		List<HashMap<String, Object>> resultMap = new ArrayList<HashMap<String,Object>>();
		
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("PUSH_ID", pushID.get("PUSH_ID"));	
		
		
		SqlSession s = dbconfig.getAdminSession();
		if( s == null ) {
			logger.info("sqlSession is null. not running query.");
			return null;
		}
		
		try {
			resultMap = s.selectList(query, paramMap);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			s.close();
		}
		
		return resultMap;		
	}
	
	
	// select. result. single
	public List<String> selectUsersToken() {
		String query = "selectUsersToken";
		List<String> tokens = new ArrayList<String>();
		
		SqlSession session = dbconfig.getServiceSession();

		if( session == null ) {
			logger.info("sqlSession is null. not running query.");
			return null;
		}
		try {
			tokens = session.selectList(query);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			session.close();
		}
		
		return tokens;
	}
	
	public Integer deleteAllPushGroups() {
		String query = "deleteAllPushGroups";
		int deleteYn = 0;
		
		SqlSession session = dbconfig.getAdminSession();

		if( session == null ) {
			logger.info("sqlSession is null. not running query.");
			return null;
		}
		try {
			deleteYn = session.delete("deleteAllPushGroups");
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			session.commit();
			session.close();
		}
		
		return deleteYn;
	}

	public void insertPushGroup(HashMap<String, Object> param) {
		
		String query = "insertPushGroup";
		
		SqlSession session = dbconfig.getAdminSession();

		if( session == null ) {
			logger.info("sqlSession is null. not running query.");
		}
		try {
			session.insert("insertPushGroup",param);

		} catch ( Exception e ) {
			session.rollback();
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			session.commit();
			session.close();
		}

	}
	
	

	
	
	
	
	
	
	
	
//	// insert. single 아마 여기서는 사용할일 없을것.
//	public Integer insert(String query, Map<String,String> param) {
//		Integer nResult = 0;
//		
//		SqlSession s = DataBaseConfig.getServiceSession();
//		if( s == null ) {
//			logger.info("sqlSession is null. not running query.");
//			return null;
//		}
//		
//		try {
//			nResult = s.insert(query,param);
//		} catch ( Exception e ) {
//			logger.error("exception/ SMSdao::insert() single param /"+StackTraceLogUtil.getStackTraceString(e));
//		} finally {
//			s.close();
//		}
//		
//		return nResult;
//	}
//	
//	// insert. list 아마 여기서는 사용할일 없을것.
//	public Integer insert(String query, List<Map<String,String>> param) {
//		Integer nResult = 0;
//		
//		SqlSession s = DataBaseConfig.getServiceSession();
//		if( s == null ) {
//			logger.info("sqlSession is null. not running query.");
//			return null;
//		}
//		
//		try {
//			for( Map<String,String> m : param ) {
//				nResult += s.insert(query,m);
//			}
//		} catch ( Exception e ) {
//			logger.error("exception/ SMSdao::insert() list param /"+StackTraceLogUtil.getStackTraceString(e));
//		} finally {
//			s.close();
//		}
//		
//		return nResult;
//	}
//	
//	// update. single.
//	public Integer update(String query, Map<String,String> param) {
//		Integer nResult = 0;
//		
//		SqlSession s = DataBaseConfig.getServiceSession();
//		if( s == null ) {
//			logger.info("sqlSession is null. not running query.");
//			return null;
//		}
//		
//		try {
//			nResult = s.update(query,param);
//		} catch ( Exception e ) {
//			logger.error("exception/ SMSdao::update() single param /"+StackTraceLogUtil.getStackTraceString(e));
//		} finally {
//			s.close();
//		}
//		
//		return nResult;
//	}
//	
//	// update. list
//	public Integer update(String query, List<Map<String,String>> param) {
//		Integer nResult = 0;
//		
//		SqlSession s = DataBaseConfig.getServiceSession();
//		if( s == null ) {
//			logger.info("sqlSession is null. not running query.");
//			return null;
//		}
//		
//		try {
//			for( Map<String,String> m : param ) {
//				nResult += s.update(query,m);
//			}
//		} catch ( Exception e ) {
//			logger.error("exception/ SMSdao::update() list param /"+StackTraceLogUtil.getStackTraceString(e));
//		} finally {
//			s.close();
//		}
//		
//		return nResult;
//	}
//	
//	// delete. single.
//	public Integer delete(String query, Map<String,String> param) {
//		Integer nResult = 0;
//		
//		SqlSession s = DataBaseConfig.getServiceSession();
//		if( s == null ) {
//			logger.info("sqlSession is null. not running query.");
//			return null;
//		}
//		
//		try {
//			nResult = s.delete(query,param);
//		} catch ( Exception e ) {
//			logger.error("exception/ SMSdao::delete() single param /"+StackTraceLogUtil.getStackTraceString(e));
//		} finally {
//			s.close();
//		}
//		
//		return nResult;
//	}
//	
//	// delete. list.
//	public Integer delete(String query, List<Map<String,String>> param) {
//		Integer nResult = 0;
//		
//		SqlSession s = DataBaseConfig.getServiceSession();
//		if( s == null ) {
//			logger.info("sqlSession is null. not running query.");
//			return null;
//		}
//		
//		try {
//			for( Map<String,String> m : param ) {
//				nResult += s.delete(query,m);
//			}
//		} catch ( Exception e ) {
//			logger.error("exception/ SMSdao::delete() list param /"+StackTraceLogUtil.getStackTraceString(e));
//		} finally {
//			s.close();
//		}
//		
//		return nResult;
//	}
}

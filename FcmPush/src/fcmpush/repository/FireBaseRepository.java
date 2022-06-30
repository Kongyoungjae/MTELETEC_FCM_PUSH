package fcmpush.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fcmpush.config.DataBaseConfig;
import fcmpush.util.StackTraceLogUtil;

public class FireBaseRepository {

	private static final Logger logger = LogManager.getLogger(FireBaseRepository.class);
	private DataBaseConfig dbconfig;

	public FireBaseRepository() {
		dbconfig = new DataBaseConfig();
	}
	
	// 현재시간 = 푸쉬예정시간 
	public List<HashMap<String, Object>> selectTodayPushInfoByNowDateTime(HashMap<String, Object> nowDateTime) {
		final String query = "selectTodayPushInfoByNowDateTime";
		
		List<HashMap<String, Object>> resultMap = new ArrayList<HashMap<String,Object>>();
			
		SqlSession s = dbconfig.getAdminSession();
		if( s == null ) {
			logger.info("sqlSession is null. not running query.");
			return null;
		}
		
		try {
			resultMap = s.selectList(query, nowDateTime);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			s.close();
		}
		
		return resultMap;
	}
	
	// 오늘 발송한 푸쉬가 있는지 확인 
	public int selectTodayPushHistCount() {
		final String query = "selectTodayPushHistCount";
		int count = 0;
			
		SqlSession s = dbconfig.getAdminSession();
		if( s == null ) {
			logger.info("sqlSession is null. not running query.");
			throw new NullPointerException();
		}
		
		try {
			count = s.selectOne(query);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			s.close();
		}
		return count;
	}
	
	public HashMap<String, Object> selectPushHistLastPushTime() {
		final String query = "selectPushHistLastPushTime";
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		SqlSession s = dbconfig.getAdminSession();
		if( s == null ) {
			logger.info("sqlSession is null. not running query.");
			throw new NullPointerException();
		}
		
		try {
			resultMap = s.selectOne(query);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			s.close();
		}
		return resultMap;
	}
	
	// 푸쉬 발송 내역 확인
	public int selectPushHistCountByPushID(HashMap<String, Object> pushID) {
		final String query = "selectPushHistCountByPushID";
		int result = 0;
		
		SqlSession s = dbconfig.getAdminSession();
		if( s == null ) {
			logger.info("sqlSession is null. not running query.");
			throw new NullPointerException();
		}
		
		try {
			result = s.selectOne(query, pushID);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			s.close();
		}
		
		return result;		
	}
	
	
	// 오전 4시이전 가입한 토큰 가져오기
	public List<String> selectUsersAllTokenBefore4AM() {
		String query = "selectUsersAllTokenBefore4AM";
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
	
	// 오전4시 이후 가입한 토큰 가져오기
	public List<String> selectTodayUsersTokenAfter4AM() {
		String query = "selectTodayUsersTokenAfter4AM";
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
	// 마지막 발송후 가입한 유저들 토큰
	public List<String> selectJoinUsersTokenAfterLastPushTime(HashMap<String, Object> lastPushTime) {
		String query = "selectJoinUsersTokenAfterLastPushTime";
		List<String> tokens = new ArrayList<String>();
		
		SqlSession session = dbconfig.getServiceSession();

		if( session == null ) {
			logger.info("sqlSession is null. not running query.");
			return null;
		}
		try {
			tokens = session.selectList(query , lastPushTime);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			session.close();
		}		
		return tokens;
	}
	
	public int selectMaxGroupSEQ() {
		final String query = "selectMaxGroupSEQ";
		int count = 0;
			
		SqlSession s = dbconfig.getAdminSession();
		if( s == null ) {
			logger.info("sqlSession is null. not running query.");
			throw new NullPointerException();
		}
		
		try {
			count = s.selectOne(query);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			s.close();
		}
		
		return count;
	}
	// 모든 푸쉬 그룹 삭제
	public void deleteAllPushGroups() {
		String query = "deleteAllPushGroups";
		
		SqlSession session = dbconfig.getAdminSession();

		if( session == null ) {
			logger.info("sqlSession is null. not running query.");
			throw new NullPointerException();
		}
		try {
			session.delete(query);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			session.commit();
			session.close();
		}
	}

	// 푸쉬그룹 INSERT
	public void insertPushGroup(HashMap<String, Object> param) {
		
		String query = "insertPushGroup";
		
		SqlSession session = dbconfig.getAdminSession();

		if( session == null ) {
			logger.info("sqlSession is null. not running query.");
		}
		try {
			session.insert(query,param);

		} catch ( Exception e ) {
			session.rollback();
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			session.commit();
			session.close();
		}
	}
	
	// 푸쉬이력 INSERT
	public void insertPushHist(HashMap<String, Object> param) {
		
		String query = "insertPushHist";
		
		SqlSession session = dbconfig.getAdminSession();

		if( session == null ) {
			logger.info("sqlSession is null. not running query.");
		}
		try {
			session.insert(query , param);

		} catch ( Exception e ) {
			session.rollback();
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			session.commit();
			session.close();
		}
	}
	
	//오늘 4시 이후 가장작은 그룹순번 
	public int selectTodayMinGroupSeqAfter4am() {
		final String query = "selectTodayMinGroupSeqAfter4am";
		int minSEQ = 0;
			
		SqlSession s = dbconfig.getAdminSession();
		if( s == null ) {
			logger.info("sqlSession is null. not running query.");
			throw new NullPointerException();
		}
		
		try {
			minSEQ = s.selectOne(query);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			s.close();
		}
		
		return minSEQ;
	}

	//오늘 오후 4시이후 만들어진 푸쉬그룹 카운트
	public int selectTodayCreatedPushGroupCountAfter4AM() {
		final String query = "selectTodayCreatedPushGroupCountAfter4AM";
		int count = 0;
			
		SqlSession s = dbconfig.getAdminSession();
		if( s == null ) {
			logger.info("sqlSession is null. not running query.");
			throw new NullPointerException();
		}
		
		try {
			count = s.selectOne(query);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			s.close();
		}
		
		return count;
	}

	public int selectPushGroupCount() {
		final String query = "selectPushGroupCount";
		int count = 0;
			
		SqlSession s = dbconfig.getAdminSession();
		if( s == null ) {
			logger.info("sqlSession is null. not running query.");
			throw new NullPointerException();
		}
		
		try {
			count = s.selectOne(query);
		} catch ( Exception e ) {
			logger.error("exception/ SMSdao::selectOne()"+StackTraceLogUtil.getStackTraceString(e));
		} finally {
			s.close();
		}
		
		return count;
	}
}

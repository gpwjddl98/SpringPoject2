package dao;


import org.apache.ibatis.session.SqlSession;


import vo.BlogUserVO;


public class BlogUserDAO {
	
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//로그인
	public BlogUserVO loginOk(Object bloguservo) {
			
		return sqlSession.selectOne("user.login",bloguservo);
	}
	
	//회원가입 : 아이디중복체크
	public int Checkuserid(String userid) {
		int result = sqlSession.selectOne("user.Checkuserid",userid);
		return result;
	}
	
	//회원가입
	public void insert(BlogUserVO bloguservo) {
		sqlSession.insert("user.user_insert",bloguservo);
	}
	
	
	
	
	
	
	
	
}

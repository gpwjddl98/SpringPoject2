package dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.KakaoVO;


public class KaKaoDAO {

	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//정보저장
	public void kakaoinsert(HashMap<String,Object> userInfo) {
		sqlSession.insert("kakao.kakaoinfo_insert",userInfo);
	}
	//정보확인
	public KakaoVO findkakao(HashMap<String,Object> userInfo) {
		System.out.println(userInfo.get("nickname"));
		System.out.println(userInfo.get("email"));
		
		return sqlSession.selectOne("kakao.kakaoinfo",userInfo);
	}
	

}

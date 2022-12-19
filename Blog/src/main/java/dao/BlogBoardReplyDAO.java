package dao;




import java.util.HashMap;
import java.util.List;

import vo.BlogBoardReplyVO;

import org.apache.ibatis.session.SqlSession;





public class BlogBoardReplyDAO {
	
	
	
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	//댓글 조회

	public List<BlogBoardReplyVO> selectList(int idx){
		List<BlogBoardReplyVO> vo = sqlSession.selectList("reply.reply_list",idx);
		return vo;
	}
	//댓글작성
	public void insert(BlogBoardReplyVO vo) {
		sqlSession.insert("reply.reply_insert",vo);
	}
	
	//댓글 삭제
	public void delete(BlogBoardReplyVO vo) {
		sqlSession.delete("reply.reply_delete",vo);
	}
	//아이디 확인
	public String idCheck(int r_no) {
		return sqlSession.selectOne("reply.reply_userid_check",r_no);
	}
	//댓글 수정
	public void modify(BlogBoardReplyVO vo) {
		sqlSession.update("reply.reply_modi",vo);
	}
	
}

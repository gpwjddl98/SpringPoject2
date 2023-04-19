package dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;


import vo.BlogBoardVO;



public class BlogBoardDAO {
	
	
	
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	//게시판 전체 조회
	public List<BlogBoardVO> selectList(){
		List<BlogBoardVO> list = sqlSession.selectList("board.blog_lsit");
		
		return list;
	}
	//글 추가하기
	public int insert(BlogBoardVO vo) {
		int res =  sqlSession.insert("board.blog_insert",vo);
		return res;
	}
	
	//글 자세히 보기 
	public BlogBoardVO selectOne(int IDX) {
		BlogBoardVO vo = sqlSession.selectOne("board.blog_view",IDX);
		return vo;
	}
	//글 수정하기
	public int update(BlogBoardVO vo) {
		int res = sqlSession.update("board.blog_update",vo);
		return res;
	}
	//글 삭제하기
	public int delet(int IDX) {
		int res = sqlSession.delete("board.blog_delet",IDX);
		return res;
	}
	
	
	
	
}

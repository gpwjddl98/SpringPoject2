package vo;

import java.util.Date;

public class BlogBoardReplyVO {

	/* 게시판 댓글 테이블
	 * CREATE TABLE blogreply( 
	 * R_NO NUMBER(3), --댓글의 고유번호 
	 * IDX NUMBER(3), --연결되어있는게시글 번호 
	 * USERID VARCHAR(30), 
	 * CONTENT VARCHAR(2000), 
	 * R_DATE DATE DEFALUT SYSDATE,
	 * PRIMARY KEY(R_NO , IDX),
	 * FOREIGN KEY(IDX) REFERENCES blogboard(IDX) ON DELETE CASCADE ); --블로그보드 테이블의 IDX랑 연걸
	 * FOREIGN KEY(USERID) REFERENCES BLOGUSER(USERID) -- 블로그유저 테이블의 USERID랑 연결
	 */
	
	
	
	private String userid , content , regdate;
	private int r_no , idx ;
	private Date r_date;
	
	
	
	public Date getR_date() {
		return r_date;
	}
	public void setR_date(Date r_date) {
		this.r_date = r_date;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getR_no() {
		return r_no;
	}
	public void setR_no(int r_no) {
		this.r_no = r_no;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	
	
	
}

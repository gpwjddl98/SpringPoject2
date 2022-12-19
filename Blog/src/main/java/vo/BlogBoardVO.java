package vo;

public class BlogBoardVO {
	
	
	/* create table blogboard(
			idx number(3) primary key,
			userid varchar2(50),
			title varchar2(100),
			content varchar2(2000),
			regdate varchar2(10)
		);

		CREATE SEQUENCE blogboard_SEQ_IDX;
	*/
	
	private int idx;
	private String title, content, userid, regdate;
	
	
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	
	

	
	
	
}

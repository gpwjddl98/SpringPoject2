package vo;

import org.springframework.web.multipart.MultipartFile;

public class BlogUserVO {

	private String userid , userpwd , username  , useremail ,usertel1 , usertel2 , usertel3 ;
	private int verify;
	
	


	public int getVerify() {
		return verify;
	}

	public void setVerify(int verify) {
		this.verify = verify;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUsertel1() {
		return usertel1;
	}

	public void setUsertel1(String usertel1) {
		this.usertel1 = usertel1;
	}

	public String getUsertel2() {
		return usertel2;
	}

	public void setUsertel2(String usertel2) {
		this.usertel2 = usertel2;
	}

	public String getUsertel3() {
		return usertel3;
	}

	public void setUsertel3(String usertel3) {
		this.usertel3 = usertel3;
	}


	
	

	

	
	
	
}

package com.korea.phj;





import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korea.service.MailSendService;

import dao.BlogUserDAO;
import vo.BlogUserVO;


@Controller
public class BlogUserController {
	
	
	public static final String VIEW_PATH = "/WEB-INF/views/";
	
	BlogUserDAO bloguserdao;
	
	public void setBloguserdao(BlogUserDAO bloguserdao) {
		this.bloguserdao = bloguserdao;
	}

	
	@Autowired
	private MailSendService mailService;
	
	@Autowired
	ServletContext application;

	
	//로그인 화면으로 이동
	@RequestMapping("/login_page")
	public String loginpage() {
		
		return VIEW_PATH + "Loginpage.jsp";
	}

	//로그인 확인
	@RequestMapping("/loginOk")
	public ModelAndView loginOk(BlogUserVO bloguservo, HttpServletRequest request , HttpSession session, Object access_Token) {

		 BlogUserVO normal = bloguserdao.loginOk(bloguservo);
		 session.setAttribute("normal",normal);
			
		 ModelAndView mav = new ModelAndView(); 
		 
		 if(request.isRequestedSessionIdValid()) {
			 session = request.getSession();
			 
			 if(session.getAttribute("normal")== null && session.getAttribute("kakaoInfo") ==null) {
				 System.out.println("세션없는곳");
				 mav.setViewName("login_page");
				 mav.addObject("massage","error");
				 session.setAttribute("login", null); 
			 }else {
				 System.out.println("세션있는곳");
				 if(session.getAttribute("kakaoInfo") != null) {
					 System.out.println("카카오로 로그인성공");
					 session.setAttribute("kakao", session.getAttribute("kakaoInfo"));
					 
					 mav.setViewName("BlogMain.do");
				 }else if(session.getAttribute("normal") != null) {
					 System.out.println("일반로그인으로 들어옴");
					 session.setAttribute("normal", normal);
					 session.setAttribute("userid", bloguservo.getUserid());
					 mav.setViewName("BlogMain.do");
				 }
			 }
		 }
				
		 return mav;
		
	}
	
	//로그아웃
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	
	
	//회원가입페이지로 이동
	@RequestMapping("/Joinpage")
	public String joinpage() {
		return VIEW_PATH + "Joinpage.jsp";
	}
	
	//회원가입 아이디 중복체크 ajax 정규식체크
	@RequestMapping("/Checkuserid")
	@ResponseBody
	public int Checkuserid(String userid) {
		
		int result = bloguserdao.Checkuserid(userid);
		
		return result;
	}
	
	//회원가입 비밀번호 정규식 체크ajax
	@RequestMapping("/Checkuserpwd")
	@ResponseBody
	public boolean Checkuserpwd(String userpwd) {
		
		boolean checkpwd = false;
		
		String pwdch = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
		
		Pattern pattern_symbol = Pattern.compile(pwdch);
		//complie : 주어진 정규표현식으로부터 패턴을 만듭니다.
		//matcher : 대상 문자열이 패턴과 일치할 경우 true를 반환합니다.
		Matcher matcher_symbol = pattern_symbol.matcher(userpwd);
		
		if(matcher_symbol.find()) { //find : 대상문자열과 패턴이 일치하는경우 true를 반환하고, 그 위치로 이동합니다
			checkpwd = true;
		}
		return checkpwd;
	}
	
	//이메일 ajax
	@RequestMapping("/Checkeamil")
	@ResponseBody
	public String Checkeamil(String useremail) {
		System.out.println("이메일 인증요청이 잘 들어옴");
		System.out.println("eamil :" + useremail);
		return mailService.joinEmail(useremail);
		
	}
	
	//회원가입
	@RequestMapping("/Join")
	public String Join(BlogUserVO bloguservo ,RedirectAttributes redirectAttributes ) {
		bloguserdao.insert(bloguservo);
		redirectAttributes.addFlashAttribute("msg","REGISTERED");
		return "redirect:/login_page";
	}

}

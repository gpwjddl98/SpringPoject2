package com.korea.phj;





import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.BlogBoardDAO;
import dao.BlogBoardReplyDAO;

import vo.BlogBoardReplyVO;
import vo.BlogBoardVO;
import vo.BlogUserVO;





@Controller
public class BlogBoardController {
	
	@Autowired
	HttpServletRequest request;
	
	public static final String VIEW_PATH = "/WEB-INF/views/";
	
	
	BlogBoardDAO blogboarddao;
	BlogBoardReplyDAO blogboardreplydao;
	

	public void setBlogboarddao(BlogBoardDAO blogboarddao) {
		this.blogboarddao = blogboarddao;
	}
	
	public void setBlogboardreplydao(BlogBoardReplyDAO blogboardreplydao) {
		this.blogboardreplydao = blogboardreplydao;
	}
	

	//블로그 메인화면  + 게시판 글 전체조회 
	@RequestMapping(value = {"/","/BlogMain.do"})
	public String BlogMain(Model model , HttpSession session) {
		Object kakaoInfo = session.getAttribute("kakaoInfo");
		List<BlogBoardVO> list = blogboarddao.selectList();		
		model.addAttribute("list",list);
		model.addAttribute("kakaoInfo",kakaoInfo);
		//session에 저장되어있는 카카오 정보를 가져온다.
		return VIEW_PATH + "BlogMain.jsp";
	}
	
	//자세히보기+댓글도 보이도록
	 @RequestMapping("/BlogView.do") 
	 public String selectOne(Model model, int idx, HttpSession session , BlogUserVO bloguservo){ 
		 List<BlogBoardVO> list = blogboarddao.selectList();
		model.addAttribute("list",list);
		//게시글
		 model.addAttribute("view",blogboarddao.selectOne(idx));
		 return VIEW_PATH + "BlogBoardView.jsp" ; 
	 }
	//글 추가하기 폼으로 이동
	@RequestMapping("/insert_form.do")
	public String insert_form(HttpServletRequest request ,HttpSession session) {
		return VIEW_PATH + "BlogBoardInsert.jsp";
	}
	
	//글 추가하기
	@RequestMapping("/insert.do")
	public String insert(BlogBoardVO vo) {
		int res = blogboarddao.insert(vo);
		return "redirect:BlogMain.do";
	}
	
	//글 수정하기 폼으로 이동
	@RequestMapping("/update_form.do")
	public String update_form(Model model, int idx) {
		 BlogBoardVO vo = blogboarddao.selectOne(idx);
		 model.addAttribute("vo",vo);
		return VIEW_PATH + "BlogBoardupdate.jsp";
	}
	
	//글 수정하기
	@RequestMapping("/modify.do")
	public String modify(Model model , BlogBoardVO vo, int idx ) {
		
		int res = blogboarddao.update(vo);
		return "redirect:BlogView.do?idx=" + idx;
	}
	
	//글 삭제하기
	@RequestMapping("/delet.do")
	public String delet(int idx) {
		int res = blogboarddao.delet(idx);
		return "redirect:BlogMain.do";
	}
	
	//댓글 목록 Ajax
	@ResponseBody
	@RequestMapping("/replyList.do")
	public List<BlogBoardReplyVO> getReplyList(int idx , HttpSession session){
		List<BlogBoardReplyVO> reply = blogboardreplydao.selectList(idx);
		return reply;
	}
	
	
	//댓글작성 Ajax
	@RequestMapping("/replyinsert.do")
	@ResponseBody
	public void reply_insert(BlogBoardReplyVO vo ,HttpSession session) {
	
		 BlogUserVO normal = (BlogUserVO)session.getAttribute("normal");
		 vo.setUserid(normal.getUserid()); 
		 blogboardreplydao.insert(vo);
	}
	
	
	//댓글삭제 Ajax
	@ResponseBody
	@RequestMapping("/replydelete.do")
	public int reply_delete(BlogBoardReplyVO reply, HttpSession session) {
		
		int result =0;
		
		BlogUserVO member = (BlogUserVO)session.getAttribute("normal");
		String userId = blogboardreplydao.idCheck(reply.getR_no());
		
		if(member.getUserid().equals(userId)) {
			reply.setUserid(member.getUserid());
			blogboardreplydao.delete(reply);
			result = 1;
		}

		return result;
	}
	
	//댓글수정 Ajax
	@ResponseBody
	@RequestMapping("/replymodi.do")
	public int reply_modi(BlogBoardReplyVO reply, HttpSession session) {
		
		int result = 0; 
		BlogUserVO member = (BlogUserVO)session.getAttribute("normal");
		String userId = blogboardreplydao.idCheck(reply.getR_no());
		
		if(member.getUserid().equals(userId)) {
			reply.setUserid(member.getUserid());
			blogboardreplydao.modify(reply);
			result = 1;
		}
		
		return result;
	}
	
	
	
	
	
	
	
	

	
}

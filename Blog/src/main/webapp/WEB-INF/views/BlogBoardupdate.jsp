<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   <%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정하기페이지</title>
<link href="resources/css/update.css" rel="stylesheet"/>
</head>
<body>
<header id="header">
			<div class="header_title">
				<h1 onclick="location.href='BlogMain.do'">HyeJung's Blog  </h1>
				<span class="userinfo">
					<c:if test="${empty sessionScope}">
						로그인이 필요합니다.
					</c:if>
					<c:if test="${not empty sessionScope}">
							<c:if test="${kakao != null}">
								${kakao.k_name } <span>(카카오계정으로 로그인중)</span>
							</c:if>
							<c:if test="${normal != null }">
								<c:if test="${normal.verify == 9 }">
									${normal.userid }
									<a href="insert_form.do">글 추가하기</a>
								</c:if>
								<c:if test="${normal.verify == 0 }">
									${normal.userid }
								</c:if>
							</c:if>
						</c:if>
				</span>
			</div><!-- header_title  -->
			<c:if test="${empty sessionScope}">
				<div class="login_btn">
					<input type="button" value="로그인" onclick="location.href='login_page'">
				</div>
			</c:if><!-- 세션이 비어있을 때 로그인 버튼 -->
			<c:if test="${not empty sessionScope}">
				<div class="login_btn">
					<input type="button" value="로그아웃" onclick="location.href='logout'">
				</div>
			</c:if><!-- 세션이 비어있지 않을 때 로그아웃 버튼 -->
	</header><!-- /header -->
	<section>
		<div class="rightinfo">
			<div class="update_box">
				<form>
					<input type="hidden" name="idx" value="${vo.idx }">
					<div class="cont">
						<div class="cont_title">
							제목 <br> <input type="text" name="title" value="${vo.title }">
						</div>
						<div class="cont_content">
							내용 <br> <textarea rows="20" cols="20" name="content" >${vo.content }</textarea>
						</div>	
					</div>
					<div class="btn">
						<input id="update_btn" type="button" value="수정하기" onclick="send(this.form);">
						<input id="cancel_btn" type="button" value="취소하기" onclick="location.href='BlogView.do?idx=${vo.idx}'">
					</div>
				</form>
			</div>
		</div>
	</section>	
	<script>
	
	function send(f){
		//넘길 파라미터 idx
		
		f.action = "modify.do";
		f.method = "get";
		f.submit();
		
		
	};
		
	
	
	</script>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>
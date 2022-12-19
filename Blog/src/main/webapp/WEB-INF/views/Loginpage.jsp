<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지 입니다.</title>
<link href="resources/css/login.css" rel="stylesheet"/> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<div class="loginbox">
		
		<form>
			<div class="login_id">
				<input type="text" id="blogid" name="userid" size="20" 
					placeholder="아이디">
			</div>
			<div class="login_pw">
				<input type="password" id="blogpw" name="userpwd" size="20"
					placeholder="비밀번호">
			</div>
			<div class="submit">
				<input class="submit_btn"type="button" value="로그인" onclick="send(this.form);">
				<c:if test="${massage == 'error' }">
					<div class="submit_"style="color:red;">아이디 또는 비밀번호를 확인헤주세요</div>
				</c:if>
			</div>
			<div class="kakao">
				<div onclick="kakaoLogin();">
				<a herf="https://kauth.kakao.com/oauth/authorize?client_id=37e286b9c406deaa2f6a4f035acc8f35&
					redirect_uri=http://localhost:9090/phj/oauth_kakao&response_type=code">
					<img src="resources/img/kakao.png">
				</a>
				</div>
			</div>
			<input type="button" id="btn_join" value="회원가입" onclick="location.href='Joinpage'">
			<input type="button" id="btn_main" value="메인으로" onclick="location.href='BlogMain.do'">
			
		</form>
	</div>
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	
	<script>
	
	  function kakaoLogin() {

		    $.ajax({
		        url: './getKakaoAuthUrl',
		        type: 'get',
		        async: false,
		        dataType: 'text',
		        success: function (res) {
		            location.href = res;
		        }
		    });

		  }

		 
	
		function send(f){
			//유효성 체크
			if(!f.blogid.value){
				alert("아이디를 입력해주세요");
				return;
			}
			if(!f.blogpw.value){
				alert("비밀번호를 입력해주세요");
				return
			}
			
			f.action = "loginOk";
			f.method = "post";
			f.submit();
			
		}
		
		var msg = "${msg}";
		if(msg == "REGISTERED"){
			alert("회원가입이 완료되었습니다. 로그인해주세요")
		}
		
	</script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link href="resources/css/join.css" rel="stylesheet"/> 
</head>
<body>
	<div class="outterbox">
		<h1 onclick="location.href='BlogMain.do'">회원가입</h1>
			<form action="Join" method="post" enctype="multipart/form-data">
				<div>
					<span>아이디 </span><p class="idCheck"></p>
					<input type="text" name="userid" class="userid">	
				</div>
				<div>
					<span>비밀번호</span><p class="pwdCheck"></p>
					<input type="password" name="userpwd" class="userpwd" placeholder ="8자리 이상 입력해주세요">
					
				</div>
				<div>
					<span>비밀번호 재확인</span><p class="pwdCheck_2"></p> 
					<input type="password" name="pwd2" class="pwd2">
					 
				</div>
				<div>
					<span>이름</span>
					<input type="text" name="username" class="username">
				</div>
				<div>
					<span style="width:100%; display: block;">전화번호</span>
					<input type="text" name="usertel1" class="usertel1" value="010" style="width:25%" readonly> &#45;
					<input type="text" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');" name="usertel2" class="usertel2" placeholder="1234"style="width:25%" maxlength="4"> &#45;
					<input type="text" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');" name="usertel3" class="usertel3" placeholder="5678"style="width:25%" maxlength="4">
				</div>
				<div>
					<span>본인확인 이메일</span><p class="emailCheck"></p>
					<input name="useremail" class="useremail">
					<input type="button" class="btn_email" value="본인인증">
					<span>인증번호 확인하기</span><p class="emailNumCheck"></p>
					<input name="checkmail" class="checkmail" placeholder="6자리 인증버호를 입력해주세요">
				</div>
				<input type="button" id="btn_join" value="가입하기" onclick="send(this.form)">
				<input type="button" id="btn_main" value="메인으로" onclick="location.href='BlogMain.do'">
			</form>
	</div>
	
	<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>
	//아이디중복체크 ajax
	let useridcheck = false;
	$(".userid").keyup(function(){
			useridcheck = false;
			var checkid =  /^[a-zA-Z0-9]*$/;
			var userid = $('.userid').val();
			if (userid == "") {
				$(".idCheck").text("");
				return;
			}
				$.ajax({
					url : "./Checkuserid",
					type : "get",
					data : {userid : userid},
					dataType : "json",
					success : function(result){
						if(result == 0){ //사용가능한 아이디
							if(checkid.test(userid) == false){
								$(".idCheck").text("영대소문자 숫자만 포함해서 작성해주세요");
								$(".idCheck").css("color", "red" );
							}else{
								$(".idCheck").text("사용 가능 합니다");
								$(".idCheck").css("color", "green","width" ,"430px");
								useridcheck = true;
							}
							
						}else{ //result가 1일경우
							$(".idCheck").text("사용 불가능 합니다");
							$(".idCheck").css("color", "red");
							useridcheck = false;
						}
					},
					error:function(){
						console.log("에러입니다.")
					}
				})		
	})
	//비밀번호 8자리 이상되었을때 확인 ajax
	$(".userpwd").keyup(function(){
		
		let userpwd = $(".userpwd").val();
		if (userpwd == "") {
			$(".pwdCheck").text("");
			return;
		}
		
		$.ajax({
			url : "./Checkuserpwd",
			type : "post",
			data : {userpwd : userpwd},
			dataType : "json",
			success : function(checkpwd){
				if(checkpwd == true){
					$('.pwdCheck').text("사용가능합니다.");
					$('.pwdCheck').css("color","green");
				}else{
					$('.pwdCheck').text("특수문자를 제외한 숫자와 문자를 8자리 이상입력하세요");
					$('.pwdCheck').css("color","RED");
				}
			},
			error:function(){
				console.log("에러입니다.");
			}
		})
	})
	
	//입력한 비밀번호 일치하는지 확인
	let userpwd = $(".userpwd").val();
	let pwd2 = $(".pwd2").val();
	$(".pwd2").keyup(function(){
		userpwd = $(".userpwd").val(); //처음 입력한 패스워드
		pwd2 = $(".pwd2").val(); //입력한 패스워드
		if( userpwd != pwd2 ){
			$(".pwdCheck_2").text("비밀번호가 일치하지 않습니다.");
			$(".pwdCheck_2").css("color","red");
		}else{
			$(".pwdCheck_2").text("확인");
			$(".pwdCheck_2").css("color","green");
			
		}
	})
	//이메일 유효성검사
	$(".useremail").keyup(function(){
		var useremail = $(".useremail").val();
		var regular = /(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))/;
		if(regular.test(useremail) == false){
			$(".emailCheck").text("이메일이 유효하지 않습니다.");
			$(".emailCheck").css("color","red");
		}else{
			$(".emailCheck").text(" ");
		}
		
	})
	//이메일 본인인증
	$(".btn_email").click(function(){
		var useremail = $(".useremail").val(); //이메일작성
		var checkmail = $(".checkmail").val(); //인증번호입력하는곳
		
		$.ajax({
			
			type : "get",
			url : "./Checkeamil",
			data : {useremail : useremail}, //
			success : function(data){
				$(".emailCheck").text("인증번호가 발송되었습니다.");
				$(".emailCheck").css("color","green");
				code = data; //인증번호
				
			},
			error : function(){ 
				console.log("오류남");
			}
		})
		
		
	})
	//인증번호 체크
	let useremailcheck = false;
	$(".checkmail").keyup(function(){
		useremailcheck = false;
		var checkmail = $(".checkmail").val();
		
		if(checkmail == code ){
			$(".emailNumCheck").text("인증번호가 일치합니다.");
			$(".emailNumCheck").css("color","green");
			useremailcheck = true;
			
		}else{
			$(".emailNumCheck").text("인증번호가 불일치 합니다 다시 확인해주세요");
			$(".emailNumCheck").css("color","red");
			useremailcheck = false;
		}
		
		
		
	}) 
	//회원가입 유효성체크
	function send(f){
		//아이디
		 var userid = f.userid.value;
		if(userid == ""){
			alert("아이디를 입력해주세요");
			$(".userid").focus();
			return;
		}
		var checkid =  /^[a-zA-Z0-9]*$/;
		if(checkid.test(userid) == false){
			alert("영대소문자 숫자만 포함해서 작성해주세요");
			$(".userid").focus(); 
			return;
		}
		if(useridcheck == false){
			alert("사용할 수 없는 아이디 입니다.");
			$(".userid").focus(); 
			return;
		}
		//비밀버호
		var userpwd = f.userpwd.value;
		if(userpwd == ""){
			alert("비밀번호를 입력해주세요");
			$(".userpwd").focus();
			return;
		}
		//이름
		var username= f.username.value;
		if( username == ""){
			alert("이릅을 입력해주세요");
			$(".username").focus();
			return;
		}
		//이메일
		if(useremailcheck == false){
			alert("인증번호가 불일치 합니다 다시 확인해주세요");
			$(".checkmail").focus();
			return;
		}
		f.submit();
	}	
	</script>
	
</body>
</html>
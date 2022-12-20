<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>혜정이의 블로그에 오신걸 환영합니다.</title>
<link href="resources/css/view.css" rel="stylesheet"/> 

<style>

	
	*{margin : 0; padding : 0;  }
	ul,li{ list-style:none;}
	a{text-decoration:none;color: #000;}
	a:hover , a:active{text-decoration:underline;}
	body{font-family: 'GmarketSansMedium'; background-image:url('resources/img/background.jpg');background-size:100%;background-repeat:no-repeat;background-color: #17807C; }
	#header{border-bottom : 3px solid #000;width: 60%;margin: 0 auto;overflow : hidden;padding : 10px 0;}
	.header_title{width: 50%; float:left;}
	h1{padding: 10px;display:inline;}
	span.userinfo{font-size: 10px;}
	.login_btn input{background-color:#FFB3AB;border:0px;color : #fff;float : right;margin:10px 10px 0 0;}
	input{width: 150px;height:30px;}
	section{margin: 0 auto;width: 60%;overflow : hidden;}
	section .rightinfo{width: 100%;float:right;}
	.list{background-color : #FFFFFF;padding : 10px;margin: 30px;border-radius : 10px;}
	
	table{width:100%;border-collapse: collapse; }
	table thead th{border-bottom: 1px solid #000;padding : 10px;}
	table tbody td{border-bottom : 1px solid #dbdbdb;padding : 8px;}
	
	.info{background-color : #FFFFFF;padding : 10px;margin: 10px 30px;border-radius : 10px;}
	.info_box_head{overflow : hidden;border-bottom : 1px solid #dbdbdb;padding-bottom : 10px;padding-top: 10px;}
	.info_box_head .title{float : left;width: 50%;height : 30px;display : block;font-size : 20px;margin-left:15px;}
	
	.info_box_head a{padding-right : 50px;}
	.info_box_head a:hover{text-decoration: none;cursor : pointer;}
	
	pre{white-space: pre-line; }
	.info_box_content{margin: 30px 15px 30px 15px;}
	table{s	width:100%;border-collapse: collapse;}
	table thead th{	border-bottom: 1px solid #000;	padding : 10px;}
	table tbody td{	border-bottom : 1px solid #dbdbdb;padding : 8px;}	
	.info{	background-color : #FFFFFF;	padding : 20px;	margin: 10px 30px 0 ;border-radius : 10px 10px 0 0;overflow : hidden;width : 90%;}
	.info_box_head {order-bottom : 1px solid #dbdbdb;padding-bottom : 10px;overflow : hidden;}
	.info_box_head .title{	width: 50%;height : 30px;float : left;display : block;font-size : 20px;}
	.info_box_head a{padding-right : 50px;}
	.info_box_head a:hover{text-decoration: none;cursor : pointer;}
	.info_box_head_button{float : right;}
	.info_box_head_button input{background-color:#FFB3AB;border:0px;color : #fff;	}gx   
	.info_box_content{margin: 30px 15px 30px 15px;}
	.reply_display{margin : 10px 0;	overflow : hidden;border : 1px solid #dbdbdb;padding : 10px;border-radius : 5px;cursor : default;}
	.a_btn{font-size : 14px; display : inline-block;float : right;margin-right : 20px;}
	.userid{font-size : 15px;margin-right : 10px;display : inline-block;float : left;}
	.date{color : #a5a3a3;font-size :11px;}
	.replyContent{font-size: 11px;width: 100%;margin-top : 15px;box-sizing: border-box;display:inline-block;}
	.reply_display #plus_reply{background-color:#FFB3AB;border:0px;color : #fff;margin-top : 10px;}
	.reply_form{padding : 0 20px 20px;margin : 0px 30px 0px; background-color : #FFFFFF;border-radius : 0 0 10px 10px;width : 90%;}
	#reply_box{width : 87%;height:70px;box-sizing: border-box;cursor:pointer;}
	#reply_btn{background-color:#FFB3AB;width: 100px;height:70px;border:0px;color : #fff;float : right;cursor : pointer;}
	input:focus {outline:none;}
	.reply_btn{float : right;}
	.reply_btn .reply_modify,.reply_delete{background-color:#FFB3AB;color : #fff;	border:0px;	width: 50px;height : 20px;margin-left : 5px;font-size : 5px;cursor : pointer;}  


	 div.replyModal { position:relative; z-index:1; display:none;}
	 div.modal_background { position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(0, 0, 0, 0.8); z-index:-1; }
	 div.modal_box { position:fixed; top:20%; left:calc(50% - 250px); width:500px; height:240px; padding:20px 10px; background:#fff; border:2px solid #666; }
	 div.modal_box textarea { font-size:16px; font-family:'맑은 고딕'; padding:10px; width:100%; height:200px; resize: none; box-sizing: border-box;}
	 div.modal_box button { font-size:14px; padding:5px 10px; margin:10px 0; background:#FFB3AB; border:0; color:#fff; width:48%}
	 div.modal_box button.modal_cancle { margin-left:14px; }

	
	
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script>
/* 댓글 목록 ajax */
function replyList(){
	var idx  = ${view.idx};
		$.getJSON("./replyList.do"+"?idx="+idx,function(data){
			var str = "";		
			$(data).each(function(){
				var repdate = new Date(this.r_date);
				 date = repdate.toLocaleDateString("ko-US")
				str += "<li data-idx='" + this.idx + "''>" 
					+ "<div class='reply_display'>"
				 	+ "<span class='userid'>" + this.userid + "</span>"
				 	+ "<span class='date'>" + date + "</span>"
				 	+ 	"<div class='reply_btn'>"
				 	+ 		"<button type='button' class='reply_modify' data-r_no='" + this.r_no + "'>수정</button>"
				 	+ 		"<button type='button' class='reply_delete' data-r_no='" + this.r_no + "'>삭제</button>"
					+ 	"</div>"
				 	+ "<span class='replyContent'>"+ this.content + "</span>"
				 	
					+ "</div>"
					+ "</li>";
				
			});
			$(".replyList ol").html(str);
		});
	};
	
</script>

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
		<div class="list">
				<table>
					<thead>
						<tr>
							<th style="width:70%; text-align: left;">글제목</th>
							<th>작성일</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="vo" items="${list}">
							<tr>
								<td><a href="BlogView.do?idx=${vo.idx }">${vo.title }</a></td>
								<td style="text-align:center;">${vo.regdate }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div><!-- table_lsit -->
		<form>
			<input type="hidden" name="idx" value="${view.idx }">
				<section class="info">
						<div class="info_box_head">
								<div class="title">${view.title }</div>
								<div class="info_box_head_button">
									<c:if test="${normal.verify == 9 }">
										<input type="button" onclick="modify();" value="수정하기">
										<input type="button" onclick="delet();"  value="삭제하기">
									</c:if>
								</div>
						</div><!-- info_box_head -->
						<pre class="info_box_content">
							${view.content }
						</pre><!-- info_box_content -->
						
					<div class="replyList">
						<ol>
							<%-- <c:forEach var="reply" items="${reply}">
								<li>
									<div class="reply_display">
										<span class="userid">${reply.userid }</span>
										<span class="replyContent">${reply.content }</span>
									</div>
								</li>
	    					 </c:forEach> --%>
	    					 
						</ol>
						<div class="replyModal">
							<div class="modal_box">
								<div>
									<textarea class="modi_content" name="modi_content"></textarea>
								</div>
								<div>
									<button type="button" class="modal_btn">수정</button>
									<button type="button" class="modal_cancle">취소</button>
								</div>
							</div>
							<div class="modal_background"></div>
						</div>
						<script>
							replyList();
						</script>
						<script>
							$(document).on("click",".reply_modify",function(){
								$(".replyModal").fadeIn(200);
								var r_no = $(this).attr("data-r_no");
								var r_cont = $(this).parent().parent().children(".replyContent").text();						
								$(".modi_content").val(r_cont);
								$(".modal_btn").attr("data-r_no" , r_no);	
							});
						</script>
						<script>
							$(document).on("click",".reply_delete",function(){						
									var data = { r_no : $(this).attr("data-r_no")};
									$.ajax({
										url : "./replydelete.do",
										type : "post",
										data : data, 
										success : function(result){
											if(result == 1 ){
												replyList();
												alert("삭제하시겠습니까?")
											}else{
												alert("작성자 본인만 삭제할 수 있습니다.")
											}
											
										},
										error : function(){
											alert("작성자 본인만 삭제할 수 있습니다.")
										}
									});//ajax
							});//on.click
						</script>
					</div>		
				</section><!-- info -->
		</form><!-- form -->
		
	    <div class="reply_form">
				<form name="f">	
						<input type="hidden" name="idx" value="${view.idx}">
						<c:if test="${not empty sessionScope}">
							<input id="reply_box" name="reply_box" placeholder="댓글을 입력해주세요" >
							<input type="button" id="reply_btn" value="등록하기">
								<script>
									$("#reply_btn").click(function(){
										var form = $("[name=f]").val();
										var idx = $("input[name=idx]").val();
										var cont = $("input[name=reply_box]").val()
										
										
										var data = {
												idx : idx,
												content : cont
													};
										console.log(data);
										$.ajax({
											url : "./replyinsert.do",
											type : "post",
											data : data, 
											success : function(){
												replyList();
												$("#reply_box").val("");
											}
										});
									});
								</script>
						</c:if>
						<c:if test="${empty sessionScope}">
							<input type="text" readonly id="reply_box" name="content" placeholder="로그인후 입력해주세요" onclick="location.href='login_page.do'">
							<input type="submit" id="reply_btn" value="등록하기" disabled='disabled'>
						</c:if>	
				</form>
		</div>
							
		</div><!-- right_info -->			
	</section><!-- section -->
	
	<script  type="text/javascript">
	

	//게시글 수정하기
	function modify(){
		
		f.action="update_form.do?idx="+${view.idx };
		f.method = "post";
		f.submit();
	}
	
	$(".modal_cancle").click(function(){
		$(".replyModal").fadeOut(200);
	});
	
	//게시글 삭제하기
	function delet(){
		var f = document.f;
		
		if( confirm("삭제된 글은 복구 할 수 없습니다 \n삭제하시겠습니까?") == true){
			
		}else{
			return;
		}
		
		f.action = "delet.do";
		f.method = "post";
		f.submit();
		
	}
	
	//댓글 수정 ajax
	$(".modal_btn").click(function(){
		var modiconfirm = confirm("정말로 수정하시겠습니까?");
		
		
		if(modiconfirm){
			var data ={
						r_no : $(this).attr("data-r_no"),
						content : $(".modi_content").val() 
						};
			$.ajax({
				url : "./replymodi.do",
				type : "post",
				data : data,
				success : function(result){
					
					if(result == 1){
						replyList();
						$(".replyModal").fadeOut(200);
					}else{
						alert("작성자만 수정할 수 있습니다.")
					}
				},
				error : function(){
					alert("로그인이 필요합니다.")
				}
			});
			
		}//if
	});

	
	
	
	</script>

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     <%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>혜정이의 블로그에 오신걸 환영합니다.</title>
<link href="resources/css/main.css" rel="stylesheet"/> 

</head>
<body>
	<header id="header">
			<div class="header_title">
				<h1>HyeJung's Blog  </h1>
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
						<c:forEach var="list" items="${list}">
							<tr>
								<td><a href="BlogView.do?idx=${list.idx }">${list.title }</a></td>
								<td style="text-align:center;">${list.regdate }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div><!-- table_lsit -->
			<c:forEach var="list" items="${list}">
			<div class="info">
				<div class="info_box_head">
					<div class="title"><a href="BlogView.do?idx=${list.idx }">${list.title }</a></div>
				</div>
				<pre class="info_box_content">
					${list.content }
				</pre>
			</div><!-- info -->
			</c:forEach>
		</div><!-- right_info -->	 
	</section><!-- /section -->
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script src="js/baffle.min.js"></script>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	
	
	
	<style>
	@import url(http://fonts.googleapis.com/earlyaccess/notosanskr.css);
		* {
			font-family: 'Noto Sans KR', sans-serif;
			margin:0;
			padding:0;
		}
	
		.header_top {
			width:100%;
			height:50px;
			margin:0 auto;
			border-bottom:1pt solid #CEDAE4;
		}
	
		#header_header {
			width:100%;
			margin:0 auto;
			height:90px;
		}
	
		#header_mainMenu {
			width:100%;
			margin:0 auto;
			height:51px;
			/*background:linear-gradient(#82E19E, #34DC66);*/
			
			color:black;
			font-weight:500;
			/*border-bottom:1pt solid #CEDAE4;*/
			border-bottom:1px solid #CEDAE4;
			border-top:1pt solid #CEDAE4;
		}
		
		/* 코리아IT아카데미 아이콘*/
		.top_header_image {
			position:absolute;
			left:300px;
			top:5px;
			cursor:pointer;
		}
		
		/* 최강선생 아이콘 */
		.header_image {
			position:absolute;
			left:330px;
			top:70px;
			cursor:pointer;
		}
		
		/* 로그인 헤더 문구 위치 */
		.header_center1 {
			position:absolute;
			left:740px;
			top:83px;
			font-size:20px;
		}
		
		
		/* 로그인/회원가입 버튼 위치*/
		.header_top-menu1 {		
			position:absolute;
			top:81px;
			right:350px;
		}
		
		/* 로그인/내정보 이미지 위치 */
		.login_image {
			position:absolute;
			right:120px;
			cursor:pointer;
		}
		
		/* 회원가입/로그아웃 이미지 위치 */
		.join_image {
			position:absolute;
			right:20px;
			cursor:pointer;
		}
		
		/* 알림 (종) 아이콘 위치*/
		.header_alarm {		
			position:absolute;
			top:86px;
			right:570px;
			width:30px;
			cursor:pointer;
		}
		
		/* 최상위 공지사항 위치*/
		.header_notice {		
			position:absolute;
			top:11px;
			right:380px;
			font-size:15px;
			cursor:pointer;
		}
		
		/* 메인페이지 메인메뉴 위치 */
		.header_main-menu {
			position:absolute;
			top:142px;
			left:330px;
		}
		
		/* 메인메뉴 라인 색상*/
		ul.login {}
		ul.login > li { display:inline-block; padding:7px 30px; width:120px; height:38px; font-size:19px; text-align:center; cursor:pointer;}
		ul.login > li:hover { font-weight:500; color:white; background:linear-gradient(#5D64DB, #2730C1)}
		ul.login > li > span { line-height:35px; }

		ul, ol, li {list-style:none; margin:0; padding:0;}
		
		ul.header_beforeLogin {}
		
		ul.header_beforeLogin > li > span { margin:0; padding:0; cursor:pointer;}
		ul.header_beforeLogin > li > span:hover { font-weight:900; }
		
		ul.header_beforeLogin > li ul.submenu { display:none; position:absolute; top:45px; right:1px; height:200px; border-radius:10px; background-color:transparent; cursor:pointer;}
		ul.header_beforeLogin > li:hover ul.submenu { display:block;}
		
		
		
		ul.header_beforeLogin > li ul.submenu > li { display:inline-block; width:155px; padding:5px 10px; text-align:left; background-color:white; border:1px solid #B0D1E0;font-size:20px; left:1px; border-radius:10px; line-height:40px;}
		ul.header_beforeLogin > li ul.submenu > li:hover { background:linear-gradient(#5D64DB, #2730C1); color:white; font-weight:600;}
		ul.header_beforeLogin > li ul.submenu > li:nth-child(1){
			border-bottom:1px #A1C1DF solid;
		}
    
	</style>
	
</head>
<body>
<!-- 최상위 헤더 ============================== -->
<div class="header_top">
	<img class="top_header_image" src="img/top_header_icon.png" onclick="location.href='https://www.koreaisacademy.com'">
	 <p class="header_notice" onclick="location.href='Login.jsp'"><MARQUEE direction="up" scrolldelay="200" scrollamount="2">3조 웹프로젝트 - 최강선생(과외 매칭 사이트)</MARQUEE></p>
</div>

<!-- 상위 헤더 ============================== -->
	<div id="header_header">
		<div class="header_top-menu1">
			<ul class="header_beforeLogin">
			
		<c:if test="${ empty user }"> <!--  로그인 전 화면 출력---------------------------------------------------- -->
				<img class="login_image" src="img/login.png" onclick="location.href='Login.jsp'">
				<li><img class="join_image" src="img/join.png">
					<ul class="submenu">
						<li onclick="location.href='join_teacher.jsp'">선생님 회원가입</li> 
						<li onclick="location.href='join_student.jsp'">학생 회원가입</li> 
					</ul>
				</li>
		</c:if>
		
		<c:if test="${ not empty user }"> <!--  로그인 후 화면 출력---------------------------------------------------- -->
			<li><img class="login_image" src="img/mypage.png" ></li>
			<li><img class="join_image" src="img/logout.png" onclick="logout()"></li>
		</c:if>
			</ul> 
		</div>
		
		
		<main>
			<section class="section--1">
				<h3 class="text__glitch header_center1">최강선생은 최선의 결과를 만들어냅니다 🙌</h3>
			</section>
		</main>


		<c:if test="${ not empty user }">
			<img class="header_alarm" src="img/header_alarm.png" onclick="logo_click()">
		</c:if>
		<img class="header_image" src="img/header_icon.png" onclick="logo_click()">
	</div>



<!-- 메인메뉴================================ -->
	<div id="header_mainMenu">
		<div class="header_main-menu">
			<ul class="login">
		      <li><span onclick="location.href='Login.jsp'">선생님 찾기</span></li>
		      <li><span onclick="location.href='Login.jsp'">학생 찾기</span></li>
		      <li><span onclick="location.href='Login.jsp'">지식인</span></li>
		      <li><span onclick="location.href='Login.jsp'">자료실</span></li>
		      <li><span onclick="location.href='Login.jsp'">1:1 대화</span></li>
		    </ul>
		
		</div>
	</div>
	
	
<!-- 스크립트가 BODY 안에 있음.-------------------------------------------------------- -->
	<script>
	
		function logo_click() {
			location.href = "Main_header.jsp";
		}
		
		function logout(f){
			alert("로그아웃 되었습니다.");
			location.href = "logout.do";
		}
		
		var text = baffle(".text__glitch");
		text.set({
			characters: "█▓█ ▒░/▒░ █░▒▓/ █▒▒ ▓▒▓/█ ░█▒/ ▒▓░ █<░▒ ▓/░>",
			speed: 120
		});
		text.start();
		text.reveal(4000);
	
		setInterval(function(){
		var a = 3999; var text = baffle(".text__glitch");
		text.set({
			characters: "█▓█ ▒░/▒░ █░▒▓/ █▒▒ ▓▒▓/█ ░█▒/ ▒▓░ █<░▒ ▓/░>",
			speed: 120
		});
		text.start();
		text.reveal(3999);},4001);
		
	</script>
<!-- 스크립트 끝------------------------------------------------------------------- -->

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="vo" items="${ list }">
		
		<div class="print" onclick="send('${ vo.id }')">
						
			<div class="list">
							
				<div class="line">			
					<c:if test="${vo.hyn eq '희망'}">
						<span class="flag b1">${ vo.hyn }</span>
					</c:if>
					<c:if test="${vo.hyn eq '희망하지않음'}">
						<span class="flag b2">${ vo.hyn }</span>
					</c:if>
				</div>
				<div class="line" style="font-weight:bold;">${ vo.nickname }</div>
				<div class="line"><i class="fa-solid fa-user fa-3x"></i>${ vo.grade } ${ vo.gender }</div>
				<div class="line"><i class="fa-solid fa-book fa-3x"></i>${ vo.subject }</div>
				<div class="line"><i class="fa-solid fa-location-dot fa-3x"></i>${ vo.addr }</div>
				<div class="line"><i class="fa-solid fa-hashtag fa-3x"></i>${ vo.hashtag }</div>
				<div class="line"><i class="fa-solid fa-headset fa-3x"></i>${ vo.myn }</div>		
						
			</div>
			
				<div class="video">
					<c:if test="${vo.gender eq '남자'}">
					<img src="/ZV2Project/Images/default_picture1.png" style="height:100%; width:100%;">
					</c:if>
					<c:if test="${vo.gender eq '여자'}">
					<img src="/ZV2Project/Images/default_picture2.png" style="height:100%; width:100%;">
					</c:if>
				</div>
			</div>
		</c:forEach>
		
		<input class="lc" type="hidden" value="${length}${compare}">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


		<c:forEach var="vo" items="${ list }">

				<div class="print">

					<div class="list" onclick="send('${ vo.id }')">

						<div class="line">
							<c:if test="${!empty vo.badge1 }">
								<span class="flag b1">${ vo.badge1 }</span>
							</c:if>
							<c:if test="${!empty vo.badge2 }">
								<span class="flag b2">${ vo.badge2 }</span>
							</c:if>
							<c:if test="${!empty vo.badge3 }">
								<span class="flag b3">${ vo.badge3 }</span>
							</c:if>
						</div>
						<div class="line" style="font-weight: bold;">${ vo.nickname }</div>
						<div class="line">
							<i class="fa-solid fa-user fa-3x"></i>${ vo.gender }</div>
						<div class="line">
							<i class="fa-solid fa-book fa-3x"></i>${ vo.subject }</div>
						<div class="line">
							<i class="fa-solid fa-location-dot fa-3x"></i>${ vo.area }</div>
						<div class="line">
							<i class="fa-solid fa-hashtag fa-3x"></i>${ vo.hashtag }</div>
						<div class="line">
							<i class="fa-solid fa-clipboard-question fa-3x"></i>${ vo.mbti }</div>
						<div class="line">
							<i class="fa-solid fa-headset fa-3x"></i>${ vo.myn }</div>

					</div>

					<div class="video" onclick="modalOn_video('${ vo.id }')">
						<img src="/ZV2Project/Images/video.png"
							style="width: 200px; height: 200px;">
					</div>

					<div id="modal_video${vo.id }" class="modal_video">
						<div id="real_modal_video">
							<div id="modal_header_video">
								<h2>강의 샘플 영상</h2>
							</div>

							<form>
								<div id="modal_body_video">
									<video controls>
										<source src="../upload/${vo.title }" type="video/mp4">
									</video>
								</div>
								<div id="modal_end_video">
									<!-- <input class="t_id" type="hidden" name="t_id" value=""> -->
									<input type="hidden" name="video" value="video"> <input
										type="button" value="닫기" onclick="modalOn_video('${vo.id}')">
									<input type="button" value="더보기" onclick="send2('${vo.id}');">
								</div>
							</form>

						</div>
					</div>



				</div>

			</c:forEach>
		
		<input class="lc" type="hidden" value="${length}${compare}">
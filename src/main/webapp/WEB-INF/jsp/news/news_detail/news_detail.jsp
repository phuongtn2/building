<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row animated fadeInRight">
	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5><spring:message code="news.list.internalnews" text="default text"/></h5>
				<div class="ibox-tools">
					<a onclick="goBack()" class="btn btn-warning btn btn-xs"><spring:message code="building.buttonBack"
					                                                                         text="default text"></spring:message></a>
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content inspinia-timeline" onclick="edit(${newsDto.newsCode})">
				<div class="timeline-item">
					<div class="row">
						<div class="col-xs-3 date">
							<i class="fa fa-users"></i>
							<fmt:formatDate pattern="MM/dd/yyyy" value="${newsDto.created}"/>
							<br/>
							<small class="text-navy">2 hour ago</small>
						</div>
						<div class="col-xs-9 content no-top-border">
							<div class="row">
								<div class="col-lg-9">
									<p class="m-b-xs" style="word-break: break-word">
										<strong>${newsDto.newsHeader}</strong></p>
									<p style="word-break: break-word">${newsDto.newsShorter}</p>
									<p style="word-break: break-word">${newsDto.newsContent}</p>
								</div>
								<div class="col-lg-3">
									<div class="btn-group">
										<a class="btn btn-success btn btn-xs" href="/news"><spring:message
												code="common.button.back" text="default text"/></a>
										<a class="btn btn-success btn btn-xs"
										   href="/news/edit/${newsDto.newsCode}"><spring:message
												code="common.button.edit" text="default text"/></a>
										<a class="btn btn-danger btn btn-xs"
										   href="/news/delete/${newsDto.newsCode}"><spring:message
												code="common.button.delete" text="default text"/></a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
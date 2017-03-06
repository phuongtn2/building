<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row animated fadeInRight">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5><spring:message code="news.list.internalnews" text="default text" /></h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <c:forEach items="${newsNewsDetailDtoList}" var="news">
                <div class="ibox-content inspinia-timeline" onclick="edit(${news.masterNewsDto.newsCode})">
                    <div class="timeline-item">
                        <div class="row">
                            <div class="col-xs-3 date">
                                <i class="fa fa-users"></i>
                                <fmt:formatDate pattern="MM/dd/yyyy" value="${news.masterNewsDto.created}" />
                                <br/>
                                <small class="text-navy">2 hour ago</small>
                            </div>
                            <div class="col-xs-9 content no-top-border">
                                <div class="row">
                                    <div class="col-lg-9">
                                        <p class="m-b-xs" style="word-break: break-all"><strong>${news.masterNewsDto.newsHeader}</strong></p>
                                        <c:if test="${news.newsDetailDtoList!=null}">
                                            <c:forEach items="${news.newsDetailDtoList}" var="newsDetail">
                                                <a style="word-break: break-all" href="/news/${newsDetail.refNewsCode}">${newsDetail.masterNewsDto.newsHeader}</a><BR>
                                            </c:forEach>
                                        </c:if>
                                        <p style="word-break: break-all">${news.masterNewsDto.newsShorter}</p>
                                    </div>
                                    <div class="col-lg-3">
                                        <div class="btn-group">
                                            <a class="btn btn-success btn btn-xs" href="/news/${news.masterNewsDto.newsCode}" ><spring:message code="common.button.view" text="default text" /></a>
                                            <a class="btn btn-success btn btn-xs" href="/news/edit/${news.masterNewsDto.newsCode}" ><spring:message code="common.button.edit" text="default text" /></a>
                                            <a class="btn btn-danger btn btn-xs" href="/news/delete/${news.masterNewsDto.newsCode}"><spring:message code="common.button.delete" text="default text" /></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
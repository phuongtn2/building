<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<spring:url value="/resources/img/flags/16/United-States.png" var="EN"/>
<spring:url value="/resources/img/flags/16/Vietnam.png" var="VN"/>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row border-bottom">
    <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#">
                <i class="fa fa-bars"></i>
            </a>
        </div>

        <ul class="nav navbar-top-links navbar-right">
            <li>
                <span><a href="${pageContext.request.contextPath}?locale=en"><spring:message code="building.langgue.english" text="default text" /></a>|<a href="${pageContext.request.contextPath}?locale=vi"><spring:message code="building.langgue.vietnamese" text="default text" /></a></span>
                <span class="m-r-sm text-muted welcome-message">${aui.fullName}.</span>
            </li>
            <li>
                <a class="right-sidebar-toggle">
                    <i class="fa fa-tasks"></i>
                </a>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                    <i class="fa fa-bell"></i>  <span class="label label-primary">8</span>
                </a>
                <ul class="dropdown-menu dropdown-alerts">
                    <div id = "NotifyListBookingServiceDto">
                    </div>
                    <li class="divider"></li>
                    <li>
                        <div class="text-center link-block">
                            <a href="/notifications">
                                <strong><spring:message code="notify.alert" text="default text" /></strong>
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </div>
                    </li>
                </ul>
            </li>
            <li>
                <a href="/logout">
                    <i class="fa fa-sign-out"></i> <spring:message code="nav.logout" text="default text" />
                </a>
            </li>
            <%--<li>
                <a class="right-sidebar-toggle fa set_vn active"><img src="${VN}"> VN</a>
            </li>

            <li>
                <a class="right-sidebar-toggle fa set_en"><img src="${EN}"> EN</a>
            </li>--%>
        </ul>

    </nav>
</div>
<!DOCTYPE html>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <meta name="google-site-verification" content="PFIaMxdD94R6zfMJeT9ZsevTIjpgW924MgmF1nmEtv0" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><spring:message code="login.title" text="default text" /></title>
    <spring:url value="/resources/css/bootstrap.min.css" var="bootsTrapCss"/>
    <spring:url value="/resources/font-awesome/css/font-awesome.css" var="awesomeFontCss" />
    <spring:url value="/resources/css/animate.css" var="animateCss" />
    <spring:url value="/resources/css/style.css" var="styleCss" />

    <link href="${bootsTrapCss}" rel="stylesheet" type="text/css"/>
    <link href="${awesomeFontCss}" rel="stylesheet" type="text/css"/>
    <link href="${animateCss}" rel="stylesheet" type="text/css"/>
    <link href="${styleCss}" rel="stylesheet" type="text/css"/>

</head>
<body class="gray-bg">
<div class="middle-box text-center loginscreen animated fadeInDown">
    <div>
        <div>
            <h1 class="logo-name">B</h1>
        </div>
        <spring:message code="building.langgue" text="default text" />
        <a href="${pageContext.request.contextPath}?locale=en"><spring:message code="building.langgue.english" text="default text" /></a>|<a href="${pageContext.request.contextPath}?locale=vi"><spring:message code="building.langgue.vietnamese" text="default text" /></a>

        <h3><spring:message code="building.welcome" text="default text" /> </h3>

        <form:form class="m-t" role="form" action="/login" name="submitForm" method="post">
            <div class="form-group">
                <input name="userName" type="text" class="form-control" placeholder="<spring:message code="login.username" text="default text" />" required="">
            </div>
            <div class="form-group">
                <input name="password" type="password" class="form-control" placeholder="<spring:message code="login.password" text="default text" />" required="">
            </div>
            <button type="submit" class="btn btn-primary block full-width m-b"><spring:message code="login.login" text="default text" /></button>
            <div style="color: red">${error}</div>
            <a href="#"><small><spring:message code="login.forgotpassword" text="default text" /></small></a>
        </form:form>
    </div>
</div>

<!-- Mainly scripts -->
<spring:url value="/resources/js/jquery-2.1.1.js" var="jqueryJs" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJs" />
<script src="${jqueryJs}"></script>
<script src="${bootstrapJs}"></script>

</body>
</html>

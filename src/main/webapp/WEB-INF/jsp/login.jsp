<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
            <a href="/forgotpassword"><small><spring:message code="login.forgotpassword" text="default text" /></small></a>
        </form:form>
    </div>
</div>

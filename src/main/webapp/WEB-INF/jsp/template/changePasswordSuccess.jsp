<!DOCTYPE html>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Building | 404 Error</title>

    <spring:url value="/resources/css/bootstrap.min.css" var="bootsTrapCss"/>
    <spring:url value="/resources/font-awesome/css/font-awesome.css" var="awesomeFontCss" />
    <spring:url value="/resources/css/animate.css" var="animateCss" />
    <spring:url value="/resources/css/style.css" var="styleCss" />

    <link href="${bootsTrapCss}" rel="stylesheet" type="text/css"/>
    <link href="${awesomeFontCss}" rel="stylesheet" type="text/css"/>
    <link href="${animateCss}" rel="stylesheet" type="text/css"/>
    <link href="${styleCss}" rel="stylesheet" type="text/css"/>

</head>

<div role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title text-center"><spring:message code="changePassButton.confirmLabel" text="default text"></spring:message></h4>
            </div>
            <div class="modal-body text-center">
                <p><spring:message code="changePassButton.confirmMessage" text="default text"></spring:message></p>
                <p><spring:message code="changePassButton.changeButtonMessage" text="default text"></spring:message></p>
                <p><spring:message code="changePassButton.cancelButtonMessage" text="default text"></spring:message></p>
            </div>
            <div id = "popupWarning" class="modal-body text-center"></div>
            <div class="modal-header text-center">
                <button onclick="changePasswordCheck()" name="addModel" class="btn btn-primary" type="submit"><spring:message code="changePassButton.countinuteButton" text="default text"></spring:message></button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="changePassButton.cancelButton" text="default text"></spring:message></button>
            </div>
        </div>

    </div>
</div>
</html>

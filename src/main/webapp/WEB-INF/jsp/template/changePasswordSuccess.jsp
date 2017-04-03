<!DOCTYPE html>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
</head>
<body>
<div role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title text-center"><spring:message code="changePassword.successLabel" text="default text"></spring:message></h4>
            </div>
            <div class="modal-body text-center">
                <p><spring:message code="changePassword.successMesg" text="default text"></spring:message></p>
            </div>
            <div class="modal-header text-center">
                <button onclick="setUrl('/profile')" class="btn btn-primary"><spring:message code="changePassButton.countinuteButton" text="default text"></spring:message></button>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    function setUrl(url){
        $("#url").val(url);
    }
</script>
</html>

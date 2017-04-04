<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page language="java" contentType="text/html;charset=UTF-8" %>
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
                <button onclick="window.location.href='/profile'" class="btn btn-primary"><spring:message code="changePassButton.countinuteButton" text="default text"></spring:message></button>
            </div>
        </div>
    </div>
</div>




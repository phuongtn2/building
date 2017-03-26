<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div class="middle-box text-center loginscreen animated fadeInDown">
	<div>
		<div>
			<h1 class="logo-name">B</h1>
		</div>
		<spring:message code="building.langgue" text="default text"/>
		<a href="${pageContext.request.contextPath}?locale=en"><spring:message code="building.langgue.english"
		                                                                       text="default text"/></a>|<a
			href="${pageContext.request.contextPath}?locale=vi"><spring:message code="building.langgue.vietnamese"
		                                                                        text="default text"/></a>

		<h3><spring:message code="building.welcome" text="default text"/></h3>

		<form:form class="m-t" role="form" action="/changepass" name="submitForm" method="post">
			<label ><spring:message code="changePassword.pass" text="default text" /></label>
			<div class="form-group">
				<input name="newPassword" type="password" class="form-control" id="newPassword"
				       placeholder="<spring:message code="changePassword.pass" text="default text" />" required="">
			</div>
			<label ><spring:message code="changePassword.retypePass" text="default text" /></label>
			<div class="form-group">
				<input name="verifyPassword" type="password" class="form-control" id="verifyPassword" onkeyup="checkValidPassword();"
				       placeholder="<spring:message code="changePassword.retypePass" text="default text" />" required="">
				<a id="warningMessage"></a>
			</div>
			<button type="button" class="btn btn-primary" data-toggle="modal"
			        data-target="#changePassButton"><spring:message code="changePassword.changePassButton"
			                                                                                   text="default text"/></button>
		</form:form>
	</div>
</div>
<!-- Modal -->
<div class="modal fade" id="changePassButton" role="dialog">
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
			<div class="modal-header text-center">
				<button onclick="changePasswordCheck()" name="addModel" class="btn btn-primary" type="submit"><spring:message code="changePassButton.countinuteButton" text="default text"></spring:message></button>
				<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="changePassButton.cancelButton" text="default text"></spring:message></button>
			</div>
		</div>

	</div>
</div>

<script>
	function changePasswordCheck(){


	};
	function checkValidPassword(){
		var html ="";
		var newPassword = $("#newPassword").val();
		var verifyPassword = $("#verifyPassword").val();
		if(newPassword != verifyPassword) html =  '<a style="color: red"><spring:message code="changePassButton.warningMessageNotMatch" text="default text"></spring:message></a>';
		else html =  '<a style="color: blueviolet"><spring:message code="changePassButton.warningMessageMatch" text="default text"></spring:message></a>';
		$('#warningMessage').html(html)
	}

</script>
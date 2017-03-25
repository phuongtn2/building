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

		<form:form class="m-t" role="form" action="/login" name="submitForm" method="post">
			<div class="form-group">
				<input name="userName" type="text" class="form-control"
				       placeholder="<spring:message code="changePassword.pass" text="default text" />" required="">
			</div>
			<div class="form-group">
				<input name="password" type="password" class="form-control"
				       placeholder="<spring:message code="changePassword.retypePass" text="default text" />" required="">
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
				<h4 class="modal-title text-center">Xác nhận thêm</h4>
			</div>
			<div class="modal-body text-center">
				<p>Bạn có muốn thêm</p>
				<p>Chọn "Xác nhận" để đi tiếp</p>
				<p>Chọn "Hủy" để quay lại</p>
			</div>
			<div class="modal-header text-center">
				<button onclick="changePassCheck()" name="addModel" class="btn btn-primary" type="submit">Xác nhận</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
			</div>
		</div>

	</div>
</div>

<script>
	function submit(){

	};

</script>
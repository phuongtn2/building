<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="row">
	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5><spring:message code="asset.title" text="default text"/></h5>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<form:form modelAttribute="masterAssetDto" method="post" id="userForm">
				<div class="row">
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label"><spring:message code="asset.name" text="default text"/></label>
							<input type="hidden" id="assetCode" name="assetCode"
							       value="<c:if test="${masterAssetDto.assetCode!= null}">${masterAssetDto.assetCode}</c:if>"
							       class="form-control">
							<input type="text" id="assetName" name="assetName"
							       value="<c:if test="${masterAssetDto.assetName!= null}">${masterAssetDto.assetName}</c:if>"
							       class="form-control" required>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="form-group">
							<label class="control-label"><spring:message code="asset.type" text="default text"/></label>
							<select name="assetType" class="form-control m-b">
								<option
										<c:if test="${masterAssetDto.assetType==1}">selected</c:if> value="1">
									<spring:message code="asset.type.general" text="default text"/></option>
								<option
										<c:if test="${masterAssetDto.assetType==2}">selected</c:if> value="2">
									<spring:message code="asset.type.personal" text="default text"/></option>
							</select>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="form-group">
							<label class="control-label"><spring:message code="asset.building"
							                                             text="default text"/></label>
							<select id="buildingCode" name="buildingCode" class="form-control m-b">
								<c:forEach items="${buildingDtoDtoList}" var="building">
									<option
											<c:if test="${masterServiceDto.buildingCode==building.buildingCode}">selected</c:if>
											value="${building.buildingCode}">${building.buildingName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="form-group">
							<label class="control-label"><spring:message code="common.status"
							                                             text="default text"/></label>
							<select id="status" name="status" class="form-control m-b">
								<option
										<c:if test="${masterAssetDto.status==1}">selected</c:if> value="1">
									<spring:message code="common.active" text="default text"/></option>
								<option
										<c:if test="${masterAssetDto.status==0}">selected</c:if> value="0">
									<spring:message code="common.deactive" text="default text"/></option>
							</select>
						</div>
					</div>
					<div class="col-sm-2">
						<div class="form-group">
							<label class="control-label"><spring:message code="asset.price"
							                                             text="default text"/></label>
							<input type="text" id="price" name="price" value="${masterAssetDto.price}"
							       class="form-control" required>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<div class="text-center">
							<input type="hidden" id="add" name="add">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addButton">
								<i class="fa fa-check"></i><spring:message code="common.button.save"
								                                           text="default text"/></button>
							<button name="reset" class="btn btn-danger" type="reset"><spring:message
									code="common.button.reset" text="default text"/></button>
						</div>
					</div>
				</div>
			</div>
			</form:form>
		</div>
	</div>
</div>

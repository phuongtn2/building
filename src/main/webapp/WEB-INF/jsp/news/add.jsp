<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div class="row">
	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5><spring:message code="news.add.buildingmanagement" text="default text"/></h5>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<form:form modelAttribute="newsDto " method="post" id="userForm">
					<p><spring:message code="news.add.news" text="default text"/></p>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<input name="newsCode" type="hidden" class="form-control"
								       value="<c:if test="${newsDto.newsCode!= null}">${newsDto.newsCode}</c:if>">
								<label class="control-label"><spring:message code="news.add.title"
								                                             text="default text"/></label>
								<input name="newsHeader" type="text"
								       placeholder="<spring:message code="news.add.title" text="default text" />"
								       class="form-control" required=true
								       value="<c:if test="${newsDto.newHeader!= null}">${newsDto.newHeader}</c:if>">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<div class="col-sm-7">
								<label class="control-label"><spring:message code="news.add.newscode"
								                                             text="default text"/></label>
								<input type="text" name="refNewsCode"
								       placeholder="<spring:message code="news.add.newscode" text="default text" />"
								       class="form-control"
								       value="<c:if test="${newsDto.refNewsCode!= null}">${newsDto.refNewsCode}</c:if>">
							</div>
							<div class="col-sm-5">
								<label class="control-label"><spring:message code="news.add.newstype"
								                                             text="default text"/></label>
								<select class="form-control m-b" name="newsType">
									<option
											<c:if test="${newsDto.newsType==1}">selected</c:if> value="1">
										<spring:message code="news.add.newsType1"
										                text="default text"></spring:message></option>
									<option
											<c:if test="${newsDto.newsType==2}">selected</c:if> value="2">
										<spring:message code="news.add.newsType2"
										                text="default text"></spring:message></option>
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label class="control-label"><spring:message code="news.add.newsshoter"
								                                             text="default text"/></label>
								<textarea name="newsShorter" type="text"
								          placeholder="<spring:message code="news.add.newsshoter" text="default text" />"
								          class="form-control" required=true
								          value="<c:if test="${newsDto.newsShorter!= null}">${newsDto.newsShorter}</c:if>"></textarea>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<div class="text-center">
								<input type="hidden" id="add" name="add">
								<button type="button" class="btn btn-primary" data-toggle="modal"
								        data-target="#addButton"><i class="fa fa-check"></i><spring:message
										code="common.button.save" text="default text"/></button>
								<button name="reset" class="btn btn-danger" type="reset"><spring:message
										code="common.button.reset" text="default text"/></button>
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
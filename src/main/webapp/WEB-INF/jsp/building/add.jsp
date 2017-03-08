<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5><spring:message code="building.title" text="default text" /></h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <form:form modelAttribute="buildingDto" method="post" id="userForm">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label" ><spring:message code="building.name" text="default text" /> </label>
                                <input type="hidden" id="buildingCode" name="buildingCode" value="<c:if test="${buildingDto.buildingCode!= null}">${buildingDto.buildingCode}</c:if>"  class="form-control">
                                <input type="text" id="buildingName" name="buildingName" value="<c:if test="${buildingDto.buildingName!= null}">${buildingDto.buildingName}</c:if>" placeholder="<spring:message code="building.name" text="default text" />" class="form-control" required=true>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label" ><spring:message code="building.floor.count" text="default text" /></label>
                                <input type="text" id="totalFloor" name="totalFloor" value="<c:if test="${buildingDto.totalFloor!= 0}">${buildingDto.totalFloor}</c:if>" placeholder="<spring:message code="building.floor.count" text="default text" />" class="form-control" required=false>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label class="control-label"><spring:message code="building.description" text="default text" /></label>
                                <textarea type="text" rows="4" id="description" name="description" placeholder="<spring:message code="building.description" text="default text" />" class="form-control" required=true><c:if test="${buildingDto.description!= null}">${buildingDto.description}</c:if></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <div class="text-center">
                                <input type="hidden" id="add" name="add">
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addButton"><i class="fa fa-check"></i><spring:message code="common.button.save" text="default text" /></button>
                                <button name="reset" class="btn btn-danger" type="reset"><spring:message code="common.button.reset" text="default text" /></button>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring1" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5><spring1:message code="building.title" text="default text" /></h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <form:form modelAttribute="buildingDto" method="post">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label" ><spring1:message code="building.name" text="default text" /> </label>
                                <input type="hidden" id="buildingCode" name="buildingCode" value="<c:if test="${buildingDto.buildingCode!= null}">${buildingDto.buildingCode}</c:if>" placeholder="Tên tòa nhà" class="form-control">
                                <input type="text" id="buildingName" name="buildingName" value="<c:if test="${buildingDto.buildingName!= null}">${buildingDto.buildingName}</c:if>" placeholder="Tên tòa nhà" class="form-control" required=true>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label" ><spring1:message code="building.floor.count" text="default text" /></label>
                                <input type="text" id="totalFloor" name="totalFloor" value="<c:if test="${buildingDto.totalFloor!= 0}">${buildingDto.totalFloor}</c:if>" placeholder="Tổng số tầng" class="form-control" required=false>
                            </div>
                        </div>
                        <%--<div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label">Tổng số phòng</label>
                                <input type="text" id="totalRoom" name="totalRoom" value="<c:if test="${buildingDto.totalRoom!= 0}">${buildingDto.totalRoom}</c:if>" placeholder="Tổng số phòng" class="form-control">
                            </div>
                        </div>--%>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label class="control-label"><spring1:message code="building.description" text="default text" /></label>
                                <textarea type="text" rows="4" id="description" name="description" placeholder="Mô tả" class="form-control" required=true><c:if test="${buildingDto.description!= null}">${buildingDto.description}</c:if></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="text-center">
                            <button name="add" class="btn btn-primary" type="submit"><i class="fa fa-check"></i>&nbsp;<spring1:message code="button.save" text="default text" /></button>
                            <button name="reset" class="btn btn-danger" type="button"><i class="fa fa-refresh"></i>&nbsp;<spring1:message code="button.reset" text="default text" /></button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
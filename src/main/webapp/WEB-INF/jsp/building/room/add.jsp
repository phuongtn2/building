<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5><spring:message code="building.room.roomInfo" text="default text"></spring:message></h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <form:form modelAttribute="roomDto" method="post" id="userForm">
                    <div class="row">
                        <div class="col-sm-8">
                            <div class="form-group">
                                <label class="control-label" for="roomAlias"><spring:message code="building.room.roomName" text="default text"></spring:message></label>
                                <input type="hidden" id="roomCode" name="roomCode" value="<c:if test="${roomDto.roomCode!= null}">${roomDto.roomCode}</c:if>" class="form-control">
                                <input type="text" id="roomAlias" name="roomAlias" value="<c:if test="${roomDto.roomAlias!= null}">${roomDto.roomAlias}</c:if>" placeholder="Tầng số" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group">
                                <label class="control-label" for="count"><spring:message code="building.room.countDetail" text="default text"></spring:message></label>
                                <input type="text" id="count" name="count" value="<c:if test="${roomDto.count!= 0}">${roomDto.count}</c:if>" placeholder="count" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group">
                                <label class="control-label"><spring:message code="building.room.status" text="default text"></spring:message></label>
                                <select name="status" class="form-control m-b">
                                    <option <c:if test="${roomDto.status==1}" >selected</c:if> value="1">Phòng trống</option>
                                    <option <c:if test="${roomDto.status==2}" >selected</c:if> value="2">Đã thuê</option>
                                </select>
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
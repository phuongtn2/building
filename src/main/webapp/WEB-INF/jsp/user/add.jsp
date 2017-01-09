<%--
  Created by IntelliJ IDEA.
  User: Giang.DaoTu
  Date: 11/11/2016
  Time: 8:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Thông Tin Thành viên</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content m-b-sm border-bottom">
            <form:form  modelAttribute="userRoleRoomDto" method="post" id="userForm">
                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="fullName">Họ Tên</label>
                            <input name="userDto.userId" type="hidden" class="form-control" value="<c:if test="${userRoleRoomDto.userDto.userId!= null}">${userRoleRoomDto.userDto.userId}</c:if>">
                            <input type="text" id="fullName" name="userDto.fullName" value="<c:if test="${userRoleRoomDto.userDto.fullName!= null}">${userRoleRoomDto.userDto.fullName}</c:if>" placeholder="Họ Tên" class="form-control">
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" >Ngày sinh</label>
                            <div class="input-group date">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input id="birthday" name="userDto.birthday" placeholder="Ngày sinh" class="form-control" value="<c:if test="${userRoleRoomDto.userDto.birthday!= null}"><fmt:formatDate pattern="MM/dd/yyyy" value="${userRoleRoomDto.userDto.birthday}"></fmt:formatDate></c:if>" >
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label">Chứng minh nhân dân</label>
                            <input  type="text" id="userDto.idCard" name="userDto.idCard" value="<c:if test="${userRoleRoomDto.userDto.idCard!= 0}">${userRoleRoomDto.userDto.idCard}</c:if>" placeholder="Chứng minh thư" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label class="control-label">Giới tính</label>
                                    <div>
                                        <input id ="genderM" type="radio" name="userDto.gender" value="1" <c:if test="${userRoleRoomDto.userDto.gender == 1 || userRoleRoomDto.userDto.gender == null}">checked="checked"</c:if> >&nbsp;&nbsp;Nam&nbsp;&nbsp;&nbsp;
                                        <input id ="genderF" type="radio" name="userDto.gender" value="2" <c:if test="${userRoleRoomDto.userDto.gender == 2}">checked="checked"</c:if> >&nbsp;&nbsp;Nữ&nbsp;&nbsp;&nbsp;
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-8">
                                <div class="form-group">
                                    <label class="control-label">Điện thoại</label>
                                    <input type="text" id="tel" name="userDto.tel" value="<c:if test="${userRoleRoomDto.userDto.tel!= null}">${userRoleRoomDto.userDto.tel}</c:if>" placeholder="Điện thoại" class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-8">
                        <div class="form-group">
                            <label class="control-label">Địa chỉ</label>
                            <input type="text" id="adress" name="userDto.address" value="<c:if test="${userRoleRoomDto.userDto.address!= null}">${userRoleRoomDto.userDto.address}</c:if>" placeholder="Địa chỉ" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label">adId</label>
                            <input type="text" id="adId" name="userDto.adId" value="<c:if test="${userRoleRoomDto.userDto.adId!= null}">${userRoleRoomDto.userDto.adId}</c:if>" placeholder="adId" class="form-control">
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label">Mail</label>
                            <input type="text" id="mail" name="userDto.mail" value="<c:if test="${userRoleRoomDto.userDto.mail!= null}">${userRoleRoomDto.userDto.mail}</c:if>" placeholder="Mail" class="form-control">
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="form-group">
                            <label class="control-label">Role</label>
                            <select class="form-control m-b" name="userRoleGroupDto.roleGroupId">
                                <option <c:if test="${userRoleRoomDto.userRoleGroupDto.roleGroupId == 1}">selected="selected"</c:if> value="1">Admin</option>
                                <option <c:if test="${userRoleRoomDto.userRoleGroupDto.roleGroupId == 2}">selected="selected"</c:if> value="2">Member</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="form-group">
                            <label class="control-label">User Status</label>
                            <select class="form-control m-b" name="userDto.userStatus">
                                <option <c:if test="${userRoleRoomDto.userDto.userStatus == 1}">selected="selected"</c:if> value="1">Active</option>
                                <option <c:if test="${userRoleRoomDto.userDto.userStatus == 2}">selected="selected"</c:if> value="2">Deactive</option>
                                <option <c:if test="${userRoleRoomDto.userDto.userStatus == 3}">selected="selected"</c:if> value="3">Pending</option>
                            </select>

                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label class="control-label">Ghi chú</label>
                            <textarea type="text" rows="4" id="memo" name="userRoleGroupDto.memo" value="<c:if test="${userRoleRoomDto.userRoleGroupDto.memo!= null}">${userRoleRoomDto.userRoleGroupDto.memo}</c:if>" placeholder="Ghi chú" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <div class="text-center">
                            <input type="hidden" id="add" name="add">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addButton"><i class="fa fa-check"></i>&nbsp;Submit</button>
                            <input name="reset" class="btn btn-danger" placeholder="Nhập lại" type="reset">
                        </div>
                    </div>
                </div>

            </form:form>
            </div>
        </div>
    </div>
</div>






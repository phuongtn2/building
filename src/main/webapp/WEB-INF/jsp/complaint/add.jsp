<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Quản Lý Tòa Nhà</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <form:form modelAttribute="complaintDto" method="post">
                    <p>Phan hoi danh gia</p>
                    <div class="row">
                        <div class="form-group">
                            <input name="complaintCode" type="hidden"  class="form-control" value="<c:if test="${complaintDto.complaintCode!= null}">${complaintDto.complaintCode}</c:if>">
                            <div class="col-sm-10">
                                <label class="control-label">Tiêu đề</label>
                                <input name="title" type="text" placeholder="Tiêu đề" class="form-control" required=true value="<c:if test="${complaintDto.title!= null}">${complaintDto.title}</c:if>">
                            </div>
                            <div class="col-sm-2">
                                <label class="control-label">Dich Vu</label>
                                    <%--<input type="text" id="address" name="address" value="<c:if test="${memberDto.address!= null}">${memberDto.address}</c:if>" placeholder="Địa chỉ" class="form-control">--%>
                                <select name="serviceCode" class="form-control m-b" >
                                    <c:forEach items="${serviceDtoList}" var="service">
                                        <option value="${service.serviceCode}">${service.serviceName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="text-center">
                            <br>
                            <button name="add" class="btn btn-primary" type="submit"><i class="fa fa-check"></i>&nbsp;Submit</button>
                            <input name="reset" class="btn btn-danger" placeholder="Nhập lại" type="reset" ></input>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
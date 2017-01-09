<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Quản lý dịch vụ</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <form:form modelAttribute="masterServiceDto" method="post">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label">Tên dịch vụ</label>
                                <input type="hidden" id="serviceCode" name="serviceCode" value="<c:if test="${masterServiceDto.serviceCode!= null}">${masterServiceDto.serviceCode}</c:if>"  class="form-control">
                                <input type="text" id="serviceName" name="serviceName" value="<c:if test="${masterServiceDto.serviceName!= null}">${masterServiceDto.serviceName}</c:if>" placeholder="Tên dịch vụ" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label">Loại dịch vụ</label>
                                <select name="serviceType" class="form-control m-b">
                                    <option <c:if test="${masterServiceDto.serviceType==1}" >selected</c:if> value="1">serviceType 1</option>
                                    <option <c:if test="${masterServiceDto.serviceType==2}" >selected</c:if> value="2">serviceType 2</option>
                                    <option <c:if test="${masterServiceDto.serviceType==3}" >selected</c:if> value="3">serviceType 3</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label"">Giá dịch vụ</label>
                                <input  type="text" id="servicePrice" name="servicePrice" value="<c:if test="${masterServiceDto.servicePrice!= null}">${masterServiceDto.servicePrice}</c:if>" placeholder="Giá dịch vụ" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="control-label">Bắt đầu</label>
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="serviceStart" name="serviceStart" type="text" class="form-control" value="<c:if test="${masterServiceDto.serviceStart!= null}"><fmt:formatDate pattern="MM/dd/yyyy" value="${masterServiceDto.serviceStart}" /></c:if>">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="control-label">Kết thúc</label>
                                <div class="input-group ">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="serviceEnd" name="serviceEnd" type="text" class="form-control"  value="<c:if test="${masterServiceDto.serviceEnd!= null}"><fmt:formatDate pattern="MM/dd/yyyy" value="${masterServiceDto.serviceEnd}" /></c:if>">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <div class="text-center">
                                <button name="add" class="btn btn-primary" type="submit"><i class="fa fa-check"></i>&nbsp;Submit</button>
                                <input name="reset" class="btn btn-danger" placeholder="Nhập lại" type="reset">
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Tài Sản Chung</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <form:form modelAttribute="masterAssetDto" method="post">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label" >Tên tài sản</label>
                                <input type="hidden" id="assetCode" name="assetCode" value="<c:if test="${masterAssetDto.assetCode!= null}">${masterAssetDto.assetCode}</c:if>"  class="form-control">
                                <input type="text" id="assetName" name="assetName" value="<c:if test="${masterAssetDto.assetName!= null}">${masterAssetDto.assetName}</c:if>" placeholder="Tên dịch vụ" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label">Loại tài sản</label>
                                <select name="assetType" class="form-control m-b">
                                    <option <c:if test="${masterAssetDto.assetType==1}" >selected</c:if> value="1">assetType 1</option>
                                    <option <c:if test="${masterAssetDto.assetType==2}" >selected</c:if> value="2">assetType 2</option>
                                    <option <c:if test="${masterAssetDto.assetType==3}" >selected</c:if> value="3">assetType 3</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label">Tòa nhà</label>
                                <input  type="text" id="buildingCode" name="buildingCode" value="<c:if test="${masterAssetDto.buildingCode!= null}">${masterAssetDto.buildingCode}</c:if>" placeholder="Giá dịch vụ" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="control-label">Bắt đầu</label>
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="dateFrom" name="dateFrom" type="text" class="form-control" value="<c:if test="${masterAssetDto.dateFrom!= null}"><fmt:formatDate pattern="MM/dd/yyyy" value="${masterAssetDto.dateFrom}" /></c:if>">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="control-label">Kết thúc</label>
                                <div class="input-group ">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="dateTo" name="dateTo" type="text" class="form-control"  value="<c:if test="${masterAssetDto.dateTo!= null}"><fmt:formatDate pattern="MM/dd/yyyy" value="${masterAssetDto.dateTo}" /></c:if>">
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
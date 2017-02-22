<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="locale" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5><locale:message code="asset.title" text="default text" /></h5>
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
                                <label class="control-label" ><locale:message code="asset.name" text="default text" /></label>
                                <input type="hidden" id="assetCode" name="assetCode" value="<c:if test="${masterAssetDto.assetCode!= null}">${masterAssetDto.assetCode}</c:if>"  class="form-control">
                                <input type="text" id="assetName" name="assetName" value="<c:if test="${masterAssetDto.assetName!= null}">${masterAssetDto.assetName}</c:if>" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group">
                                <label class="control-label"><locale:message code="asset.type" text="default text" /></label>
                                <select name="assetType" class="form-control m-b">
                                    <option <c:if test="${masterAssetDto.assetType==1}" >selected</c:if> value="1"><locale:message code="asset.type.general" text="default text" /></option>
                                    <option <c:if test="${masterAssetDto.assetType==2}" >selected</c:if> value="2"><locale:message code="asset.type.personal" text="default text" /></option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label"><locale:message code="asset.building" text="default text" /></label>
                                <input  type="text" id="buildingCode" name="buildingCode" value="<c:if test="${masterAssetDto.buildingCode!= null}">${masterAssetDto.buildingCode}</c:if>" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group">
                                <label class="control-label"><locale:message code="asset.price" text="default text" /></label>
                                <input  type="text" id="assetPrice" name="assetPrice" value="<c:if test="${masterAssetDto.buildingCode!= null}">${masterAssetDto.buildingCode}</c:if>" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <div class="text-center">
                                <button name="add" class="btn btn-primary" type="submit"><i class="fa fa-check"></i>&nbsp;<locale:message code="button.save" text="default text" /></button>
                                <button name="reset" class="btn btn-danger" type="button"><i class="fa fa-refresh"></i>&nbsp;<locale:message code="button.reset" text="default text" /></button>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
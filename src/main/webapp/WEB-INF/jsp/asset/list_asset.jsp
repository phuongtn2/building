<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5><spring:message code="nav.assetlist" text="default text" /></h5>

                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <input type="text" class="form-control input-sm m-b-xs" id="filter" placeholder="<spring:message code="common.search" text="default text" />">

                <table class="footable emp-sales table table-stripped table-bordered table-hover dataTables-example" data-page-size="8" data-filter=#filter>
                    <thead>
                    <tr>
                        <th><spring:message code="asset.name" text="default text" /></th>
                        <th data-hide="phone,tablet"><spring:message code="asset.type" text="default text" /></th>
                        <th data-hide="phone,tablet"><spring:message code="asset.building" text="default text" /></th>
                        <th data-hide="phone,tablet"><spring:message code="common.status" text="default text" /></th>
                        <th data-hide="phone,tablet"><spring:message code="asset.price" text="default text" /></th>
                        <th data-hide="phone,tablet" class=T"text-center"><spring:message code="common.action" text="default text" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${masterAssetDtoList}" var="masterAsset">
                        <tr class="gradeC" >
                            <td>${masterAsset.assetName}</td>
                            <td data-hide="phone,tablet">
                                <c:if test="${masterAsset.assetType==1}" ><spring:message code="asset.type.general" text="default text" /></c:if>
                                <c:if test="${masterAsset.assetType==2}" ><spring:message code="asset.type.personal" text="default text" /></c:if>
                            </td>
                            <td data-hide="phone,tablet">
                                <c:forEach items="${buildingDtoDtoList}" var="building">
                                    <c:if test="${masterAsset.buildingCode==building.buildingCode}" >${building.buildingName}</c:if>
                                </c:forEach>
                            </td>
                            <td data-hide="phone,tablet">
                                <c:if test="${masterAsset.status==1}" ><spring:message code="status.active" text="default text" /></c:if>
                                <c:if test="${masterAsset.status==0}" ><spring:message code="status.disable" text="default text" /></c:if>
                            </td>
                            <td data-hide="phone,tablet">${masterAsset.price} $</td>
                            <td class="text-center">
                                <div class="btn-group">
                                    <a class="btn-success btn btn-xs" href="/asset/edit/${masterAsset.assetCode}"><spring:message code="common.button.edit" text="default text" /></a>
                                    <a type="button" data-toggle="modal" data-target="#deleteButton" class="btn-danger btn btn-xs" onclick="setUrl('/asset/delete/${masterAsset.assetCode}')"><spring:message code="common.button.delete" text="default text" /></a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="13">
                            <ul class="pagination pull-right"></ul>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>

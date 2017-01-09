<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Danh sách tài sản</h5>

                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <input type="text" class="form-control input-sm m-b-xs" id="filter" placeholder="Tìm kiếm">

                <table class="footable emp-sales table table-stripped table-bordered table-hover dataTables-example" data-page-size="8" data-filter=#filter>
                    <thead>
                    <tr>
                        <th>assetName</th>
                        <th data-hide="phone,tablet">assetType</th>
                        <th data-hide="phone,tablet">buildingCode</th>
                        <th data-hide="phone,tablet">dateFrom</th>
                        <th data-hide="phone,tablet">dateTo</th>
                        <th data-hide="phone,tablet" class="text-center">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${masterAssetDtoList}" var="masterAsset">
                        <tr class="gradeC" >
                            <td>${masterAsset.assetName}</td>
                            <td data-hide="phone,tablet">
                                <c:if test="${masterAsset.assetType==1}" >assetType 1 </c:if>
                                <c:if test="${masterAsset.assetType==2}" >assetType 2 </c:if>
                                <c:if test="${masterAsset.assetType==3}" >assetType 3 </c:if>
                            </td>
                            <td data-hide="phone,tablet">${masterAsset.buildingCode}</td>
                            <td data-hide="phone,tablet"><fmt:formatDate pattern="MM/dd/yyyy" value="${masterAsset.dateFrom}" /></td>
                            <td data-hide="phone,tablet"><fmt:formatDate pattern="MM/dd/yyyy" value="${masterAsset.dateTo}" /></td>
                            <td class="text-center">
                                <div class="btn-group">
                                    <a class="btn-success btn btn-xs" href="/asset/edit/${masterAsset.assetCode}">Edit</a>
                                    <a class="btn-danger btn btn-xs" href="/asset/delete/${masterAsset.assetCode}">Delete</a>
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

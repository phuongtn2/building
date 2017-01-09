<%--
  Created by IntelliJ IDEA.
  User: Giang.DaoTu
  Date: 11/11/2016
  Time: 8:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Danh sách dịch vụ</h5>

                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <input type="text" class="form-control input-sm m-b-xs" id="filter"
                       placeholder="Tìm kiếm dịch vụ">

                <table class="footable emp-sales table table-stripped table-bordered table-hover dataTables-example" data-page-size="8" data-filter=#filter>
                    <thead>
                    <tr>
                        <th>serviceCode</th>
                        <th data-hide="phone,tablet">servicePrice</th>
                        <th data-hide="phone,tablet">assetCode</th>
                        <th data-hide="phone,tablet">Trạng thái dịch vụ</th>
                        <th data-hide="phone,tablet">bookFrom</th>
                        <th data-hide="phone,tablet">bookTo</th>
                        <th data-hide="phone,tablet" class="text-center">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${bookServiceDtoList}" var="bookServiceDto">
                        <tr class="gradeC" >
                            <td>
                                <c:if test="${bookServiceDto.serviceCode==1}" >serviceCode 1 </c:if>
                                <c:if test="${bookServiceDto.serviceCode==2}" >serviceCode 2 </c:if>
                                <c:if test="${bookServiceDto.serviceCode==3}" >serviceCode 3 </c:if>
                            </td>
                            <td data-hide="phone,tablet">${bookServiceDto.servicePrice}</td>
                            <td data-hide="phone,tablet">
                                <c:if test="${bookServiceDto.assetCode==1}" >assetCode 1 </c:if>
                                <c:if test="${bookServiceDto.assetCode==2}" >assetCode 2 </c:if>
                                <c:if test="${bookServiceDto.assetCode==3}" >assetCode 3 </c:if>
                            </td>
                            <td data-hide="phone,tablet">
                                <c:if test="${bookServiceDto.status==1}" >status 1 </c:if>
                                <c:if test="${bookServiceDto.status==2}" >status 2 </c:if>
                                <c:if test="${bookServiceDto.status==3}" >status 3 </c:if>
                            </td>
                            <td data-hide="phone,tablet"><fmt:formatDate pattern="MM/dd/yyyy" value="${bookServiceDto.bookFrom}" /></td>
                            <td data-hide="phone,tablet"><fmt:formatDate pattern="MM/dd/yyyy" value="${bookServiceDto.bookTo}" /></td>
                            <td class="text-center">
                                <div class="btn-group">
                                    <a class="btn-success btn btn-xs" href="/request_booking/edit/${bookServiceDto.t_bookServiceCode}">Edit</a>
                                    <a class="btn-danger btn btn-xs" href="/request_booking/delete/${bookServiceDto.t_bookServiceCode}">Delete</a>
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


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
                <h5><spring:message code="user.list" text="default text" /></h5>

                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <input type="text" class="form-control input-sm m-b-xs" id="filter"
                       placeholder="<spring:message code="common.search" text="default text" />">

                <table class="footable emp-sales table table-stripped table-bordered table-hover dataTables-example" data-page-size="8" data-filter=#filter>
                    <thead>
                    <tr>
                        <th><spring:message code="user.fullname" text="default text" /></th>
                        <th data-hide="phone,tablet"><spring:message code="user.username" text="default text" /></th>
                        <th data-hide="phone,tablet"><spring:message code="user.status" text="default text" /></th>
                        <th data-hide="phone,tablet"><spring:message code="common.startdate" text="default text" /></th>
                        <th data-hide="phone,tablet"><spring:message code="common.enddate" text="default text" /></th>
                        <th data-hide="phone,tablet"><spring:message code="user.note" text="default text" /></th>
                        <th  data-hide="phone,tablet" class="text-center"><spring:message code="common.action" text="default text" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userDtoList}" var="user">
                        <tr class="gradeC" >
                            <td>${user.fullName}</td>
                            <td data-hide="phone,tablet">${user.adId}</td>
                            <td data-hide="phone,tablet">
                                <c:if test="${user.userStatus==1}" > <span class="label label-success"><spring:message code="common.active" text="default text" /></span> </c:if>
                                <c:if test="${user.userStatus==2}" > <span class="label label-danger"><spring:message code="common.deactive" text="default text" /></span> </c:if>
                                <c:if test="${user.userStatus==3}" > <span class="label label-primary"><spring:message code="common.pending" text="default text" /></span> </c:if>
                            </td>
                            <td data-hide="phone,tablet"><fmt:formatDate pattern="MM/dd/yyyy" value="${user.startDay}" /></td>
                            <td data-hide="phone,tablet"><fmt:formatDate pattern="MM/dd/yyyy" value="${user.endDay}" /></td>
                            <td data-hide="phone,tablet">${user.memo}</td>
                            <td class="text-center">
                                <div class="btn-group">
                                    <a class="btn-success btn btn-xs" href="/user/edit/${user.userId}"><spring:message code="common.button.edit" text="default text" /></a>
                                    <a type="button" data-toggle="modal" data-target="#deleteButton" class="btn-danger btn btn-xs" onclick="setUrl('/user/delete/${user.userId}')"><spring:message code="common.button.delete" text="default text" /></a>
                                    <a class="btn-success btn btn-xs" href="/user/view/${user.userId}"><spring:message code="common.button.view" text="default text" /></a>
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

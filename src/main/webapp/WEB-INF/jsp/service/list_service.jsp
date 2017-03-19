<%--
  Created by IntelliJ IDEA.
  User: Giang.DaoTu
  Date: 11/11/2016
  Time: 8:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">
	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5><spring:message code="service.list" text="default text"/></h5>

				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<input type="text" class="form-control input-sm m-b-xs" id="filter"
				       placeholder="<spring:message code="common.search" text="default text" />">

				<table class="footable emp-sales table table-stripped table-bordered table-hover dataTables-example"
				       data-page-size="8" data-filter=#filter>
					<thead>
					<tr>
						<th><spring:message code="service.name" text="default text"/></th>
						<th data-hide="phone,tablet"><spring:message code="service.type" text="default text"/></th>
						<th data-hide="phone,tablet"><spring:message code="common.building" text="default text"/></th>
						<th data-hide="phone,tablet"><spring:message code="common.start" text="default text"/></th>
						<th data-hide="phone,tablet"><spring:message code="common.end" text="default text"/></th>
						<th data-hide="phone,tablet" class="text-center"><spring:message code="common.action"
						                                                                 text="default text"/></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${masterServicesDtoList}" var="masterServices">
						<tr class="gradeC">
							<td>${masterServices.serviceName}</td>
							<td data-hide="phone,tablet">
								<c:if test="${masterServices.serviceType==1}">serviceType 1 </c:if>
								<c:if test="${masterServices.serviceType==2}">serviceType 2 </c:if>
								<c:if test="${masterServices.serviceType==3}">serviceType 3 </c:if>
							</td>
							<td data-hide="phone,tablet">${building.buildingName}</td>
							<td data-hide="phone,tablet"><fmt:formatDate pattern="MM/dd/yyyy"
							                                             value="${masterServices.serviceStart}"/></td>
							<td data-hide="phone,tablet"><fmt:formatDate pattern="MM/dd/yyyy"
							                                             value="${masterServices.serviceEnd}"/></td>
							<td class="text-center">
								<div class="btn-group">
									<a class="btn-success btn btn-xs"
									   href="/service/edit/${masterServices.serviceCode}"><spring:message
											code="common.button.edit" text="default text"/></a>
									<a class="btn-danger btn btn-xs"
									   href="/service/delete/${masterServices.serviceCode}"><spring:message
											code="common.button.delete" text="default text"/></a>
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

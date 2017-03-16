<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<spring:url value="/resources/img/building.jpg" var="building"/>

<div class="row">
	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5><spring:message code="feedbackhistory.feedbackhistorylist" text="default text"/></h5>

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
						<th><spring:message code="common.title" text="default text"/></th>
						<th data-hide="phone,tablet"><spring:message code="login.username" text="default text"/></th>
						<th data-hide="phone,tablet"><spring:message code="feedbackhistory.servicecode"
						                                             text="default text"/></th>
						<th data-hide="phone,tablet"><spring:message code="feedbackhistory.followstatus"
						                                             text="default text"/></th>
						<th data-hide="phone,tablet"><spring:message code="common.createdate" text="default text"/></th>
						<th data-hide="phone,tablet" class="text-center"><spring:message code="common.action"
						                                                                 text="default text"/></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${listComplaintHistory}" var="complaintDto">
						<tr class="gradeC">
							<td>${complaintDto.title}</td>
							<td data-hide="phone,tablet">${complaintDto.userName}</td>
							<td data-hide="phone,tablet">${complaintDto.serviceCode}</td>
							<td data-hide="phone,tablet">${complaintDto.followStatus}</td>
							<td data-hide="phone,tablet"><fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss"
							                                             value="${complaintDto.created}"></fmt:formatDate></td>
							<td class="text-center">
								<div class="btn-group">
									<a class="btn-success btn btn-xs" href="/complaint"><spring:message
											code="common.button.view" text="default text"/></a>
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
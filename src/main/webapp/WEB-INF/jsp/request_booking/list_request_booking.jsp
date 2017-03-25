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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="row">
	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5><spring:message code="listReqest.listBooking" text="default text"></spring:message></h5>

				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<input type="text" class="form-control input-sm m-b-xs" id="filter"
				       placeholder="<spring:message code="listReqest.search" text="default text"></spring:message>">

				<table class="footable emp-sales table table-stripped table-bordered table-hover dataTables-example"
				       data-page-size="8" data-filter=#filter>
					<thead>
					<tr>
						<th><spring:message code="listReqest.adId" text="default text"></spring:message></th>
						<th data-hide="phone,tablet"><spring:message code="listReqest.assetNameOrServiceName" text="default text"></spring:message></th>
						<th data-hide="phone,tablet"><spring:message code="listReqest.price" text="default text"></spring:message></th>
						<th data-hide="phone,tablet"><spring:message code="listReqest.bookFrom" text="default text"></spring:message></th>
						<th data-hide="phone,tablet"><spring:message code="listReqest.bookTo" text="default text"></spring:message></th>
						<th data-hide="phone,tablet"><spring:message code="listReqest.status" text="default text"></spring:message></th>
						<th data-hide="phone,tablet"><spring:message code="listReqest.followStatus" text="default text"></spring:message></th>
						<th data-hide="phone,tablet"><spring:message code="listReqest.memo" text="default text"></spring:message></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${ListBookingServiceDto}" var="listBookingServiceDto">
						<tr class="gradeC">
							<td>
								<c:forEach items="${serviceUserDtoList}" var="user">
									<c:if test="${listBookingServiceDto.bookingServiceDto.userId==user.userId}">${user.adId}</c:if>
								</c:forEach>
							</td>
							<td data-hide="phone,tablet">${listBookingServiceDto.serviceOrAssetName}</td>
							<td data-hide="phone,tablet">${listBookingServiceDto.bookingServiceDto.totalPrice}</td>
							<td data-hide="phone,tablet"><fmt:formatDate pattern="MM/dd/yyyy"
							                                             value="${listBookingServiceDto.bookingServiceDto.bookFrom}"></fmt:formatDate></td>
							<td data-hide="phone,tablet"><fmt:formatDate pattern="MM/dd/yyyy"
							                                             value="${listBookingServiceDto.bookingServiceDto.bookTo}"></fmt:formatDate></td>
							<td data-hide="phone,tablet">${listBookingServiceDto.bookingServiceDto.status}</td>
							<td data-hide="phone,tablet">${listBookingServiceDto.bookingServiceDto.followStatus}</td>
							<td data-hide="phone,tablet">${listBookingServiceDto.bookingServiceDto.memo}</td>
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


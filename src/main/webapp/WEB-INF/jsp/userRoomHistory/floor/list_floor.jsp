<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<meta name="viewport" content="width=device-width, initial-scale=1">
<div class="row">
	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5><spring:message code="building.floor.floorList" text="default text"></spring:message></h5>
				<div class="ibox-tools">
					<a onclick="goBack()" class="btn btn-warning btn btn-xs"><spring:message code="building.buttonBack"
					                                                                         text="default text"></spring:message></a>
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<div class="table-responsive">
					<table class="footable emp-sales table table-striped table-bordered table-hover dataTables-example"
					       data-page-size="10">
						<thead>
						<tr>
							<th class="text-center"><spring:message code="building.floor.floorNumber"
							                                        text="default text"></spring:message></th>
							<th data-hide="phone,tablet" class="text-center"><spring:message
									code="building.floor.floorName" text="default text"></spring:message></th>
							<th data-hide="phone,tablet" class="text-center"><spring:message
									code="building.floor.totalRoom" text="default text"></spring:message></th>
							<th data-hide="phone,tablet" class="text-center"><spring:message
									code="building.floor.floorType" text="default text"></spring:message></th>
							<th data-hide="phone,tablet" class="text-center"><spring:message
									code="building.floor.action" text="default text"></spring:message></th>
						</tr>
						</thead>
						<tbody>
						<div class="row m-b-sm">
							<%int count = 0;%>
							<c:forEach items="${floorDtoList}" var="floor">
								<tr
										<c:if test="${count%2 == 0}"><% count ++;%>class="gradeX"
										</c:if>
											<c:if test="${count%2 != 0}"><% count ++;%>class="gradeC"
								</c:if>   >
									<td class="col-sm-2">${floor.floorSeq}</td>
									<td data-hide="phone,tablet" class="col-sm-4">${floor.floorAlias}</td>
									<td data-hide="phone,tablet" class="col-sm-1">${floor.totalRoom}</td>
									<td data-hide="phone,tablet" class="col-sm-1">${floor.floorType}</td>
									<td data-hide="phone,tablet" class="col-sm-3 text-center">
										<div class="btn-group">
											<a class="btn btn-warning btn btn-xs"
											   href="/history/floor/room/${floor.floorCode}">&nbsp;&nbsp;View Room&nbsp;&nbsp;</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</div>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
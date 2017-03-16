<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<div class="row">
	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5><spring:message code="building.room.roomList" text="default text"></spring:message></h5>
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
							<th class="text-center"><spring:message code="building.room.roomName"
							                                        text="default text"></spring:message></th>
							<th data-hide="phone,tablet" class="text-center"><spring:message
									code="building.room.countDetail" text="default text"></spring:message></th>
							<th data-hide="phone,tablet" class="text-center"><spring:message code="building.room.status"
							                                                                 text="default text"></spring:message></th>
							<th data-hide="phone,tablet" class="text-center"><spring:message
									code="building.floor.action" text="default text"></spring:message></th>
						</tr>
						</thead>
						<tbody>
						<div class="row m-b-sm">
							<%int count = 0;%>
							<c:forEach items="${roomDtoList}" var="room">
								<tr
										<c:if test="${count%2 == 0}"><% count ++;%>class="gradeX"
										</c:if>
											<c:if test="${count%2 != 0}"><% count ++;%>class="gradeC"
								</c:if>   >
									<td class="col-sm-4">${room.roomAlias}</td>
									<td data-hide="phone,tablet" class="col-sm-2">${room.count}</td>
									<td data-hide="phone,tablet" class="col-sm-2">${room.status}</td>
									<td data-hide="phone,tablet" class="col-sm-4 text-center">
										<div class="btn-group">
											<a class="btn btn-warning btn btn-xs"
											   href="/history/floor/room/details/${room.roomCode}">&nbsp;&nbsp;View Room&nbsp;&nbsp;</a>
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

<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<div class="row">
	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5><spring:message code="building.list" text="default text"></spring:message></h5>
				<div class="ibox-tools">
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
							<th class="text-center"><spring:message code="building.name" text="default text"/></th>
							<th data-hide="phone,tablet" class="text-center"><spring:message code="building.floor.count"
							                                                                 text="default text"></spring:message></th>
							<th data-hide="phone,tablet" class="text-center"><spring:message code="building.room.count"
							                                                                 text="default text"></spring:message></th>
							<th data-hide="phone,tablet" class="text-center"><spring:message code="common.description"
							                                                                 text="default text"></spring:message></th>
							<th data-hide="phone,tablet" class="text-center"><spring:message code="common.action"
							                                                                 text="default text"></spring:message></th>
						</tr>
						</thead>
						<tbody>
						<div class="row m-b-sm">
							<%int count = 0;%>
							<c:forEach items="${buildingDtoList}" var="building">
								<tr
										<c:if test="${count%2 == 0}"><% count ++;%>class="gradeX"
										</c:if>
											<c:if test="${count%2 != 0}"><% count ++;%>class="gradeC"
								</c:if>   >
									<td class="col-sm-2">${building.buildingName}</td>
									<td data-hide="phone,tablet" class="col-sm-1">${building.totalFloor}</td>
									<td data-hide="phone,tablet" class="col-sm-1">${building.totalRoom}</td>
									<td data-hide="phone,tablet" class="col-sm-5">${building.description}</td>
									<td data-hide="phone,tablet" class="col-sm-3 text-center">
										<div class="btn-group">
											<a class="btn btn-success btn btn-xs"
											   href="/building/edit/${building.buildingCode}"><spring:message
													code="common.button.edit" text="default text"></spring:message></a>
											<a class="btn btn-danger btn btn-xs"
											   href="/building/delete/${building.buildingCode}"><spring:message
													code="common.button.delete"
													text="default text"></spring:message></a>
											<a class="btn btn-warning btn btn-xs"
											   href="/building/floor/${building.buildingCode}"><spring:message
													code="building.addfloor" text="default text"></spring:message></a>
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

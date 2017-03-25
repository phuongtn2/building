<%--
  Created by IntelliJ IDEA.
  User: Giang.DaoTu
  Date: 11/11/2016
  Time: 8:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>Thông Tin Thành viên</h5>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content m-b-sm border-bottom">
				<form:form modelAttribute="userRoleRoomDto" method="post" id="userForm">
				<div class="row">
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label">Building</label>
							<select class="form-control m-b" name="userRoomDto.buildingCode"
							        onchange="findFloorByBuildingCode(this.value);">
								<option value="0">Chọn tòa nhà</option>
								<c:forEach items="${buildingDtoList}" var="building">
									<option value="${building.buildingCode}">${building.buildingName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label">Floor</label>
							<%--<select class="form-control m-b" name="userRoomDto.floorCode" onchange="" id="floorList">--%>
								<%--<option> ksjdksjdk</option>--%>
							<select class="form-control m-b" name="userRoomDto.floorCode">
								<option>chon tang</option>
								<div id = "floorList">
								</div>
							</select>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label">Room</label>
							<select class="form-control m-b" name="userRoomDto.roomCode">
								<option value="1">Room 1</option>
								<option value="2">Room 2</option>
								<option value="3">Room 3</option>
								<option value="4">Room 4</option>
							</select>

						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<label class="control-label">Bắt đầu</label>
							<div class="input-group date">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input id="startDay" name="userDto.startDay" type="text" class="form-control"
								       value="<c:if test="${userRoleRoomDto.userDto.startDay!= null}"><fmt:formatDate pattern="MM/dd/yyyy" value="${userRoleRoomDto.userDto.startDay}"></fmt:formatDate></c:if>">
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<label class="control-label">Kết thúc</label>
							<div class="input-group date">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input id="endDay" name="userDto.endDay" type="text" class="form-control"
								       value="<c:if test="${userRoleRoomDto.userDto.endDay!= null}"><fmt:formatDate pattern="MM/dd/yyyy" value="${userRoleRoomDto.userDto.endDay}"></fmt:formatDate></c:if>">
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="row">
						<div class="form-group">
							<div class="text-center">
								<input type="hidden" id="add" name="add">
								<button type="button" class="btn btn-primary" data-toggle="modal"
								        data-target="#addButton"><i class="fa fa-check"></i><spring:message
										code="common.button.save" text="default text"/></button>
								<button name="reset" class="btn btn-danger" type="reset"><spring:message
										code="common.button.reset" text="default text"/></button>
							</div>
						</div>
					</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	function findFloorByBuildingCode(buildingId) {
		$.ajax({
			type: "GET",
			contentType: "application/json",
			url: "/history/findFloor/"+ buildingId,
			dataType: 'json',
			timeout: 100000,
			success: function (data) {
				console.log("SUCCESS: ", data);
				display(data);
			},
			error: function (e) {
				console.log("ERROR: ", e);
				display(e);
			},
			done: function (e) {
				console.log("DONE");
			}
		});
		function display(data) {
			var json = "<h4>Ajax Response</h4><pre>"
					+ JSON.stringify(data, null, 4) + "</pre>";
			var html = "";
			for (var i = 0; i < data.length; i++) {
				html = html +
				'<option value="data[i].floorCode">'+ data[i].floorAlias +'</option>';

			}
			$('#floorList').html(html);
		}
	}
	;
</script>
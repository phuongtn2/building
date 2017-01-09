<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<meta name="viewport" content="width=device-width, initial-scale=1">
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Danh Sách Phòng</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <div class="table-responsive">
                    <table class="footable emp-sales table table-striped table-bordered table-hover dataTables-example" data-page-size="10">
                        <thead>
                            <tr>
                                <th class="text-center">Tên Phòng </th>
                                <th data-hide="phone,tablet" class="text-center">count</th>
                                <th data-hide="phone,tablet" class="text-center">status</th>
                                <th data-hide="phone,tablet" class="text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <div class="row m-b-sm">
                                <%int count = 0;%>
                                <c:forEach items="${roomDtoList}" var="room">
                                    <tr <c:if test = "${count%2 == 0}"><% count ++;%>class="gradeX"</c:if><c:if test = "${count%2 != 0}"><% count ++;%>class="gradeC"</c:if>   >
                                        <td class="col-sm-4">${room.roomAlias}</td>
                                        <td data-hide="phone,tablet" class="col-sm-2">${room.count}</td>
                                        <td data-hide="phone,tablet" class="col-sm-2">${room.status}</td>
                                        <td data-hide="phone,tablet" class="col-sm-4 text-center">
                                            <div class="btn-group">
                                                <a class="btn btn-warning btn btn-xs" href="/history/floor">&nbsp;&nbsp;Quay lại&nbsp;&nbsp;</a>
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
<%--
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Thông tin tòa nhà</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">

                <form:form id="form" action="#" class="wizard-big row">
                    <h1>Tòa nhà</h1>
                    <fieldset>
                        &lt;%&ndash;<div class="row">
                            <div class="col-lg-12">&ndash;%&gt;

                                    <div class="table-responsive">
                                        <table class="footable emp-sales table table-striped table-bordered table-hover dataTables-example" data-page-size="10">
                                            <thead>
                                                <tr>
                                                    <th class="text-center">Tên tòa nhà </th>
                                                    <th data-hide="phone,tablet" class="text-center">Tổng số tầng</th>
                                                    <th data-hide="phone,tablet" class="text-center">Tổng số phòng</th>
                                                    <th data-hide="phone,tablet" class="text-center">Mô tả</th>
                                                    <th data-hide="phone,tablet" class="text-center">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <div class="row m-b-sm">
                                                    <%int count = 0;%>
                                                    <c:forEach items="${buildingDtoList}" var="building">
                                                        <tr <c:if test = "${count%2 == 0}"><% count ++;%>class="gradeX"</c:if><c:if test = "${count%2 != 0}"><% count ++;%>class="gradeC"</c:if>   >
                                                            <td class="col-sm-2">${building.buildingName}</td>
                                                            <td data-hide="phone,tablet" class="col-sm-1">${building.totalFloor}</td>
                                                            <td data-hide="phone,tablet" class="col-sm-1">${building.totalRoom}</td>
                                                            <td data-hide="phone,tablet" class="col-sm-6">${building.description}</td>
                                                            <td data-hide="phone,tablet" class="col-sm-2 text-center">
                                                                <div class="btn-group">
                                                                    <a class="btn btn-success btn btn-xs" href="/building/edit/${building.buildingCode}" >&nbsp;&nbsp;Edit&nbsp;&nbsp;</a>
                                                                    <a class="btn btn-danger btn btn-xs" href="/building/delete/${building.buildingCode}">&nbsp;&nbsp;Delete&nbsp;&nbsp;</a>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </div>
                                            </tbody>
                                        </table>
                                    </div>
                             &lt;%&ndash;   </div>
                            </div>&ndash;%&gt;

                    </fieldset>
                    <h1>Tầng</h1>
                    <fieldset>
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label>First name *</label>
                                    <input id="name" name="name" type="text" class="form-control required">
                                </div>
                                <div class="form-group">
                                    <label>Last name *</label>
                                    <input id="surname" name="surname" type="text" class="form-control required">
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label>Email *</label>
                                    <input id="email" name="email" type="text" class="form-control required email">
                                </div>
                                <div class="form-group">
                                    <label>Address *</label>
                                    <input id="address" name="address" type="text" class="form-control">
                                </div>
                            </div>
                        </div>
                    </fieldset>

                    <h1>Phòng</h1>
                    <fieldset>
                        <div class="text-center" style="margin-top: 120px">
                            <h2>You did it Man :-)</h2>
                        </div>
                    </fieldset>
                </form:form>
            </div>
        </div>
    </div>
</div>--%>

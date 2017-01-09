<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<meta name="viewport" content="width=device-width, initial-scale=1">
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Danh sách tòa nhà</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <div class="row m-b-sm">
                    <c:forEach items="${buildingDtoList}" var="building" varStatus="loopStatus">
                        <div class="col-lg-3">
                            <a type="hidden" placeholder="${loopStatus.index}"></a>
                            <div class="contact-box center-version">
                                <a href="/history/floor/${building.buildingCode}">
                                    <img alt="image" class="img-circle" src="../../../resources/img/3D_building.jpg">
                                    <h3 class="m-b-xs"><strong>${building.buildingName}</strong></h3>
                                    <div class="font-bold">${building.description}</div>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>


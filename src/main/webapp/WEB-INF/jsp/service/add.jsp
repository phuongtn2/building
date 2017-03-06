<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5><spring:message code="service.management" text="default text" /></h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <form:form modelAttribute="masterServiceDto" method="post">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label"><spring:message code="service.name" text="default text" /></label>
                                <input type="hidden" id="serviceCode" name="serviceCode" value="<c:if test="${masterServiceDto.serviceCode!= null}">${masterServiceDto.serviceCode}</c:if>" class="form-control">
                                <input type="text" id="serviceName" name="serviceName" value="<c:if test="${masterServiceDto.serviceName!= null}">${masterServiceDto.serviceName}</c:if>" placeholder="<spring:message code="service.name" text="default text" />" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-8">
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label class="control-label"><spring:message code="service.type" text="default text" /></label>
                                    <select name="serviceType" class="form-control m-b">
                                        <option <c:if test="${masterServiceDto.serviceType==1}" >selected</c:if> value="1">serviceType 1</option>
                                        <option <c:if test="${masterServiceDto.serviceType==2}" >selected</c:if> value="2">serviceType 2</option>
                                        <option <c:if test="${masterServiceDto.serviceType==3}" >selected</c:if> value="3">serviceType 3</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="form-group">
                                    <label class="control-label"><spring:message code="common.building" text="default text" /></label>
                                    <select id ="buildingCode" name="buildingCode" class="form-control m-b" >
                                        <c:forEach items="${buildingDtoList}" var="building">
                                            <option value="${building.buildingCode}">${building.buildingName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="control-label"><spring:message code="common.start" text="default text" /></label>
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="serviceStart" name="serviceStart" type="text" class="form-control" value="<c:if test="${masterServiceDto.serviceStart!= null}"><fmt:formatDate pattern="MM/dd/yyyy" value="${masterServiceDto.serviceStart}" /></c:if>">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="control-label"><spring:message code="common.end" text="default text" /></label>
                                <div class="input-group ">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="serviceEnd" name="serviceEnd" type="text" class="form-control"  value="<c:if test="${masterServiceDto.serviceEnd!= null}"><fmt:formatDate pattern="MM/dd/yyyy" value="${masterServiceDto.serviceEnd}" /></c:if>">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <div class="text-center">
                                <button name="add" class="btn btn-primary" type="submit"><i class="fa fa-check"></i><spring:message code="common.button.save" text="default text" /></button>
                                <button name="reset" class="btn btn-danger"type="reset"><spring:message code="common.button.reset" text="default text" /></button>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<spring:url value="/resources/js/jquery-2.1.1.js" var="jqueryJs" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJs" />
<script src="${jqueryJs}"></script>
<script src="${bootstrapJs}"></script>
<script>
    $(document).ready(function() {

        $("#buildingCode").change(function(){
            var buildingCode = this.value;
            $.ajax({
                type : "GET",
                contentType : "application/json",
                url : "/asset/findAssetsByBuildingCode/" + buildingCode,
                dataType : 'json',
                timeout : 100000,
                success : function(data) {
                    console.log("SUCCESS: ", data);
                    display(data);
                },
                error : function(e) {
                    console.log("ERROR: ", e);
                    display(e);
                },
                done : function(e) {
                    console.log("DONE");
                }
            });
        });
        function display(data) {
            var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);
        }
    });
</script>
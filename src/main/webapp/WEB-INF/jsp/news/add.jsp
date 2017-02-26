<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5><spring:message code="news.add.buildingmanagement" text="default text" /></h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <form:form modelAttribute="newsDto " method="post">
                    <p><spring:message code="news.add.news" text="default text" /></p>
                    <div class="row">
                        <div class="form-group">
                            <input name="newCode" type="hidden" class="form-control" value="<c:if test="${newsDto.newCode!= null}">${newsDto.newCode}</c:if>">
                            <div class="col-sm-7">
                                <label class="control-label"><spring:message code="news.add.title" text="default text" /></label>
                                <input name="newHeader" type="text" placeholder="<spring:message code="news.add.title" text="default text" />" class="form-control" required=true value="<c:if test="${newsDto.newHeader!= null}">${newsDto.newHeader}</c:if>">
                            </div>
                            <div class="col-sm-5">
                                <label class="control-label"><spring:message code="news.add.newsshoter" text="default text" /></label>
                                <input name="newShorter" type="text" placeholder="<spring:message code="news.add.newsshoter" text="default text" />" class="form-control" required=true value="<c:if test="${newsDto.newShorter!= null}">${newsDto.newShorter}</c:if>">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <div class="col-sm-7">
                                <label class="control-label"><spring:message code="news.add.newscode" text="default text" /></label>
                                <input type="text" name="refNewCode" placeholder="<spring:message code="news.add.newscode" text="default text" />" class="form-control" value="<c:if test="${newsDto.refNewCode!= null}">${newsDto.refNewCode}</c:if>" >
                            </div>
                            <div class="col-sm-5">
                                <label class="control-label"><spring:message code="news.add.newstype" text="default text" /></label>
                                <select class="form-control m-b" name="newType">
                                    <option <c:if test="${newsDto.newType==1}">selected</c:if> value="1">newsType 1</option>
                                    <option <c:if test="${newsDto.newType==2}">selected</c:if> value="2">newsType 2</option>
                                    <option <c:if test="${newsDto.newType==3}">selected</c:if> value="3">newsType 3</option>
                                    <option <c:if test="${newsDto.newType==4}">selected</c:if> value="4">newsType 4</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <div class="col-sm-12">
                                <label class=" control-label"><spring:message code="news.add.content" text="default text" /></label>
                                <textarea  name="newContent" placeholder="<spring:message code="news.add.content" text="default text" />" class="form-control" rows="4" required=true value="<c:if test="${newsDto.newContent!= null}">${newsDto.newContent}</c:if>"></textarea>
                                    <%--<textarea  name="newContent" placeholder="Nội dung" class="form-control" rows="4" required=true ><c:if test="${newsDetailDto.newContent!= null}">${newsDetailDto.newContent}</c:if></textarea>--%>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="text-center">
                            <br>
                            <button name="add" class="btn btn-primary" type="submit"><i class="fa fa-check"></i><spring:message code="common.button.save" text="default text" /></button>
                            <input name="reset" class="btn btn-danger" placeholder="<spring:message code="common.button.reset" text="default text" />" type="reset" />
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
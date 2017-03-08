<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="col-lg-6 centered" id="wrapper" style="text-align: center" >
    <a items="${profileDto}" var="profileDto">
    <div class="widget-head-color-box navy-bg p-lg text-center">
        <div class="m-b-md">
            <h2 class="font-bold no-margins">
                <a style="word-break: break-word; font-size: xx-large;" >${profileDto.userDto.fullName}</a>
            </h2>
        </div>
        <img src="/resources/img/profile.jpg" class="img-circle circle-border m-b-md" alt="profile">
    </div>
    <div class="widget-text-box text-center">
        <div class="row">
            <a class="control-label" style="font-size: initial;color: blueviolet;font-weight: bold;"><spring:message code="profile.userDto.birthday" text="default text" /></a>
            <a style="word-break: break-word;font-size: initial;color: initial;"><fmt:formatDate pattern="MM/dd/yyyy" value="${profileDto.userDto.birthday}"></fmt:formatDate></a>
        </div>
        <div class="row">
            <a class="control-label" style="font-size: initial;color: blueviolet;font-weight: bold;"><spring:message code="profile.userDto.address" text="default text" /></a>
            <a style="word-break: break-word;font-size: initial;color: initial;">${profileDto.userDto.address}</a>
        </div>
        <div class="row">
            <a class="control-label" style="font-size: initial;color: blueviolet;font-weight: bold;"><spring:message code="profile.userDto.mail" text="default text" /></a>
            <a style="word-break: break-word;font-size: initial;color: initial;">${profileDto.userDto.mail}</a>
        </div>
        <div class="row">
            <a class="control-label" style="font-size: initial;color: blueviolet;font-weight: bold;"><spring:message code="profile.userDto.memo" text="default text" /></a>
            <a style="word-break: break-word;font-size: initial;color: initial;">${profileDto.userDto.memo}</a>
        </div>
        <div class="row">
            <a class="control-label" style="font-size: initial;color: blueviolet;font-weight: bold;"><spring:message code="profile.buildingName" text="default text" /></a>
            <a style="word-break: break-word;font-size: initial;color: initial;">${profileDto.buildingName}</a>
        </div>
        <div class="row">
            <a class="control-label" style="font-size: initial;color: blueviolet;font-weight: bold;"><spring:message code="profile.floorAlias" text="default text" /></a>
            <a style="word-break: break-all;font-size: initial;color: initial;">${profileDto.floorAlias}</a>
        </div>
        <div class="row">
            <a class="control-label" style="font-size: initial;color: blueviolet;font-weight: bold;"><spring:message code="profile.roomAlias" text="default text" /></a>
            <a style="word-break: break-word;font-size: initial;color: initial;">${profileDto.roomAlias}</a>
        </div>
    </div>
    </a>
</div>



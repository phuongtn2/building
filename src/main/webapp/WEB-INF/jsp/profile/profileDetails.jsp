<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="col-lg-6 centered" id="wrapper" style="text-align: center" >
    <a items="${profileDto}" var="profileDto">
    <div class="widget-head-color-box navy-bg p-lg text-center">
        <div class="m-b-md">
            <h2 class="font-bold no-margins">
                <a style="word-break: break-all" >${profileDto.userDto.fullName}</a>
            </h2>
        </div>
        <img src="/resources/img/profile.jpg" class="img-circle circle-border m-b-md" alt="profile">
    </div>
    <div class="widget-text-box text-center">
        <div class="row">
            <a style="word-break: break-all" ><fmt:formatDate pattern="MM/dd/yyyy" value="${profileDto.userDto.birthday}"></fmt:formatDate></a>
        </div>
        <div class="row">
            <a style="word-break: break-all" >${profileDto.userDto.address}</a>
        </div>
        <div class="row">
            <a style="word-break: break-all" >${profileDto.userDto.mail}</a>
        </div>
        <div class="row">
            <a style="word-break: break-all" >${profileDto.userDto.memo}</a>
        </div>
        <div class="row">
            <a style="word-break: break-all" >${profileDto.buildingName}</a>
        </div>
        <div class="row">
            <a style="word-break: break-all" >${profileDto.floorAlias}</a>
        </div>
        <div class="row">
            <a style="word-break: break-all" >${profileDto.roomAlias}</a>
        </div>
    </div>
    </a>
</div>



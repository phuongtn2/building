<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="page-wrapper" class="gray-bg">
    <div class="col-lg-7 center-block" >
        <div class="widget-head-color-box navy-bg p-lg text-center">
            <a items="${profileDto}" var="profileDto">
                <div class="m-b-md">
                    <h2 class="font-bold no-margins">
                        <a style="word-break: break-all" >${profileDto.userDto.fullName}</a>
                    </h2>
                </div>
                <img src="/resources/img/profile_big.jpg" class="img-circle circle-border m-b-md" alt="profile">
                <div class="widget-text-box">
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
    </div>
</div>


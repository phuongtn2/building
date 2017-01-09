<%@page language="java" contentType="text/html;charset=UTF-8"%>
<spring:url value="/resources/img/building.jpg" var="building"/>
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav metismenu" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element"> <span>
                            <img alt="image" class="img-circle" src="${building}" />
                             </span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">${aui.fullName}</strong>
                             </span> <span class="text-muted text-xs block">Member <b class="caret"></b></span> </span> </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li><a href="profile.html">Thông Tin Cá Nhân</a></li>
                        <li class="divider"></li>
                        <li><a href="/changepass">Thây Đổi Mật Khẩu</a></li>
                        <li class="divider"></li>
                        <li><a href="/logout">Đăng Xuất</a></li>
                    </ul>
                </div>
                <div class="logo-element">
                    B
                </div>
            </li>
            <li id="news" class="active">
                <a href="/news"><i class="fa fa-th-large"></i> <span class="nav-label">Tin nội bộ </br> (News)</span> <span class="fa arrow"></span></a>
            </li>
            <li id="complaint">
                <a href="/complaint"><i class="fa fa-th-large"></i> <span class="nav-label">Phản hồi đánh giá </br> (Complaint)</span> <span class="fa arrow"></span></a>
                <ul id="complaintUL" class="nav nav-second-level collapse">
                    <li id="complaintLi"><a href="/complaint">Phản hồi đánh giá </br> (Complaint) </a></li>
                    <li id="complaint_history"><a href="/complaint/history">Lich Su </br> (History) </a></li>
                </ul>
            </li>
            <li id="request">
                <%--<a href="/request_booking"><i class="fa fa-th-large"></i> <span class="nav-label">Yêu cầu </br> (Request/Booking)</span> <span class="fa arrow"></span></a>--%>
                <a href="/request_booking"><i class="fa fa-th-large"></i> <span class="nav-label">Yêu cầu </br> (Request/Booking)</span> <span class="fa arrow"></span></a>
            </li>
            <li id="building">
                <a href="/building"><i class="fa fa-th-large"></i> <span class="nav-label">Tòa nhà </br> (Building)</span> <span class="fa arrow"></span></a>
            </li>
            <li id="asset">
                <a href="/asset"><i class="fa fa-th-large"></i> <span class="nav-label">Tài sản chung </br> (Asset)</span> <span class="fa arrow"></span></a>
            </li>
            <li id="history">
                <a href="/history"><i class="fa fa-th-large"></i> <span class="nav-label">Lịch sử căn hộ </br> (History)</span> <span class="fa arrow"></span></a>
            </li>
            <li id="user">
                <a href="/user"><i class="fa fa-th-large"></i> <span class="nav-label">Member</span> <span class="fa arrow"></span></a>
            </li>
            <li id="service">
                <a href="/service"><i class="fa fa-th-large"></i> <span class="nav-label">Phí dịch vụ </br> (Service)</span> <span class="fa arrow"></span></a>
            </li>
        </ul>

    </div>
</nav>
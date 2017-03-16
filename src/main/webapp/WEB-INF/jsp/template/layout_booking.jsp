<%--
  Created by IntelliJ IDEA.
  User: phuon
  Date: 19/02/2017
  Time: 9:53 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Building</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <spring:url value="/resources/css/bootstrap.min.css" var="bootsTrapCss"/>
    <%-- <spring:url value="/resources/css/plugins/toastr/toastr.min.css" var="toastrCss" />--%>
    <spring:url value="/resources/font-awesome/css/font-awesome.css" var="awesomeFontCss" />
    <spring:url value="/resources/css/animate.css" var="animateCss" />
    <spring:url value="/resources/css/style.css" var="styleCss" />
    <%--<spring:url value="/resources/js/plugins/gritter/jquery.gritter.css" var="gritterCss" />--%>

    <link href="${bootsTrapCss}" rel="stylesheet" type="text/css"/>
    <%-- <link href="${toastrCss}" rel="stylesheet" type="text/css"/>--%>
    <link href="${awesomeFontCss}" rel="stylesheet" type="text/css"/>
    <link href="${animateCss}" rel="stylesheet" type="text/css"/>
    <link href="${styleCss}" rel="stylesheet" type="text/css"/>
    <%--<link href="${gritterCss}" rel="stylesheet" type="text/css"/>--%>
    <spring:url value="/resources/WebContent/codebase/schedule_all.css" var="scheduleCss"/>
    <link href="${scheduleCss}" rel="stylesheet" type="text/css"/>
    <%--<spring:url value="/resources/WebContent/codebase/dhtmlxscheduler.js" var="dhtmlxschedulerJs" />
    <script src="${dhtmlxschedulerJs}"></script>
    <spring:url value="/resources/WebContent/codebase/ext/dhtmlxscheduler_agenda_view.js" var="agendaJs" />
    <script src="${agendaJs}"></script>--%>
    <%--<spring:url value="/resources/WebContent/codebase/dhtmlxscheduler_glossy.css" var="glossyCss" />
    <link href="${glossyCss}" rel="stylesheet" type="text/css"/>--%>
    <spring:url value="/resources/WebContent/codebase/schedule.js" var="scheduleJs" />
    <script src="${scheduleJs}" type="text/javascript" charset="utf-8"></script>


    <spring:url value="/resources/WebContent/codebase/locale/locale_en.js" var="localeJs" />
    <spring:url value="/resources/WebContent/codebase/ext/dhtmlxscheduler_tooltip.js" var="dhtmlxschedulerTooltipJs" />
    <script src="${localeJs}"></script>
    <script src="${dhtmlxschedulerTooltipJs}"></script>
    <script>
        function change(id) {
            var listId = ["news", "complaint", "request", "building" , "service", "user"
                , "history", "asset", "complaint_history", "complaintUL", "complaintLi"];
            for (i = 0; i <=  listId.length; i++) {
                if(id === listId[i]){
                    $('#'+listId[i]).addClass( "active" );
                }else{
                    $('#'+listId[i]).removeClass( "active" )
                }
            }
        }
    </script>
</head>
<body onload="change('request')">
<div id="wrapper" style=" height: 100%;background-color: #293846;">
        <%--<%@include file="../template/navbar.jsp" %>
        <%@include file="../template/notify.jsp" %>--%>
        <tiles:insertAttribute name="navbar" />
        <tiles:insertAttribute name="notify" />
        <div id="page-wrapper" class="gray-bg" style="height: 100%;">
            <div class="wrapper wrapper-content animated fadeInRight" style=" height: 100%;">
                <%--<%@include file="add.jsp" %>
                <%@include file="list_news.jsp" %>--%>
                <tiles:insertAttribute name="body" />
            </div>
            <%--<%@include file="../template/footer.jsp" %>--%>
            <tiles:insertAttribute name="footer" />
        </div>
    </div>
    <!-- Mainly scripts -->
    <spring:url value="/resources/js/jquery-2.1.1.js" var="jqueryJs" />
    <spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJs" />
    <script src="${jqueryJs}"></script>
    <script src="${bootstrapJs}"></script>

    <spring:url value="/resources/js/plugins/metisMenu/jquery.metisMenu.js" var="metisMenuJs" />
    <spring:url value="/resources/js/plugins/slimscroll/jquery.slimscroll.min.js" var="slimscrollJs" />
    <script src="${metisMenuJs}"></script>
    <script src="${slimscrollJs}"></script>
    <!-- Flot -->
    <spring:url value="/resources/js/plugins/flot/jquery.flot.js" var="flotJs" />
    <spring:url value="/resources/js/plugins/flot/jquery.flot.tooltip.min.js" var="tooltipJs" />
    <spring:url value="/resources/js/plugins/flot/jquery.flot.spline.js" var="splineJs" />
    <spring:url value="/resources/js/plugins/flot/jquery.flot.resize.js" var="resizeJs" />
    <spring:url value="/resources/js/plugins/flot/jquery.flot.pie.js" var="pieJs" />
    <script src="${flotJs}"></script>
    <script src="${tooltipJs}"></script>
    <script src="${splineJs}"></script>
    <script src="${resizeJs}"></script>
    <script src="${pieJs}"></script>
    <!-- Peity -->
    <spring:url value="/resources/js/plugins/peity/jquery.peity.min.js" var="peityJs" />
    <%--<spring:url value="/resources/js/demo/peity-demo.js" var="peityDemoJs" />--%>
    <script src="${peityJs}"></script>
    <%-- <script src="${peityDemoJs}"></script>--%>

    <!-- Custom and plugin javascript -->
    <spring:url value="/resources/js/inspinia.js" var="inspiniaJs" />
    <spring:url value="/resources/js/plugins/pace/pace.min.js" var="paceJs" />
    <script src="${inspiniaJs}"></script>
    <script src="${paceJs}"></script>

    <!-- jQuery UI -->
    <spring:url value="/resources/js/plugins/jquery-ui/jquery-ui.min.js" var="uiMinJs" />
    <script src="${uiMinJs}"></script>

    <script>
        function customLayout(){
            $( window ).load(function() {
                $(".dhxlayout_cont").css({"left": "235px","top": "75px"});
            });
        }
    </script>
</body>
</html>

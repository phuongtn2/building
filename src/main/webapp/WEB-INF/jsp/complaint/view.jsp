<!DOCTYPE html>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Building | Home</title>
        <spring:url value="/resources/css/bootstrap.min.css" var="bootsTrapCss"/>
        <spring:url value="/resources/css/plugins/toastr/toastr.min.css" var="toastrCss" />
        <spring:url value="/resources/font-awesome/css/font-awesome.css" var="awesomeFontCss" />
        <spring:url value="/resources/css/animate.css" var="animateCss" />
        <spring:url value="/resources/css/style.css" var="styleCss" />
        <spring:url value="/resources/js/plugins/gritter/jquery.gritter.css" var="gritterCss" />

        <link href="${bootsTrapCss}" rel="stylesheet" type="text/css"/>
        <link href="${toastrCss}" rel="stylesheet" type="text/css"/>
        <link href="${awesomeFontCss}" rel="stylesheet" type="text/css"/>
        <link href="${animateCss}" rel="stylesheet" type="text/css"/>
        <link href="${styleCss}" rel="stylesheet" type="text/css"/>
        <link href="${gritterCss}" rel="stylesheet" type="text/css"/>
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
                if(id === "complaint"){
                    $('#complaintUL').addClass( "in" );
                    //if(id === "complaintLi"){
                        $('#complaintLi').addClass( "active" );
                    //}else{
                        $('#complaint_history').removeClass( "active" );
                    //}
                    /*if(id === "complaint_history"){
                        $('#complaint_history').addClass( "active" );
                    }else{
                        $('#complaintLi').removeClass( "active" );
                    }*/
                }else {
                    $('#complaintUL').removeClass( "in" );
                }
            }
        </script>

    </head>

    <body onload="change('complaint')">
        <div id="wrapper">
            <%@include file="../template/navbar.jsp" %>
            <%@include file="../template/notify.jsp" %>
            <div id="page-wrapper" class="gray-bg">
                <div class="wrapper wrapper-content animated fadeInRight">
                    <%@include file="add.jsp" %>
                    <%@include file="list_comment.jsp" %>
                </div>
                <%@include file="../template/footer.jsp" %>
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
        <spring:url value="/resources/js/demo/peity-demo.js" var="peityDemoJs" />
        <script src="${peityJs}"></script>
        <script src="${peityDemoJs}"></script>

        <!-- Custom and plugin javascript -->
        <spring:url value="/resources/js/inspinia.js" var="inspiniaJs" />
        <spring:url value="/resources/js/plugins/pace/pace.min.js" var="paceJs" />
        <script src="${inspiniaJs}"></script>
        <script src="${paceJs}"></script>

        <!-- jQuery UI -->
        <spring:url value="/resources/js/plugins/jquery-ui/jquery-ui.min.js" var="uiMinJs" />
        <script src="${uiMinJs}"></script>

        <!-- GITTER -->
        <spring:url value="/resources/js/plugins/gritter/jquery.gritter.min.js" var="gritterJs" />
        <script src="${gritterJs}"></script>

        <!-- Sparkline -->
        <spring:url value="/resources/js/plugins/sparkline/jquery.sparkline.min.js" var="sparklineMinJs" />
        <script src="${sparklineMinJs}"></script>

        <!-- Sparkline demo data  -->
        <spring:url value="/resources/js/demo/sparkline-demo.js" var="sparklineJs" />
        <script src="${sparklineJs}"></script>

        <!-- ChartJS-->
        <spring:url value="/resources/js/plugins/chartJs/Chart.min.js" var="chartJs" />
        <script src="${chartJs}"></script>

        <!-- Toastr -->
        <spring:url value="/resources/js/plugins/toastr/toastr.min.js" var="toastrJs" />
        <script src="${toastrJs}"></script>

        <script>
            function onEnter(type,complaintCode, parentComplaintCode) {
                var key = window.event.keyCode;
                if (key === 13) {
                    var data = {};
                    data["complaintCode"] = complaintCode;
                    data["message"] = ""
                    if(type === "comment") {
                        data["tranSeq"] = 1;
                        data["message"] = $('#comment_' + complaintCode).val();
                    }
                    else {
                        data["tranSeq"] = 2;
                        data["message"] = $('#reply_'+ complaintCode +'_' + parentComplaintCode).val();
                    }

                    data["parentComplaintCode"] = parentComplaintCode;
                    if(data["message"] != ""){
                        $.ajax({
                            type: "POST",
                            contentType: "application/json",
                            url: "/complaint/comment",
                            data: JSON.stringify(data),
                            dataType: 'json',
                            timeout: 600000,
                            success: function (data) {
                                if(type === "comment"){
                                    $('#appendComment_'+complaintCode).append(
                                            '<div class="social-comment">' +
                                            '<a href="#" class="pull-left">' +
                                            '<img alt="image" src="${building}">' +
                                            '</a>' +
                                            '<div class="media-body">' +
                                            '<a href="#">' + data["userName"] + '</a>' + data["message"] +
                                            '<br/>' +
                                            '<a href="#" class="small"><i class="fa"></i> Comment</a>' +
                                            '</div>' +
                                            '</div>'
                                            /*'<div class="social-comment">' +
                                             '<a href="#" class="pull-left">' +
                                             '<img alt="image" src="${building}">' +
                 '</a>' +
                 '<div class="media-body">' +
                 '<textarea onkeypress="onEnter("reply"' + ',' + data["complaintCode"] + ','+ data["parentComplaintCode"] + ');" ' +
                 'class="form-control" placeholder="Write reply..." id="reply_'+ data["complaintCode"] +'_' + data["parentComplaintCode"] + '" ></textarea>' +
                 '</div>' +
                 '</div>' +*/
                                    );
                                    $('#comment_' + complaintCode).val("");
                                }else{
                                    $('#appendReply_'+ complaintCode + '_' + parentComplaintCode).append('<div class="social-comment">' +
                                            '<a href="#" class="pull-left">' +
                                            '<img alt="image" src="${building}">' +
                                            '</a>' +
                                            '<div class="media-body">' +
                                            ' <a href="#">' + data["userName"]+
                                            '</a>' + data["message"] +
                                            '<br/>' +
                                            '<a href="#" class="small"><i class="fa"></i> Reply</a>' +
                                            '</div>' +
                                            '</div>');
                                    $('#reply_'+ complaintCode + '_' + parentComplaintCode).val("");
                                }

                            },
                            error: function (e) {
                                alert(e);
                                $('#reply_'+ complaintCode + '_' + parentComplaintCode).val("");
                            }
                        });
                        //window.location = "http://localhost:8080//complaint";
                }
                    return false;
                }
                else {
                    return true;
                }
            }
        </script>
    </body>
</html>

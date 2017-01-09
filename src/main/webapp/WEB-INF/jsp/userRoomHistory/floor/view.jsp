<!DOCTYPE html>
<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Building | Building</title>
    <spring:url value="/resources/css/bootstrap.min.css" var="bootsTrapCss"/>
    <spring:url value="/resources/css/plugins/toastr/toastr.min.css" var="toastrCss" />
    <spring:url value="/resources/font-awesome/css/font-awesome.css" var="awesomeFontCss" />
    <spring:url value="/resources/css/animate.css" var="animateCss" />
    <spring:url value="/resources/css/style.css" var="styleCss" />
    <spring:url value="/resources/js/plugins/gritter/jquery.gritter.css" var="gritterCss" />
    <spring:url value="/resources/css/plugins/dataTables/datatables.min.css" var="datatablesCss" />
    <spring:url value="/resources/css/plugins/footable/footable.core.css" var="footableCss" />
    <spring:url value="/resources/css/plugins/steps/jquery.steps.css" var="stepsCss" />
    <link href="${stepsCss}" rel="stylesheet">
    <link href="${datatablesCss}" rel="stylesheet" type="text/css"/>
    <link href="${bootsTrapCss}" rel="stylesheet" type="text/css"/>
    <link href="${toastrCss}" rel="stylesheet" type="text/css"/>
    <link href="${awesomeFontCss}" rel="stylesheet" type="text/css"/>
    <link href="${animateCss}" rel="stylesheet" type="text/css"/>
    <link href="${styleCss}" rel="stylesheet" type="text/css"/>
    <link href="${gritterCss}" rel="stylesheet" type="text/css"/>
    <link href="${footableCss}" rel="stylesheet" type="text/css"/>
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

<body onload="change('history')">
<div id="wrapper">
    <%@include file="../../template/navbar.jsp" %>
    <%@include file="../../template/notify.jsp" %>
    <div id="page-wrapper" class="gray-bg">
        <div class="wrapper wrapper-content animated fadeInRight">
            <%@include file="list_floor.jsp" %>
        </div>
        <%@include file="../../template/footer.jsp" %>
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
<spring:url value="/resources/js/plugins/jeditable/jquery.jeditable.js" var="jeditableJs" />
<script src="${jeditableJs}"></script>
<spring:url value="/resources/js/plugins/dataTables/datatables.min.js" var="datatablesJs" />
<script src="${datatablesJs}"></script>
<spring:url value="/resources/js/plugins/footable/footable.all.min.js" var="footableJs" />
<script src="${footableJs}"></script>
<!-- Steps -->
<spring:url value="/resources/js/plugins/staps/jquery.steps.min.js" var="stepsJs" />
<script src="${stepsJs}"></script>

<!-- Jquery Validate -->
<spring:url value="/resources/js/plugins/validate/jquery.validate.min.js" var="validateJs" />
<script src="${validateJs}"></script>
<!-- Page-Level Scripts -->
<script>
    $(document).ready(function(){
        $('.dataTables-example').DataTable({
            dom: '<"html5buttons"B>lTfgitp',
            buttons: [
            ]
        });
        $('.footable').footable();
        /* Init DataTables */
        var oTable = $('#editable').DataTable();

        /* Apply the jEditable handlers to the table */
        oTable.$('td').editable( 'http://localhost:8080/building', {
            "callback": function( sValue, y ) {
                var aPos = oTable.fnGetPosition( this );
                oTable.fnUpdate( sValue, aPos[0], aPos[1] );
            },
            "submitdata": function ( value, settings ) {
                return {
                    "row_id": this.parentNode.getAttribute('id'),
                    "column": oTable.fnGetPosition( this )[2]
                };
            },

            "width": "90%",
            "height": "10%"
        } );
    });

</script>

</body>
</html>

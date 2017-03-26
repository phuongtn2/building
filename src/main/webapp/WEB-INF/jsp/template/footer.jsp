<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="footer">
    <div class="pull-right">
        Email <strong>chuchuot12a15tnp@gmail.com</strong>
    </div>
    <div>
        <strong>Copyright</strong> Building &copy; 2016
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="addButton" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title text-center"><spring:message code="addButton.confirmLabel" text="default text"></spring:message></h4>
            </div>
            <div class="modal-body text-center">
                <p><spring:message code="addButton.confirmMessage" text="default text"></spring:message></p>
                <p><spring:message code="addButton.addButtonMessage" text="default text"></spring:message></p>
                <p><spring:message code="addButton.cancelButtonMessage" text="default text"></spring:message></p>
            </div>
            <div class="modal-header text-center">
                <button onclick="submit()" name="addModel" class="btn btn-primary" type="submit"><spring:message code="addButton.countinuteButton" text="default text"></spring:message></button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="addButton.cancelButton" text="default text"></spring:message></button>
            </div>
        </div>

    </div>
</div>

<div class="modal fade" id="deleteButton" role="dialog">
    <div class="modal-dialog">
        <input type="hidden" id="url" value="">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title text-center"><spring:message code="deleteButton.confirmLabel" text="default text"></spring:message></h4>
            </div>
            <div class="modal-body text-center">
                <p><spring:message code="deleteButton.confirmMessage" text="default text"></spring:message></p>
                <p><spring:message code="deleteButton.deleteButtonMessage" text="default text"></spring:message></p>
                <p><spring:message code="deleteButton.cancelButtonMessage" text="default text"></spring:message></p>
            </div>
            <div class="modal-header text-center">
                <button onclick="deleteItem()" name="addModel" class="btn btn-primary" type="submit"><spring:message code="deleteButton.countinuteButton" text="default text"></spring:message></button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="deleteButton.cancelButton" text="default text"></spring:message></button>
            </div>
        </div>

    </div>
</div>

<script>
    function submit(){
        document.getElementById("userForm").submit();
    };
    function deleteItem(){
        window.location = $("#url").val();
    }
    function setUrl(url){
        $("#url").val(url);
    }
</script>
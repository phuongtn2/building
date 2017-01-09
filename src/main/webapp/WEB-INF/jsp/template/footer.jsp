<%@page language="java" contentType="text/html;charset=UTF-8"%>
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
                <h4 class="modal-title text-center">Xác nhận thêm</h4>
            </div>
            <div class="modal-body text-center">
                <p>Bạn có muốn thêm</p>
                <p>Chọn "Xác nhận" để đi tiếp</p>
                <p>Chọn "Hủy" để quay lại</p>
            </div>
            <div class="modal-header text-center">
                <button onclick="submit()" name="addModel" class="btn btn-primary" type="submit">Xác nhận</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
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
                <h4 class="modal-title text-center">Xác nhận xóa</h4>
            </div>
            <div class="modal-body text-center">
                <p>Bạn có muốn xóa</p>
                <p>Chọn "Xác nhận" để đi xóa</p>
                <p>Chọn "Hủy" để quay lại</p>
            </div>
            <div class="modal-header text-center">
                <button onclick="deleteItem()" name="deleteModel" class="btn btn-primary" type="submit">Xác nhận</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
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
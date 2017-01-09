<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Thông Tin Tòa Nhà</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <form:form modelAttribute="buildingDto" method="post">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label" >Tên tòa nhà</label>
                                <input type="hidden" id="buildingCode" name="buildingCode" value="<c:if test="${buildingDto.buildingCode!= null}">${buildingDto.buildingCode}</c:if>" placeholder="Tên tòa nhà" class="form-control">
                                <input type="text" id="buildingName" name="buildingName" value="<c:if test="${buildingDto.buildingName!= null}">${buildingDto.buildingName}</c:if>" placeholder="Tên tòa nhà" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label" >Tổng số tầng</label>
                                <input type="text" id="totalFloor" name="totalFloor" value="<c:if test="${buildingDto.totalFloor!= 0}">${buildingDto.totalFloor}</c:if>" placeholder="Tổng số tầng" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label">Tổng số phòng</label>
                                <input type="text" id="totalRoom" name="totalRoom" value="<c:if test="${buildingDto.totalRoom!= 0}">${buildingDto.totalRoom}</c:if>" placeholder="Tổng số phòng" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label class="control-label">Mô tả</label>
                                <textarea type="text" rows="4" id="description" name="description" placeholder="Mô tả" class="form-control"><c:if test="${buildingDto.description!= null}">${buildingDto.description}</c:if></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="text-center">
                            <button name="add" class="btn btn-primary" type="submit"><i class="fa fa-check"></i>&nbsp;Lưu</button>
                            <button name="reset" class="btn btn-danger" type="button"><i class="fa fa-refresh"></i>&nbsp;Nhập lại</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
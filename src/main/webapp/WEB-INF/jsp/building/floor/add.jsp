<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>Thông Tin Tầng</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <form:form modelAttribute="floorDto" method="post">
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="form-group">
                                <label class="control-label" for="floorSeq">Tầng số</label>
                                <input type="hidden" id="floorCode" name="floorCode" value="<c:if test="${floorDto.floorCode!= 0}">${floorDto.floorCode}</c:if>"class="form-control">
                                <input type="text" id="floorSeq" name="floorSeq" value="<c:if test="${floorDto.floorSeq!= 0}">${floorDto.floorSeq}</c:if>" placeholder="Tầng số" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group">
                                <label class="control-label" for="totalRoom">Tổng số phòng</label>
                                <input type="text" id="totalRoom" name="totalRoom" value="<c:if test="${floorDto.totalRoom!= 0}">${floorDto.totalRoom}</c:if>" placeholder="Tổng số phòng" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label" for="floorAlias">Tên tầng</label>
                                <input type="text" id="floorAlias" name="floorAlias" value="<c:if test="${floorDto.floorAlias!= null}">${floorDto.floorAlias}</c:if>" placeholder="Tên tầng" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label">Loại tầng</label>
                                <select name="floorType" class="form-control m-b">
                                    <option <c:if test="${floorDto.floorType==1}" >selected</c:if> value="1">Bình dân</option>
                                    <option <c:if test="${floorDto.floorType==2}" >selected</c:if> value="2">Vip</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="text-center">
                            <button name="add" class="btn btn-primary " type="submit"><i class="fa fa-check"></i>&nbsp;Lưu</button>
                            <button name="reset" class="btn btn-danger " type="button"><i class="fa fa-refresh"></i>&nbsp;Nhập lại</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
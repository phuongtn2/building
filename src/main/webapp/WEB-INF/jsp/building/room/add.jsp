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
                <form:form modelAttribute="roomDto" method="post">
                    <div class="row">
                        <div class="col-sm-2">
                            <div class="form-group">
                                <label class="control-label" for="roomAlias">Tên phòng</label>
                                <input type="hidden" id="roomCode" name="roomCode" value="<c:if test="${roomDto.roomCode!= null}">${roomDto.roomCode}</c:if>" class="form-control">
                                <input type="text" id="roomAlias" name="roomAlias" value="<c:if test="${roomDto.roomAlias!= null}">${roomDto.roomAlias}</c:if>" placeholder="Tầng số" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group">
                                <label class="control-label" for="count">count</label>
                                <input type="text" id="count" name="count" value="<c:if test="${roomDto.count!= 0}">${roomDto.count}</c:if>" placeholder="count" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label class="control-label">status</label>
                                <select name="status" class="form-control m-b">
                                    <option <c:if test="${roomDto.status==1}" >selected</c:if> value="1">Phòng trống</option>
                                    <option <c:if test="${roomDto.status==2}" >selected</c:if> value="2">Đã thuê</option>
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
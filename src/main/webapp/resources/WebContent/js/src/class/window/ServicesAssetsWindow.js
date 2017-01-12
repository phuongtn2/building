module.exports = function ServicesAssetsWindow(
    serviceCode, serviceName,
    assetCode, assetName, assetPrice,
    afterSelectProc
){
    "use strict";
    var that = this;
    var servicesGird = null;
    var assetsGird = null;
    var _COL = {
        SELF: 0,
        SERVICE_NAME: 1,
        ASSET_NAME: 3,
        ASSET_PRICE: 4
    };
    var initWindow = function(){
        that.win = _ghWins.ghWins.createWindow("servicesAssetsWindow", 320, 30, 300, 260);
        that.win.setText("Services - Assets");
        that.win.setDimension(520, 400);
        that.win.button("minmax1").hide();
        that.win.button("park").hide();
        that.win.button("close").attachEvent("onClick", function(){
            that.win.setModal(false);
            that.win.close();
        });
        that.win.setModal(true);
    };
    var initForm = function(){
        var def = $.Deferred();
        that.subForm = that.win.attachForm();
        that.subForm.loadStruct(getTemplatePath(_GH.DATA_PATH.SERVICES_ASSETS_WIN), "json", function(){
            servicesGird = new dhtmlXGridObject(that.subForm.getContainer("services"));
            servicesGird.attachEvent("onDataReady",function(){
                _.forEach([0, 2, 3, 4], function(idx) { servicesGird.setColumnHidden(idx, true);});
            });
            assetsGird = new dhtmlXGridObject(that.subForm.getContainer("assets"));
            assetsGird.attachEvent("onDataReady",function(){
                _.forEach([0, 1, 2], function(idx) { assetsGird.setColumnHidden(idx, true);});
            });
            that.initGrid(servicesGird, 1, 0);
        });
        that.subForm.attachEvent('onButtonClick', function(name, command){
            switch(name) {
                case "selectBtn":
                    var selectServiceGird = (_.isEmpty(servicesGird))? null: servicesGird.getSelectedRowId();
                    //var originflg = false;
                    if (_.isEmpty(selectServiceGird)) {
                        dhtmlx.alert({
                            title : "Error",
                            type : "alert-error",
                            text : "Do not select item"
                        });
                        return;
                    } else {
                        if (!_.isNull(serviceCode)) serviceCode.value = servicesGird.cells(selectServiceGird, _COL.SELF).getValue();
                        if (!_.isNull(serviceName)) serviceName.value = servicesGird.cells(selectServiceGird, _COL.SERVICE_NAME).getValue();
                        /*var origin1 = servicesGird.cells(selectGird, _COL.ORIGIN).getValue();
                        if(origin1 === "1"){
                            originflg = true;
                        }*/
                    }
                    var selectAssetGird = (_.isEmpty(assetsGird))? null: assetsGird.getSelectedRowId();
                    if (!_.isEmpty(selectAssetGird)) {
                        if (!_.isNull(assetCode)) assetCode.value = assetsGird.cells(selectAssetGird, _COL.SELF).getValue();
                        if (!_.isNull(assetName)) assetName.value = assetsGird.cells(selectAssetGird, _COL.ASSET_NAME).getValue();
                        if (!_.isNull(assetPrice)) assetPrice.value = assetsGird.cells(selectAssetGird, _COL.ASSET_PRICE).getValue();
                        /*var origin2 = resTypeGrid2.cells(selectResType2, _COL.ORIGIN).getValue();
                        if(origin2 === "1"){
                            originflg = true;
                        }*/
                    } else {
                        if (!_.isNull(assetCode)) assetCode.value = "";
                        if (!_.isNull(assetName)) assetName.value = "";
                        if (!_.isNull(assetPrice)) assetPrice.value = "";
                    }
                    if(afterSelectProc && typeof afterSelectProc === "function"){
                        afterSelectProc(false);
                    }
                    that.win.close();
                    def.resolve();
                    break;
            }
        });
        return def.promise();
    };

    this.initGrid = function(grid, level, parentResTypeId) {
        var previousId;
        if (!_.isNull(grid)) grid.clearAll(true);
        grid.attachEvent("onRowSelect", function(selectId, ind){
            if (selectId === previousId) {
                return;
            } else {
                previousId = selectId;
            }
            var parentId = _.parseInt(grid.cells(selectId, _COL.SELF).getValue());
            if (level == 1) {
                that.initGrid(assetsGird, 2, parentId);
            } else{
                return;
            }
        });
        // get list services by buildingCode
        var uri = "";
        loadGridByGet(grid, _GH.DATA_PATH.SERVICES_ASSETS_GRID, uri, "serviceCode");
    };

    this.init = function(){
        initWindow();
        return initForm();
    };
};

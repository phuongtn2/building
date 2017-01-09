"use strict";

/**
 * 問合せ物件登録ウインドウ
 */
var RegisterEventWindow = function(showTouNo, processAfterRegister, personalId, entryId){
    var that = this;

    var setHandler = function(){
        that.form.attachEvent("onButtonClick", function(name){
            switch(name){
                case "L_003":
                    if (that.form.validate()) {
                        // 物件の登録
                        dhtmlx.message({ type:"info", text:"物件の登録"});
                        that.form.setItemValue("entryId", entryId);
                        var sendParam = that.form.getFormData();
                        sendParam.personalId = personalId;
                        postSyncURL(_GH.URL("ENTRY_EVENT_REGIST"), sendParam);
                        processAfterRegister();
                        that.win.setModal(false);
                        that.win.close();
                    }
                    break;
                default:
                    break;
            }
        });

        that.form.attachEvent("onFocus", function(name, value){
            switch(name){
                case "eventName": // Regista番号変更
                    _ghWins.doEvent(this.getInput("eventId"), this.getInput("eventName"), that.form);
                    break;
            };
        });
        // validation error process
        that.form.attachEvent("onValidateError", function (name/*, value, result*/) {
            dhtmlx.message({
                type:"error",
                text: that.form.getItemLabel(name) + "は必須入力です。"
            });
        });
    };

    var initForm = function(){
        //フォームの作成
        that.form = that.win.attachForm();
        var def = $.Deferred();
        that.form.loadStruct(loadJSON(getTemplatePath(_GH.DATA_PATH.EVENT_ADD_WIN_FORM)), "json", function() {
            def.resolve();
        });
        def.done(function () {
            // range and evaluation options setting
            that.form.reloadOptions("eventJoin", _prop.getOptionsWithEmpty(_GH.CODE_DEF.EVENT_JOIN));
        });

        return def.promise();
    };

    var initWindow = function(){
        that.winName = "registerProduction";
        that.win = _ghWins.ghWins.createWindow(that.winName, 20, 30, 400, 230);
        that.win.setText("問合せ物件登録ウィンドウ");
        that.win.hide();
        that.win.button("park").hide();
        that.win.button("minmax1").hide();
        that.win.button("close").attachEvent("onClick", function(){
            that.win.setModal(false);
            that.win.hide();
        });
        that.win.attachEvent("onClose", function(win){
            return true;
        });

    };

    this.init = function(){
        initWindow();
        initForm().done(function(){
            setHandler();
            that.win.setModal(true);
            that.win.show();
        });
    };
};

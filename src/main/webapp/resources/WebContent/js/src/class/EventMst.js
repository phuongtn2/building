/**
 * 物件マスタのオブジェクト
 * @param dhxObject アタッチするためのDHTMLXオブジェクト。
 */
module.exports = function EventMst(dhxObject) {
     var that = this;
     this.dhxObject = dhxObject;

     this.leftEventGrid;
     this.eventForm;

     var _CONST = _GH.EVENT_M;

     this.EVENTID = {
         L_001 : "L_001", //物件新規作成
         L_003 : "L_003" //物件削除
     };

     // 物件ツールバーの設定
     this.initToolbar = function(){
         var cell = that.dhxObject.cells("a");
         this.toolbar = cell.attachToolbar();
         this.toolbar.loadStruct(getTemplatePath(_GH.DATA_PATH.MST_EVENT_TOOLBAR_XML));
         this.toolbar.setIconSize(_GH.ICON_SIZE);
         this.toolbar.attachEvent("onClick", function(id){
             switch (id){
                 case that.EVENTID.L_001: // 物件新規作成
                     if (that.eventForm) {
                         if (!confirm("物件詳細に表示される内容がクリアされますが、よろしいですか？")) {
                             return;
                         }
                     }
                     that.leftEventGrid.clearSelection();
                     that.initEventForm(-1);
                     break;
             };
         })
     };

     // 物件エリア（左）の初期化
     this.initEventGrid = function() {
         var cell = that.dhxObject.cells("a");
         //予め、ページング用のオブジェクトを詰め込んでおく
         cell.attachHTMLString(_CONST.HTML.EVENT_BODY);
         cell.appendObject("csList");
         cell.appendObject("eventrecInfoArea");
         cell.appendObject("eventgridbox");
         cell.appendObject("eventpagingArea");
         if (that.leftEventGrid) {
             that.leftEventGrid.clearAll(true);
         }
         that.leftEventGrid = new dhtmlXGridObject("csList");
         that.leftEventGrid.setImagePath(_GH.PATH_TYPE.DHX_IMGS);
         that.leftEventGrid.setSkin(_GH.PATH_TYPE.SKIN);
         that.leftEventGrid.i18n.paging=_GH.FORMAT.GRID_NAV;
         that.leftEventGrid.enablePaging(true,50,10,"eventgridbox",true,"eventrecInfoArea");
         that.leftEventGrid.setPagingSkin(_GH.PATH_TYPE.GRID_NAV, _GH.PATH_TYPE.SKIN);
         that.leftEventGrid.enableAutoWidth(true);
         /** データロード時のイベント */
         that.leftEventGrid.attachEvent("onDataReady",function(){
             that.leftEventGrid.attachHeader((_ghUser.isNon())?
                     "#text_filter,#text_filter,#text_filter,#text_filter":"#text_filter,#text_filter,#text_filter,#text_filter");// フィルター追加
             // 表示グリッドを整える。
             that.resizeWindow(cell, "csList");
         });
         loadGridByGet(that.leftEventGrid, _GH.DATA_PATH.MST_EVENT_EVENT_GRID_JSON,  "event/list", "eventId", that.gridDataLogicFunc);
         /** 行選択時のイベント */
         that.leftEventGrid.attachEvent("onRowSelect", function(selectedEventId, cInd){
             console.log("onRowSelect  rId:" + selectedEventId + " cInd:" + cInd);
             that.initEventForm(selectedEventId);
         });
     };

     /**
      * 画面変更になった際に呼び出される。
      */
     this.resizeWindow = function(_cell, csListName) {
         resizeGridListInLayoutCell(_cell, csListName);
     };

     // Grid表示用にデータを振り分けるロジック
     this.gridDataLogicFunc = function(data, id, rowIdx) {
         var val;
         switch(id) {
             case "holdDate":
                 val = doDateFormatYyyymmdd(null, data[id]);
                 break;
             default:
               val = data[id];
               break;
         }
         return val;
     };
     // 物件詳細を初期化する。
     this.initEventForm = function(eventId) {
         var cell = that.dhxObject.cells("b");
         var saveUri = (eventId > 0)? "event/update": "event/regist";
         that.eventForm = cell.attachForm();
         that.eventForm.loadStruct(getTemplatePath(_GH.DATA_PATH.MST_EVENT_EVENT_FORM_JSON), "json", function(){
             $('[name=eventName]').attr("style","margin-left:24px");
             $('[name=customisedEventId]').attr("style","margin-left:10px");
             $('[name=eventType]').attr("style","margin-left:22px");
             $('[name=holdDate]').attr("style","margin-left:49px");
             $('[name=place]').attr("style","margin-left:61px");
             $('[name=participant]').attr("style","margin-left:37px");
             $('[name=memo]').attr("style","margin-left:64px; width:128%" );
             //事業部
             this.setItemValue("department", _ghUser.getLoginType());

             if (eventId > 0) {
                 // 更新：物件IDがある場合は、取得する。
                 var eventData = getJSONSync("event/id/" + eventId);
                 this.setFormData(eventData);
             } else {
                 //新規の場合は削除の必要がないので、削除ボタンは隠す
                 this.hideItem("L_003");

             }
         });

         that.eventForm.attachEvent("onButtonClick", function (name){
             switch(name) {
                 case that.EVENTID.L_001 : // 物件情報の保存
                     that.eventForm.updateValues();
                     if (that.eventForm.validate()) {

                         var regStr = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
                         var checkStartTime = false;
                         var checkEndTime = false;
                         var startTime = that.eventForm.getItemValue("startTime");
                         var endTime = that.eventForm.getItemValue("endTime");
                         if(startTime != ""){
                             var reg = new RegExp(regStr,"g");
                             checkStartTime = reg.test(startTime);
                         }else{
                             checkStartTime = true;
                         }

                         if(endTime != ""){
                             var reg = new RegExp(regStr,"g");
                             checkEndTime = reg.test(endTime);
                         }else{
                             checkEndTime = true;
                         }

                         if(!checkStartTime || !checkEndTime){
                             if(!checkStartTime){
                                 $('[name=startTime]').attr("style","color:red; width:42px");
                             }else{
                                 $('[name=startTime]').attr("style","color:black; width:42px");
                             }
                             if(!checkEndTime){
                                 $('[name=endTime]').attr("style","color:red; width:42px");
                             }else{
                                 $('[name=endTime]').attr("style","color:black; width:42px");
                             }
                             dhtmlx.message({
                                 type:"error",
                                 text: "開催日" + "は必須入力です。"
                             });
                         }else{
                             saveForm(that.eventForm, saveUri, function(){
                                 that.initEventGrid();
                                 that.initEventForm(-1);
                                 dhtmlx.message({text: "物件情報を保存しました。"});
                             });
                         }

                     }
                     break;
                 case that.EVENTID.L_003 : // 削除
                     if (confirm("物件を本当に削除しますか？")) {
                         var callback = function() {
                             that.leftEventGrid.deleteRow(that.leftEventGrid.getSelectedRowId());
                             that.initEventForm(-1);
                             dhtmlx.message({text: "物件情報を削除しました。"});
                         };
                         delURL("event/delete/" + eventId, null, callback);
                     }
                     break;
             };
         });
         // バリデートエラー時の処理
         that.eventForm.attachEvent("onValidateError", function (name, value, result){
             dhtmlx.message({
                 type:"error",
                 text: that.eventForm.getItemLabel(name) + "は必須入力です。"
             });
         });
     };

     /**
      * 初期化する。
      */
     this.init = function(){
         that.dhxObject.cells("a").setText("イベントマスタ");
         that.dhxObject.cells("b").setText("イベント詳細");
         var height = (_ghUser.isNon())? 380: 600;
         that.dhxObject.cells("b").setHeight(height);
         //that.dhxObject.cells("c").setText("棟・部屋一覧");
         that.initToolbar();
         that.initEventGrid();
     };


 };

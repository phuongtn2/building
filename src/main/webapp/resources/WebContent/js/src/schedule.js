require('dhtmlxvault');
require('dhxScheduler');
require('script!../../codebase/locale/locale_en.js');
require('script!../../codebase/ext/dhtmlxscheduler_tooltip.js');
require('script!../../codebase/scheduler.api.js');
require('script!../../codebase/dhtmlxscheduler_minical.js');
require('script!../../codebase/dhtmlxscheduler_timeline.js');
require('script!./class/SFAClassIF.js');
require('gh_wins');
require('gh_data');
//require('services_asset');
var ServicesAssetsWindow = require('./class/window/ServicesAssetsWindow.js');

function ohgHIREScheduleObject() {
    "use strict";
    var that = this;
    var _CONST = _GH.S07F010Sche;

    window.onerror = function (msg, file, line, column, err) {
        return true;
    };

    var URI = {
            SELF : "request_booking/list/self",
            DIVEMP : "request_booking/list/divemp?",
            ALL : "request_booking/list/dept",
            REGIST : "request_booking/regist",
            UPDATE : "request_booking/update"
    };

    // 配色のマッピング配列
    var mappingArray = [];
    var department = [];
    // 区切り文字
    var FILTER_DELIMITER = "/";
    // ミニカレンダー
    var miniCalendar = null;
    // 絞込フォーム
    var schdForm = null;

    // フォーマッタ
    var dateFormat=scheduler.date.date_to_str("%Y/%m/%d %H:%i");

    // ハッシュ変更のイベントリスナーの登録
    this.locationHashChanged = function() {
    };
    window.onhashchange = this.locationHashChanged;

    // 絞込ウィンドウのフォーム設定
    var FILTER_WIN_FORM = null;

    // インターバルID（定期実行を止めるときは clearInterval メソッドに渡すこと）
    var intervalID;
    // インターバル時間（ 分 * 秒 * ミリ秒）
    var INTERVAL_TIME = 5 * 60 * 1000;

    /** ----------------------------------------------------------- */
    /** 初期化 */
    /** ----------------------------------------------------------- */
    /**
     * 画面の初期化
     */
    this.init = function() {
        // setting timeout for binding channels
        // this._bindTM = window.setTimeout(function(){that._bindChannels();}, 1000);
        // テストモードを変える
        //if (!_ghUser.checkConnect()) {
            //_ghWins.doGoogleLoginWin(_ohgHire);
        //    return;
        //}
        // スケジュールの設定
        var def = that.setConfig();
        // Lightboxの設定
        //scheduler.init('scheduler_here',new Date(),"day");
        scheduler.showLightbox = function(id) {
            that.setEvent();
            var ev = scheduler.getEvent(id);
            that.doScheduleWindow(ev);
        };

        // インターバル（定期実行）処理
        def.done(function(){
            intervalID = setInterval(that.refresh, INTERVAL_TIME);
            //resetDhxFormItemValues(schdForm);
        });
    };
    /**
     * スケジュールをロードする。
     */
    this.loadSchedule = function() {
        scheduler.load(_GHAPI.SERVER + URI.SELF, "json");
    };
    /**
     * 表示リセット処理
     */
    this.refresh = function() {
        console.log("リセット処理！", new Date());
        scheduler.clearAll();
        that.loadSchedule();
    };
    /**
     * 絞り込みウィンドウ用のFormテンプレートを初期化する。
     */
    this.initFilterWinForm = function() {
        FILTER_WIN_FORM = null;
        FILTER_WIN_FORM = _.cloneDeep(_CONST.FILTER_WIN_FORM);
    };
    /**
     * Dhtmlx Schedulerの設定を行う。
     */
    this.setConfig = function() {
        // 複数日にまたがるイベントは禁止
        scheduler.config.multi_day = true;

        scheduler.config.event_duration = _GH.FORMAT.SCHD_INTERVAL * 60;
        scheduler.config.auto_end_date = true;
        scheduler.config.details_on_create = true;
        scheduler.config.details_on_dblclick = true;

        // サーバから来る日付型フォーマット
        scheduler.config.xml_date="%Y/%m/%d %H:%i";
        // Dynamic Loding時にリクエストに付与する日付フォーマット
        scheduler.config.load_date = "%Y/%m/%d";

        // カレンダーの表示日付の設定
        scheduler.config.scroll_hour = 9; // 朝の9時を一番上にする。
        scheduler.config.month_date = "%Y%F"; // 年月表示
        scheduler.config.week_date = "%D"; // 日付表示
        scheduler.config.day_date = "%M%j"; // 年月表示のデフォルト
        scheduler.config.default_date = "%Y %M %j"; // 年月日表示のデフォルト
        scheduler.config.api_date="%Y/%m/%d %H:%i";
        scheduler.config.dblclick_create = true;

        // イベントの編集バーは詳細ウィンドウだけ必要
        scheduler.config.icons_select = ["icon_details"];
        // レイアウトの設定
        var def = that.setLayout();
        def.done(function(){
            // ビュー変更イベントの設定
            that.setEvent();
            // イベントのフィルタリング設定
           // that.setEventFilter();
        });
        // ツールチップの設定
        scheduler.templates.tooltip_text = function(start, end, event) {
            //return that.getEventText(_CONST.VIEW_MODE.DAY, event);
            return event.text;
        };
        return def;
    };
    /**
     * イベントの設定を行います。
     */
    this.setEvent = function() {
        // ビュー変更前イベント
        scheduler.attachEvent("onBeforeViewChange", function(old_mode, old_date, mode, date){
            // 表示内容をリフレッシュする。
            console.log("onBeforeViewChange", old_mode, old_date, mode, date);
            return true;
        });

        // ビューの変更イベント。それぞれで異なる表示をするように設定する。
        scheduler.attachEvent("onViewChange", function (new_mode , new_date){
            console.log("onViewChange", new_mode , new_date);
            switch(new_mode) {
                case "day":
                    scheduler.templates.event_text = function(start, end, event){
                        return event.text;
                    };
                    break;
                case "week": // 「週」表示
                    scheduler.templates.event_text = function(start, end, event){
                        return event.text;
                    };
                    break;
                case "month":
                    scheduler.templates.event_text = function(start, end, event){
                        return event.text;
                    };
                    break;
            }
            scheduler.updateView();
        });

        // イベントロード時
        scheduler.attachEvent("onEventLoading", function(ev){
            // すでに同じイベントが存在するかをチェックする。
            if (_.find(scheduler.getEvents(), function(e) { return e.t_bookServiceCode === ev.t_bookServiceCode})) {
                // 同じイベントが存在するため、画面にセットしない。
                scheduler.updateView();
                return false;
            } else {
                // 表示テキストの設定（月表示用）
                //ev.text = that.getEventText(_CONST.VIEW_MODE.DAY, ev);
                // イベントのカラー設定
                that.setEventColor(ev, mappingArray);
                // 色ラベルを作る
                that.showEventLabel(schdForm, mappingArray);
                // 表示内容をリフレッシュする。
                scheduler.updateView();
                return true;
            }
        });

        // ロードエラー時
        scheduler.attachEvent("onLoadError", function(resp){
            console.log("onLoadError", resp);
            dhtmlx.message("Failed to load data");
        });

        // イベント変更前（ドラッグ＆ドロップ完了前）。
        scheduler.attachEvent("onBeforeEventChanged", function(event, mouseEvent, is_new, original){
            console.log("onBeforeEventChanged", event, mouseEvent, is_new, original);
            event.is_new = is_new;
            if (is_new) {
                //TODO ６月リリースには不要。次回検討
                // 新規イベントの場合、独自にウィンドウを呼び出す。
//                event.scheduleTimeFrom = event.start_date;
//                event.scheduleTimeTo = event.end_date;
//                event.organizer = _ghUser.getGhId();
//                event.organizerFullName = _ghUser.getName();
                return false;
            } else {
                // 変更後の日時を設定する。
                event.bookFrom = getStrDate(event.start_date, _GH.FORMAT.YMD_HMS);
                event.bookTo = getStrDate(event.end_date, _GH.FORMAT.YMD_HMS);
                postSyncURL(URI.UPDATE, event);
                scheduler.updateEvent(event.id);
                return true;
            }
        });
        scheduler.attachEvent("onDblClick", function (id, e){
            that.doScheduleWindow(e);
        });

        scheduler.attachEvent("onEmptyClick", function (date, e){
            that.doScheduleWindow(e);
        });
        // イベントをドラッグする前に、操作できるかどうかチェックする。
        //scheduler.attachEvent("onBeforeDrag", that.canOperate);
    };
    /**
     * 該当イベントが操作可能かどうかを返す。
     */
    /*this.canOperate = function(eventId) {
        var event = scheduler.getEvent(eventId);
        if (!event) {
            // イベントがなければ、別段問題なしとして、trueを返す。（操作対象がないため）
            that.doScheduleWindow(event);
            return true;
        }else {
            //dhtmlx.message("イベントを操作する権限がありません。");
            //event.readonly = true;
            that.doScheduleWindow(event);
            return false;
        }
    };*/
    /**
     * イベントのフィルタリング設定を行います。
     */
    this.setEventFilter = function() {
        scheduler.filter_month = scheduler.filter_day = scheduler.filter_week = function(id, event) {
            // フォームを更新する
            schdForm.updateValues();
            // 絞込フォームから必要な物を取得する。
            var fd = schdForm.getFormData();
            if (fd.buildingCode) {
                // 入力欄をデリミタで分割し、その中に対象となる物件が含まれているかをチェック
                var preSelectArea = fd.buildingCode.split(FILTER_DELIMITER);
                var targetValue = event.buildingName;
                if (_.indexOf(preSelectArea, targetValue) == -1) {
                    return false;
                }
            }
            // ランクの絞込
            if (fd.statusFrom != "" || fd.statusTo != "") {
                var recruit = parseInt(event.recruitStatus);
                var recruitFrom = (fd.statusFrom == "")? -1: parseInt(fd.statusFrom);
                var recruitTo = (fd.statusTo == "")? -1: parseInt(fd.statusTo);
                // from / toが逆の場合は逆にする
                if (recruitFrom > recruitTo) {
                    recruitTo = [recruitFrom, recruitFrom = recruitTo][0];
                }
                if (recruit < recruitFrom || recruitTo < recruit) {
                    return false;
                }
            }
            return true;
        };
    };
    /**
     * ミニカレンダーの設定を行います。
     * @param {Object} baseObject ミニカレンダーを配置するタグのIDまたはオブジェクト
     */
    this.setMiniCalendar = function(baseObject) {
        miniCalendar = scheduler.renderCalendar({
            container : baseObject,
            navigation : true,
            handler : function(date) {
                scheduler.setCurrentView(date, scheduler._mode);
            }
        });
        scheduler.templates.calendar_month = scheduler.date.date_to_str("%Y%F"); // ミニカレンダーの年月フォーマット
        scheduler.linkCalendar(miniCalendar); // カレンダーとミニカレンダーを連携させる。
        scheduler.setCurrentView(scheduler._date, scheduler._mode);
    };
    /**
     * レイアウトの設定を行います。
     */
    this.setLayout = function() {
        console.log("スケジュール画面を初期化");
        // レイアウトの初期化
        if (this.dhxLayout != null) {
            this.dhxLayout.unload();
        }
        this.dhxLayout = new dhtmlXLayoutObject("schedule", _GH.SCREEN_TYPE.SCHEDULE['pattern']);
        this.dhxLayout.setOffsets({top : 8, left : 8, right : 8, bottom : 8});
        this.dhxLayout.cells("a").setHeight(250);
        // ダブルクリックで開いたり、閉じたりさせる。
        this.dhxLayout.attachEvent("onDblClick", function(name){
            if (this.cells(name).isCollapsed()) {
                this.cells(name).expand();
            } else {
                this.cells(name).collapse();
            }
        });
        // スケジュール操作画面（左）
        var def = that.setFilteringArea(this.dhxLayout.cells("a"));
        // カレンダー画面（右）
        that.setCalendarArea(this.dhxLayout.cells("b"));
        return def;
    };
    /**
     * 左側のカレンダーと絞込エリアの設定を行います。
     * @param {Object} layoutCell 絞込エリアを設定するレイアウトのセル。
     */
    this.setFilteringArea = function(layoutCell) {
        layoutCell.setText("Filter Request Booking");
        //layoutCell.setWidth(330);
        schdForm = layoutCell.attachForm();
        var def = $.Deferred();
        schdForm.loadStruct(getTemplatePath(_GH.DATA_PATH.SCHD_FORM_JSON), "json", function() {
            schdForm.reloadOptions("status", _prop.getOptionsWithEmpty(_GH.CODE_DEF.STATUS));
            that.setMiniCalendar(schdForm.getContainer("miniCalendarContainer"));
            def.resolve();
        });
        // ボタンクリックイベント
        schdForm.attachEvent('onButtonClick', function(name, command){
            switch(name) {
                case "reset": // 絞り込みリセットボタン
                    // 検索内容をリセットする。
                    resetDhxFormItemValues(schdForm);
                    scheduler.updateView();
                    break;
                case "filter": // 全案内・来場予約表示ボタン
                    scheduler.clearAll();
                    resetDhxFormItemValues(schdForm);
                    scheduler.load(_GHAPI.SERVER + URI.ALL, "json");
                    scheduler.updateView();
                    break;
                case "printPDFBtn":
                    var filename = "Request Booking" + getStrDate(new Date()) + ".pdf";
                    var minDate = scheduler.getState().min_date;
                    var maxDate = scheduler.getState().max_date;
                    maxDate.setHours(maxDate.getHours() - 1);
                    var format = _GH.FORMAT.YMD;
                    scheduler.exportToPDF({
                        name: filename,
                        format:"A4",
                        orientation:"portrait",
                        zoom:1,
                        header:"<h1>Building " + getStrDate(minDate, format) + " - " + getStrDate(maxDate, format) + "</h1>",
                        footer:"<h4>" + _ghUser.getName() + " " + getStrDate(new Date(), format) + "</h4>"
                    });
                    break;
            }
        });
        schdForm.attachEvent('onFocus', function(name, command){
            switch(name) {
                //phuongtn2
                case "buildingName":
                    _ghWins.doBuilding(schdForm.getInput("buildingCode"), schdForm.getInput("buildingName"), schdForm)

                    break;
            }
        });
        schdForm.attachEvent("onChange", function (name, value){
            scheduler.updateView();
       });
        return def;
    };
    /**
     * 画面右のカレンダー表示エリアの設定を行います。
     * @param {Object} layoutCell 絞込エリアを設定するレイアウトのセル。
     */
    this.setCalendarArea = function(layoutCell) {
        //layoutCell.setWidth(1100);
        layoutCell.hideHeader();
        layoutCell.attachScheduler(new Date(), "week", _CONST.HTML.CAL_BTN);
        // Dynamic Loadingの設定を行う。
        scheduler.config.show_loading = false; // ロード時のスピナー設定
        scheduler.setLoadMode("week");
        // イベントのロード
        that.loadSchedule();
    };
    /**
     * イベント表示用のテキストを取得します。
     * @param {String} viewMode 表示するViewの種類[day, week, month]
     * @param {Object} event Dhtmlx SchedulerのEventオブジェクト
     */
    this.getEventText = function (viewMode, event) {
        var text = "";
        switch(viewMode) {
            case _CONST.VIEW_MODE.DAY: // 「日」表示
            case _CONST.VIEW_MODE.WEEK: // 「週」表示
                text = event.status + " " + event.memo;
                break;
            case _CONST.VIEW_MODE.MONTH: // 「月」表示
            default:
                text = event.status + " " + event.memo;
                break;
        }
        text = text.split("null").join("");
        text = text.split("undefined").join("");
        return text;
    };
    this.setEventColor = function(event, mappingArray) {
        that.initFilterWinForm(); // 絞り込みウィンドウの初期化

        var colorDense = event.status;
        var colorSet = _GH.S07F010Sche.EVENT_COLOR.NORMAL[colorDense];
        if (mappingArray.indexOf(event.status) < 0) {
            mappingArray.push(event.status);
        }
        event.color = colorSet.back;
        event.textColor = colorSet.text;
    };
    this.showEventLabel = function(schdForm, mappingArray) {
        var tableHtml = "<table border=1 rules='rows'><thead>";
        tableHtml += "<tr><th>採用ステータス</th><th>色</th></tr>";
        tableHtml += "</thead><tbody>";
        for (var i=0; i<mappingArray.length; i++) {
            var mapArr = mappingArray[i];
            var colorSets = _GH.S07F010Sche.EVENT_COLOR.NORMAL[mapArr];
            tableHtml += "<tr><td width='150px' height='20px'>" + _prop.getPropName(_GH.CODE_DEF.STATUS, mapArr) + "</td>"
                + "<td width='100px' bgcolor='" + colorSets.back + "' style='color:" + colorSets.text + "'></td>" + "</tr>";
        }
        tableHtml += "</tbody></table>";
        schdForm.getContainer("eventLabelContainer").innerHTML = tableHtml;
    };
    this.doBuildingWin = function(targetInput) {
        var win = _ghWins.ghWins.createWindow("buildingWin", 30, 100, 350, 450);
        win.setText("Select building");
        win.button("park").hide();
        win.button("minmax1").hide();
        win.button("close").attachEvent("onClick", function(){
            win.setModal(false);
            win.close();
        });
        win.attachEvent("onClose", function(win){
            scheduler.updateView();
            return true;
        });
        var buildingForm = win.attachForm();
        buildingForm.loadStruct(FILTER_WIN_FORM, "json", function() {
            var inpVal = targetInput.value;
            if (_.isString(inpVal) && inpVal.length > 0) {
                var preSelectArea = inpVal.split(FILTER_DELIMITER);
                var dataset = {};
                _.forEach(buildingForm.getFormData(), function(value, key, object) {
                    if (_.indexOf(preSelectArea, buildingForm.getItemValue(key)) >= 0) {
                        dataset[key] = true;
                    } else {
                        dataset[key] = false;
                    }
                });
                buildingForm.setFormData(dataset);
            }
        });
        buildingForm.attachEvent("onButtonClick", function(name){
            if (name == "selectBtn") {
                var text = "";
                _.forEach(buildingForm.getFormData(), function(value, key, object) {
                    if (value != 0) {
                        text += value + FILTER_DELIMITER;
                    }
                });
                text = text.substring(0, text.length - 1);
                targetInput.value = text;
                win.setModal(false);
                win.close();
            } else if (name == "allCheck") {
                var dataset = {};
                _.forEach(buildingForm.getFormData(), function(value, key, object) {
                    dataset[key] = true;
                });
                buildingForm.setFormData(dataset);
            } else if (name == "allUncheck") {
                var dataset = {};
                _.forEach(buildingForm.getFormData(), function(value, key, object) {
                    dataset[key] = false;
                });
                buildingForm.setFormData(dataset);
            }
       });
        win.setModal(true);
        win.show();
    };
    this.doScheduleWindow = function(event) {
        if (_ghWins.ghWins.window("scheduleWin") == null) {
            var win = _ghWins.ghWins.createWindow("scheduleWin", 20, 30, 625, 660);
            win.setText("Request Booking");
            win.hide();
            win.button("park").hide();
            win.button("minmax1").hide();
            win.button("close").attachEvent("onClick", function(){
                if (event.is_new) {
                    scheduler.deleteEvent(event.id);
                }
                win.setModal(false);
                win.close();
            });
        }
        var win = _ghWins.ghWins.window("scheduleWin");
        var scheduleForm = win.attachForm();
        scheduleForm.loadStruct(getTemplatePath(_GH.DATA_PATH.SCHD_LIGHT_FORM_JSON), "json", function() {
            scheduleForm.setFormData(event);
            //if (!that.canOperate(event.id)) {
            //    scheduleForm.lock();
            //}
        });
        scheduleForm.attachEvent('onButtonClick', function(name, command){
            scheduleForm.updateValues();
            switch(name) {
                case "saveBookBtn":
                    event.t_bookServiceCode = scheduleForm.getItemValue("t_bookServiceCode"); // スケジュールID
                    event.serviceCode = scheduleForm.getItemValue("serviceCode"); // スケジュールID
                    event.assetCode = scheduleForm.getItemValue("assetCode"); // 顧客ID
                    event.start_date = scheduleForm.getItemValue("bookFrom");
                    event.end_date = scheduleForm.getItemValue("bookTo");
                    event.status = scheduleForm.getCheckedValue("status");
                    event.memo = scheduleForm.getItemValue("memo"); //
                    var uri = (event.t_bookServiceCode)? URI.UPDATE: URI.REGIST;
                    if (scheduleForm.validate()) {
                        if (!isDateInRange(scheduleForm.getItemValue("bookFrom"), 7)) {
                            if (!confirm(_MSG.S07F020.M_001)) return;
                        }
                        saveForm(scheduleForm, uri);
                        scheduler.updateEvent(event.id);
                        scheduler.updateView();
                        if(event.status === 1) {
                            scheduler.deleteEvent(event.id);
                        }
                        that.refresh();
                        win.close();
                    }
                    break;
                case "deleteBookBtn":
                    window.open(encodeURI(_GH.PATH_TYPE.CS_MAIN + scheduleForm.getItemValue("personalId")));
                    break;
            }
        });

        scheduleForm.attachEvent("onInputChange", function(name, value, form){
            switch(name) {
                case "bookFrom": // 案内日時（開始）
                    // 案内日時（終了）に2時間後の値を入れる。
                    var fromTime = scheduleForm.getItemValue("bookFrom");
                    fromTime.setHours(fromTime.getHours() + _GH.FORMAT.SCHD_INTERVAL);
                    scheduleForm.setItemValue("bookTo", fromTime);
                    break;
                case "bookTo":
                    var fromTime = scheduleForm.getItemValue("bookFrom");
                    var toTime = scheduleForm.getItemValue("bookTo");
                    fromTime.setFullYear(toTime.getFullYear());
                    fromTime.setMonth(toTime.getMonth());
                    fromTime.setDate(toTime.getDate());
                    scheduleForm.setItemValue("bookFrom", fromTime);
                    break;
            };
        });
        scheduleForm.attachEvent("onFocus", function(name){
            switch(name) {
                case "services":
                    var servicesAssets = new ServicesAssetsWindow(
                        scheduleForm.getInput("serviceCode")
                        ,scheduleForm.getInput("serviceName")
                        ,scheduleForm.getInput("assetCode")
                        ,scheduleForm.getInput("assetName")
                        ,scheduleForm.getInput("assetPrice")
                        , null
                    );
                    servicesAssets.init();
                    break;
            }
        });
        scheduleForm.attachEvent("onValidateError", function (name, value, result){
            dhtmlx.message({
                type:"error",
                text: scheduleForm.getItemLabel(name) + "input error!"
            });
        });
        win.setModal(true);
        win.show();
    };

    this.initWins = function() {
        _ghWins = null;
        _ghWins = new CommonWindows();
    };
    this.initWins();
}
var _ohgHire;
function init() {
    _ohgHire = new ohgHIREScheduleObject();
    _ohgHire._unreadTMTime = 1;
    _ohgHire.init();
}
window.addEventListener("load",function(){
    init();
}, false);
// テスト時は書き換えること。本番はnull, テストはその他
var _GH_MODE_LIST = ["PROD", "TEST", "DEV"];
var _GH_MODE = _GH_MODE_LIST[2];
var _GH_CONNS = {
    PROD: "",
    TEST: window.location.protocol + "//" + window.location.host + "/GH_API/gh/",
    DEV : "http://localhost:3000/"
};

/**
 * API接続先情報
 * TODO モードによって切り替えたい
 */
var _GHAPI = {
    SERVER : window.location.protocol + "//" + window.location.host + "/",
    URL : {
        AUTH : "auth/login/"
    }
};
var _GH = (function(){
    "use strict"
    return {
        URL: function(url) {
            return _GHAPI.URL[url];
        },
        FILE_ROOT: window.location.protocol + "//" + window.location.host + "/",
        ICON_SIZE: 24,
        SCREEN_TYPE : {
            SCHEDULE: {pattern : "2E", name : "Request Booking", hash: "#schedule", useEntryTree: false, loginType: [1, 2]}
        },
        PATH_TYPE : {
            TEMPLATE : "/resources/WebContent/template/data/",
            DHX_ICONS : "/resources/WebContent/codebase/imgs/dhxtoolbar_skyblue",
            SKIN : "dhx_skyblue",
            GRID_NAV: "bricks",
            DHX_IMGS : "/resources/WebContent/codebase/imgs/",
            CODE_DEF : "/resources/WebContent/template/codeDef.json"
        },
        // コード定義
        CODE_DEF : {
            STATUS: 1,
            SCHED_DEL_REASON : 67,
            SCHED_MS_PURPOSE : 69,
            SCHED_COUNT   : 95,
            SCHED_COND        : 96,
            SCHED_MS_PLACE : 97
        },
        // データファイル名
        DATA_PATH : {
            SCHD_FORM_JSON : {filename: "FilterRequest.json", common: true},
            SCHD_LIGHT_FORM_JSON : {filename: "BookingForm.json", common: true},
            SERVICES_ASSETS_WIN : {filename: "ServicesAssetsWin.json", common: true},
            SERVICES_ASSETS_GRID : {filename: "ServicesAssetsGrid.json", common: true},
            WIN_BUILDING_FORM_JSON : {filename: "BuildingWinForm.json", common: true},
            BUILDING_GIRD : {filename: "BuildingGrid.json", common: true}
        },
        FORMAT : {
            YMD : "%Y/%m/%d",
            YMD_HM : "%Y/%m/%d %H:%i",
            YMD_HMS : "%Y/%m/%d %H:%i:%s",
            YMDHMS : "%Y%m%d%H%i%s",
            AREA_DELIMITER: "/",
            SCHD_INTERVAL: 2,
            GRID_NAV: {
                records:"レコード数&nbsp;&nbsp;",
                to:" - ",
                page:"ページ",
                perpage:"行 / ページ",
                of:" / 全",
                notfound:"該当データがありませんでした。"
            },
            GRID_BORDER1: "border-style: solid; border-width: 0px 0.5px; border-color: rgba(118, 118, 118, 0.5);" /** 縦にGrayの罫線を引く */
        },
        // キーボードのキーコード
        KEY_CODE : {
            ENTER: 13
        },
        WINS : {
            EVENT_MODE : {
                // ウィンドウの幅、ウィンドウの高さ、物件専用フラグ、物件＋部屋フラグ
                EVENT: {width: 360, height: 410, isEvent: true, isRoom: false}
            }
        },
        // スケジュール
        S07F010Sche : {
            HTML: {
                // カレンダー表示切り替えボタン用のソース
                CAL_BTN : '<div class="dhx_cal_tab" name="day_tab" style="right:204px;"></div>' // 日表示
                + '<div class="dhx_cal_tab" name="week_tab" style="right:140px;"></div>' // 週表示
                + '<div class="dhx_cal_tab" name="month_tab" style="right:280px;"></div>' // 月表示
            },
            // イベントのカラー定義
            EVENT_COLOR: {
                NORMAL: { // １つ目は薄い色、２つ目は濃い色。事業部によって定義が異なる。
                    1: {back: "#1D2088", text: "#000000"},
                    2: {back: "#A3BCE2", text: "#000000"},
                    3: {back: "#6C9BD2", text: "#000000"}
                }
            },
            // Calendar Viewの種別定義
            VIEW_MODE: {
                DAY : "day",
                WEEK : "week",
                MONTH : "month"
            },
            // 絞込ウィンドウ
            FILTER_WIN_FORM : [
                {type: "settings", position: "label-right"},
                {type: "fieldset", label: "表示させる項目を選んでください", width: 300, offsetLeft: 15, list:[]},
                {type: "fieldset", label: "オプション", width: 300, offsetLeft: 15, list:[
                    {type: "button", name: "allCheck", value: "すべてチェック"},
                    {type: "newcolumn"},
                    {type: "button", name: "allUncheck", value: "すべてはずす", offsetLeft: 15}
                ]},
               {type: "button", name: "selectBtn", value: "選択する", offsetLeft: 200}
           ],
           TANTO_TYPE: {
               // 案内者
               1 : {id: "organizer", name: "organizerFullName"},
               // 同行者
               2 : {id: "attendee1", name: "attendeeFullName1"},
               // 接客者
               3 : {id: "attendee2", name: "attendeeFullName2"},
               4 : {id: "attendee3", name: "attendeeFullName3"}
           }
        }
    };
}());

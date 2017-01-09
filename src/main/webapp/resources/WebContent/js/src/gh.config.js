/**
 * カレンダーの日本語化設定
 * add once, make sure dhtmlxcalendar.js is loaded
 */
dhtmlXCalendarObject.prototype.langData["jp"] = {
    // date format
    dateformat: "%Y/%m/%d %H:%i:%s",

    //カレンダー表示時の年月表記
    hdrformat:"%Y年 %n月",

    // full names of months
    monthesFNames: [
        "1月","2月","3月","4月","5月","6月","7月",
        "8月","9月","10月","11月","12月"
    ],
    // short names of months
    monthesSNames: [
        "1月","2月","3月","4月","5月","6月","7月",
        "8月","9月","10月","11月","12月"
    ],
    // full names of days
    daysFNames: [
        "日曜日","月曜日","火曜日","水曜日",
        "木曜日","金曜日","土曜日"
    ],
    // short names of days
    daysSNames: [
        "日","月","火","水",
        "木","金","土"
    ],
    // starting day of a week. Number from 1(Monday) to 7(Sunday)
    weekstart: 1,
    // the title of the week number column
    weekname: "週"
};
//make custom language default
dhtmlXCalendarObject.prototype.lang = "jp";

/**
 * ファイルアップロード用
 */
dhtmlXVaultObject.prototype.strings = {
    done:       "完了しました。",     // text under filename in files list
    error:      "エラーが発生しました。",    // text under filename in files list
    size_exceeded: "アップロード可能な容量を超えています。 (最大 #size#)",
    btnAdd:     "ファイルを追加",   // button "add files"
    btnUpload:  "アップロード",   // button "upload"
    btnCancel:  "キャンセル",   // button "cancel uploading"
    btnClean:   "クリアー",    // button "clear all"

    dnd:        "ここにドラッグ＆ドロップ"  // dnd text while the user is dragging files
};

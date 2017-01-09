/**
 * SFAのユーティリティクラス
 */
/**
 * 引数を文字列として返す。
 */
function toString(str) {
    if (str) {
        return str;
    } else {
        if (_.isDate(str) || _.isNumber(str)) {
            return str;
        } else {
            return "";
        }
    }
}

/**
 * 型判定
 * @param type 型名（String,Number,Boolean,Date,Error,Array,Function,RegExp,Object）
 * @param obj 判定したいオブジェクト
 */
function is(type, obj) {
    var clas = Object.prototype.toString.call(obj).slice(8, -1);
    return obj !== undefined && obj !== null && clas === type;
}

/**
 * null,undefined を指定した文字列に変換する。デフォルト空文字
 * @param value 変換対象文字列
 * @param target 変換文字列
 * */
function replaceStr(value,target){
    if(value === undefined || value === null){
        var replaceStr = "";
        if(target !== undefined && target !== null){
            replaceStr = target;
        }
        return replaceStr
    }
    return value;
}

function getTemplatePath(dataPath) {
    return _GH.PATH_TYPE.TEMPLATE + dataPath.filename;
}

// JSON文字列をJSONオブジェクトに変更する。
// 安全のため、直接JSON.parseを実行せず、これを呼び出すこと。
function parseJSON(json) {
    if (!json) {
        return {};
    } else {
        try {
            return window.dhx4.s2j(json);
        } catch (e) {
            console.log("JSONのパースに失敗しました。[%s]", e.message);
            return {};
        }
    }
}

function getJSONValue(json, key) {
    var parsedJSON;
    if (!json || is("String", json)) {
        parsedJSON = parseJSON(json);
    } else {
        parsedJSON = json;
    }
    console.log("json = ", parsedJSON, typeof parsedJSON);
    return parsedJSON[key];
}

/** ------------------------------------------------
 *  ■セッションストレージ用のスクリプト
 *   ------------------------------------------------ */
/**
 * セッションストレージから指定したキーに対応する値を取得する。
 */
function getSS(key) {
    var getVal = sessionStorage.getItem(key);
    if (getVal) {
        return getVal;
    } else {
        return "";
    }
}

/**
 * セッションストレージに指定したキーと値をセットする。
 * @param key
 * @param value
 */
function setSS(key, value) {
    sessionStorage.setItem(key, value);
}

/**
 * セッションストレージに指定したキーに値を追記する。
 *
 * @param key
 * @param value
 */
function addSS(key, value) {
    var preVal = getSS(key);
    setSS(key, preVal + value);
}

/**
 * セッションストレージから指定したキーの情報を削除する
 * @param key
 */
function deleteSS(key) {
    sessionStorage.removeItem(key);
}

/** ------------------------------------------------
 *  ■URL関連のスクリプト
 *   ------------------------------------------------ */
/**
 * ハッシュをパースする。
 * ハッシュの構造は#hash_name/1という形式とする。
 * 戻りは以下のオブジェクトを返す。
 * {
 *   hash : hash_name,
 *   param : 1
 * }
 */
function getHash() {
    // ハッシュを取得する。「#hash_name/1」
    var ghHash = location.hash;
    // /で分割する(最大２つ）
    var parts = ghHash.split("/", 2);
    // 長さが１の場合は、空文字を追加する。
    if (parts.length == 1) {
        parts.push("");
    }
    return {
        hash : parts[0],
        param : parts[1]
    }
}
/**
 * 現在のロケーションから画面種別を取得する。
 * @returns
 */
function getScreenType() {
    return _GH.SCREEN_TYPE.SCHEDULE;
}
/**
 * クエリ・ストリングを取得する。
 * @param {Object} obj キー：バリューのオブジェクト
 * @return POST, GET用のクエリ・ストリング foo=etc&bar=all
 */
function getQueryString(obj) {
    var str = [];
    for(var p in obj)
      if (obj.hasOwnProperty(p)) {
        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
      }
    return str.join("&");
}

/**
 * 対象の日付の文字列型を返す
 * 引数なしでもOK
 * @param date Dateオブジェクト
 * @param format 日付の成形フォーマット
 * @return 指定フォーマットの文字列
 * */
function getStrDate(date,format){
    var dt;
    var fmt;
    if(!is("Date",date)){
        dt = new Date();
    }else{
        dt = date;
    }
    if(!is("String",format)){
        fmt = _GH.FORMAT.YMDHMS;
    }else{
        fmt = format;
    }

    var cal = new dhtmlXCalendarObject();
    return cal.getFormatedDate(fmt, dt);
}


/**
 * ファイルから取得したJSONデータにヘッダー情報を設定する。
 * @param headerPath ヘッダー用のJSONファイルのパス
 * @param dataPath サーバより取得したデータ用のJSONファイルのパス
 *
 * */
function replaceJSON(headerPath, dataPath){
    // ファイルからJSONを読み込む
    var jsonHeader = null;
    var _jsonHeaderString = "";
    var jsonData = null;
    var _jsonDataString = "";
    var replaceJsonString = "";

    jsonHeader = window.dhx4.ajax.getSync(headerPath);
    _jsonHeaderString = jsonHeader.xmlDoc.responseText;
    jsonData = window.dhx4.ajax.getSync(dataPath);
    _jsonDataString = jsonData.xmlDoc.responseText;

    //ヘッダーを変換
    replaceJsonString = _jsonDataString.replace(_GH.CONVERSION.JSON_HEADER,_jsonHeaderString);
    //Webサイトを変換
    replaceJsonString = replaceJsonString.split(_GH.CONVERSION.WEB_SITE).join(_GH.PATH_TYPE.WEB_SITE);
    //Web会員(一般)アイコンを変換
    replaceJsonString = replaceJsonString.split(_GH.CONVERSION.MEMBER_N).join(_GH.PATH_TYPE.WEB_MEMBER_N);
    //Web会員(プレミアム)アイコンを変換
    replaceJsonString = replaceJsonString.split(_GH.CONVERSION.MEMBER_P).join(_GH.PATH_TYPE.WEB_MEMBER_P);

    return replaceJsonString;
}

/**
 * ファイルからJSONオブジェクトを生成する。
 * @param path JSONファイルのパス
 * @return JSONオブジェクト
 * */
function loadJSON(path){
    // ファイルからJSONを読み込む
    var json = null;
    var jsonString = "";

    json = window.dhx4.ajax.getSync(path);
    jsonString = json.xmlDoc.responseText;

    return parseJSON(jsonString);
}

/**
 * BASE64をUTF8文字列に変換するメソッド
 * @param str
 * @returns
 */
function b64_to_utf8(str) {
    if (!str) return "";
    return decodeURIComponent(escape(window.atob(str)));
}

/**
 * オブジェクトから指定したパラメータを取り除く。
 * オブジェクトから作ったクローンに対して処理をするので、元のオブジェクトは変更されない。
 * @param {Object} obj 要素を取り除く対象となるオブジェクト
 * @param {Array} removeParamArray 取り除く要素のキー配列
 * @returns 取り除いた後のオブジェクト
 */
function remove_params_from_object_clone(obj, removeParamArray) {
    if (!_.isObject(obj)) {
        return obj;
    }
    // オブジェクトを壊さないためにクローンを作る。
    var cloneObj = _.cloneDeep(obj);
    _.forEach(removeParamArray, function(key) {
        delete cloneObj[key];
    });
    return cloneObj;
}

/**
 * フォーム値変更チェック。変更されていればtrue
 * @param {Object} beforeDataForm 最初のフォームデータ（getFormData()で取得）
 * @param {Object} afterDataForm 比較するフォームデータ（getFormData()で取得）
 * @param {Array} exceptKeys 比較を除外するキー配列
 */
function doChangeForm(beforeDataForm, afterDataForm, exceptKeys) {
    var doChange = false;
    if (!_.isObject(beforeDataForm)) {
        return doChange;
    }
    if (!_.isArray(exceptKeys)) exceptKeys = [];
    _.forEach(beforeDataForm, function(beforeVal, key) {
        if (!_.has(exceptKeys, key) && afterDataForm[key] !== beforeVal) {
            doChange = true;
        }
    });
    return doChange;
}

/**
 * 誕生日の算出
 * @param {Date} birthday 誕生日
 * @param {Date} when 基準日
 * @return {String} 年齢
 * */
function getAge(birthday, when){
    if(!_.isDate(birthday)){
        return "";
    }
    if(!_.isDate(when)){
        when = new Date();
    }
    var b = new Date(birthday.getTime()).setFullYear(2000);
    var w = new Date(when.getTime()).setFullYear(2000);
    return when.getFullYear() - birthday.getFullYear() - (w >= b ? 0: 1);
}
/**
 * Cookieを設定する。
 * デフォルトでExpiredを設定する。
 * @param name {String} Cookei名
 * @param value {String} Cookieの値
 */
function setGhCookie(name, value){
    var expire = new Date();
    expire.setTime(_GH.COOKIE.EXPIRE);
    document.cookie = name + "=" + encodeURIComponent(value) + ";expires=" + expire.toUTCString();
}

/**
 * クッキー情報をオブジェクトとして返す
 * @return {Array} クッキー情報
 * */
function getCookies()
{
    var result = [];
    var textCookies = document.cookie;
    if( textCookies != "" )
    {
        var cookies = textCookies.split( "; " );

        for( var i = 0; i < cookies.length; i++ )
        {
            var cookie = cookies[ i ].split( "=" );
            try{
                // クッキーの名前をキーとして 配列に追加する
                result[ cookie[ 0 ] ] = decodeURIComponent( cookie[ 1 ] );
            } catch(e){
                if (!e instanceof URIError){
                    throw e;
                }
            }
        }
    }
    return result;
}
/** ------------------------------------------------
 *  ■DHTMLXの補助的なメソッド
 *   ------------------------------------------------ */
/**
 * DHTMLXのグリッドで、指定した行を有効／無効に切り替える。
 */
function disableDhtmlxGridRow(dhtmlxGrid, rowNo, disable) {
    if (!is("Object", dhtmlxGrid)) {
        return;
    }
    for(var i=0; i < dhtmlxGrid.getRowsNum(); i++){
        dhtmlxGrid.cells2(i, rowNo).setDisabled(disable);
    }
}

/**
 * 配列型のクエリパラメータを作成する。
 * @param {String} name 項目名
 * @param {Array} values 値の配列
 * @returns クエリ（name=value&name=value&・・・）
 */
function getArrayQueryParam(name, values) {
    var query = "";
    _.forEach(values, function(value) {
    	if(value != ""){ //dont add to queryString if it's null
    		query += name + "=" + value + "&";
    	}
    });
    return query;
}

/**
 * フォーム値の大小を比較して、正しい順番にセットする。
 * @param {Object} dhxForm 検査するフォームオブジェクト
 * @param {String} fromName 開始の項目名
 * @param {String} toName 終了の項目名
 */
function swapDhtmlxFormVal(dhxForm, fromName, toName) {
    if (!_.isObject(dhxForm)) {
        return;
    }
    var tempFrom = dhxForm.getItemValue(fromName);
    var tempTo = dhxForm.getItemValue(toName);
    // どちらかが空文字であれば、処理しない
    if (tempFrom === "" || tempTo === "") {
        return;
    }

    if ((_.isDate(tempFrom) || _.isNumber(tempFrom)) && ( _.isDate(tempTo) || _.isNumber(tempFrom))) {
        if (tempFrom > tempTo) {
            dhxForm.setItemValue(fromName, tempTo);
            dhxForm.setItemValue(toName, tempFrom);
        }
    } else if (_.isString(tempFrom) && _.isString(tempTo)) {
        if (tempFrom.match(/^[0-9]+$/i)) {
            tempFrom = _.parseInt(tempFrom);
        }
        if (tempTo.match(/^[0-9]+$/i)) {
            tempTo = _.parseInt(tempTo);
        }
        if (tempFrom > tempTo) {
            dhxForm.setItemValue(fromName, tempTo.toString());
            dhxForm.setItemValue(toName, tempFrom.toString());
        }
    }
}

/**
 * DHTMLXのFormのTemplateのフォーマット用関数
 * @param name
 * @param value
 * @returns {指定フォーマットの文字列}
 */
function doDateFormatYyyymmdd(name, value) {
    if (_.isDate(value)) {
        return getStrDate(value, "%Y/%m/%d");
    } else {
        if (_.isEmpty(value)) value = "";
        return value.substring(0, 10);
    }
}

/**
 * Web会員ステータスから顧客メインに表示する情報を返す。
 * @param name
 * @param value
 * @returns {指定フォーマットの文字列}
 */
function doWebMenberFormat(name, value) {
    var webStatus = _.parseInt(value);
    if (webStatus === 3) {
     // プレミアム会員
        return "<img src='./imgs/premium_icon.gif' /><span style='font-weight:bold'>Web会員登録</span>";
    } else if (webStatus > 0 && webStatus < 5) {
        // 通常会員(通常会員、プレミアム申請中、プレミアム否認）
        return "<img src='./imgs/world.png' /><span style='font-weight:bold'>Web会員登録</span>";
    } else {
        // 非会員
        return "Web会員未登録";
    }
}

/**
 * レコメンドの物件の文章１を取得する。
 * @param recommend レコメンド物件情報
 * @param useHtml htmlで修飾するかどうか
 * @returns
 */
function getRecommendProdSentence1(recommend, useHtml) {
    var arrangeText = function(str) {
        return useHtml? "<span style='color:red;'>" + str + "</span>": str;
    };
    var yyyyMMdd = function(obj){
        var startDate = new Date(obj.genbaStartDate);
        return d2sAsMMdd(startDate);
    };
    var buildingType = function(obj) {
        if (_.endsWith(obj.houseType, "マンション")) {
            return "MS";
        } else if (_.endsWith(obj.houseType ,"土地")) {
            var val = "";
            if (_.startsWith(obj.buildingCondition, "建築条件")) {
                val = "条付";
            }
            return val + "土地";
        } else if (_.endsWith(obj.houseType, "戸建")) {
            var house = "戸建";
            var cond = "";
            if (obj.buildingType === "中古一戸建") {
                house = "中古";
            } else if (obj.buildingType === "新築一戸建") {
                house = "新築";
            }
            if (obj.landRight === "借地権のみ" || obj.landRight === "所有権・借地権混在") {
                cond = "(借)";
            }
            return arrangeText(house) + cond;
        } else {
            return "";
        }
    };
    var floorPlan = function(obj) {
        if (_.endsWith(obj.houseType ,"土地")) {
            return "";
        } else {
            var typeS = (_.isEmpty(obj.floorTypeS) || obj.floorTypeS == "未選択")? "": obj.floorTypeS;
            return obj.roomNumber + obj.floorType + typeS;
        }
    };
    var area = function(obj) {
        var val = "土地" + obj.landArea + "㎡";
        if (_.endsWith(obj.houseType, "戸建")) {
            val += " 延床" + obj.buildingArea + "㎡";
        }
        return val;
    };
    var complete = function(obj) {
        return (obj.completeFlg == 1)? "完": "";
    };
    return [yyyyMMdd(recommend),
            buildingType(recommend),
            complete(recommend),
            arrangeText(recommend.price + "万"),
            arrangeText(floorPlan(recommend)),
            area(recommend)
            ].join(" ");
}

/**
 * レコメンドの物件の文章２を取得する。
 * @param recommend レコメンド物件情報
 * @param useHtml htmlで修飾するかどうか
 * @returns
 */
function getRecommendProdSentence2(recommend, useHtml) {
    var clean = function(str) {
        return (str)? str : "";
    };
    var arrangeText = function(str) {
        return useHtml? "<span style='color:red;'>" + clean(str) + "</span>": clean(str);
    };
    var address = function(obj) {
        var lastAddress = _.isString(obj.address4)? clean(obj.address4).split("丁目").join(""): clean(obj.address4);

        return arrangeText(obj.address1) + clean(obj.address3) + lastAddress;
    };
    var locomotion = function(obj) {
        if (obj.locomotion1 === "徒歩") {
            return obj.locomotion1 + obj.walk1 + "分";
        } else if (obj.locomotion1 === "バス") {
            return obj.locomotion1 + obj.busTime1 + "分";
        } else {
            return "";
        }
    };
    var station = function(obj) {
        return (obj.station1)? "「" + arrangeText(obj.station1) + "」駅": "";
    };
    return [
            address(recommend),
            recommend.railway1,
            station(recommend),
            locomotion(recommend),
            recommend.infoServiceCompany
        ].join(" ");
}
/**
 * スケジュール登録時に必要な対応履歴情報をセットする。
 * @param dhxSchedForm
 */
function setActDataOfSchedule(dhxSchedForm) {
    var actType = 5;
    dhxSchedForm.setItemValue("actType", actType);
    dhxSchedForm.setItemValue("actTypeDetail", convertScheduleStatusToActTypeDetail(dhxSchedForm.getItemValue("scheduleStatus")));
    dhxSchedForm.setItemValue("actMemo", dhxSchedForm.getItemValue("memo"));
}

/**
 * スケジュール登録時に必要な対応履歴情報をセットする。
 * TODO 上記のメソッドと合わせたい。
 * @param event
 */
function setActDataOfScheduleEvent(event) {
    var actType = 5;
    event.actType = actType;
    event.actTypeDetail = convertScheduleStatusToActTypeDetail(event.scheduleStatus);
    event.actMemo = event.memo;
}

/**
 * スケジュール種別を対応履歴詳細に変換する。
 * @param scheduleStatus
 * @returns {Number}
 */
function convertScheduleStatusToActTypeDetail(scheduleStatus) {
    var actTypeDetail = 0;
    if (!scheduleStatus) scheduleStatus = 0;
    var status = _.parseInt(scheduleStatus);
    switch(status) {
        case 1:
            actTypeDetail = 10;
            break;
        case 2:
            actTypeDetail = 11;
            break;
        case 3:
            actTypeDetail = 12;
            break;
    }
    return actTypeDetail;
}

/**
 * 担当者へ顧客情報を転送するための本文を生成する。
 * @param dhxForm
 * @returns {String}
 */
function getPersonalInfoAsText(dhxForm) {
    var dataForClip = {};
    _.forEach(_GH.S03F030TRANS.KEYS, function(label, key){
        // キーが存在した場合のみ値を設定する。
        var value = dhxForm.getItemValue(key);
        switch(dhxForm.getItemType(key)) {
            case "input":
                break;
            case "select":
                var selectItem = dhxForm.getSelect(key);
                value = selectItem.options[selectItem.selectedIndex].text;
                break;
            case "calendar":
                value = getStrDate(value, _GH.FORMAT.YMD);
                break;
            case "checkbox":
                if (dhxForm.isItemChecked(key)) {
                    value = 1;
                } else {
                    value = 0;
                }
                break;
            case "hidden":
                break;
            default: // その他のタイプは表示しない。hidden, buttonなど。
                value = "";
                break;
        }
        dataForClip[key] = value;
    });
    return getPersonalInfoText(dataForClip);
}
/**
 * SFAへのリンクを返す。
 */
function getGHPersonalLink(personalId) {
    return window.location.protocol + "//" + window.location.host + "/GH_CLIENT/" + _GH.PATH_TYPE.CS_MAIN + personalId;
}
/**
  * 顧客情報のテキストを取得する。
  * クリップボードにコピーや担当者に転送で利用する。
  *
  * @param {Object} dataForClip テンプレートに入れるためのデータ
  */
function getPersonalInfoText(dataForClip) {
    var header = "\
お疲れ様です。${myName}です。\r\n\
候補者情報を転送します。\r\n\
\r\n\
";
    return getPersonalInfoTextWithHeader(header, dataForClip);
}

function getPersonalInfoTextWithHeader(header, dataForClip){
    var template = _.template(header + _GH.TRANSFER_MESSAGE_TEMPLATE);

    var transFlg = function(f){
        return f == 1 ? "○" : "×";
    };

    var context = {
        personalId: dataForClip["personalId"],
        GHLink : getGHPersonalLink(dataForClip["personalId"]),
        webMemberId: dataForClip["webMemberId"],
        myName: _ghUser.getName(),
        lastNameKana: dataForClip["lastNameKana"],
        firstNameKana: dataForClip["firstNameKana"],
        lastName: dataForClip["lastName"],
        firstName: dataForClip["firstName"],
        relation: dataForClip["relation"],
        employFullName: dataForClip["employFullName"],
        mobileTel: dataForClip["mobileTel"],
        nowAdrTel: dataForClip["nowAdrTel"],
        offAdrTel: dataForClip["offAdrTel"],
        pcEmail: dataForClip["pcEmail"],
        mobileEmail: dataForClip["mobileEmail"],
        birthday: dataForClip["birthday"],
        gender: dataForClip["gender"],
        schoolName: dataForClip["schoolName"],
        schoolDepartment: dataForClip["schoolDepartment"],
        schoolSubject: dataForClip["schoolSubject"],
        judgeInterviewStatus: _prop.getPropName(_GH.CODE_DEF.STATUS, dataForClip["judgeInterviewStatus"]),
        prospectiveImpCode: dataForClip["prospectiveImpCode"],
        empCode: dataForClip["empCode"],
        jobType: dataForClip["jobType"],
        jobTypeTech: dataForClip["jobTypeTech"],
        club: dataForClip["club"],
        circle: dataForClip["circle"],
        workedNumber: dataForClip["workedNumber"],
        latestCompany: dataForClip["latestCompany"],
        latestStartDate: doDateFormatYyyymmdd("", dataForClip["latestStartDate"]),
        latestEndDate: doDateFormatYyyymmdd("", dataForClip["latestEndDate"]),
        latestBelongType: dataForClip["latestBelongType"],
        latestBusinessType: dataForClip["latestBusinessType"],
        latestJobType: dataForClip["latestJobType"],
        latestEmpPattern: dataForClip["latestEmpPattern"],
        selectionDeclinationFlag: dataForClip["selectionDeclinationFlag"],
        divisionName: dataForClip["divisionName"],
        recruitStatus: _prop.getPropName(_GH.CODE_DEF.STATUS, dataForClip["recruitStatus"]),
        entryDate: _ghUser.isNon()? doDateFormatYyyymmdd("", dataForClip["entryDate"]): null
    };
    return template(context);
}

/**
 * 顧客ランク用のソートメソッド
 */
function sortPersonalRank(a,b,order){
    var n = (a === "★なし")? 0: a.length;
    var m =(b === "★なし")? 0: b.length;
    if(order=="asc")
        return n>m?1:-1;
    else
        return n<m?1:-1;
}

/**
 * 日付のみを整形して返す
 * @param date 日付
 */
function yyyyMMdd(date){
    return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();
}

/**
 * 日付のみを整形して返す
 * @param date 日付
 */
function d2sAsMMdd(date){
    return (date.getMonth() + 1) + "/" + date.getDate();
}

function getServerDatetimeString(date) {
    if (_.isDate(date)) {
        return window.dhx4.date2str(date, "%Y/%m/%d %H:%i:%s");
    } else {
        return "";
    }
}
/**
 * 現在日から指定した月数、日数を加算した日付オブジェクトを返す。
 * @param month
 * @param day
 */
function getAfterDate(month, day) {
    var date = new Date();
    if (_.isNumber(month)) {
        date.setMonth(date.getMonth() + month);
    }
    if (_.isNumber(day)) {
        date.setDate(date.getDate() + day);
    }
    return date;
}

/**
 * 安全に文字列を半角カンマでスプリットします。
 * @param str スプリットしたい文字列
 * @param isInt Int型にキャストするかどうか。デフォルトはfalse
 */
function safeCommaSplit(str, isInt) {
    if (_.isString(str) && _.trim(str, " ,").length > 0) {
        var split = str.split(",");
        return (isInt)?
            _.transform(split, function(result, n) {result.push(_.parseInt(n));}):
            split;
    } else {
        return [];
    }
}
/**
 * Font AwesomeのHTMLタグを取得する。
 * @param name
 * @param isSmall
 */
function getFontWesome(name, isSmall) {
    if (_.isEmpty(name)) {
        return "";
    } else {
        name = (_.startsWith(name, "fa-"))? name: "fa-" + name;
        if (isSmall) {
            return "<i class='fa " + name + "'></i> ";
        } else {
            return "<i class='fa " + name + " fa-2x'></i> ";
        }
    }
}
/**
 * DHXMLのFormオブジェクトの値をリセットする
 * @param {Object} dhxForm リセットしたいFormオブジェクト
 * @param {Array} exceptKeys リセットを除外したいキー配列
 */
function resetDhxFormItemValues(dhxForm, exceptKeys) {
    if (_.isObject(dhxForm)) {
        if (!_.isArray(exceptKeys)) {
            exceptKeys = [];
        }
        var dataset = {};
        _.forEach(dhxForm.getFormData(), function(value, key) {
            // 除外キー配列に値が入っていなければ消す。
            if (_.indexOf(exceptKeys, key) < 0) {
                dataset[key] = "";
            }
        });
        dhxForm.setFormData(dataset);
    }
}
/**
 * 選択したランクに合わせて、メールステータスをおすすめのものに変える。
 * @param dhxForm 顧客情報が入ったFormオブジェクト
 * @param newRankVal 変更後のランク
 */
function setRecommendMailStatusAlongWithRank(dhxForm, newRankVal) {
    if (_ghUser.isExp()) return; // MSは処理しない。
    // クレームの場合は処理しない
    if (_.parseInt(dhxForm.getItemValue("personalstatus")) === _GH.S03F010CM.CUST_CLAIM) return;
    // ランクが1,0は対象としない。
    var newRank = _.parseInt(newRankVal);
    if (newRank <= _GH.S03F010CM.RANK_NOUSE) return;
    var currentMailStatus = _.parseInt(dhxForm.getItemValue("emailstatus"));
    // メール許可NGの場合は対象としない。
    if (currentMailStatus == _GH.S03F010CM.MAIL_NONE) return;
    var recommendMailStatus = (newRank > _GH.S03F010CM.RANK_STD)?
            _GH.S03F010CM.MAIL_SINGLE: _GH.S03F010CM.MAIL_PROXY;
    // 現在のレコメンドステータスと変更後のメールステータスが違う場合、ユーザに問う
    if (currentMailStatus !== recommendMailStatus) {
        var msg = (recommendMailStatus === _GH.S03F010CM.MAIL_SINGLE)?
                "個別メールのみOK に変更しますか？": "代行メール送信も全てOK に変更しますか？";
        if (confirm(msg)) {
            dhxForm.setItemValue("emailstatus", recommendMailStatus);
        }
    }
}
/**
 * ビット演算で格納された複数選択の値を分解して、チェックボックスに値をセットする。
 * 使用するのは間取り（現時点ではfloorplanのみ）
 * ■処理イメージ
//            var floorPlan = recCondInfo["floorPlan"];
//            if ((floorPlan & 7) !== 0) {
//                recCondInfo["floorPlan7"] = true;
//            }
//            if ((floorPlan & 8) !== 0) {
//                recCondInfo["floorPlan8"] = true;
//            }
//            if ((floorPlan & 16) !== 0) {
//                recCondInfo["floorPlan16"] = true;
//            }
//            if ((floorPlan & 32) !== 0) {
//                recCondInfo["floorPlan32"] = true;
//            }
 * ■上記の場合の呼び出しイメージ
 * setBitOperationValueAtCheckbox(recCondInfo, "floorPlan", [7, 8, 16, 32]);
 *
 * @param keyValObj Formのキー：バリューが入ったオブジェクト
 * @param bitValueKey ビット演算で計算された値のキー。このキーとビット値を連結したものが、チェックボックスの名前（キー）になる。
 * @param valueArray ビット値の数値配列
 */
function setBitOperationValueAtCheckbox(keyValObj, bitValueKey, valueArray) {
    // そもそもオブジェクトがない場合は処理を終える。
    if (_.isEmpty(keyValObj) || _.isEmpty(bitValueKey)) return;
    // ビット値を取り出す。
    var bitValue = _.parseInt(keyValObj[bitValueKey]);
    _.forEach(valueArray, function(value) {
        // ビット値に含まれるかどうかをチェックし、含まれていればチェックボックスをONにする。
        if ((bitValue & value) !== 0) {
            keyValObj[bitValueKey + value] = true;
        } else {
            keyValObj[bitValueKey + value] = false;
        }
    });
}
/**
 * チェックボックスの値をビット演算して返す。
 * 使用するのは間取り（現時点ではfloorplanのみ）
 *
 * @param dhxForm DHXML Formオブジェクト（チェックボックスとその値を格納するHidden項目が含まれる）
 * @param bitValueKey ビット演算で計算が格納される項目のキー（ビット演算結果を格納するHidden項目）。このキーとビット値を連結したものが、チェックボックスの名前（キー）になる。
 * @param valueArray ビット値の数値配列
 * @returns {Number} ビット演算結果
 */
function getBitOperationValueInCheckbox(dhxForm, bitValueKey, valueArray) {
    var bitValue = 0;
    _.forEach(valueArray, function(value) {
        var chkName = bitValueKey + value;
        bitValue += dhxForm.isItemChecked(chkName)? _.parseInt(dhxForm.getItemValue(chkName)) : 0;
    });
    return bitValue;
}

/**
 * 文字列で"null"と入っているものを null に変換。"null"をcalendarに入れると表示がおかしくなるなど、不都合がある際に使用。
 *
 * @param data      変換対象
 * @returns {Object} 変換後の値
 */
function transNull(data){
    var ret = _.transform(data, function(result, value, key){
        if(value === "null"){
            // 日付などがおかしくなるため変換
            value = null;
        }
        result[key] = value;
    });
    return ret;
}
/**
 * 引数を安全に数値型にする。
 * 全角は半角に変換されてから、Int型にパースされる。
 * デフォルト値の指定がなければ、0が入る。
 *
 * @param num 数値にしたい変数
 * @param defaultValue デフォルト値。指定がなければ0
 */
function toNumberSafety(num, defaultValue) {
    var number = _.parseInt(toHankaku(num));
    if (_.isNaN(number)) {
        number = _.parseInt(defaultValue);
        if (_.isNaN(number)) number = 0;
    }
    return number;
}

/**
 * 一斉メールやDM、担当者変更画面に表示されるグリッドを調整する。
 * @param cell
 * @param csListName
 */
function resizeGridListInLayoutCell(cell, csListName) {
    if (_.isEmpty(csListName)) csListName = "csList";
    var gridObject = document.getElementById(csListName);
    if(!_.isEmpty(gridObject)) {
        gridObject.style.width = (cell.getWidth() - 15) + "px";
        gridObject.style.height = (cell.getHeight() - 114) + "px";
    }
}

/**
 * Formのセレクトのオプションから不要な値を除く。
 * @param options オプションオブジェクト。_prop.getOptions(xxx)の結果。
 * @param removeValueArray 取り除く値の配列。[1,2,3]
 */
function removePropOptions(options, removeValueArray) {
    if (_.isEmpty(options) || !_.isArray(removeValueArray)) {
        return; // そのまま返す。
    } else {
        _.remove(options, function(opt) {
            return _.indexOf(removeValueArray, _.parseInt(opt.value)) >= 0;
        });
        return options;
    }
}

/**
 * 顧客情報のマスキングが必要かどうかを判断する。
 * @param personalEmployeeId 顧客の担当者ID
 * @param divisionId 顧客の組織ID
 */
function needMaskCunstomerInfo(personalEmployeeId, divisionId) {
    if (_ghUser.isShien() || _ghUser.isMSReform()) {
        // 戸建（反響ツリーで絞られているため）、営業支援、オーダーシステム課はマスキングは必要なし
        return false;
    }
    if (_ghUser.hasManDivision(divisionId)) {
        // 自分の権限の範囲なら、マスキングは必要なし
        return false;
    }
    if (personalEmployeeId == _ghUser.getGhId()) {
        // 自分の顧客ならば、マスキングは必要なし
        return false;
    }
    // 顧客が課担当なし
    if (personalEmployeeId == 0) {
        if (_ghUser.isNon()) {
            // 戸建の場合は担当無しは操作可能
            return false;
        } else if (_ghUser.getDivisionId() == divisionId) {
            // MSの場合は、自分の所属であればマスキングは必要なし
            return false;
        }
    }
    // ここまでに該当がなければマスキングする。
    return true;
}

/**
 * [ユーティリティ関数] メールアドレスを含むFormDataからメールアドレスを取得する。
 * @param {Object} personalInfo 顧客情報FormのFormData（DHTMLXFormのgetFormData()をしたもの）
 * @return メールアドレスのセミコロン区切り
 */
function getSendEmailsFromCustInfo(personalInfo) {
    var tempToAddress = "";
    for (var i = 1; i <= 3; i++) {
        if (!_.isEmpty(personalInfo["email" + i]) && personalInfo["emailsendableflg" + i] == 1) {
            tempToAddress += personalInfo["email" + i] + ";";
        }
    }
    return tempToAddress;
}
/**
 * 与えられた文字列を半角に変換します。
 * @param str
 * @returns
 */
function toHankaku(str) {
    if (_.isNumber(str)) {
        return str;
    } else if (_.isEmpty(str)) {
        return "";
    } else {
        var hen = str.replace(/[！-～]/g,function(s){
            return String.fromCharCode(s.charCodeAt(0)-0xFEE0);
        });
        return hen;
    }
}

/**
 * 全角のハイフンを半角にします
 * @param str
 * @returns
 */
function toHankakuHyphen(str){
    str = str.replace(/[―‐－ー]/g, '-');
    return str;
}

/**
 * ハイフンを取り除きます。
 * @param str
 * @returns
 */
function removeTelephoneHyphen(str) {
    var tel = str.replace(/^([0-9\-]+)$/g,function(s){
        return str.split("-").join("");
    });
    return tel;
}

/**
 * 住所から郵便番号を逆引きして、設定する。
 * すでに郵便番号を持っている場合は何もしない。
 * @param personalForm 顧客詳細情報をもつDhtmlx Formオブジェクト
 */
function setZipCodeFromAddress(personalForm) {
    // すでに郵便番号が入っていれば無視
    if (personalForm.getItemValue("zipcode").length > 0) {
        return;
    }
    if (personalForm.getItemValue("otherAddress").length > 0) {
        var address = personalForm.getItemValue("address") + personalForm.getItemValue("otherAddress");
        // 住所を半角に変換して、最初の数値のところで切り落とす。（「練馬区春日町１－１」→「練馬区春日町」）
        address = toHankaku(address);
        var numIndex = address.indexOf(address.match(/\d/));
        if (numIndex > 0) address = address.substring(0, numIndex);
        var zipInfo = getJSONSync("zip/address/" + address);
        if (zipInfo.length === 0) {
            dhtmlx.message({text: address + "に該当する住所がありませんでした。"});
        } else if (zipInfo.length === 1) {
            var zip = zipInfo[0];
            personalForm.setItemValue("zipcode", zip.zipCode);
            personalForm.setItemValue("prefecture", zip.prefecture);
            personalForm.setItemValue("address", zip.city);
        } else {
            var addressCandidate = [];
            _.forEach(zipInfo, function(zip) {
                addressCandidate.push(zip.prefecture + zip.city + zip.town);
            });
            addressCandidate = _.slice(addressCandidate, 0, 5); // 最大５件とする。
            dhtmlx.message({text: address + "に該当する住所が複数取得されました。もっと絞り込んでください。<BR>" + addressCandidate.join("<BR>")});
        }
    }
}
/** ------------------------------------------------
 *  ■画像アップロードの補助的なメソッド
 *   ------------------------------------------------ */
function convertOtherImageToOwn(originalImage){
    var URL = $(originalImage).attr("src");

    var readImg = new Image();
    readImg.crossOrigin = "Anonymous";
    var def = $.Deferred();
    readImg.onload = function() {
        var readCanvas = $("<canvas>");
        readCanvas[0].width = this.naturalWidth;
        readCanvas[0].height = this.naturalHeight;
        var readCtx = readCanvas[0].getContext("2d");
        readCtx.drawImage(this, 0, 0);
        var data = readCanvas[0].toDataURL("image/jpeg", 1.0);

        // FormDataに縮小後の画像データを追加する
        // Blob形式にすることで、サーバ側は従来のままのコードで対応可能
        var blob = dataURLtoBlob(data);
        var fd = new FormData();
        fd.append("file", blob, "uploadImage.jpg");

        // フォームの全ての入力値をFormDataに追加
//        var formAry = $("form").serializeArray()
//        $.each(formAry, function(i, field){
//            fd.append(field["name"], field["value"]);
//        });

        $.ajax({
            url: "/imageFile-upload.php?mode=html5&SFA_USER_ID=" + _ghUser.getGhId(),
            type: "POST",
            data: fd,
            dataType: "json",
            contentType: false,
            processData: false,
            async: false,
            complete: function(data){
                $(originalImage).attr({"src":JSON.parse(data.responseText).name, "title":JSON.parse(data.responseText).extra.hostname});
            }
        });
        def.resolve();
    };
    readImg.onerror = function() {
        $(originalImage).attr({"src":"", "alt":URL});
        console.log("エラー", $(originalImage), originalImage);
        def.reject();
    };

    readImg.src = URL;
    return def.promise();
}

function dataURLtoArrayBuffer(data) {
    var byteString = atob(data.split(",")[1]);
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }
    return ab
}

function dataURLtoBlob(data) {
    var mimeString = data.split(",")[0].split(":")[1].split(";")[0];
    var ab = dataURLtoArrayBuffer(data);
    var bb = (window.BlobBuilder || window.WebKitBlobBuilder || window.MozBlobBuilder);
    if (bb) {
        bb = new (window.BlobBuilder || window.WebKitBlobBuilder || window.MozBlobBuilder)();
        bb.append(ab);
        return bb.getBlob(mimeString);
    } else {
        bb = new Blob([ab], {
                "type": (mimeString)
        });
        return bb;
    }
}
/**
 * 処理待ちメソッド
 * 使い方は下記の通り。
    $.wait(2000).done(function(){
        console.log("2000ミリ秒待ちました");
    });
 */
$.wait = function(msec) {
    // Deferredのインスタンスを作成
    var d = new $.Deferred;

    setTimeout(function(){
        // 指定時間経過後にresolveしてdeferredを解決する
        d.resolve(msec);
    }, msec);

    return d.promise();
};

function initialEffectDate(){
    var initialDate = new XDate();
    var threshold = new XDate();
    threshold.setHours(21);
    threshold.setMinutes(45);
    // 9時45分以降の場合、翌日を入力しておく
    if(initialDate >= threshold){
        initialDate.addDays(1);
    }
    return initialDate.toDate();
}

// ひらがな文字であるか判別する
function isHiraganaCode(c){
  return ((c >= 12353 && c <= 12435) || c == 12445 || c == 12446);
}

// 全角ひらがなを全角カタカナに変換する
function hiraganaCodeToKatakanaCode(src){
  // 引数のチェック
  if(src == null)
    return "";

  var str = new String;
  var len = src.length;
  for(var i = 0; i < len; i++)
  {
    var c = src.charCodeAt(i);
    if(isHiraganaCode(c))
      str += String.fromCharCode(c + 96); // ひらがなをカタカナに変換する
    else
      str += src.charAt(i);
  }

  return str;
}

function toZenkaku(str){
	//配列を用意する
	hankaku = ["ｶﾞ", "ｷﾞ", "ｸﾞ", "ｹﾞ", "ｺﾞ", "ｻﾞ", "ｼﾞ", "ｽﾞ", "ｾﾞ", "ｿﾞ", "ﾀﾞ", "ﾁﾞ", "ﾂﾞ", "ﾃﾞ", "ﾄﾞ", "ﾊﾞ", "ﾊﾟ", "ﾋﾞ", "ﾋﾟ", "ﾌﾞ", "ﾌﾟ", "ﾍﾞ", "ﾍﾟ", "ﾎﾞ", "ﾎﾟ", "ｳﾞ", "ｧ", "ｱ", "ｨ", "ｲ", "ｩ", "ｳ", "ｪ", "ｴ", "ｫ", "ｵ", "ｶ", "ｷ", "ｸ", "ｹ", "ｺ", "ｻ", "ｼ", "ｽ", "ｾ", "ｿ", "ﾀ", "ﾁ", "ｯ", "ﾂ", "ﾃ", "ﾄ", "ﾅ", "ﾆ", "ﾇ", "ﾈ", "ﾉ", "ﾊ", "ﾋ", "ﾌ", "ﾍ", "ﾎ", "ﾏ", "ﾐ", "ﾑ", "ﾒ", "ﾓ", "ｬ", "ﾔ", "ｭ", "ﾕ", "ｮ", "ﾖ", "ﾗ", "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ", "ｦ", "ﾝ", "｡", "｢", "｣", "､", "･", "ｰ", "ﾞ", "ﾟ"];
	zenkaku  = ["ガ", "ギ", "グ", "ゲ", "ゴ", "ザ", "ジ", "ズ", "ゼ", "ゾ", "ダ", "ヂ", "ヅ", "デ", "ド", "バ", "パ", "ビ", "ピ", "ブ", "プ", "ベ", "ペ", "ボ", "ポ", "ヴ", "ァ", "ア", "ィ", "イ", "ゥ", "ウ", "ェ", "エ", "ォ", "オ", "カ", "キ", "ク", "ケ", "コ", "サ", "シ", "ス", "セ", "ソ", "タ", "チ", "ッ", "ツ", "テ", "ト", "ナ", "ニ", "ヌ", "ネ", "ノ", "ハ", "ヒ", "フ", "ヘ", "ホ", "マ", "ミ", "ム", "メ", "モ", "ャ", "ヤ", "ュ", "ユ", "ョ", "ヨ", "ラ", "リ", "ル", "レ", "ロ", "ワ", "ヲ", "ン", "。", "「", "」", "、", "・", "ー", "゛", "゜"];
	//変換開始
	for (i=0; i<=88; i++) { //89文字あるのでその分だけ繰り返す
		while (str.indexOf(hankaku[i]) >= 0){ //該当する半角カナがなくなるまで繰り返す
			str = str.replace(hankaku[i], zenkaku[i]); //半角カナに対応する全角カナに置換する
		}
	}

    return str;
}

function getEstimatedItemLabel(name) {
    if (_.isEmpty(name)) {
        return "入力値";
    }
    // 項目IDを小文字に正規化
    name = _.trim(name).toLowerCase();
    if (name.indexOf("mail") >= 0) {
        return "メールアドレス";
    } else if (name.indexOf("tel") >= 0) {
        return "TEL";
    } else if (name.indexOf("address") >= 0) {
        return "住所";
    } else {
        return "入力値";
    }
}

/**
 * ライブラリの承認ステータスの翻訳を取得します。
 */
function getLibraryApprovalStatusName(approvalStatus, approvalFlg) {
    if (approvalStatus == _GH.S05F030Lib.APPROVAL_STATUS.ACCEPT) {
        // 承認済みの場合、ライブラリ承認フラグの値を見る。
        return _prop.getPropName(_GH.CODE_DEF.LIB_APPROVAL_FLG, approvalFlg);
    } else {
        return _prop.getPropName(_GH.CODE_DEF.LIB_APPROVAL_STATUS, approvalStatus);
    }
}

/**
 * 日付が指定した日数の範囲内かどうかをチェックする。
 * ※ xDate必須
 * @param {Date} date 判定する日付
 * @param {Number} dayInt 基準となる日数。過去は＋、未来はー
 */
function isDateInRange(date, dayInt){
    var today = new XDate();
    var effect = new XDate(date);
    return effect.diffDays(today) < dayInt;
}

function registerEntryEnable(effectDate){
    var today = new XDate();
    var effect = new XDate(effectDate);
    return effect.diffDays(today) >= 7;
}

/**
 * 顧客ステータスが「契約」「クレーム」以外の場合は「追客中」にする。
 *
 */
function resetPersonalStatusIfNotContractOrClaim(personalData) {
    if (_.isEmpty(personalData) || !personalData.personalstatus) {
        return;
    }
    var contractStatus = _prop.getValueFromPropName(_GH.CODE_DEF.STATUS, "契約");
    var claimStatus = _prop.getValueFromPropName(_GH.CODE_DEF.STATUS, "クレーム");
    if(personalData.personalstatus != contractStatus && personalData.personalstatus != claimStatus){
        var nonContactStatus = _prop.getValueFromPropName(_GH.CODE_DEF.STATUS, "連絡不可");
        if (personalData.personalstatus == nonContactStatus) {
            // 連絡不可だった場合は、連絡可能とする。
            personalData.telstatus = 1; //電話許可
            personalData.emailstatus = 1; //個別メール許可
        }
        personalData.personalstatus = _prop.getValueFromPropName(_GH.CODE_DEF.STATUS, "追客中");
        personalData.nonresponseflg = 0; // 非反響フラグは外す。
    }
}

/**
 * 顧客情報を使って置換タグを置き換える。
 */
function replaceSFAMailTags(text, custFormData) {
    if (text && custFormData) {
        text = text.replace( /\{personal_lastName\}/g , custFormData["lastName"] );
        text = text.replace( /\{personal_firstName\}/g , custFormData["firstName"] );
        text = text.replace( /\{personal_sub_lastName\}/g , custFormData["subLastName"] );
        text = text.replace( /\{personal_sub_firstName\}/g , custFormData["subFirstName"] );
        var emps = (custFormData["employFullName"])? custFormData["employFullName"].split(" "): ["", ""];
        text = text.replace( /\{myLastName\}/g , emps[0] );
        text = text.replace( /\{myFirstName\}/g , emps[1] );
    }
    return text;
}
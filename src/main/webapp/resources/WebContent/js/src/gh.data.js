/**
 * データ通信用のスクリプト
 */
// DHTMLX用のパラメータをつけない。
window.dhx4.ajax.cache = true;

// DHTMLX用のAJAXイベント失敗時のイベント
window.dhx4.attachEvent("onAjaxError", function(requestObject){
    try{
        var message = is("String", requestObject.responseText)? JSON.parse(requestObject.responseText).message: "サーバーエラーが発生しました。";
    } catch(e){
        var message = "サーバーエラーが発生しました。";
    }
    var params = { 'status' : requestObject.status, 'errorMessage' :message};
    if (requestObject.status == 204) {
        // 204の場合は、何もしない
        return;
    }
    throw new Error(JSON.stringify({message: message, params: params}));
});

function loadForm(dhxForm, uri, param) {
    console.log("loadForm", dhxForm, _GHAPI.SERVER + uri, param);
    try {
        window.dhx4.ajax.get(_GHAPI.SERVER + uri, function(r){
            var t = null;
            try { t = r.xmlDoc; } catch(e) {}
            if (t != null && t.status.toString() == "200") {
                // response is ok
                dhxForm.setFormData(parseJSON(r.xmlDoc.responseText));
            } else {
                // error
                console.log("loadFormでエラー", "status:" + t.status, t.statusText);
                dhtmlx.message({ type:"error", text:"サーバ通信に失敗しました。しばらく時間を置いて再度試してください。<br>"
                    + "status:" + t.status + "[" + t.statusText + "]" });
            }
        });
    } catch(e) {
        console.log("フォームデータ取得時にエラーが発生！", e);
        dhtmlx.message({ type:"error", text:"サーバとの接続ができませんでした。[" + e.name + "]"});
    }
}

/**
 * フォームデータを保存します。
 * @param {Object} dhxForm DHTMLXのFormオブジェクト
 * @param {String} uri APIのURL
 * @param {String] method HTTPメソッド
 * @param {Function} afterFunc 成功時に実行する関数
 */
function saveForm(dhxForm, uri, afterFunc) {
    dhxForm.updateValues();
    console.log("saveForm", dhxForm.getFormData(), uri);
    dhxForm.send(_GHAPI.SERVER + uri, "post", function(loader, response){
        var t = null;
        try { t = loader.xmlDoc; } catch(e) {};
        if (t != null && t.status.toString() == "200") {
            // response is ok
            console.log("saveForm response", response);
            dhtmlx.message({ type:"info", text: "情報を保存しました。"});
            if(is("Function", afterFunc)) {
                afterFunc();
            }
        } else {
            try{
                var message = is("String", t.responseText)? JSON.parse(t.responseText).message: "サーバーエラーが発生しました。";
            } catch(e){
                var message = "サーバーエラーが発生しました。";
            }
            var params = { 'status': t.status, 'errorMessage': message};
            throw new Error(JSON.stringify({message: message, params: params}));
        }
    });
};

/**
 * save form, after save success, call afterFunc with response
 * @param {Object} dhxForm DHTMLXのFormオブジェクト
 * @param {String} uri APIのURL
 * @param {String] method HTTPメソッド
 * @param {Function} afterFunc 成功時に実行する関数
 */
function saveFormAndCallbackWithResponse(dhxForm, uri, afterFunc) {
    dhxForm.updateValues();
    console.log("saveForm", dhxForm.getFormData(), uri);
    dhxForm.send(_GHAPI.SERVER + uri, "post", function(loader, response){
        var t = null;
        try { t = loader.xmlDoc; } catch(e) {};
        if (t != null && t.status.toString() == "200") {
            // response is ok
            console.log("saveForm response", response);
            dhtmlx.message({ type:"info", text: "情報を保存しました。"});
            if(is("Function", afterFunc)) {
                afterFunc(response);
            }
        } else {
            try{
                var message = is("String", t.responseText)? JSON.parse(t.responseText).message: "サーバーエラーが発生しました。";
            } catch(e){
                var message = "サーバーエラーが発生しました。";
            }
            var params = { 'status': t.status, 'errorMessage': message};
            throw new Error(JSON.stringify({message: message, params: params}));
        }
    });
};

/**
 * フォームを伴わないアクセス方法
 * (GET形式)
 * @param {String} uri URI
 * @param {Object} param パラメータ
 * @param {Boolean} isParse JSONテキストをオブジェクトにパースするかどうか
 * */
function accesSyncURL(uri, param, isParse) {
    console.log("accesURL", _GHAPI.SERVER + uri, param);
    var parseFlg = true;
    if(is("Boolean",isParse)){
        parseFlg = isParse;
    }
    var queryString ="";
    _.forEach(param, function(value, key, param) {

        if(queryString == ""){
            queryString = "?" + key + "=" + value;
        }else{
            queryString = queryString + "&" + key + "=" + value;
        }
        console.log(value + ' : ' + key);
    });

    var r = window.dhx4.ajax.getSync(_GHAPI.SERVER + uri + encodeURI(queryString));
    var t = null;
    try { t = r.xmlDoc; } catch(e) {};
    if (t != null) {
        // response is ok
        //resJson = parseJSON(r.xmlDoc.responseText);
        if(parseFlg){
            return parseJSON(r.xmlDoc.responseText);

        }else{
            return r.xmlDoc.responseText;
        }
        console.log("HTTP:200 " + r.xmlDoc.responseText);
    } else {
        var message = is("String", t.responseText)? JSON.parse(t.responseText).message: "サーバーエラーが発生しました。";
        var params = { 'status' : t.status, 'errorMessage' :message};
        throw new Error(JSON.stringify({message: message, params: params}));
    }
};

/**
 * フォームを伴わないアクセス方法
 * (POST形式)
 * @param {String} uri URI
 * @param {Object} param パラメータ
 * @param {Array} removeParamNames paramの中で対象外となる項目名の配列
 * @param {String} additionalQueryParam 追加でつけるクエリ。最後は必ず&で終わること。
 * */
function postSyncURL(uri, param, removeParamNames, additionalQueryParam){
    console.log("postSyncURL", _GHAPI.SERVER + uri, param, additionalQueryParam);
    try {
        // 追加的なクエリパラメータがあれば、先に足しておく。
        var queryString = additionalQueryParam? additionalQueryParam: "";
        _.forEach(param, function(value, key, param) {
            if(removeParamNames && _.indexOf(removeParamNames, key) >= 0) {
                // 使用しないキーの場合は、何もしない
            } else if(queryString == ""){
                queryString = key + "=" + value;
            }else{
                queryString = queryString + "&" + key + "=" + value;
            }
        });
        console.log("postSyncURLのクエリパラメータ", queryString);

        var r = window.dhx4.ajax.postSync(_GHAPI.SERVER + uri, encodeURI(queryString));
        var t = null;
        try { t = r.xmlDoc; } catch(e) {}
        if (t != null && t.status.toString() == "200") {
            console.log("HTTP:200 ", JSON.parse(r.xmlDoc.responseText));
            return parseJSON(r.xmlDoc.responseText);
        } else {
            // error
            console.log("sfa.data.postSyncURLでエラー", "status:" + t.status, t.statusText);
            dhtmlx.message({ type:"error", text:"サーバ通信に失敗しました。しばらく時間を置いて再度試してください。<br>"
                + "status:" + t.status + "[" + t.statusText + "]" });
            return {error: true,  status: t.status, text: t.statusText};
        }
    } catch(e) {
        console.log("データ取得時にエラーが発生！", e);
        dhtmlx.message({ type:"error", text:JSON.parse(e["message"])["message"]});
        return {error: true,  status: 500, text: e.name};
    }
}
/**
 * 指定したURIで削除を実行する。（HTTPのDELETEメソッドを利用する）
 * @param uri URI
 * @param param パラメータ
 * @param callback コールバック
 */
function delURL(uri, param, callback) {
    console.log("削除実行API", uri, param);
    try {
        window.dhx4.ajax.query({
            method : "DELETE",
            url : _GHAPI.SERVER + uri,
            data : param,
            async : false,
            callback : callback,
            headers:{
                "Content-Type":"application/x-www-form-urlencoded; charset=UTF-8"
            }
        });
    } catch(e) {
        console.log("データ削除時にエラーが発生！", e);
        dhtmlx.message({ type:"error", text:JSON.parse(e["message"])["message"]});
    }
};
/**
 * フォームを伴わないアクセス方法
 * (PUT形式)
 * @param {String} uri URI
 * @param {Object} param パラメータ
 * @param {Function} callbackFunc コールバックするファンクション
 * */
function putURL(uri, param, callbackFunc){

    console.log("putURL", _GHAPI.SERVER + uri, param);
    try {
        var queryString ="";
        _.forEach(param, function(value, key, param) {
            if (_.isString(key)) {
                queryString += key + "=" + value + "&";
            }
        });
        console.log("PUTのクエリ", queryString);

      var url = _GHAPI.SERVER + uri;
      var res = window.dhx4.ajax.query({
          method : "PUT",
          url : url,
          data : queryString,
          async : true,
          callback : function(r){

              var t = null;
              try { t = r.xmlDoc; } catch(e) {};
              if (t != null && t.status.toString() == "200") {
                  if (is("Function", callbackFunc)) {
                      callbackFunc(parseJSON(r.xmlDoc.responseText));
                  }
                  console.log("HTTP:200 " + r.xmlDoc.responseText);
              } else {
                  // error
                  console.log("sfa.data.postSyncURLでエラー", "status:" + t.status, t.statusText);
                  dhtmlx.message({ type:"error", text:"サーバ通信に失敗しました。しばらく時間を置いて再度試してください。<br>"
                      + "status:" + t.status + "[" + t.statusText + "]" });
              }
          },
          headers:{
              "Content-Type":"application/x-www-form-urlencoded; charset=UTF-8",
              "LOGIN_TYPE": _ghUser.getLoginType()
          }
      });
    } catch(e) {
        console.log("データ更新時にエラーが発生", e);
        dhtmlx.message({ type:"error", text:JSON.parse(e["message"])["message"]});
    }
};
/**
 * フォームを伴わないアクセス方法
 * (PUT形式)
 * @param {String} uri URI
 * @param {Object} param パラメータ（JSON文字列に変換する）
 * @param {Function} callbackFunc コールバックするファンクション
 * */
function syncPutJSON(uri, param, afterFunc){

    console.log("putURL", _GHAPI.SERVER + uri, JSON.stringify(param));
    try {
        var res = window.dhx4.ajax.query({
            method : "PUT",
            url : _GHAPI.SERVER + uri,
            data : JSON.stringify(param),
            async : false,
            callback: afterFunc,
            headers:{
                "Content-Type":"application/json"
            }
        });
    } catch(e) {
        console.log("データ更新時にエラーが発生", e);
        dhtmlx.message({ type:"error", text:JSON.parse(e["message"])["message"]});
    }
};

/**
 * GETメソッドでAPIコールを行い、引数のグリッドにデータをロードする。
 *
 * @param {Object} dhxGrid データをロードさせるGridオブジェクト
 * @param {String} templateFileName テンプレートファイル名
 * @param {String} uri REST APIにアクセスするためのURI
 * @param {String} primaryColName 主キーになるカラム名。それで取得された値はNumberであること。nullの場合は行番号を使う。
 * @param {Function} logicFunc Grid表示用にデータを振り分けるロジック関数
 *               引数は１行分のデータオブジェクト（key:value), key, 行番号(0始まり)
 * @param {Function} afterFunc １行データ取得後の後処理を行う関数。nullの場合は実施しない。
 *               引数は１行分の配列[値1, 値2, ・・・]
 */
function loadGridByGet(dhxGrid, templateFileName, uri, primaryColName, logicFunc, afterFunc) {
    // テンプレートファイルの取得
    var template = loadJSON(getTemplatePath(templateFileName));
    try {
        dhxGrid.clearAll(false); // 行を消す
        // サーバ通信し、データを取得する。
        var r = window.dhx4.ajax.getSync(_GHAPI.SERVER + uri);
        var gridData = commonGridDataAsJSObject(template, r, primaryColName, logicFunc, afterFunc);
        //TODO あとで、下記の処理分もCommonの方に寄せる。
        if (gridData.rows.length === 0) {
            console.log("データ取得件数が０件でした");
        }
        dhxGrid.parse(gridData, "json");
    } catch(e) {
        console.log("データ取得時にエラーが発生！", e);
        dhtmlx.alert({ title: "サーバ通信エラー", type:"alert-error", text:JSON.parse(e["message"])["message"]});
    }
};

/**
 * POSTメソッドでAPIコールを行い、引数のグリッドにデータをロードする。
 *
 * @param {Object} dhxGrid データをロードさせるGridオブジェクト
 * @param {String} templateFileName テンプレートファイル名
 * @param {String} uri REST APIにアクセスするためのURI
 * @param {String} primaryColName 主キーになるカラム名。それで取得された値はNumberであること。nullの場合は行番号を使う。
 * @param {Function} logicFunc Grid表示用にデータを振り分けるロジック関数
 *               引数は１行分のデータオブジェクト（key:value), key, 行番号(0始まり)
 * @param {Function} afterFunc １行データ取得後の後処理を行う関数。nullの場合は実施しない。
 *               引数は１行分の配列[値1, 値2, ・・・]
 */
function loadGridByPost(dhxGrid, templateFileName, uri, primaryColName, logicFunc, afterFunc, postPram) {
    // テンプレートファイルの取得
    var template = loadJSON(getTemplatePath(templateFileName));
    try {
        // サーバ通信し、データを取得する。
        var r = window.dhx4.ajax.postSync(_GHAPI.SERVER + uri, postPram);
        var gridData = commonGridDataAsJSObject(template, r, primaryColName, logicFunc, afterFunc);
        //TODO あとで、下記の処理分もCommonの方に寄せる。
        if (gridData.rows.length === 0) {
            console.log("データ取得件数が０件でした");
        }
        dhxGrid.parse(gridData, "json");
    } catch(e) {
        console.log("データ取得時にエラーが発生！", e);
        dhtmlx.alert({ title: "サーバ通信エラー", type:"alert-error", text:JSON.parse(e["message"])["message"]});
    }
};

/**
 * Gridに読み込ませるためのデータJSONオブジェクトを取得する。
 *
 * @param {String} templateFileName テンプレートファイル名
 * @param {String} uri REST APIにアクセスするためのURI
 * @param {String} primaryColName 主キーになるカラム名。それで取得された値はNumberであること。nullの場合は行番号を使う。
 * @param {Function} logicFunc Grid表示用にデータを振り分けるロジック関数
 *               引数は１行分のデータオブジェクト（key:value), key, 行番号(0始まり)
 * @param {Function} afterFunc １行データ取得後の後処理を行う関数。nullの場合は実施しない。
 *               引数は１行分の配列[値1, 値2, ・・・]
 * @return Grid表示用のJSONオブジェクト
 */
function getGridDataAsJSObject(templateFileName, uri, primaryColName, logicFunc, afterFunc) {
    // テンプレートファイルの取得
    var template = loadJSON(getTemplatePath(templateFileName));
    try {
        // サーバ通信し、データを取得する。
        var r = window.dhx4.ajax.getSync(_GHAPI.SERVER + uri);
        return commonGridDataAsJSObject(template, r, primaryColName, logicFunc, afterFunc);
    } catch(e) {
        console.log("データ取得時にエラーが発生！", e);
        dhtmlx.alert({ title: "サーバ通信エラー", type:"alert-error", text:JSON.parse(e["message"])["message"]});
    }
};

/**
 * Gridに読み込ませるためのデータJSONオブジェクトを取得する。
 *
 * @param {String} templateFileName テンプレートファイル名
 * @param {String} uri REST APIにアクセスするためのURI
 * @param {String} primaryColName 主キーになるカラム名。それで取得された値はNumberであること。nullの場合は行番号を使う。
 * @param {Function} logicFunc Grid表示用にデータを振り分けるロジック関数
 *               引数は１行分のデータオブジェクト（key:value), key, 行番号(0始まり)
 * @param {Function} afterFunc １行データ取得後の後処理を行う関数。nullの場合は実施しない。
 *               引数は１行分の配列[値1, 値2, ・・・]
 * @return Grid表示用のJSONオブジェクト
 */
function commonGridDataAsJSObject(template, r, primaryColName, logicFunc, afterFunc, postPram) {
    var t = null;
    try { t = r.xmlDoc; } catch(e) {};
    if (t != null && t.status.toString() == "200") {
        // レスポンスをJSONオブジェクトにする。レスポンスは配列とする。
        var responseData = parseJSON(t.responseText);

        if(is("Array",responseData)){
            // １件ずつ処理する。
            _.forEach(responseData, function(data, rowIdx) {
                // Grid１行分のデータ構造のテンプレート
                var primaryNo = (is("String", primaryColName))? data[primaryColName]: rowIdx;
                var row = {id: primaryNo, data:[]};
                // テンプレートのHeaderカラムのIDを使って、dataから必要な値を取得する。
                _.forEach(template.head, function(headData, colIdx) {
                    // 振り分けロジックがない場合は、直接値を取得する。
                    if (is("Function", logicFunc)) {
                        row.data.push(logicFunc(data, headData.id, rowIdx));
                    } else {
                        row.data.push(data[headData.id]);
                    }
                });
                if(is("Function", afterFunc)) {
                    template.rows.push(afterFunc(row));
                } else {
                    template.rows.push(row);
                }
            });
        }else{
            // Grid１行分のデータ構造のテンプレート
            var primaryNo = (is("String", primaryColName))? responseData[primaryColName]: 0;
            var row = {id: primaryNo, data:[]};
            // テンプレートのHeaderカラムのIDを使って、dataから必要な値を取得する。
            _.forEach(template.head, function(headData, colIdx) {
                // 振り分けロジックがない場合は、直接値を取得する。
                if (is("Function", logicFunc)) {
                    row.data.push(logicFunc(responseData, headData.id, 0));
                } else {
                    row.data.push(responseData[headData.id]);
                }
            });
            if(is("Function", afterFunc)) {
                template.rows.push(afterFunc(row));
            } else {
                template.rows.push(row);
            }
        }
        console.log("Gridに表示するデータ", template);
        return template;
    } else {
        // error
        console.log("ohgHire.data.getGridDataJsonObjectで200以外のレスポンスが返る", "status:" + t.status, t.statusText);
        dhtmlx.alert({ title: "サーバデータ取得エラー", type:"alert-error", text:"データ取得に失敗しました。しばらく時間を置いて再度試してください。<br>"
            + "status:" + t.status + "[" + t.statusText + "]" });
    }
};


/**
 * GETメソッドでJSONオブジェクトを取得する。
 * TODO エラーハンドリング未実装
 *
 * @param uri {String} URI文字列
 * @returns JSON オブジェクト
 */
function getJSONSync(uri) {
    var r = window.dhx4.ajax.getSync(_GHAPI.SERVER + uri);
    console.log("getJSONSync JSON", r.xmlDoc);
    return window.dhx4.s2j(r.xmlDoc.responseText);
}

/**
 * テンプレートフォルダ内にある内容を文字列として取得する。
 * @param {Object} dataPath _GH.DATA_PATHに設定されているオブジェクト
 * @returns {String} ファイル内容の文字列
 */
function getTemplateText(dataPath) {
    try {
        var t = null;
        try {t = window.dhx4.ajax.getSync(getTemplatePath(dataPath)).xmlDoc} catch(e) {};
        if (t !== null && t.status.toString() === "200") {
            return t.responseText;
        } else {
            return "";
        }
    } catch(e) {
        return "";
    }
};

/**
 * 担当者にメッセージを送る。
 */
function sendMessageToEmployee(employeeIds, subject, body) {
    var param = {};
    // 不正な値を取り除く
    _.remove(employeeIds, function(n) { return !(n > 0); });
    _.forEach(employeeIds, function(value, index) {
        if (!_.isNumber(value)) {
            employeeIds[index] = _.parseInt(value);
        }
      });
    // 重複を取り除く
    employeeIds = _.uniq(employeeIds);
    if (employeeIds.length === 0) {
        alert("送信対象社員がいないため、メール送信することができませんでした。");
        return;
    }
    param["toEmployeeIdList"] = employeeIds;
    param["subject"] = subject;
    param["body"] = body.split("\n").join("<BR>");
    syncPutJSON("sendmail/empMessage", param);
}

/**
 * 対応履歴を直接登録する。
 */
function registActHitoryByPost(personalId, employeeId, actType, actTypeDetail, actMemo) {
    if (_.isString(personalId)) personalId = _.parseInt(personalId);
    if (!(personalId > 0)) {
        alert("顧客が指定されていないため、対応履歴を登録できません。");
        return;
    }
    if (!(employeeId > 0)) {
        employeeId = _ghUser.getGhId();
    }
    if (!(actType > 0)) {
        alert("対応種別が指定されていないため、対応履歴を登録できません。");
        return;
    }
    var actDate = window.dhx4.date2str(new Date(), "%Y/%m/%d %H:%i:%s");
    var param = {
            "personalId": personalId,
            "employeeId": employeeId,
            "actType": actType,
            "actTypeDetail": actTypeDetail,
            "actMemo": actMemo,
            "actDateTime": actDate
    };
    return postSyncURL("act/regist", param);
}
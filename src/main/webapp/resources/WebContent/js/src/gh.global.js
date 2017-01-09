/**
 * SFAで使うグローバル変数
 */
/**
 * ユーザ情報
 */
var _ghUser = (function() {
    "use strict";
    var _ghId = 0;   // user ID（GH ID）
    var _adId = "";   // ActiveDirectoryID
    var _pwd = "";
    var _name = ""; // ユーザ名（社員名）
    var _manDivisionIds = []; // 権限のある組織ID
    var _divisionId = "";    // 所属組織ID
    var _divisionName = "";    // 所属組織
    var _role = [];   // ロール
    var _authToken = ""; // 認証トークン
    var _admitDepartment = 1; // 承認用事業部
    var _kmType = _GH.KD; // 戸建て／MS種別（戸建て／MSどちらを表示するか）
    var _login = false;     // ログインしているかどうか
    var _loginType = _GH.NON;
    var _neType = _GH.NON;

    // Cookieにログイン情報を格納する。
    function _saveCookie() {
        var loginInfo = {
            ghId : _ghId,
            adId: _adId,
            pwd: _pwd,
            name : _name,
            manDivisionIds : _manDivisionIds,
            divisionId : _divisionId,
            role : _role,
            authToken : _authToken,
            admitDepartment: _admitDepartment,
            kmType : _kmType,
            login : _login,
            loginType : _loginType,
            neType : _neType
        };
        setGhCookie(_GH.COOKIE.LOGIN, encodeURI(JSON.stringify(loginInfo)));
    };

    // 認証トークンが有効かどうかをテストする。
    function  _testAuthToken() {
        var result = false;
        try {
            if (!_.isEmpty(_authToken) && _loginType) {
                var division = getJSONSync("employee/connect");
                result = !_.isEmpty(division);
            }
        } catch(e) {
            console.log("認証トークン確認中エラー", e);
        }
        return result;
    }
    function _setHeaderInfo(response, adId, pwd) {
        console.log("認証レスポンス", response);
        _authToken = response.getResponseHeader("AUTHORIZED_TOKEN");
        _role = safeCommaSplit(response.getResponseHeader("ROLEID_LIST"));
        _loginType = _.parseInt(response.getResponseHeader("LOGIN_TYPE"));
        _admitDepartment = safeCommaSplit(response.getResponseHeader("AUTHORIZED_ADMIT_DEPARTMENTS"), true);
        _neType = _GH.NE_TYPE[_loginType];
        _ghId = _.parseInt(response.getResponseHeader("LOGIN_USER_ID"));
        _name = b64_to_utf8(response.getResponseHeader("LOGIN_USER_NAME"));
        _manDivisionIds = safeCommaSplit(response.getResponseHeader("MANAGE_DIVISON_IDS"), true);
        _divisionId = _.parseInt(response.getResponseHeader("EMPLOYEE_DIVISION_ID"));
        _divisionName = b64_to_utf8(response.getResponseHeader("EMPLOYEE_DIVISION_NAME"));
        console.log("login type:" + _loginType);
        if(adId) _adId = adId;
        if(pwd) _pwd = window.btoa(pwd);
        _login = true;
        _saveCookie();
    }
    function _connectApi(moveErrPage) {
        var r = null;
        var url = _GHAPI.SERVER + "employee/connect";
        try {
            // 認証トークンや事業部がない場合はCookieから取る。
            var cookies = getCookies();
            var loginInfo = (_.isEmpty(cookies[_GH.COOKIE.LOGIN]))? null: JSON.parse(decodeURI(cookies[_GH.COOKIE.LOGIN]));
            // Cookie情報が正しい場合は、ログイン情報をセットする。
            if (loginInfo) {
                _ghId = loginInfo.ghId;
                _adId = loginInfo.adId;
                _pwd = loginInfo.pwd;
                _name = loginInfo.name;
                _manDivisionIds = loginInfo.manDivisionIds;
                _divisionId = loginInfo.divisionId;
                _role = loginInfo.role;
                _authToken = loginInfo.authToken;
                _admitDepartment = loginInfo.admitDepartment;
                _kmType = loginInfo.kmType;
                _login = loginInfo.login;
                _loginType = loginInfo.loginType;
                _neType = loginInfo.neType;
            }
            if (! _loginType) {
                // default to recruit type - NON if when no recruit type was selected
                _loginType = _GH.LOGIN_TYPE.NON;
            }
            if (!_.isEmpty(_authToken)) {
                r = window.dhx4.ajax.getSync(url);
            } else {
                return false;
            }
        } catch(e) {
            //FIXME Windows統合認証をする場合はエラーになったら事業部を変える必要がある
            console.log("認証トークン確認中エラー", e);
            if (moveErrPage) {
                location.href = "login_error.html";
                window.onbeforeunload = null;
            }
            return false;
        }
        var t = null;
        t = r.xmlDoc;
        var httpStatus = t.status; // number
        if (t != null) {
            if(!_login) {
                _setHeaderInfo(t);
                dhtmlx.message({text: "ようこそ、" + _ghUser.getName() + "さん"});
            }
            return true;
        } else {
            return false;
        }
    }
    /**
     * ログアウトする
     */
    function _logout() {
        try {
            getJSONSync("auth/logout");
        } catch (e) {
            console.log("ログアウト時のエラー", e);
        }
    }

    return {
        // user ID (GH ID)
        getGhId : function() {
            return _ghId;
        },
        setGhId : function(ghId) {
            _ghId = ghId;
        },
        getAdId : function() {
            return _adId;
        },
        getPwd : function() {
            return window.atob(_pwd);
        },
        // ユーザ名（社員名）
        getName : function() {
            return _name;
        },
        setName : function(name) {
            _name = name;
        },
        // login type
        getLoginType : function() {
            return _loginType;
        },
        setLoginType : function(loginType) {
            _loginType = _.parseInt(loginType);
            _neType = _GH.NE_TYPE[_loginType];
        },
        // 所属組織ID
        getDivisionId : function() {
            return _divisionId;
        },
        setDivisionId : function(orgId) {
            _divisionId = orgId;
        },
        // 所属組織
        getDivisionName : function() {
            return _divisionName;
        },
        setDivisionName : function(org) {
            _divisionName = org;
        },
        // 権限のある組織
        getManDivisionIds : function() {
           return _manDivisionIds;
        },
        // 引数の組織IDに対して権限があるかどうか
        hasManDivision : function(divisionId) {
            if (!divisionId) return false;
            if (!_.isNumber(divisionId)) divisionId = _.parseInt(divisionId.toString());
            return _.indexOf(_manDivisionIds, divisionId) >= 0;
        },
        // ロール
        getRole : function() {
            return _role;
        },
        setRole : function(roleString) {
            _role = roleString.split(",");
        },
        hasRole : function(roleString) {
            // 指定したロールを持っているかどうかを確認。ECMAScript5
            return _role.indexOf(roleString) >= 0;
        },
        isShien : function() {
            // 営業支援ロールを持っているかどうかを確認
//            var role = (_department == _GH.DEPTARTMENT.MS)?
//                    _GH.ROLE.PERSONAL_ALL_SEARCH_MANSION: _GH.ROLE.PERSONAL_ALL_SEARCH_HOUSE;
//            return _role.indexOf(role) >= 0;
        	return true;
        },
        isApprove : function() {
            // ライブラリ承認権限があるかどうか
            return _role.indexOf(_GH.ROLE.TEMPLATE_CHECK_MANAGER) >= 0 || _role.indexOf(_GH.ROLE.TEMPLATE_CHECK_MARKETING) >= 0;
        },
        // 認証トークン
        getAuthToken : function() {
            return _authToken;
        },
        setAuthToken : function(authToken) {
            _authToken = authToken;
        },
        // 戸建て／MS種別
        getKMType : function() {
            return _kmType;
        },
        getNEType : function() {
            return _neType;
        },
        isNon : function() {
            return _neType == _GH.NON;
        },
        isExp : function() {
            return _neType == _GH.EXP;
        },
        setKD : function() {
            _kmType = _GH.KD;
        },
        setMS : function() {
            _kmType = _GH.MS;
        },
        swapKMType : function() {
            if (_kmType == _GH.KD) {
                _kmType = _GH.MS;
            } else {
                _kmType = _GH.KD;
            }
        },
        // 営業員かどうか(営業であればTrue、支援やマーケ、情シスであればFalse)
        isSales : function() {
            return _role.indexOf(_GH.ROLE.EIGYOIN) >= 0;
        },
        // MSのリフォーム担当（オーダーシステム課）かどうか
        isMSReform : function() {
            return _kmType == _GH.MS && _role.indexOf(_GH.ROLE.REFORM_MANSION) >= 0;
        },
        // 上長かどうかを判断する。
        isManager : function(divisionId) {
//            console.log("Lodashのテスト1", _.has([1,2,3,4,5], 4));
//            console.log("Lodashのテスト2", _.has([1,2,3,4,5], 9));
            //TODO 顧客のDivisionIdを見て判断すること
            return (_manDivisionIds.length > 0);
        },
        // ログインフラグ
        isLogin : function() {
            return _login;
        },
        setLogin : function(login) {
            _login = login;
        },
        /**
         * Checks whether you're in the main screen (index.html)
         * @returns {boolean} Returns true when in main screen, otherwise false.
         */
        inMainScreen: function() {
            return _.endsWith(window.location.pathname, 'index.html');
        },
        // レスポンスヘッダーから値を取得する
        setHeaderInfo: function(response, adId, pwd) {
            _setHeaderInfo(response, adId, pwd);
        },
        // APIへの接続確認を実施する。
        checkConnect : function() {
            return _connectApi(false);
        },
        logout : function() {
            _logout();
        },
        // toStringのオーバーライド
        toString : function() {
            var str = "戸建て/MS[" + _kmType + "], SFA_ID[" + _ghId + "], ユーザ名[" + _name + "], 所属組織[" + _divisionName + "], ロール["
                + JSON.stringify(_role) + "], 認証トークン[" + _authToken + "], マネージャ権限組織[" + _manDivisionIds + "]";
            console.log(str);
            return str;
        }
    };
}());

/**
 * 共通ウィンドウ群
 */
var _ghWins = null;

/**
 * コード定義（プロパティ）
 */
var _prop = (function() {
    "use strict";
    var _propString = null;
    var _prop = null;
    var _propCache = {};
    var _previousKMType = null;
    function _initProp() {
        // ファイルからコード定義を読み込む
        var r = window.dhx4.ajax.getSync(_GH.PATH_TYPE.CODE_DEF);
        _propString = r.xmlDoc.responseText;
        // 使わない項目（kmType = 'x'のもの）を落とす
        _prop = _.filter(JSON.parse(_propString), function(p) {
            return p.tmType !== "x";
        });
        console.log("コード定義を初期化しました。");
    }
    /**
     * コード定義（プロパティ）を取得する。
     * kmType(戸建て・MS区分）による選別は行わない。
     * @param codeId : string 一意のコードID
     * @return {"groupId":1,"groupName":"プロテクト","codeId":"1_0","CodeName":"プロテクトではない","Value":0, "kmType": "ms"}
     */
    function _getProp(codeId) {
        if (_prop == null) {
            _initProp();
        }
        var index = _.findIndex(_prop, { 'codeId': codeId });
        if (index < 0) {
            console.log("コード定義にコードID[%s]は存在しません。", codeId);
            return {};
        } else {
            return _prop[index];
        }
    }
    /**
     * コード定義（プロパティ）グループを取得する。
     * kmType(戸建て・MS区分）による選別は行う。
     * @param groupId : int グループID
     * @return [{"groupId":1,"groupName":"プロテクト","codeId":"1_0","codeName":"プロテクトではない","value":0, "kmType": "ms"}, ・・・]
     */
    function _getPropGroup(groupId) {
        if (_prop == null) {
            _initProp();
        }
        // 引数のグループIDと同じ、かつ、戸建て・MS区分に合致するものだけに絞る
        return _.filter(_prop, function(p) {
            return p.groupId == groupId && (p.kmType == null || p.kmType == _ghUser.getKMType());
        });
    }
    /**
     * コード定義（プロパティ）グループのValueにマッチしたCodeNameを取得する。
     * @param groupId : int グループID
     * @param value : int 区分値
     * @return CodeNameの値
     */
    function _getPropName(groupId, value) {
        _clearCache();
        if (_propCache[groupId] === undefined) {
            _propCache[groupId] = {};
            _.forEach(_getPropGroup(groupId), function(v/*, key, object*/) {
                _propCache[groupId][v.value] = v.codeName;
            });
        }
        var propName = _propCache[groupId][value];
        return _.isEmpty(propName)? "": propName;
    }
    /**
     * コード名（画面に表示するプロパティの翻訳）からプロパティ値を取得する。（逆引き）
     * @param groupId グループID
     * @param codeName コード名（画面に表示するプロパティの翻訳）
     */
    function _getValueFromPropName(groupId, codeName) {
        var props = _getPropGroup(groupId);
        if (props) {
            return _.result(_.find(props, function(props){
                return props["codeName"] == codeName;
            }), "value");
        } else {
            return "";
        }
    }
    /**
     * プロパティのキャッシュをクリアする。
     */
    function _clearCache() {
        if (_previousKMType !== _ghUser.getKMType()) {
            // キャッシュをクリアする。
            _propCache = {};
            _previousKMType = _ghUser.getKMType();
            console.log("プロパティのキャッシュをクリアしました。")
        }
    }

    /**
     * SelectリストのOption用のデータを取得する。
     *
     * @param groupId : int グループID
     * @param opts: array 初期化したい配列。渡した配列にデータが追記される。
     * @return [{text: "Registered users", value: "Option 1", selected: true},
     *               {text: "Guests", value: "Option 2", selected: true}]
     */
    function _getOptions(groupId, opts) {
        _.forEach(_getPropGroup(groupId), function(value/*, key, object*/) {
            var opt = {text: value.codeName, value: value.value};
            if (value.selected == 1) {
                opt.selected = 1;
            }
            opts.push(opt);
        });
        return opts;
    }

    return {
        // コード定義（プロパティ）グループを取得する。
        getPropGroup : function(groupId) {
            return _getPropGroup(groupId);
        },
        // コード定義（プロパティ）を取得する。
        getProp : function(codeId) {
            return _getProp(codeId);
        },
        // コード名（画面に表示するプロパティの翻訳）からプロパティ値を取得する。（逆引き）
        getValueFromPropName : function(groupId, codeName) {
            return _getValueFromPropName(groupId, codeName);
        },
        // コード定義（プロパティ）の値を取得する。
        getPropValue : function(codeId) {
            return _getProp(codeId).value;
        },
        // SelectリストのOption用のデータを取得する。
        getOptions : function(groupId) {
            return _getOptions(groupId, []);
        },
        // 空を選んだ状態のSelectリストのOption用のデータを取得する。
        getOptionsWithEmpty : function(groupId) {
            return _getOptions(groupId, [{text: "", value: "", selected: true}]);
        },
        // ComboリストのOption用のデータを取得する。指定されたvalueのデータがOptionにない場合は1つ目に追加する。ただし、表示テキストと値は同じとする。
        getComboOptions : function(groupId, value) {
            var opts = _getOptions(groupId, []);
            if (!_.includes(opts, value)) {
                opts.push({text: value, value: value});
                opts = _.sortBy(opts, 'value');
            }
            return opts;
        },
        // コード定義（プロパティ）グループのValueにマッチしたCodeNameを取得する
        getPropName : function(groupId, value) {
            return _getPropName(groupId, value);
        },
		/**
         * @param name of value
         * @param name of text
         * @param objects list objects value
         * @returns {Array}
         */
        setOptionsWithObjects : function(value, text, objects) {
            var opts = [{text: "", value: "", selected: true}];
            $.each(objects, function(index, obj) {
                opts.push({value: obj[value], text: obj[text] });
            });
            return opts;
        }
    }
}());

/** ------------------------------------------------
 *  ■バリデートルールのスクリプト
 *   ------------------------------------------------ */
/**
 * @param data
 * @returns {boolean}
 */
function ValidTel(data) {
    if (_.isEmpty(data)) {
        return true;
    }
    return !_.isEmpty(data.toString().match(/^[0-9]+-{0,1}[0-9]+-{0,1}[0-9]+$/));
}
/**
 * @return {boolean}
 */
function ValidZip(data) {
    if (_.isEmpty(data)) {
        return true;
    }
    return !_.isEmpty(data.toString().match(/^[0-9]{3}[\-]?[0-9]{4}$/));
}

//set zero value to element if it's null
function setZeroIfNull(form, name){
	if(form.getItemValue(name) == "" || form.getItemValue(name) === null){
		form.setItemValue(name, "0");
	}
}

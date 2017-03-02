// TODO Window を作成した後、hide すると親画面のレイアウトにも Window のものが反映されることがある。
// この現象は親画面の Layout を unload して new しても発生するため、明らかに dhtmlx 側の不具合。
// 対処法：window を hide するのではなく、close すること
/**
 * 共通ウィンドウクラス
 */
function CommonWindows() {
    "use strict";

    this.ghWins = new dhtmlXWindows();
    this.ghWins.attachEvent("onShow", function(win){
        win.button("park").hide();
        win.button("minmax1").hide();
        win.setModal(true);
        win.center();
    });
    this.ghWins.attachEvent("onClose", function(win){
        win.setModal(false);
        return true;
    });
    this.ghWins.attachEvent("onHide", function(win){
        win.setModal(false);
        return true;
    });
    this.ghWins.attachEvent("onFocus", function(win){
        win.setModal(true);
    });
    var that = this;

    /**
     * ウィンドウを閉じます。
     * @param {String} windowName ウィンドウ名
     * @param {Boolean} doClose クローズするならtrue, 隠すだけならfalse
     *
     */
    this.closeWindow = function(windowName, doClose) {
        var win = this.ghWins.window(windowName);
        if (!_.isEmpty(win) && !win.isHidden()) {
            if (doClose) {
                win.close();
            } else {
                win.hide();
            }
        }
    };
    /**
     * LDAPログインダイアログを開く
     */
    this.doGoogleLoginWin = function (context) {
        var win = this.ghWins.window("loginWin");
        if (win == null) {
            win = this.ghWins.createWindow("loginWin", 20, 30, 600, 300);
            win.maximize();
            win.button("close").hide();
            win.hide();
            win.setText("Googleログイン");
        }
        win.show();
        var loginForm = win.attachForm();
        var auth2;
        var SCOPES = ["https://www.googleapis.com/auth/calendar"];
        loginForm.loadStruct(getTemplatePath(_GH.DATA_PATH.LOGIN_FORM), "json", function () {
            // initialize log in with Google button
            if (_.isUndefined(gapi.auth2)) {
                gapi.load('auth2', function () {
                    auth2 = gapi.auth2.init({
                        client_id: _GH.GOOGLE_CLIENT_ID,
                        cookiepolicy: 'single_host_origin',
                        scope: SCOPES.join(' ')
                    });

                    attachSignIn($('.g-signin2').get(0));
                });
            }
            else {
                auth2 = gapi.auth2.getAuthInstance();
                attachSignIn($('.g-signin2').get(0));
            }
        });
        loginForm.setFontSize("16" + "px");
        loginForm.attachEvent("onValidateError", function (name/*, value, result*/){
            //エラーメッセージを表示
            dhtmlx.message({ type:"error", text:loginForm.getItemLabel(name) + "は必ず入力してください。"});
        });

        function attachSignIn(element) {
            auth2.attachClickHandler(element, {}, function (googleUser) {
                googleLogin(googleUser.getAuthResponse().id_token, googleUser.getAuthResponse().access_token);
            });
        }
        function googleLogin(token, accessToken) {
            // logout before login process
            getJSONSync("auth/logout");
            _ghUser.setLoginType(loginForm.getItemValue("loginType"));
            try {
                var r = window.dhx4.ajax.postSync(_GHAPI.SERVER + "auth/googleLogin", "token=" + encodeURIComponent(token) + "&accessToken=" + encodeURIComponent(accessToken));
            } catch (e) {
                console.log("Log in with Google failure", e);
                dhtmlx.message({type:"error", text: JSON.parse(e.message).message});
                return;
            }
            var t = r.xmlDoc;
            //var httpStatus = t.status; // number
            if (t != null) {
                console.log("Authentication successful");
                _ghUser.setHeaderInfo(t);
                console.log(_ghUser.toString());
                win.close();
                context.refresh();
                context.init();
                dhtmlx.message({text: "ようこそ、" + _ghUser.getName() + "さん"});
            } else {
                var message = is("String", t.responseText)? JSON.parse(t.responseText).message: "サーバーエラーが発生しました。";
                //var params = { 'status' : httpStatus, 'errorMessage' :message};
                //throw new Error(JSON.stringify({message: message, params: params}));
                dhtmlx.message({type:"error", text: message});
            }
        }

    };

    this.doBuilding = function(buildingCodeInput, buildingNameInput, formObject){
        var winName = "BuildingWin";
        var MODE =  _GH.WINS.EVENT_MODE.EVENT;
        //var DEPT = (_ghUser.isNon())? _GH.WINS.EVENT_POS.KD: _GH.WINS.EVENT_POS.MS;
        var win = this.ghWins.window(winName);
        if (win == null) {
            var winWidth = MODE.width;
            win = this.ghWins.createWindow(winName, 20, 30, winWidth, MODE.height);
            win.setText("Select Building");
            win.hide();
            win.attachEvent("onClose", function (/*win*/) {
                if (!_.isEmpty(formObject)) formObject.updateValues();
                return true;
            });
        }
        var bukkenForm = win.attachForm();
        bukkenForm.loadStruct(loadJSON(getTemplatePath(_GH.DATA_PATH.WIN_BUILDING_FORM_JSON)), "json", function() {
        if (MODE.isEvent) {
                this.hideItem("arrow");
                this.hideItem("info");
            }
        });
        var gridDataLogicFunc = function (data, id/*, rowIdx*/) {
            var val;
            switch (id) {
                case "buildingName":
                    val = _.isEmpty(data[id])? data["buildingName"]: data[id];
                    break;
                default:
                    val = data[id];
                    break;
            }
            return val;
        };
        var leftGrid = new dhtmlXGridObject(bukkenForm.getContainer("leftGrid"));
        leftGrid.setImagePath(_GH.PATH_TYPE.DHX_IMGS);
        leftGrid.setSkin(_GH.PATH_TYPE.SKIN);
        leftGrid.attachEvent("onDataReady",function(){
            leftGrid.setColumnHidden(0, true);
        });

        var searchBuilding = function(doSearchAll) {
            var buildingName = bukkenForm.getItemValue("searchBuildingName");
            var query = "";
            if (!_.isEmpty(buildingName)) {
                query += "buildingName=" + buildingName + "&";
            }
            if (_.isEmpty(query) && !doSearchAll) return;
            if (leftGrid) leftGrid.clearAll();
            loadGridByGet(leftGrid, _GH.DATA_PATH.BUILDING_GIRD, "building/list?" + query, "buildingCode", gridDataLogicFunc);
        };

        bukkenForm.attachEvent("onChange", function (name/*, value*/) {
            switch (name) {
                case "searchBuildingName":
                    searchBuilding();
                    break;
            }
        });
        bukkenForm.attachEvent("onKeyDown",function (inp, ev, name/*, value*/) {
            switch (name) {
                case "searchBuildingName":
                    if (ev.keyCode == _GH.KEY_CODE.ENTER) searchBuilding();
                    break;
            }
        });

        var previousEventId = 0;
        searchBuilding(true);
        leftGrid.attachEvent("onRowDblClicked", function (selectBuildingCode/*, cInd*/) {
            if (buildingCodeInput !== null) buildingCodeInput.value = leftGrid.cells(selectBuildingCode, 0).getValue();
            if (buildingNameInput !== null) buildingNameInput.value = leftGrid.cells(selectBuildingCode, 1).getValue();
            win.close();
        });
        win.show();
    };

    /**
     * 担当者選択ダイアログを開く
     * @param tantoIdInput {Object} 担当者IDを設定するためのInputオブジェクト
     * @param tantoNameInput {Object} 担当者名を設定するためのInputオブジェクト
     * @param divisionIdInput
     * @param divisionName
     * @param divisionSelectable
     * @param initialEmp
     * @param def
     * @param doShowAll
     * @param isCheckBox
     * @param isGetName
     */
    this.doTantoWin = function(tantoIdInput, tantoNameInput, divisionIdInput, divisionName, divisionSelectable, initialEmp, def, doShowAll, isCheckBox, isGetName) {
        var winName = "TantoWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 400, 380);
            win.hide();
            win.setText("担当者選択");
        }
        var tantoTree = null;
        var tantoForm = win.attachForm();
        var divisionId = !isCheckBox ? 0 : [];
        var employeeId = !isCheckBox ? 0 : [];
        tantoForm.loadStruct(getTemplatePath(_GH.DATA_PATH.TANTO_JSON), function() {
            // TODO (confirm): only show cancel button in main screen (in SFA version: SFA page)
            if (_ghUser.inMainScreen()) {
                this.showItem("deleteTantoBtn");
            }
            tantoTree = new dhtmlXTreeObject(tantoForm.getContainer("tantoTree"),"100%","100%",0);
            if(isCheckBox){
            	tantoTree.enableCheckBoxes(true);
                tantoTree.enableThreeStateCheckboxes(true);
                this.hideItem("notice");
                this.showItem("checkBoxBtn");
            }            
            tantoTree.setImagePath(_GH.PATH_TYPE.TREE_IMGS);
            tantoTree.enableKeyboardNavigation(true);
            //TODO ここはキャッシュで持たせたい
            var uri = (doShowAll)? "0/3": _ghUser.getLoginType() + "/3";
            tantoTree.parse(getJSONSync(_GHAPI.URL.TANTO + uri +"?quitFlg=0"), function () {
                if (initialEmp) {
                    // 引数で与えられた担当者IDを探す
                    var allNodesIds = tantoTree.getAllSubItems(0);
                    allNodesIds = allNodesIds.split(",");
                    _.forEach(allNodesIds, function (id) {
                        if(tantoTree.getUserData(id, "employeeId") === initialEmp){
                            tantoTree.findItem(tantoTree.getItemText(id));
                        }
                    });
                } else {
                    tantoTree.findItem(_ghUser.getName());
                }
                // 何も選択されていなければ、ルートを開く
                if (!tantoTree.getSelectedItemId()) {
                    var rootId = safeCommaSplit(tantoTree.getAllItemsWithKids())[0];
                    tantoTree.openItem(rootId);
                }
            }, "json");
            // ダブルクリックイベント
            if (!isCheckBox) {
            	  tantoTree.attachEvent("onDblClick", function (/*id*/) {
                    win.close();
                });
            }
        });

        // クリックイベント
        tantoForm.attachEvent('onButtonClick', function(name/*, command*/){
            switch(name) {
                case "searchTantoBtn": // 検索ボタンクリック
                    tantoTree.findItem(tantoForm.getItemValue("searchTanto"));
                    break;
                case "deleteTantoBtn": // 取消ボタンクリック
                    if(tantoIdInput) tantoIdInput.value = 0;
                    if(tantoNameInput) tantoNameInput.value = "";
                    if(divisionIdInput) divisionIdInput.value = 0;
                    if(divisionName) divisionName.value = "";
                    win.close();
                    break;
                case "checkBoxBtn":
                	win.close();
                    break;
            }
        });

        win.attachEvent("onClose", function(){
            var selectedId = (!isCheckBox)? tantoTree.getSelectedItemId() : tantoTree.getAllChecked();
            // 何も選択されていない場合、処理を終える
            if(selectedId == 0){
                return true;
            }
            if(isCheckBox){
        		var checkBoxId = _.words(selectedId, /[^, ]+/g);
        		if(checkBoxId === "undefined"){
        			return true;
        		}
        		var text = [];
        		_.forOwn(checkBoxId, function(value/*, key*/) {
        			if(tantoTree.getUserData(value, "employeeId") !== undefined){
        				employeeId.push(tantoTree.getUserData(value, "employeeId"));
        				if((divisionId.indexOf(tantoTree.getUserData(value, "divisionId")) == -1)){
        					divisionId.push(tantoTree.getUserData(value, "divisionId"));
        				}
        				text.push(tantoTree.getItemText(value));
        			}
    			});
        		if(tantoIdInput){
                    tantoIdInput.value = employeeId;
                }
        		tantoNameInput.value = text.join(";");
        	} else {
        		if (tantoTree.getUserData(selectedId, "employeeId") !== undefined) {
                    // 担当者が選択された場合
                	
                    employeeId = tantoTree.getUserData(selectedId, "employeeId");
                    if(tantoIdInput){
                        tantoIdInput.value = employeeId;
                    }
                    divisionId = tantoTree.getUserData(selectedId, "divisionId");
                    if(divisionIdInput && !_.isEmpty(divisionId) && divisionId != 0){
                        divisionIdInput.value = divisionId;
                    }

                    // 担当者名に課名も付与
                    var parentId = tantoTree.getParentId(selectedId);
                    var division = tantoTree.getItemText(parentId);
                    (isGetName) ? $(tantoNameInput).val(tantoTree.getSelectedItemText()).change() : $(tantoNameInput).val(division + " " + tantoTree.getSelectedItemText()).change();
                    var selectedName = tantoTree.getSelectedItemText();
                    if (divisionName && !_.isEmpty(selectedName) && selectedName != 0) {
                        $(divisionName).val(selectedName).change();
                    }
                } else {
                    // 組織が選択できない場合、組織がクリックされた場合は無視する
                    if(!divisionSelectable){
                        console.log("組織を選択したため、無視する");
                        return;
                    }
                    divisionId = tantoTree.getUserData(selectedId, "divisionId");
                    if(divisionIdInput && !_.isEmpty(divisionId) && divisionId != 0){
                        divisionIdInput.value = divisionId;
                    }
                    var selectedName = tantoTree.getSelectedItemText();
                    if (divisionName && !_.isEmpty(selectedName) && selectedName != 0) {
                        $(divisionName).val(selectedName).change();
                    }
                    // 担当者情報があれば、空にする。
                    if (tantoIdInput) {
                        tantoIdInput.value = 0;
                    }
                }
        	}            
            // 主に値の受け渡し
            if (def) {
               def.resolve(divisionId, employeeId);
            }
            return true;
        });

        win.show();
    };

    /**
     * 画像アップロードウィンドウを開く
     * @param ckEditor 画像アップロードで帰ってきたURLを設定するためのCKEditorオブジェクト
     */
    this.doImageUploadWin = function(ckEditor) {
        var winName = "ImageUploadWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 330, 160);
            win.setText("画像アップロードウィンドウ");
            win.hide();
        }
        // 中身の用意
        var imageForm = win.attachForm();
        imageForm.loadStruct(getTemplatePath(_GH.DATA_PATH.MAIL_IMAGE_FORM_JSON), "json");
        imageForm.enableLiveValidation(true);
        // 添付ファイルアップロードの開始
        imageForm.attachEvent("onBeforeFileAdd", function(file){
            // 入力チェック
            if (!this.validate()) {
                console.log("バリデーションerror");
                return false;
            }

            // 拡張子チェック
            var ext = "";
            var k = String(file).match(/\.([^\.\s]*)$/i); // "filename.jpg" -> [".jpg","jpg"]
            if (k != null) ext = k[1];

            if (_.indexOf(["bmp", "gif", "jpeg", "jpg", "png"], ext.toLowerCase()) < 0) {
                dhtmlx.alert({
                    title:"ファイル拡張子エラー",
                    type:"alert-error",
                    text:"画像ファイル（拡張子がbmp, gif, jpeg, jpg, png）ではないため、アップロードできません。"
                });
                return false;
            }

            // 値の設定
            var imageFile = imageForm.getUploader("imageFile");
            imageFile.setDoCompress(true);
            imageFile.setImageQuality(parseFloat(imageForm.getItemValue("imageFileQuality")));
            imageFile.setImageMaxWidth(parseInt(imageForm.getItemValue("imageFileMaxWidth")));
            imageFile.setImageMaxHeight(parseInt(imageForm.getItemValue("imageFileMaxHeight")));
            imageFile.setSendParams("SFA_USER_ID", _ghUser.getGhId());
            return true;
        });
        // 値が変更された際の設定
        imageForm.attachEvent("onChange",function(name, value){
            if (name == "imageFileQuality") {
                imageForm.getUploader("imageFile").setImageQuality(parseFloat(value));
            } else if (name == "imageFileMaxWidth") {
                imageForm.getUploader("imageFile").setImageMaxWidth(parseInt(value));
            } else if (name == "imageFileMaxHeight") {
                imageForm.getUploader("imageFile").setImageMaxHeight(parseInt(value));
            }
        });
        // 画像ファイルアップロードの完了
        imageForm.attachEvent("onUploadFile",function(realName, serverName){
            console.log("画像のフォーム", this.getFormData());
            console.log("<b>onUploadComplete</b> realName   = " + realName);
            console.log("<b>onUploadComplete</b> serverName = " + serverName);
            ckEditor.insertHtml("<img src='" + _GH.FILE_ROOT + serverName + "'/>");
        });
        imageForm.attachEvent("onUploadComplete",function(/*count*/){
            win.close();
        });
        // 添付ファイルアップロードの失敗
        imageForm.attachEvent("onUploadFail",function(realName){
            dhtmlx.alert({
                title:"画像アップロードエラー",
                type:"alert-error",
                text:realName + "をアップロードすることができませんでした。"
            });
            win.close();
        });
        // バリデートエラー時の処理
        imageForm.attachEvent("onValidateError", function (name/*, value, result*/) {
            dhtmlx.message({
                type:"error",
                text: imageForm.getItemLabel(name) + "は数字で必ず入力してください。"
            });
        });
        win.show();
    };

    /**
     * 希望地域ダイアログを開く
     * @param {Object} targetInput 希望地域ダイアログで選んだ値を設定するためのInputオブジェクト
     * @param {Boolean} showOptNon 「希望地域なし」表示フラグ
     * @param {Object} formObject フォームオブジェクト
     */
    this.doKiboAreaWin = function(targetInput, showOptNon, formObject) {
        // 希望地域の区切り文字
        var winName = "KiboAreaWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            //{id: "w2", left: 350, top: 30,  width: 650, height: 200, text: "希望地域ウィンドウ", move: true},
            win = this.ghWins.createWindow(winName, 20, 30, 800, 725);
            win.hide();
            win.setText("希望地域ウィンドウ");
            win.attachEvent("onClose", function(/*win*/){
                if (!_.isEmpty(formObject)) formObject.updateValues();
                return true;
            });
        }
        // 中身の用意
        var kiboAreaForm = win.attachForm();
        kiboAreaForm.loadStruct(getTemplatePath(_GH.DATA_PATH.KIBO_AREA_JSON), "json", function() {
            // すでに地域が選択されている場合の処理
            var inpVal = targetInput.value;
            if (is("String", inpVal) && inpVal.length > 0) {
                var preSelectArea = inpVal.split(_GH.FORMAT.AREA_DELIMITER);
                var dataset = {};
                _.forEach(kiboAreaForm.getFormData(), function(value, key/*, object*/) {
                    dataset[key] = _.indexOf(preSelectArea, kiboAreaForm.getItemValue(key)) >= 0;
                });
                kiboAreaForm.setFormData(dataset);
            }
            // 希望地域なしを表示しない場合は隠す
            if (showOptNon) {
                kiboAreaForm.showItem("opt_non");
            } else {
                kiboAreaForm.hideItem("opt_non");
            }
        });
        // チェックボックス値変更
        kiboAreaForm.attachEvent("onChange", function (name/*, value*/){
            // 「すべて」チェックボックスを押したときの動作
            if (name.lastIndexOf("all") > 0) {
                var setval = kiboAreaForm.isItemChecked(name); // 「すべて」がチェックしているかどうか
                var prefix = name.split("_")[0] + "_"; // すべてのプレフィックスを取る。
                var dataset = {};
                _.forEach(kiboAreaForm.getFormData(), function(value, key/*, object*/) {
                    if (key.indexOf(prefix) == 0) {
                        dataset[key] = setval;
                    }
                });
                kiboAreaForm.setFormData(dataset);
            }
       });
        // ボタンクリック
        kiboAreaForm.attachEvent("onButtonClick", function(name){
            if (name == "kiboAreaSelectBtn") {
                // 選択ボタンクリック
                // 選択した地域をカンマでつなげて、Inputに登録する。
                var text = ""; 
                _.forEach(kiboAreaForm.getFormData(), function(value, key/*, object*/) {
                    if (key.lastIndexOf("all") < 0 && value != 0) {
                        text += value + _GH.FORMAT.AREA_DELIMITER;//separate each area with "/"
                    }
                });
                text = (text.length == 0)? "": "/" +text; // add "/" for first area 
                if(escape(text).replace(/%u.{4}/g,"xx").length > 600){
                    dhtmlx.confirm({
                        type:"confirm",
                        text: "希望地域の文字数が制限を超えています。"
                    });
                    return;
                }
                targetInput.value = text;
                win.close();
            } else if (name == "opt_all_chk") {
                // すべて選択
                var dataset = {};
                _.forEach(kiboAreaForm.getFormData(), function(value, key/*, object*/) {
                    if (key !== "opt_non") { // 「希望地域なし」は除く
                        dataset[key] = true;
                    }
                });
                kiboAreaForm.setFormData(dataset);
            } else if (name == "opt_all_dechk") {
                // チェックをはずす
                var dataset = {};
                _.forEach(kiboAreaForm.getFormData(), function(value, key/*, object*/) {
                    dataset[key] = false;
                });
                kiboAreaForm.setFormData(dataset);
            }
       });
        win.show();
    };

    /**
     * 沿線ダイアログを開く
     * @param {String} ensenIdInp 沿線IDを設定するための沿線Inputオブジェクト
     * @param {String} ensenNameInp 沿線名を設定するための沿線Inputオブジェクト
     * @param {String} ekiFromIdInp 駅IDを設定するための始駅Inputオブジェクト
     * @param {String} ekiFromNameInp 駅名を設定するための始駅Inputオブジェクト
     * @param {String} ekiToIdInp 駅IDを設定するための終駅Inputオブジェクト
     * @param {String} ekiToNameInp 駅名を設定するための終駅Inputオブジェクト
     * @param {Object} formObject フォームオブジェクト
     */
    this.doEnsenWin = function(ensenIdInp, ensenNameInp, ekiFromIdInp, ekiFromNameInp, ekiToIdInp, ekiToNameInp, formObject) {
        var win = this.ghWins.window("railwayWin");
        if (win == null) {
            win = this.ghWins.createWindow("railwayWin", 20, 30, 550, 550);
            win.hide();
            win.setText("沿線ウィンドウ");
        }
        // 中身の用意
        var railwayForm = win.attachForm();
        railwayForm.loadStruct(getTemplatePath(_GH.DATA_PATH.ENSEN_JSON), "json");
        // ラジオボタン
        railwayForm.attachEvent("onChange", function (name, value){
            // 駅ウィンドウを開く
            that.doEkiWin(ensenIdInp, ensenNameInp, ekiFromIdInp, ekiFromNameInp, ekiToIdInp, ekiToNameInp, value, this.getItemLabel(name, value), formObject);
            win.close();
        });
        // ボタンクリック
        railwayForm.attachEvent('onButtonClick', function(name/*, command*/){
            switch (name) {
                case "deselectBtn": // 駅・沿線の選択取り消しボタン
                    _.forEach([ensenIdInp, ensenNameInp, ekiFromIdInp, ekiFromNameInp, ekiToIdInp, ekiToNameInp], function (inpObj) {
                        if (inpObj !== null) inpObj.value = "";
                    });
                    win.close();
                    break;
            }
        });
        win.show();
    };

    /**
     * 駅ダイアログを開く
     * @param {Object} ensenIdInp 沿線IDを設定するための沿線Inputオブジェクト
     * @param {Object} ensenNameInp 沿線名を設定するための沿線Inputオブジェクト
     * @param {Object} ekiFromIdInp 駅IDを設定するための始駅Inputオブジェクト
     * @param {Object} ekiFromNameInp 駅名を設定するための始駅Inputオブジェクト
     * @param {Object} ekiToIdInp 駅IDを設定するための終駅Inputオブジェクト
     * @param {Object} ekiToNameInp 駅名を設定するための終駅Inputオブジェクト
     * @param {Number} railwayId 沿線ID
     * @param {String} ensenName 沿線名
     * @param {Object} formObject フォームオブジェクト
     */
    this.doEkiWin = function(ensenIdInp, ensenNameInp, ekiFromIdInp, ekiFromNameInp, ekiToIdInp, ekiToNameInp, railwayId, ensenName, formObject) {
        var winName = "ekiWin";
        // 開始と終了の２駅使うかどうか
        var useTowStations = (ekiToIdInp !== null && ekiToIdInp !== undefined);
        var win = this.ghWins.window(winName);
        if (win == null) {
            var winWidth = useTowStations? 420: 230;
            win = this.ghWins.createWindow(winName, 20, 30, winWidth, 400);
            win.hide();
            win.button("close").attachEvent("onClick", function(){
                that.doEnsenWin(ensenIdInp, ensenNameInp, ekiFromIdInp, ekiFromNameInp, ekiToIdInp, ekiToNameInp, formObject);
                return true;
            });
        }
        win.setText(ensenName + "の駅ウィンドウ");
        var startStationGrid = null;
        var endStationGrid = null;
        // 中身の用意
        var stationForm = win.attachForm();
        var templatePath = useTowStations? _GH.DATA_PATH.EKI_JSON: _GH.DATA_PATH.EKI_ONE_JSON;
        stationForm.loadStruct(getTemplatePath(templatePath), "json", function(){
            // 駅情報を取得する。
            startStationGrid = new dhtmlXGridObject(stationForm.getContainer("startStation"));
            startStationGrid.attachEvent("onDataReady",function(){
                _.forEach(_GH.WINS.EKI.HIDE_COL, function(idx) { startStationGrid.setColumnHidden(idx, true);});
                startStationGrid.selectRow(0); // 始発駅を選ぶ
            });
            loadGridByGet(startStationGrid, _GH.DATA_PATH.EKI_GRID_JSON, _GH.URL("RAILWAY") + railwayId, "ekiId");
            if (useTowStations) {
                endStationGrid = new dhtmlXGridObject(stationForm.getContainer("endStation"));
                endStationGrid.attachEvent("onDataReady",function(){
                    _.forEach(_GH.WINS.EKI.HIDE_COL, function(idx) { endStationGrid.setColumnHidden(idx, true);});
                    endStationGrid.selectRow(endStationGrid.getRowsNum() - 1); // 終点を選ぶ
                    endStationGrid.showRow(endStationGrid.getSelectedRowId());
                });
                loadGridByGet(endStationGrid, _GH.DATA_PATH.EKI_GRID_JSON, _GH.URL("RAILWAY") + railwayId, "ekiId");
            }
        });
        // クリックイベント
        stationForm.attachEvent('onButtonClick', function(name/*, command*/){
            switch(name) {
                case "selectBtn": // 選択ボタン
                    // 駅が両方選ばれていることを確認
                    var startSelectRowId = startStationGrid.getSelectedRowId();
                    var endSelectRowId = useTowStations? endStationGrid.getSelectedRowId(): "dummy";
                    if (startSelectRowId == null || endSelectRowId == null) {
                        dhtmlx.alert({
                            title:"駅選択エラー",
                            type:"alert-error",
                            text:"始駅と終駅の両方を選択してください。"
                        });
                        return;
                    }
                    // 路線情報セット
                    if (ensenIdInp !== null) ensenIdInp.value = railwayId;
                    if (ensenNameInp !== null) ensenNameInp.value = ensenName;
                    // 始駅と終駅が逆の場合は、正しい順番にしてセットする。
                    if (useTowStations) {
                        if (startStationGrid.cells(startSelectRowId, 3).getValue() < endStationGrid.cells(endSelectRowId, 3).getValue()) {
                            // 正順
                            if (ekiFromIdInp !== null) ekiFromIdInp.value = startStationGrid.cells(startSelectRowId, 0).getValue();
                            if (ekiFromNameInp !== null) ekiFromNameInp.value = startStationGrid.cells(startSelectRowId, 4).getValue();
                            if (ekiToIdInp !== null) ekiToIdInp.value = endStationGrid.cells(endSelectRowId, 0).getValue();
                            if (ekiToNameInp !== null) ekiToNameInp.value = endStationGrid.cells(endSelectRowId, 4).getValue();
                        } else {
                            // 逆順
                            if (ekiToIdInp !== null) ekiToIdInp.value = startStationGrid.cells(startSelectRowId, 0).getValue();
                            if (ekiToNameInp !== null) ekiToNameInp.value = startStationGrid.cells(startSelectRowId, 4).getValue();
                            if (ekiFromIdInp !== null) ekiFromIdInp.value = endStationGrid.cells(endSelectRowId, 0).getValue();
                            if (ekiFromNameInp !== null) ekiFromNameInp.value = endStationGrid.cells(endSelectRowId, 4).getValue();
                        }
                    } else {
                        if (ekiFromIdInp !== null) ekiFromIdInp.value = startStationGrid.cells(startSelectRowId, 0).getValue();
                        if (ekiFromNameInp !== null) ekiFromNameInp.value = startStationGrid.cells(startSelectRowId, 4).getValue();
                    }
                    if (!_.isEmpty(formObject)) formObject.updateValues();
                    win.close();
                    break;
            }
        });
        win.show();
    };

    /**
     * メール作成ウィンドウ
     * @param {Array} personalIds 送信対象顧客IDの配列
     * @param {Object} personalInfo 顧客情報 顧客メイン／顧客詳細のFormのデータセットを想定
     * @param model
     */
    this.doMailWindow = function(personalIds, personalInfo, model) {
        var winName = "mailWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 1050, 750);
            win.hide();
        }
        var attachments = {}; // 添付ファイルマップ。キーは実ファイル名、バリューはサーバ上のファイル名
        var ckeditor = null;
        console.log("送信対象顧客ID", personalIds);
        // 中身の用意
        var mailForm = win.attachForm();
        mailForm.loadStruct(getTemplatePath(_GH.DATA_PATH.MAIL_FORM_JSON), "json", function() {
            // 表示設定
            win.setText("一斉メールを作成します。");
            mailForm.setItemValue("toAddress", "一覧表でチェックされた個人にメールします。");
            mailForm.disableItem("toAddress");
            mailForm.disableItem("emailAddressDetailBtn");
            // CCの設定
            mailForm.reloadOptions("ccType", _prop.getOptions(_GH.CODE_DEF.MAIL_CC));
            // 送信者（From）の設定
            var removeValues = [];
            // 営業支援であれば、送信者「自分」を除く
            if (_ghUser.hasRole(_GH.ROLE.PERSONAL_ALL_SEARCH_HOUSE)) removeValues.push(1);
            // 代理送信権限がないなら、送信者「担当課員」を除く
            if (!_ghUser.hasRole(_GH.ROLE.MAIL_PROXY_HOUSE)) removeValues.push(2);
            // @INFO送信権限がないなら、送信者「＠INFO」を除く
            if (!_ghUser.hasRole(_GH.ROLE.MAIL_ATINFO_HOUSE)) removeValues.push(3);

            mailForm.reloadOptions("fromType", removePropOptions(_prop.getOptions(_GH.CODE_DEF.MAIL_FROM), removeValues));
            // MS営業支援の個別設定
            if (_ghUser.isExp() && _ghUser.hasRole(_GH.ROLE.PERSONAL_ALL_SEARCH_MANSION)) mailForm.setItemValue("fromType", 3);
            // カレンダーで選択できるのは今日から２週間以内。
            mailForm.getCalendar("timerStart").setSensitiveRange(new Date(), getAfterDate(0, 14));

            // CKEditorの設定
            CKEDITOR.config.height = "400px";
            CKEDITOR.config.width = "950px";
            ckeditor = CKEDITOR.replace( "body", _GH.WINS.CKEDITOR.MAIL);
            // ペースト時にGmailの画像があったら強制的に削除する
            //TODO ペースト時に画像があった場合は同様にすべきかも。
            ckeditor.on( 'paste', function( evt ) {
                var originalPasteText = evt.data.dataValue;
                var foundGmailImges = originalPasteText.match(/<img.*?>/gi);
                _.forEach(foundGmailImges, function(imageStr) {
                    // 置換
                    originalPasteText = originalPasteText.split(_.escape($(imageStr)[0].src)).join("\" alt=\"この画像はペーストできません。「画像の挿入」ボタンから画像を登録してください。");
                });
                evt.data.dataValue = originalPasteText;
            });

            //TODO 添付ファイル？
            // メール編集可否を見る。不可であれば、件名・本文・物件・ファイルアップロードは編集できなくする。
            // メールの件名と本文をセッションストレージの値を設定する。
            mailForm.setItemValue("subject", getSS(_GH.S05F010ML.MAIL_SUB));
            ckeditor.setData(getSS(_GH.S05F010ML.MAIL_BODY));
            // メール件名が空の場合、デフォルトを入れる。
            if (_.isEmpty(mailForm.getItemValue("subject"))) {
                mailForm.setItemValue("subject", _GH.WINS.MAIL_SUBJECT);
            }
            CKEDITOR.config.readOnly = false;
            mailForm.getUploader('attachmentFiles').setURL(_GHAPI.SERVER + _GH.URL('FILE_UPLOAD_URL'));
        });
        // メールフォームのクリックイベント設定
        mailForm.attachEvent('onButtonClick', function(name/*, command*/){
            switch(name) {
                case "emailAddressDetailBtn": // 詳細ボタン
                    that.doDetailAddressWin(personalInfo, mailForm.getInput("toAddress").id);
                    break;
                case "sendTestEmailBtn": // テスト送信
                    that.sendTestMail(mailForm, ckeditor, attachments, personalIds);
                    break;
                case "sendEmailBtn": // 送信
                    that.sendMail(winName, mailForm, ckeditor, attachments, personalIds, model);
                    break;
            }
        });
        // ライブラリのフォーカスイベント設定
        mailForm.attachEvent('onFocus', function(name/*, command*/){
            switch(name) {
                case "libraryName": // ライブラリ選択ウィンドウ
                    that.doLibSelectWin(personalIds.length);
                    break;
                case "eventName": // 物件ウィンドウを開く
                    that.doEvent(this.getInput("eventId"), this.getInput("eventName"), mailForm);
                    break;
            }
        });

        // 送信者の @info を選らんだ場合、署名がつかないのでメッセージで注意喚起
        mailForm.attachEvent('onChange', function(name, value/*, form*/){
            switch(name){
                case "fromType":
                    if(value.toString() === _prop.getValueFromPropName(_GH.CODE_DEF.MAIL_FROM, '@INFO').toString()){
                        dhtmlx.message("署名は手動で入力してください。");
                    }
            }
        });

        // 添付ファイルアップロードの設定
        that.setFileUploadEvents(mailForm, attachments);
        win.show();
    };
    /**
     * メール作成画面のメールアドレス詳細ウィンドウ
     * @param {Object} personalInfo 顧客情報
     * @param {String} toAddressId toAddress要素のID
     */
    this.doDetailAddressWin = function(personalInfo, toAddressId) {
        var winName = "detailAddressWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 380, 220);
            win.setText("送信先アドレス変更ウィンドウ");
            win.hide();
            win.attachEvent("onClose", function(/*win*/){
                return true;
            });
        }
        var addressForm = win.attachForm();
        addressForm.loadStruct(getTemplatePath(_GH.DATA_PATH.MAIL_ADDRESS_FORM_JSON), "json", function() {
            addressForm.setFormData(personalInfo);
        });
        addressForm.attachEvent('onButtonClick', function(name/*, command*/){
            switch (name) {
                case "addressOKBtn" :
                    // メールアドレスと送信チェックが有効な組が１組以上あることをチェックする。
                    var tempToAddress = getSendEmailsFromCustInfo(addressForm.getFormData());
                    if (tempToAddress.length === 0) {
                        dhtmlx.alert({
                            title:"メールアドレスチェックエラー",
                            type:"alert-error",
                            text:"送信先のメールアドレスを１つ以上チェックしてください。"
                        });
                    } else {
                        // 変更した値を保存する。
                        postSyncURL("personal/update", addressForm.getFormData());
                        document.getElementById(toAddressId).value = tempToAddress;
                        win.close();
                    }
                    break;
            }
        });
        win.show();
    };

    /**
     * ライブラリ選択ウィンドウ
     * @param {Number} sendCount 送信人数
     */
    this.doLibSelectWin = function(sendCount) {
        var winName = "libSelectWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 1200, 450);
            win.setText("ライブラリ選択");
            win.hide();
        }
        win.show();
        var winLayout = win.attachLayout("2U");
        var mailLibTree  =  new S05F030LibTree(winLayout);
        mailLibTree.initWin(sendCount);
    };

    /**
     * ライブラリ作成・編集ウィンドウ
     * @param {Number} libraryDirId ライブラリディレクトリID。新規作成時で、不明な場合は0。その他は必ず指定すること。
     * @param {Number} libraryId ライブラリID。新規ライブラリの場合は0
     * @param {Object} mailFormInfo メール作成ウィンドウのgetFormDataの内容
     * @param {Object} collection 連鎖的に更新するためのオブジェクトコレクション
     * @param {Boolean} readOnly 読み取り専用。trueの場合は読み取り専用（編集不可）
     */
    this.doLibWin = function(libraryDirId, libraryId, mailFormInfo, collection, readOnly) {
        console.log("ライブラリウィンドウ表示", libraryDirId, libraryId, mailFormInfo);
        if (libraryId > 0) {
            // サーバからライブラリ情報を取得する。エラーの場合、処理続行しない
            var libraryData = getJSONSync("library/id/" + libraryId);
        }

        var winName = "libWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 1200, 750);
            win.setText("ライブラリの作成・編集");
            win.hide();
        }
        var attachments = {}; // 添付ファイルマップ。キーは実ファイル名、バリューはサーバ上のファイル名
        var ckeditor = null; // CKEditor（本文入力欄）
        // 中身の用意
        var mailForm = win.attachForm();
        mailForm.loadStruct(getTemplatePath(_GH.DATA_PATH.MAIL_LIB_FORM_JSON), "json", function() {
            // 値の設定
            mailForm.setItemValue("libraryDirId", libraryDirId);
            mailForm.setItemValue("libraryId", libraryId);
            mailForm.setItemValue("employeeId", _ghUser.getGhId());
            // プルダウンの設定
            mailForm.reloadOptions("approvalSelect", _prop.getOptionsWithEmpty(_GH.CODE_DEF.LIB_APPROVE));
            // CKEditorの設定
            CKEDITOR.config.height = "400px";
            CKEDITOR.config.width = "950px";
            ckeditor = CKEDITOR.replace( "libBodyTextarea", _GH.WINS.CKEDITOR.LIB);
            // ペースト時に画像があったら強制的に削除する
            ckeditor.on( 'paste', function( evt ) {
                var originalPasteText = evt.data.dataValue;
                var foundMailImges = originalPasteText.match(/<img.*?>/gi);
                _.forEach(foundMailImges, function(imageStr) {
                    // 置換
                    originalPasteText = originalPasteText.split(_.escape($(imageStr)[0].src)).join("\" alt=\"この画像はペーストできません。「画像の挿入」ボタンから画像を登録してください。");
                });
                evt.data.dataValue = originalPasteText;
            });
            // フォームの中身をセット
            if (libraryId > 0) {
                mailForm.setFormData(libraryData);
                ckeditor.setData(mailForm.getItemValue("mailBody"));
                // 承認ステータスによる制御
                var libraryApprovalStatus = libraryData["libraryApprovalStatus"]; // 申請ステータス
                var libraryApprovalFlg = libraryData["libraryApprovalFlg"]; // 承認フラグ
                mailForm.setItemValue("approvalStatusLabel", getLibraryApprovalStatusName(libraryApprovalStatus, libraryData["libraryApprovalFlg"]));
                var lockApproveArea = function() {
                    mailForm.hideItem("referArea");
                    mailForm.hideItem("approveArea");
                    mailForm.lock();
                    try {
                        ckeditor.setReadOnly();
                    } catch(e) {
                        //TODO CKEditor内でエラーになるが処理自体はされているため、握りつぶす。
                        console.log("ckeditor.setReadOnly()", e);
                    }
                };
                var isManager = _ghUser.hasRole(_GH.ROLE.TEMPLATE_CHECK_MANAGER);
                var isMarketing = _ghUser.hasRole(_GH.ROLE.TEMPLATE_CHECK_MARKETING);
                switch(libraryApprovalStatus) {
                    case _GH.S05F030Lib.APPROVAL_STATUS.NONE: // 非申請
                    case _GH.S05F030Lib.APPROVAL_STATUS.DISMISSAL: // 差戻し
                        if (isMarketing  || isManager) {
                            mailForm.hideItem("referArea");
                        } else {
                            mailForm.hideItem("approveArea");
                        }
                        break;
                    case _GH.S05F030Lib.APPROVAL_STATUS.MANAGER: // 申請（上長）
                    case _GH.S05F030Lib.APPROVAL_STATUS.MARKET: // 申請（マーケ）
                        if (isManager || isMarketing) {
                            mailForm.hideItem("referArea");
                        } else {
                            lockApproveArea();
                        }
                        break;
                    case _GH.S05F030Lib.APPROVAL_STATUS.ACCEPT: // 承認
                        if (isManager) {
                            // 上長承認の場合、マーケへの承認はできるようにする。
                            if (libraryApprovalFlg == _GH.S05F030Lib.APPROVE_FLG.MGR) {
                                // マネージャへの依頼は不要
                                mailForm.reloadOptions("approvalSelect",
                                        removePropOptions(_prop.getOptionsWithEmpty(_GH.CODE_DEF.LIB_APPROVE),
                                                [_GH.S05F030Lib.APPROVAL_STATUS.MANAGER]));
                            } else {
                                lockApproveArea();
                            }
                        } else if (isMarketing) {
                            // マーケの場合、承認依頼は不要。
                            mailForm.hideItem("referArea");
                        } else {
                            lockApproveArea();
                        }
                        break;
                }
                // 添付ファイル
                var uploadedFiles = libraryData["attachementURLList"];
                console.log("添付ファイル数", uploadedFiles.length, uploadedFiles);
                if (uploadedFiles.length > 0) {
                    mailForm.showItem("uploadedFiles");
                    _.forEach(uploadedFiles, function(filepath, i){
                        // extract file name from path
                        var p = filepath.match(/\?fileName=(.+)/);
                        if (p) {
                            p = decodeURIComponent(p[1]);
                        } else {
                            p = filepath;
                        }
                        var splitPaths = p.split('/');
                        var filename = splitPaths[(splitPaths.length - 1)];
                        mailForm.addItem("uploadedFiles",
                                {type: "template", label: "添付ファイル" + (i + 1), name: "uploadedFile_" + i, position:"label-left"
                                    ,value: "<a href=\""  + filepath + "\">" + filename + "</a>"
                                    ,userdata: {filename: filename, filepath: filepath}}, i);
                        attachments[filename] = filepath;
                    });
                }
                // 読み取り専用モード
                if (readOnly) {
                    mailForm.lock();
                }
                mailForm.getUploader('attachmentFiles').setURL(_GHAPI.SERVER + _GH.URL('FILE_UPLOAD_URL'));
            } else {
                if (is("Object", mailFormInfo)) {
                    // ライブラリIDがない場合は、メールフォーム情報から構築する。
                    mailForm.setFormData(mailFormInfo);
                    ckeditor.setData(mailFormInfo["body"]);
                }
                // メール件名が空の場合、デフォルトを入れる。
                if (_.isEmpty(mailForm.getItemValue("mailSubject"))) {
                    mailForm.setItemValue("mailSubject", _GH.WINS.MAIL_SUBJECT);
                }
                mailForm.disableItem("referTypeBtn");// 保存するまでは有効にならない
                mailForm.hideItem("approveArea"); // 承認覧は不要
                mailForm.hideItem("uploadedFiles"); // アップロード一覧は不要
            }
        });
        // メールフォームのクリックイベント設定
        mailForm.attachEvent('onButtonClick', function(name/*, command*/){
            switch(name) {
                case "sendTestEmailBtn": // テスト送信
                    mailForm.setItemValue("subject", mailForm.getItemValue("mailSubject"));
                    that.sendTestMail(mailForm, ckeditor, attachments, [_GH.DUMMY_CUST_ID]);
                    break;
                case "saveLibraryBtn": // ライブラリ保存
                    if(mailForm.getItemValue("libraryId") > 0){
                        that.updateTemplate(winName, mailForm, ckeditor, attachments, collection);
                    } else {
                        that.createTemplate(winName, mailForm, ckeditor, attachments, collection);
                    }
                    break;
                case "referTypeBtn": // 承認依頼
                    that.requestJudgeLibrary(winName, mailForm, ckeditor);
                    break;
                case "approveBtn": // 承認
                    var approvalLevel;
                    if (_ghUser.hasRole(_GH.ROLE.TEMPLATE_CHECK_MARKETING)) {
                        approvalLevel = _GH.S05F030Lib.APPROVE_FLG.MKT;
                    } else if (_ghUser.hasRole(_GH.ROLE.TEMPLATE_CHECK_MANAGER)) {
                        approvalLevel = _GH.S05F030Lib.APPROVE_FLG.MGR;
                    }
                    console.log("承認", approvalLevel);
                    if (approvalLevel) {
                        that.changeApprovalLibrary(winName, mailForm, ckeditor, approvalLevel, _GH.S05F030Lib.APPROVAL_STATUS.ACCEPT);
                    }
                    break;
                case "passbackBtn": // 差戻し
                    that.changeApprovalLibrary(winName, mailForm, ckeditor,
                            _GH.S05F030Lib.APPROVE_FLG.DISMISSAL, _GH.S05F030Lib.APPROVAL_STATUS.DISMISSAL);
                    break;
            }
        });
        // フォーカスイベント設定
        mailForm.attachEvent('onFocus', function(name/*, command*/){
            switch(name) {
                case "eventName": // 物件ウィンドウを開く
                    that.doEvent(this.getInput("eventId"), this.getInput("eventName"), mailForm);
                    break;
            }
        });
        that.setFileUploadEvents(mailForm, attachments);
        win.show();
    };

    /**
     * 署名編集ウィンドウ
     * @param {Number} employeeId 社員ID。指定がなければログインユーザのものを使う。
     */
    this.doSignatureWin = function(employeeId) {
        if (_.isEmpty(employeeId)) {
            employeeId = _ghUser.getGhId();
        }
        var winName = "signatureWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 850, 700);
            win.setText("署名の編集");
            win.hide();
        }
        var ckeditor = null; // CKEditor（本文入力欄）
        // 中身の用意
        var mailForm = win.attachForm();
        mailForm.loadStruct(getTemplatePath(_GH.DATA_PATH.MAIL_SIG_FORM_JSON), "json", function() {
            // CKEditorの設定
            CKEDITOR.config.height = "400px";
            ckeditor = CKEDITOR.replace( "signatureBodyTextarea", _GH.WINS.CKEDITOR.LIB);
            // フォームの中身をセット
            var libraryData = getJSONSync("library/signature/" + employeeId);
            mailForm.setFormData(libraryData);
            ckeditor.setData(mailForm.getItemValue("mailBody"));
        });
        // メールフォームのクリックイベント設定
        mailForm.attachEvent('onButtonClick', function (name/*, command*/) {
            switch (name) {
                case "saveSignatureBtn": // 署名保存
                    mailForm.updateValues();
                    if (_.isEmpty(ckeditor.getData())) {
                        dhtmlx.alert({
                            title:"署名本文チェックエラー",
                            type:"alert-error",
                            text:"署名の本文が入力されていません。<BR>本文は必ず入力してください。"
                        });
                    } else {
                        var param = mailForm.getFormData();
                        param["mailKbn"] = _GH.S05F030Lib.MAILKBN.SIGNATURE;
                        param["employeeId"] = employeeId;
                        param["mailBody"] = ckeditor.getData();
                        var uri = (mailForm.getItemValue("libraryId") > 0)? "library/update": "library/create";
                        syncPutJSON(uri, param);
                        win.close();
                    }
                    break;
            }
        });
        win.show();
    };

    /**
     * メール内容確認ウィンドウ
     * @param {Number} mailSeqId メールシーケンスID
     */
    this.doMailReferWin = function(mailSeqId) {
        console.log("メール内容確認ウィンドウ表示", mailSeqId, CKEDITOR.config.plugins);
        if (mailSeqId <= 0) {
            return;
        }
        var winName = "mailReferWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 815, 700);
            win.setText("メール内容確認");
            win.hide();
        }
        var attachments = {}; // 添付ファイルマップ。キーは実ファイル名、バリューはサーバ上のファイル名
        var ckeditor = null; // CKEditor（本文入力欄）
        // 中身の用意
        var mailForm = win.attachForm();
        mailForm.loadStruct(getTemplatePath(_GH.DATA_PATH.MAIL_REF_JSON), "json", function() {
            // CKEditorの設定
            CKEDITOR.config.height = "400px";
            ckeditor = CKEDITOR.replace( "mailReferTextarea", _GH.WINS.CKEDITOR.REF);
            // フォームの中身をセット
            var libraryData = getJSONSync("sendmail/personalMail/" + mailSeqId);
            mailForm.setFormData(libraryData);
            ckeditor.setData(mailForm.getItemValue("mailBody"));
            // 添付ファイル
            var uploadedFiles = libraryData["attachementURLList"];
            console.log("添付ファイル数", uploadedFiles.length, uploadedFiles);
            if (uploadedFiles.length > 0) {
                _.forEach(uploadedFiles, function(filepath, i){
                    var splitPaths = filepath.split("/");
                    var filename = splitPaths[(splitPaths.length - 1)];
                    mailForm.addItem("uploadedFiles",
                            {type: "template", label: "添付ファイル" + (i + 1), name: "uploadedFile_" + i, position:"label-left"
                                ,value: "<a href=\"/"  + filepath + "\">" + filename + "</a>"}, i);
                    attachments[filename] = filepath;
                });
            } else {
                // 添付ファイルなし
                mailForm.hideItem("uploadedFiles");
            }
            mailForm.lock();
        });
        win.show();
    };

    /**
     * 案内・来場予約作成・編集ウィンドウ
     * @param {Object} personalInfo 顧客情報
     * @param {Number} scheduleId スケジュールID（編集時に必要）
     * @param model
     */
    this.doScheduleWindow = function (personalInfo, scheduleId, model, afterFunction) {
        var winName = "scheduleWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 625, 660);
            win.setText("スケジュール登録・編集ウィンドウ");
            win.hide();
        }
        // 中身の用意
        var scheduleForm = win.attachForm();
        scheduleForm.loadStruct(getTemplatePath(_GH.DATA_PATH.SCHD_LIGHT_FORM_JSON), "json", function() {
            // リストの設定
            scheduleForm.reloadOptions("recruitStatus", _prop.getOptionsWithEmpty(_GH.CODE_DEF.STATUS));
            scheduleForm.reloadOptions("deleteReason", _prop.getOptionsWithEmpty(_GH.CODE_DEF.SCHED_DEL_REASON));
            if (scheduleId > 0) {
                // 既存データを取得し、反映する。
                var schedData = getJSONSync("schedules/id/" + scheduleId);
                scheduleForm.setFormData(schedData);
            } else {
                // スケジュールIDがない場合は、新規作成
                scheduleForm.setItemValue("personalId", personalInfo["personalId"]); // 顧客ID
                scheduleForm.setItemValue("entryId", personalInfo["entryId"]); // 顧客ID
                scheduleForm.setItemValue("personalFullName", personalInfo["lastName"] + " " + personalInfo["firstName"]); // 顧客名
                scheduleForm.setItemValue("organizer", _ghUser.getGhId()); // 案内者ID（ログインユーザ）
                scheduleForm.setItemValue("organizerFullName", _ghUser.getName()); // 案内者名（ログインユーザ）
                // 開始日を現在時刻の３０分切り上げ、終了日を＋2時間とする。
                var currentDate = new Date();
                if (currentDate.getMinutes() < 30) {
                    currentDate.setMinutes(30);
                } else {
                    currentDate.setHours(currentDate.getHours() + _GH.FORMAT.SCHD_INTERVAL);
                    currentDate.setMinutes(0);
                }
                scheduleForm.setItemValue("scheduleTimeFrom", currentDate);
                currentDate.setHours(currentDate.getHours() + _GH.FORMAT.SCHD_INTERVAL);
                scheduleForm.setItemValue("scheduleTimeTo", currentDate);
                scheduleForm.hideItem("deleteReasonArea");
            }
        });
        // 保存ボタンのクリックイベント設定
        scheduleForm.attachEvent('onButtonClick', function (name/*, command*/) {
            scheduleForm.updateValues();
            switch (name) {
                case "saveScheduleBtn": // 保存ボタン
                    setActDataOfSchedule(scheduleForm);
                    if (scheduleForm.validate()) {
                        // 過去日付登録チェック
                        if (!isDateInRange(scheduleForm.getItemValue("scheduleTimeFrom"), 7)) {
                            if (!confirm(_MSG.S07F020.M_001)) return;
                        }
                        // 戸建：案内種別チェック "scheduleStatus"
                        if (!scheduleForm.getItemValue("scheduleStatus")) {
                            dhtmlx.message({
                                type:"error",
                                text: "案内種類（来社、案内、待ち合わせなど）は必須入力です。"
                            });
                            return;
                        }
                        // 削除確認
                        if (!_.isEmpty(scheduleForm.getItemValue("deleteReason"))) {
                            if (!confirm(_MSG.S07F020.M_002)) return;
                        }
                        var recruitStatusName = _prop.getPropName(_GH.CODE_DEF.STATUS, scheduleForm.getItemValue("recruitStatus"));
                        scheduleForm.setItemValue("scheduleTitle", "【採用】" + recruitStatusName + ":" + scheduleForm.getItemValue("personalFullName"));
                        var uri = (scheduleId > 0)? "schedules/update": "schedules/regist";
                        saveForm(scheduleForm, uri, afterFunction);
                        win.close();
                        if (scheduleId > 0){
                            if(model){model.update();}
                        } else {
                            if(model){model.append();}
                        }
                    }
                    break;
                case "showScheduleBtn": // スケジュール表示ボタン
                    window.open("schedule.html");
                    break;
            }
        });
        // フォーカスイベント設定
        scheduleForm.attachEvent('onFocus', function (name/*, command*/) {
            switch (name) {
                // 物件ウィンドウを表示する。
                case "organizerFullName":
                    that.doTantoWin(this.getInput("organizer"), this.getInput("organizerFullName"));
                    break;
                case "attendeeFullName1":
                    that.doTantoWin(this.getInput("attendee1"), this.getInput("attendeeFullName1"));
                    break;
                case "attendeeFullName2":
                    that.doTantoWin(this.getInput("attendee2"), this.getInput("attendeeFullName2"));
                    break;
                case "attendeeFullName3":
                    that.doTantoWin(this.getInput("attendee3"), this.getInput("attendeeFullName3"));
                    break;
            }
        });
        // 入力欄の変更イベント
        scheduleForm.attachEvent("onChange", function (name/*, value, form*/) {
            var fromTime;
            switch (name) {
                case "scheduleTimeFrom": // 案内日時（開始）
                    // 案内日時（終了）に２時間後の値を入れる。
                    fromTime = scheduleForm.getItemValue("scheduleTimeFrom");
                    fromTime.setHours(fromTime.getHours() + _GH.FORMAT.SCHD_INTERVAL);
                    scheduleForm.setItemValue("scheduleTimeTo", fromTime);
                    break;
                case "scheduleTimeTo": // 案内日時（終了）
                    // 案内日時（開始）に終了日と同じ年月日を入れる。
                    fromTime = scheduleForm.getItemValue("scheduleTimeFrom");
                    var toTime = scheduleForm.getItemValue("scheduleTimeTo");
                    fromTime.setFullYear(toTime.getFullYear());
                    fromTime.setMonth(toTime.getMonth());
                    fromTime.setDate(toTime.getDate());
                    scheduleForm.setItemValue("scheduleTimeFrom", fromTime);
                    break;
            }
        });
        // バリデートエラー時の処理
        scheduleForm.attachEvent("onValidateError", function (name/*, value, result*/) {
            dhtmlx.message({
                type:"error",
                text: scheduleForm.getItemLabel(name) + "は必ず入力してください。"
            });
        });
        win.show();
    };

    /**
     * 顧客詳細ウィンドウ
     *
     * @param {Number} personalId 顧客ID
     * @param {Boolean} readonly 読み込み専用フラグ。trueの場合は読み込み専用
     * @param {object} personalModel 顧客情報更新通知用オブジェクト。画面の更新を行う
     * @param {object} reportModel 指示報告更新通知用オブジェクト。画面の更新を行う
     * @param {object} scheduleModel 案内予約更新通知用オブジェクト。画面の更新を行う
     */
    this.doPersonalWindow = function(personalId, readonly, personalModel, reportModel, scheduleModel) {
        // 顧客情報フォーム
        var winName = "custWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow("custWin", 20, 30, 1124, 750);
            win.setText("顧客詳細ウィンドウ");
            win.hide();
        }
        var winLayout = win.attachLayout("1C");
        winLayout.cells("a").hideHeader();
        // 中身の用意
        var custForm = winLayout.cells("a").attachForm();
        custForm.loadStruct(getTemplatePath(_GH.DATA_PATH.CUSTWIN_JSON), "json", function() {
            // 性別のオプション設定
            custForm.reloadOptions("gender", _prop.getOptionsWithEmpty(_GH.CODE_DEF.GENDER));
            custForm.reloadOptions("subGender", _prop.getOptionsWithEmpty(_GH.CODE_DEF.GENDER));
            // メール許可のオプション設定
            custForm.reloadOptions("emailstatus", _prop.getOptions(_GH.CODE_DEF.EMAIL_STATUS));
            // 都道府県のオプション設定
            custForm.reloadOptions("prefecture", _prop.getOptionsWithEmpty(_GH.CODE_DEF.PREFECTURE));
            // 間取りのオプション設定
            custForm.reloadOptions("abodenowfloorplan", _prop.getOptionsWithEmpty(_GH.CODE_DEF.FLOOR_PLAN));
            // 職業種
            custForm.reloadOptions("jobtype", _prop.getOptionsWithEmpty(_GH.CODE_DEF.JOB_TYPE));
            custForm.reloadOptions("subJobtype", _prop.getOptionsWithEmpty(_GH.CODE_DEF.JOB_TYPE));
            // 戸建価格MIN,MAX, 土地価格MIN,MAX
            custForm.reloadOptions("searchhousemin", _prop.getOptionsWithEmpty(_GH.CODE_DEF.HOPE_BUGET));
            custForm.reloadOptions("searchhousemax", _prop.getOptionsWithEmpty(_GH.CODE_DEF.HOPE_BUGET));
            custForm.reloadOptions("searchlandmin", _prop.getOptionsWithEmpty(_GH.CODE_DEF.HOPE_BUGET));
            custForm.reloadOptions("searchlandmax", _prop.getOptionsWithEmpty(_GH.CODE_DEF.HOPE_BUGET));
            // 現況住まい種類
            custForm.reloadOptions("abodenowbuildingtype", _prop.getOptionsWithEmpty(_GH.CODE_DEF.BUILDING_TYPE));
            // 買替有無
            custForm.reloadOptions("abodenowreplacement", _prop.getOptionsWithEmpty(_GH.CODE_DEF.RE_BUY));
            // 購入目的・検討理由
            custForm.reloadOptions("purchasepurpose", _prop.getOptionsWithEmpty(_GH.CODE_DEF.BUY_PURPOSE));
            custForm.reloadOptions("reason", _prop.getOptionsWithEmpty(_GH.CODE_DEF.BUY_REASON));
            // WEB会員状態
            custForm.reloadOptions("webMemberSTATUS", _prop.getOptionsWithEmpty(_GH.CODE_DEF.WEBMEMBER_STATUS));
            // 追わない理由
            custForm.reloadOptions("personalstatusreason", _prop.getOptionsWithEmpty(_GH.CODE_DEF.STATUS_REASON));
            // 電話ステータス
            custForm.reloadOptions("telstatus", _prop.getOptions(_GH.CODE_DEF.TEL_STATUS));
            // 自分フラグ
            custForm.reloadOptions("empflg", _prop.getOptions(_GH.CODE_DEF.FLG));
            custForm.reloadOptions("managerflg", _prop.getOptions(_GH.CODE_DEF.FLG));
            custForm.reloadOptions("centermanagerflg", _prop.getOptions(_GH.CODE_DEF.FLG));
            // プロテクト
            custForm.reloadOptions("protect", _prop.getOptions(_GH.CODE_DEF.PROTECT));
            // ランクのオプション設定
            custForm.reloadOptions("emplevel", _prop.getOptions(_GH.CODE_DEF.RANK));
            custForm.reloadOptions("managerlevel", _prop.getOptions(_GH.CODE_DEF.RANK));
            // 設計変更
            // DM対象

            // 顧客検索
            var custData = getJSONSync("personal/id/" + personalId);
            // 間取りの設定(戸建はセレクト、MSはチェックボックス）
            if (_ghUser.isNon()) {
                custForm.reloadOptions("floorplan", _prop.getOptionsWithEmpty(_GH.CODE_DEF.FLOOR_PLAN));
            } else {
                setBitOperationValueAtCheckbox(custData, "floorplan", _GH.S03F010CM.FLOOR_CHK);
            }
            // 顧客ステータスのオプション設定
            var custStatus = custData["personalstatus"];
            if (custStatus == _GH.S03F010CM.CUST_CLAIM) {
                custForm.showItem("displayClaimStatus");
                custForm.reloadOptions("personalstatus", _prop.getOptions(_GH.CODE_DEF.STATUS));
            } else {
                custForm.reloadOptions("personalstatus", removePropOptions(_prop.getOptions(_GH.CODE_DEF.STATUS), [_GH.S03F010CM.CUST_CLAIM]));
                // クレーム以外のステータス
                if (custData["contractFlg"] == 1 || custStatus == _GH.S03F010CM.CUST_CONTRACT) {
                    // 契約の場合
                    custForm.showItem("displayOtherStatusBlue");
                    custForm.setItemValue("displayOtherStatusBlue", "契約済");
                } else if (custStatus != _GH.S03F010CM.CUST_GO) {
                    // 追客以外
                    custForm.showItem("displayOtherStatusGreen");
                    custForm.setItemValue("displayOtherStatusGreen", _prop.getPropName(_GH.CODE_DEF.STATUS, custStatus));
                }
            }

            // 値が無い場合の初期値の設定
            var mailSelect = custForm.getSelect("emailstatus");
            mailSelect.value = _prop.getValueFromPropName(_GH.CODE_DEF.EMAIL_STATUS, "一斉・個別OK");
            var emplevelSelect = custForm.getSelect("emplevel");
            emplevelSelect.value = _prop.getValueFromPropName(_GH.CODE_DEF.RANK, "★なし");

            custForm.setFormData(custData);
            // 権限による自分フラグの表示設定
            if (!_ghUser.isManager() && !_ghUser.isShien()) {
                // 課員
                custForm.disableItem("managerflg");
                custForm.disableItem("centermanagerflg");
                custForm.hideItem("managerlevel");
                custForm.hideItem("leadersmemo");
            }
            // プロテクト権限の有無
            if (_ghUser.hasRole(_GH.ROLE.PROTECT_PERSONAL_CHANGE) && _.parseInt(custForm.getItemValue("employeeId")) !== 0) {
                custForm.enableItem("protect");
            } else {
                custForm.disableItem("protect");
            }
            // 担当者の有無(自分フラグとプロテクトの利用可否が決まる）
            if (custData["employeeId"] == 0){
                custForm.disableItem("empflg");
                custForm.disableItem("managerflg");
                custForm.disableItem("centermanagerflg");
            } else {
                custForm.enableItem("empflg");
                custForm.enableItem("managerflg");
                custForm.enableItem("centermanagerflg");
            }
            // ツールバーの設定
            var custToolbar = winLayout.attachToolbar();
            that.setPersonalWinToolbar(win, custToolbar, custForm, personalModel, reportModel, scheduleModel);
        });
        // 入力値の変更チェック
        var formChange = false;
        custForm.attachEvent("onBeforeChange", function (name, old_value, new_value){
            formChange = (old_value !== new_value);
            if (name == "personalstatus") {
                // ステータスを契約にした場合、契約済みフラグ/設計変更フラグを立てる。
                if (new_value == _GH.S03F010CM.CUST_CONTRACT) {
                    if (confirm(_MSG.S03F010.M_001)) {
                        custForm.setItemValue("makeContractActFlg", 1);
                        custForm.checkItem("contractFlg");
                        custForm.checkItem("designupdate");
                    } else {
                        custForm.setItemValue("makeContractActFlg", 0);
                        return false;
                    }
                }
            }
            return true;
        });
        // ボタンクリックイベント
        custForm.attachEvent('onButtonClick', function (name/*, command*/) {
            switch(name) {
                case "addressDialogLinkBtn": // 郵便番号検索ウィンドウを表示
                    _ghWins.doPostWindow(this.getInput("zipcode"), this.getSelect("prefecture"), this.getInput("address"), this.getInput("otherAddress"));
                    break;
            }
        });
        // フォーカスイベント
        custForm.attachEvent("onFocus", function(name){
            // 希望地域１，２
            if (name == "searchword1") {
                that.doKiboAreaWin(this.getInput("searchword1"), false, custForm);
            } else if (name == "searchword2") {
                that.doKiboAreaWin(this.getInput("searchword2"), false, custForm);
            } else if (name == "companyRailwayLineName" || name == "companyStationName") {
                // 主・勤務先駅沿線
                _ghWins.doEnsenWin(this.getInput("companyrailwayline"), this.getInput("companyRailwayLineName"),
                        this.getInput("companystation"), this.getInput("companyStationName"),
                        null, null, custForm);
            } else if (name == "subCompanyRailwayLineName" || name == "subCompanyStationName") {
                // 副・勤務先駅沿線
                _ghWins.doEnsenWin(this.getInput("subCompanyrailwayline"), this.getInput("subCompanyRailwayLineName"),
                        this.getInput("subCompanystation"), this.getInput("subCompanyStationName"),
                        null, null, custForm);
            } else if (name == "searchRailwayLineName1" || name == "searchStationFromName1" || name == "searchStationToName1") {
                // 希望沿線1
                _ghWins.doEnsenWin(this.getInput("searchrailwayline1"), this.getInput("searchRailwayLineName1"),
                        this.getInput("searchstationfrom1"), this.getInput("searchStationFromName1"),
                        this.getInput("searchstationto1"), this.getInput("searchStationToName1"), custForm);
            } else if (name == "searchRailwayLineName2" || name == "searchStationFromName2" || name == "searchstationtoName2") {
                // 希望沿線2
                _ghWins.doEnsenWin(this.getInput("searchrailwayline2"), this.getInput("searchRailwayLineName2"),
                        this.getInput("searchstationfrom2"), this.getInput("searchStationFromName2"),
                        this.getInput("searchstationto2"), this.getInput("searchStationToName2"), custForm);
            }
        });
        custForm.attachEvent("onChange", function (name, value){
            switch (name) {
                case "emplevel": // ランク（担当者）
                    // 選択したランクに合わせて、メールステータスをおすすめのものに変える。
                    setRecommendMailStatusAlongWithRank(custForm, value);
                    break;
                case "birthday": // 誕生日から年齢の設定
                    this.setItemValue("age", getAge(this.getItemValue(name)));
                    break;
                case "subBirthday": // 誕生日から年齢の設定
                    this.setItemValue("subAge", getAge(this.getItemValue(name)));
                    break;
                case "address": // 住所から郵便番号を逆引き
                case "otherAddress": // 以下住所から郵便番号を逆引き
                    setZipCodeFromAddress(this);
                    break;
                case "lastNameKana":
                case "firstNameKana":
                case "subLastNameKana":
                case "subFirstNameKana":
                    var toKatakana = hiraganaCodeToKatakanaCode(value);
                    toKatakana = toZenkaku(toKatakana);
                    custForm.setItemValue(name, toKatakana);
                    break;
            }

            // 数値は全て半角にする
            if(custForm.getItemType(name) === "input"){
                var inputed = custForm.getItemValue(name);
                inputed = toHankaku(inputed);
                // 一部、ハイフンが入る物を半角ハイフンに変換する
                var transHyphen = [
                    "tel1", "tel2", "tel3", "tel4", "fax",
                    "email1", "email2", "email3", "email4",
                    "zipcode", "otherAddress",
                    "companyaddress", "subcompanyaddress"
                ];
                if(_.include(transHyphen, name)){
                    inputed = toHankakuHyphen(inputed);
                }
                custForm.setItemValue(name, inputed);
            }
        });

        win.button("close").attachEvent("onClick", function(){
            if (formChange) {
                if (confirm("顧客情報が変更されています。変更を保存しますか？")) {
                    personalModel.update(custForm, "custWin");
                }
            }
            win.close();
        });
        win.show();
    };

    /**
     * 顧客詳細ウィンドウのツールバーの設定を行います。
     */
    this.setPersonalWinToolbar = function(win, custToolbar, custForm, personalModel, reportModel, scheduleModel) {
        custToolbar.setIconsPath(_GH.PATH_TYPE.DHX_ICONS);
        custToolbar.setIconSize(_GH.ICON_SIZE);
        custToolbar.loadStruct(getTemplatePath(_GH.DATA_PATH.CUSTWIN_MENU_XML), function(){
            // MS、かつ、プロテクト変更権限
            if (_ghUser.isExp()) {
                if (_ghUser.hasRole(_GH.ROLE.PROTECT_PERSONAL_CHANGE) && _.parseInt(custForm.getItemValue("employeeId")) !== 0) {
                    custToolbar.enableItem("detailMenuProtect");
                } else {
                    custToolbar.disableItem("detailMenuProtect");
                }
            }
            // 担当者の有無(自分フラグ・プロテクト、担当への転送の利用可否が決まる）
            if (_.parseInt(custForm.getItemValue("employeeId")) === 0){
                custToolbar.disableItem("detailMenuFlag");
                custToolbar.disableItem("detailMenuTransfer");
            } else {
                custToolbar.enableItem("detailMenuFlag");
                custToolbar.enableItem("detailMenuTransfer");
            }

            // 電話、メールの各ステータスごとに不許可の場合にはデータを隠す
            var telNG = _prop.getValueFromPropName(_GH.CODE_DEF.TEL_STATUS, "電話不可");
            if(custForm.getItemValue("telstatus") == telNG){
                custForm.hideItem("tel1");
                custForm.hideItem("telsupple1");
                custForm.hideItem("tel2");
                custForm.hideItem("telsupple2");
                custForm.hideItem("tel3");
                custForm.hideItem("telsupple3");
                custForm.hideItem("tel4");
                custForm.hideItem("telsupple4");
                custForm.hideItem("fax");
                custToolbar.disableItem("detailMenuExcel");
            }
            var emailNG = _prop.getValueFromPropName(_GH.CODE_DEF.EMAIL_STATUS, "ダメ全部NG");
            if(custForm.getItemValue("emailstatus") == emailNG){
                custForm.hideItem("email1");
                custForm.hideItem("emailsupple1");
                custForm.hideItem("emailsendableflg1");
                custForm.hideItem("email2");
                custForm.hideItem("emailsupple2");
                custForm.hideItem("emailsendableflg2");
                custForm.hideItem("email3");
                custForm.hideItem("emailsupple3");
                custForm.hideItem("emailsendableflg3");
                custForm.hideItem("email4");
                custForm.hideItem("emailsupple4");
                custForm.hideItem("emailsendableflg4");
                custToolbar.disableItem("detailMenuExcel");
            }

            // 電話とメール両方ダメなら、全部隠す
            if(custForm.getItemValue("emailstatus") == emailNG && custForm.getItemValue("telstatus") == telNG){
                custForm.hideItem("personalInfo1");
            }

            // クレームの場合
            if (_.parseInt(custForm.getItemValue("personalstatus")) === _GH.S03F010CM.CUST_CLAIM) {
                if(_ghUser.isExp()) custToolbar.disableItem("detailMenuProtect");
                custToolbar.disableItem("detailMenuFlag");
                custToolbar.disableItem("detailMenuScheduler");
                custToolbar.disableItem("detailMenuTransfer");
                custToolbar.disableItem("detailMenuClaim");
                custToolbar.disableItem("detailMenuExcel");
                custForm.disableItem("personalstatus");
                custForm.hideItem("personalInfo1");
                custForm.hideItem("personalInfo2");
                custForm.hideItem("personalAddress");
                custForm.hideItem("company");
                custForm.hideItem("subCompany");
                custForm.showItem("displayClaimStatus");
            }
            this.addSpacer("sepX"); // スペイサーを入れて、それより右のメニューを右寄せにする。
        });
        custToolbar.attachEvent("onClick", function(id) {
            var custmerId = custForm.getItemValue("personalId");
            var personalName = custForm.getItemValue("lastName") + " " + custForm.getItemValue("firstName");
            var employeeId = custForm.getItemValue("employeeId");
            switch (id) {
                case "detailMenuFlag": // 自分フラグ
                    that.changePersonalFlags(custForm, true);
                    dhtmlx.message("フラグを更新しました。");
                    break;
                case "detailMenuProtect": // プロテクト
                    that.changePersonalFlags(custForm, false, true);
                    dhtmlx.message("プロテクトを更新しました。");
                    break;
                case "detailMenuScheduler": // 案内・来場予約
                    that.doScheduleWindow(custForm.getFormData(), null, scheduleModel);
                    break;
                case "detailMenuDirect": // 指示登録
                    that.doReportWindow(custForm.getFormData(), reportModel);
                    break;
                case "detailMenuTransfer": // 担当へ転送
                    var lastName = custForm.getItemValue("lastName");
                    var firstName = custForm.getItemValue("firstName");
                    var mailBody = getPersonalInfoAsText(custForm);
                     that.doTransferMailWindow(lastName, firstName, employeeId, mailBody);
                    break;
                case "detailMenuExcel": // お客様カード（Excel）出力
                    var query = "authorizedToken=" + _ghUser.getAuthToken() + "&personalId=" + custmerId;
                    window.open(_GHAPI.SERVER + "personalCard/download?" + query, "_blank");
                    break;
                case "detailMenuCharge": // 担当変更
                    var custEmpMap = {};
                    custEmpMap[custmerId] = custForm.getItemValue("employeeId");
                     that.doChargeWindow(custEmpMap, personalModel, _ghWins.ghWins.window("custWin"));
                    break;
                case "detailMenuStatus": // 状態
                    custForm.setItemFocus("emplevel");
                    break;
                case "detailMenuClaim": // クレーム
                     that.doClaimWindow(custmerId, personalName, employeeId, personalModel, _ghWins.ghWins.window("custWin"));
                    break;
                case "detailMenuContract": // 契約関連
                    dhtmlx.message("契約関連");
                    break;
                case "detailMenuSave": // 保存
                    personalModel.update(custForm, "custWin");
                    break;
            }
        });
    };

    /**
     * クレーム登録ウィンドウ
     * @param personalId Personal ID.
     * @param personalName
     * @param employeeId
     * @param personalModel
     * @param parentWindow
     */
    this.doClaimWindow = function(personalId, personalName, employeeId, personalModel, parentWindow) {
        var win = this.ghWins.window("claimWin");
        if (win == null) {
            win = this.ghWins.createWindow("claimWin", 20, 30, 665, 525);
            win.setText("クレーム登録ウィンドウ：" + personalName + "様");
            win.hide();
        }
        // 中身の用意
        var claimForm = win.attachForm();
        claimForm.loadStruct(getTemplatePath(_GH.DATA_PATH.CLAIM_JSON), "json", function() {
            // 表示設定
            claimForm.setItemValue("personalId", personalId); // 顧客ID
            claimForm.setItemValue("employeeId", employeeId); // 顧客の担当者
            claimForm.setItemValue("operatorId", _ghUser.getGhId()); // 登録者
        });
        // ボタンのクリックイベント設定
        claimForm.attachEvent('onButtonClick', function (name/*, command*/) {
            switch (name) {
                case "claimSaveBtn": // 保存ボタン
                    claimForm.updateValues();
                    if (claimForm.validate()) {
                        var after = function() {
                            // クレームの対応履歴登録
                            registActHitoryByPost(personalId, _ghUser.getGhId(), _GH.S03F090ACT.ACT.CLAIM, _GH.S03F090ACT.ACT_D.CLAIM,
                                    claimForm.getItemValue("claimcause") + " / " + claimForm.getItemValue("claimmethod") + " / " + claimForm.getItemValue("claimresult"));
                            // クレーム対象者にメール
                            var sendEmpIds = [employeeId, _ghUser.getGhId(), claimForm.getItemValue("claimtargetemployeeId"), claimForm.getItemValue("subclaimtargetemployeeId"), claimForm.getItemValue("claimmanagerId")];
                            sendMessageToEmployee(sendEmpIds, "[GH] クレーム登録：" + personalName + "様",
                                    "<p>GHで【" + personalName + "】様がクレーム登録されました。</P><br>"
                                    + getGHPersonalLink(personalId)+ "<br><br>"
                                    + "【クレーム対象者１】：" + claimForm.getItemValue("claimTargetFullName") + "<br>"
                                    + "【クレーム対象者２】：" + claimForm.getItemValue("subClaimTargetFullName") + "<br>"
                                    + "【クレーム責任者】　：" + claimForm.getItemValue("claimManagerFullName") + "<br><br>"
                                    + "【原因　　】：" + claimForm.getItemValue("claimcause") + "<br>"
                                    + "【対処方法】：" + claimForm.getItemValue("claimmethod") + "<br>"
                                    + "【対処結果】：" + claimForm.getItemValue("claimresult") + "<br>");
                            win.close();
                        };
                        // クレームの保存
                        saveForm(claimForm, "claim/regist", after);
                        if(parentWindow){
                            parentWindow.close();
                        }
                        // 顧客データに変更を通知する
                        if(personalModel){
                            personalModel.toClaim(personalId);
                        }
                    }
                    break;
                default: // クレーム原因簡易入力ボタン
                    var claimPhrase = _GH.S03F040CLAIM[name];
                    claimForm.setItemValue("claimcause", claimPhrase["claimcause"]);
                    claimForm.setItemValue("claimmethod", claimPhrase["claimmethod"]);
                    claimForm.setItemValue("claimresult", claimPhrase["claimresult"]);
                    break;
            }
        });
        // フォーカスイベント
        claimForm.attachEvent("onFocus", function(name){
            switch(name) {
                case "eventId": // 物件番号、物件名
                case "eventName":
                    that.doEvent(this.getInput("eventId"), this.getInput("eventName"), claimForm);
                    break;
                case "claimTargetFullName": // クレーム対象者
                    that.doTantoWin(this.getInput("claimtargetemployeeId"), this.getInput("claimTargetFullName"));
                    break;
                case "subClaimTargetFullName": // クレーム対象者2
                    that.doTantoWin(this.getInput("subclaimtargetemployeeId"), this.getInput("subClaimTargetFullName"));
                    break;
                case "claimManagerFullName": // 対応責任者
                    that.doTantoWin(this.getInput("claimmanagerId"), this.getInput("claimManagerFullName"));
                    break;
            }
        });
        // バリデートエラー時の処理
        claimForm.attachEvent("onValidateError", function (name/*, value, result*/) {
            dhtmlx.message({
                type:"error",
                text: claimForm.getItemLabel(name) + "は必須入力です。"
            });
        });
        win.show();
    };

    /**
     * クレーム編集ウィンドウ
     * @param personalId
     */
    this.doClaimEditWindow = function(personalId) {
        if (!personalId) {
            alert("顧客IDが指定されていないため、クレーム表示できません。");
            return;
        }
        var claimList = getJSONSync("claim/id/" + personalId);
        if (_.isEmpty(claimList)) {
            alert("この顧客にクレームは登録されていません。");
            return;
        }

        var winName = "claimEditWin";
        var win = this.ghWins.createWindow(winName, 20, 30, 665, 525);
        var claimData = claimList[0];
        // 中身の用意
        var claimForm = win.attachForm();
        claimForm.loadStruct(getTemplatePath(_GH.DATA_PATH.CLAIM_JSON), "json", function() {
            claimForm.setItemValue("operatorId", _ghUser.getGhId()); // 登録者
            claimForm.enableLiveValidation(true);
            claimForm.setFormData(claimData);
            win.setText("クレーム編集ウィンドウ");
        });
        // ボタンのクリックイベント設定
        claimForm.attachEvent('onButtonClick', function (name/*, command*/) {
            switch (name) {
                case "claimSaveBtn": // 保存ボタン
                    claimForm.updateValues();
                    if (claimForm.validate()) {
                        var after = function() {
                            win.close();
                        };
                        saveForm(claimForm, "claim/update", after);
                    }
                    break;
                default: // クレーム原因簡易入力ボタン
                    var claimPhrase = _GH.S03F040CLAIM[name];
                    claimForm.setItemValue("claimcause", claimPhrase["claimcause"]);
                    claimForm.setItemValue("claimmethod", claimPhrase["claimmethod"]);
                    claimForm.setItemValue("claimresult", claimPhrase["claimresult"]);
                    break;
            }
        });
        // フォーカスイベント
        claimForm.attachEvent("onFocus", function(name){
            switch(name) {
                case "eventId": // 物件番号、物件名
                case "eventName":
                    that.doEvent(this.getInput("eventId"), this.getInput("eventName"), claimForm);
                    break;
                case "claimTargetFullName": // クレーム対象者
                    that.doTantoWin(this.getInput("claimtargetemployeeId"), this.getInput("claimTargetFullName"));
                    break;
                case "subClaimTargetFullName": // クレーム対象者2
                    that.doTantoWin(this.getInput("subclaimtargetemployeeId"), this.getInput("subClaimTargetFullName"));
                    break;
                case "claimManagerFullName": // 対応責任者
                    that.doTantoWin(this.getInput("claimmanagerId"), this.getInput("claimManagerFullName"));
                    break;
            }
        });
        // バリデートエラー時の処理
        claimForm.attachEvent("onValidateError", function (name/*, value, result*/) {
            dhtmlx.message({
                type:"error",
                text: claimForm.getItemLabel(name) + "は必須入力です。"
            });
        });
        win.show();
    };

    /**
     * 担当変更ウィンドウ
     * @param {Array}  personalEmployeeMap 担当変更対象顧客IDの配列
     * @param {Object} personalModel      顧客変更管理オブジェクト。変更処理および変更の通知を行う
     * @param {parentWindow} parentWindow 親ウインドウ。処理成功時に親ウインドウを閉じる
     */
    this.doChargeWindow = function(personalEmployeeMap, personalModel, parentWindow, callbackFunc) {
        var winName = "chargeWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 515, 530);
            win.setText("担当変更");
            win.hide();
        }
        var personalIds = _.keys(personalEmployeeMap);
        var employeeIds = _.values(personalEmployeeMap);
        console.log("担当変更", personalIds, employeeIds);

        var chargeTree = null;
        // 中身の用意
        var chargeForm = win.attachForm();
        chargeForm.loadStruct(getTemplatePath(_GH.DATA_PATH.CHARGE_FORM_JSON), "json", function() {
            // 組織ツリーの設定
            chargeTree = new dhtmlXTreeObject(chargeForm.getContainer("chargeTree"),"100%","100%",0);
            chargeTree.setImagePath(_GH.PATH_TYPE.TREE_IMGS);
            if (_ghUser.isNon()) {
                chargeTree.enableRadioButtons(true);
            } else {
                chargeTree.enableCheckBoxes(true);
                chargeTree.enableSmartCheckboxes(true);
            }
            //"entry/divisionList/" + _ghUser.getLoginType() + "/3?quitFlg=0"
        	chargeTree.parse(getJSONSync(_GHAPI.URL.TANTO + _ghUser.getLoginType() + "/3?quitFlg=0"), function(){
                // ログインユーザを選択させる
                chargeTree.findItem(_ghUser.getName());
            }, "json");
            chargeTree.openAllItems(1);
        });
        // 担当変更のクリックイベント設定
        chargeForm.attachEvent('onButtonClick', function (name/*, command*/) {
            switch (name) {
                case "sameDepartmentBtn": // 事業部内の処理の実行
                    if (!chargeTree.getAllChecked()) {
                        dhtmlx.alert({
                            title:"変更後の担当者未選択",
                            type:"alert-error",
                            text:"変更後の担当者が選ばれていません。<BR>担当者を選択してください。"
                        });
                        return;
                    }
                    var treeIdxs = chargeTree.getAllChecked().split(",");
                    if (personalIds.length < treeIdxs.length) {
                        dhtmlx.alert({
                            title:"変更対象顧客数の不足",
                            type:"alert-error",
                            text:"変更対象顧客数よりも移動先の担当者が多いです。<BR>担当者を顧客数(" + personalIds.length + "人)以下にしてください。"
                        });
                        return;
                    }
                    // ボタンをロックする。
                    chargeForm.lock();
                    // 顧客をランダムに配分する。
                    personalIds = _.shuffle(personalIds); // 顧客配列をシャッフル（ランダム化）
                    var unitLen = Math.ceil(personalIds.length / treeIdxs.length); // 一人あたりの顧客人数
                    var currentPos = 0; // 顧客配列の現在地
                    // 担当者１人ずつ処理していく
                    _.forEach(treeIdxs, function(treeIdx, i) {
                        console.log(treeIdx, i, chargeTree.getUserData(treeIdx, "employeeId"), chargeTree.getUserData(treeIdx, "divisionId"));
                        var targetDivisionId = chargeTree.getUserData(treeIdx, "divisionId");
                        var targetEmployeeId = chargeTree.getUserData(treeIdx, "employeeId");
                        var targetPersonalIds = _.slice(personalIds, currentPos, currentPos + unitLen);
                        if (targetPersonalIds.length > 0) {
                            // 対象顧客配列がある場合に処理を実行する。
                            var query = getArrayQueryParam("personalId", targetPersonalIds);
                            var formData = {};
                            if (targetEmployeeId) {
                                // 担当者変更
                                formData["divisionId"] = targetDivisionId;
                                formData["employeeId"] = targetEmployeeId;
                            } else if (targetDivisionId) {
                                // 課担当なし
                                formData["divisionId"] = targetDivisionId;
                                formData["employeeId"] = "";
                            }
                            formData["protect"] = chargeForm.isItemChecked("protect")? 1: 0;
                            var response = postSyncURL("changeEmp/change", formData, [], query);
                            if (!response.error) {
                                // １人の顧客であれば、メール送信する
                                if (personalIds.length === 1) {
                                    if (targetEmployeeId > 0) {
                                        sendMessageToEmployee([targetEmployeeId], "担当者変更で顧客が１名追加",
                                                "<p>以下の顧客が新しく担当になりました。</p><br>" + getGHPersonalLink(personalIds[0]));
                                    }
                                    if (employeeIds[0] > 0) {
                                        sendMessageToEmployee(employeeIds, "担当者変更で顧客が１名移動", "<p>担当変更により顧客が１名別のところに移動しました。</p>");
                                    }
                                }
                                dhtmlx.message({text: chargeTree.getItemText(treeIdx) + "に顧客情報" + targetPersonalIds.length + "件を割り当てしました。"});
                            }
                        }
                        // 現在地をずらす
                        currentPos += unitLen;
                    });

                    // 他の画面に変更を通知する
                    if(personalModel){
                        personalModel.changeCharge();
                    }

                    if(parentWindow){
                        parentWindow.close();
                    }
                    if (_.isFunction(callbackFunc))
                        callbackFunc();
                    win.close();
                    break;
            }
        });
        win.show();
    };

    /**
     * 指示報告ウィンドウ
     * @param {Object} custFormData 顧客情報
     * @param {Object} model 画面の更新通知を行うオブジェクト
     * @param callBack
     */
    this.doReportWindow = function(custFormData, model, callBack) {
        var personalId = custFormData["personalId"];
        //var personalName = custFormData["lastName"] + " " + custFormData["firstName"];
        var personalFullName = custFormData["lastName"] + " " + custFormData["firstName"];
        var employeeId = _ghUser.getGhId()
        var employFullName = _ghUser.getName();

        var winName = "directWin";
        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 450, 485);
            win.setText("指示登録");
            win.hide();
        }

        //フォームの作成
        var directWinForm = win.attachForm();
        directWinForm.loadStruct(getTemplatePath(_GH.DATA_PATH.REPORT_WIN_FORM_JSON), "json", function() {
            //クッキーより初期設定を取得
            var cokkies = getCookies();
            var now  = new Date();
            var param = {
                 employerFullName : employFullName
                ,employeeId : employeeId
                ,directEmployeeId: _ghUser.getGhId()
                ,divisionId : _ghUser.getDivisionId()
                ,personalId : personalId
                ,personalFullName : personalFullName
                ,important : is("String",cokkies.important)?cokkies.important:"2"
                ,expirationDate : new Date(now.getFullYear(), now.getMonth(), now.getDate(), 22, 0, 0)
                ,directMailFlg : is("String",cokkies.directMailFlg)?cokkies.directMailFlg:"0"
                ,reportMailFlg : is("String",cokkies.reportMailFlg)?cokkies.reportMailFlg:"0"
                ,directorCheckFlg : is("String",cokkies.directorCheckFlg)?cokkies.directorCheckFlg:"0"
            };
            // カレンダーで選択できるのは今日から１ヶ月以内。
            directWinForm.getCalendar("expirationDate").setSensitiveRange(now, getAfterDate(1, 0));
            console.log("指示ウィンドウの初期パラメータ", param);
            this.setFormData(param);
        });

        // 指示報告のクリックイベント設定
        directWinForm.attachEvent('onButtonClick', function (name/*, command*/) {
            switch (name) {
                case "L_002": //保存ボタン
                    directWinForm.updateValues();
                    var data = this.getFormData();
                    setGhCookie("directMailFlg", data.directMailFlg);
                    setGhCookie("reportMailFlg", data.reportMailFlg);
                    setGhCookie("directorCheckFlg", data.directorCheckFlg);
                    if (directWinForm.validate()) {
                        saveForm(directWinForm, "direct/regist");
                        win.close();
                        if (_.isFunction(callBack)) {
                            callBack.call();
                        }
                        // 画面の更新通知
                        if(model){model.append()}
                        break;
                    }
            }
        });

        directWinForm.attachEvent("onFocus", function (name/*, value*/) {
            //選択画面のイベントをここに設定する。
            switch (name) {
                case "employerFullName": //担当者選択ウィンドウ
                    that.doTantoWin(this.getInput("employeeId"), this.getInput("employerFullName"));
                    break;
                case "eventName": //物件選択ウィンドウ
                    that.doEvent(this.getInput("eventId"), this.getInput("eventName"), directWinForm);
                    break;
            }
        });

        directWinForm.attachEvent("onChange", function (name, value/*, state*/) {
            if (name == "directMailFlg" || name == "reportMailFlg" || name == "directorCheckFlg") {
                setGhCookie(name, value);
            }
       });
        // バリデートエラー時の処理
        directWinForm.attachEvent("onValidateError", function (name/*, value, result*/) {
            dhtmlx.message({
                type:"error",
                text: directWinForm.getItemLabel(name) + "は必須入力です。"
            });
        });
        win.show();
    };

    /**
     * 担当者へ転送ウィンドウ
     * @param personalLastName
     * @param personalFirstName
     * @param employeeId
     * @param mailBody
     * @param employFullName
     */
    this.doTransferMailWindow = function(personalLastName, personalFirstName, employeeId, mailBody, employFullName) {
        var winName = "transferWin";
        var employFullName = (employFullName !== undefined) ? employFullName : "";
        var personalName = personalLastName + " " + personalFirstName;

        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 350, 450);
            win.setText("担当者に転送");
            win.hide();
        }

        //フォームの作成
        var transferForm = win.attachForm();
        transferForm.loadStruct(getTemplatePath(_GH.DATA_PATH.PERSONAL_TRANSFER_JSON), "json", function() {
            transferForm.setItemValue("toEmployeeIdList", employeeId);
            transferForm.setItemValue("subject", "【ohgHire担当者へ連絡】 #{" + entryName + "} #{" + personalName + "}様");
            transferForm.setItemValue("body", mailBody);
            transferForm.setItemValue("toEmployee", employFullName);
            transferForm.setItemLabel("warnMsg", "対象のお客様の情報を担当者へ送信します。<BR>この情報には個人情報も含まれますので個人情報保護法の下、個人情報の取り扱いには十分注意してください。");
            transferForm.showItem("warnMsg");
        });
        transferForm.attachEvent("onFocus", function (name/*, value*/) {
            //選択画面のイベントをここに設定する。
            switch (name) {
                case "toEmployee": //担当者選択ウィンドウ
                    that.doTantoWin(this.getInput("toEmployeeIdList"), this.getInput("toEmployee"),null ,null, true, null, null, null, true);
                    break;
            }
        });
        // 指示報告のクリックイベント設定
        transferForm.attachEvent('onButtonClick', function (name/*, command*/) {
            switch (name) {
                case "tranferBtn": //保存ボタン
                	transferForm.setItemValue("toEmployee", this.getInput("toEmployee").value);
                    if (transferForm.validate()) {
                        var param = transferForm.getFormData();
                        // 送信先社員IDを配列にする。今は１人だけしか想定していないため、下記の単純な実装とする。
                        param["toEmployeeIdList"] = _.map(_.words(param["toEmployeeIdList"], /[^, ]+/g), _.parseInt);
                        // 本文の改行コードを変換する。
                        param["body"] = param["body"].split("\n").join("<BR>");
                        try{
                            syncPutJSON("sendmail/empMessage", param, function(/*res*/){
                                dhtmlx.message({text: "メールを送信しました。"})
                            });
                        } catch(e){
                            dhtmlx.message({text: "メールを送信でエラー"});
                            throw e;
                        }
                        win.close();
                    }
                    break;
            }
        });
        // バリデートエラー時の処理
        transferForm.attachEvent("onValidateError", function (name/*, value, result*/){
            dhtmlx.message({
                type:"error",
                text: transferForm.getItemLabel(name) + "は必須入力です。"
            });
        });
        win.show();
    };

    /**
     * 現場、物件マスタウィンドウを開く
     * @param {Object} eventIdInput 物件IDをセットするINPUTオブジェクト
     * @param {Object} eventNameInput 物件名をセットするINPUTオブジェクト
     * @param {Object} formObject フォームオブジェクト
     * */

    /**
     * 郵便番号ウィンドウ
     * @param {Object} zipCodeInput 郵便番号Inputオブジェクト
     * @param {Object} prefectureSelect 都道府県Selectオブジェクト(getSelect()で取得）
     * @param {Object} cityInput 市区町村Inputオブジェクト
     * @param {Object} townInput その他の住所Inputオブジェクト
     */
    this.doPostWindow = function(zipCodeInput, prefectureSelect, cityInput, townInput) {

        var windowName = "postWin";
        var win = this.ghWins.window(windowName);
        if (win == null) {
            win = this.ghWins.createWindow(windowName, 20, 30, 550, 450);
            win.setText("郵便番号ウィンドウ");
            win.hide();
        }
        //フォームの作成
        var postForm = win.attachForm();
        postForm.loadStruct(getTemplatePath(_GH.DATA_PATH.WIN_POST_FORM_JSON), "json", function() {
            if (zipCodeInput && zipCodeInput.value != "") {
                this.setItemValue("zipCode", zipCodeInput.value);
                that.searchPostInformation(postForm, zipCodeInput, prefectureSelect, cityInput, townInput, windowName);
            } else {
                var address = (prefectureSelect && prefectureSelect.value)? prefectureSelect.value : "";
                address += (cityInput && cityInput.value)? cityInput.value : "";
                address += (townInput && townInput.value)? townInput.value : "";
                if (address !== ""){
                    this.setItemValue("address", address);
                    that.searchPostInformation(postForm, zipCodeInput, prefectureSelect, cityInput, townInput, windowName);
                }
            }
        });
        postForm.attachEvent("onButtonClick", function(name){
            switch(name) {
                case "L_001": //検索
                    that.searchPostInformation(postForm, zipCodeInput, prefectureSelect, cityInput, townInput, windowName);
                    break;
            }
       });
        win.show();
    };

    /**
     * グリッドの列の表示／非表示を切り替えるウィンドウを表示する。
     * @param {Object} grid グリッドオブジェクト
     * @param {Array} unconfiguable ウィンドウに表示させない項目の配列
     */
    this.doShowHideColumnWindow = function(grid, unconfiguable) {
        var windowName = "showHideColumnWin";
        var win = this.ghWins.window(windowName);
        if (win == null) {
            win = this.ghWins.createWindow(windowName, 600, 30, 160, 500);
            win.hide();
            win.setText("");
        }

        //フォームの作成
        var showHideColumnForm = win.attachForm();

        var check_list = _.map(_.range(1, grid.getColumnsNum()), function(num){
            // 一週間前～未メール日数は列の表示非表示設定をさせない
            var id = grid.getColumnId(num);
            if(_(unconfiguable).contains(id)){
                return;
            }
            var label = grid.getColumnLabel(num);
            var checked = !grid.isColumnHidden(num);
            return {type: "checkbox", name: id, label: label, position: "label-right", checked: checked};
        });

        showHideColumnForm.loadStruct(check_list);
        showHideColumnForm.attachEvent("onChange", function(name){
            var ind = grid.getColIndexById(name);
            var show = showHideColumnForm.isItemChecked(name);
            grid.setColumnHidden(ind, !show);
        });
        win.show();
    };
    /**
     * 郵便番号検索を実行するメソッド
     */
    this.searchPostInformation = function(postForm, zipCodeInput, prefectureSelect, cityInput, townInput, windowName) {
      //入力チェック
        var formData = postForm.getFormData();
        var url;
        if(formData.zipCode != ""){
            // ハイフンを除いて検索にかける
            url = _GH.URL("WIN_POST_CODE_LIST") + formData.zipCode.split("-").join("");
        }else if(formData.address != ""){
            if(formData.address.length <=1){
                return;
            }
            url = _GH.URL("WIN_POST_CODE_ADDRESS") + encodeURI(formData.address);
        }else{
            //未入力
            return;
        }
        // Grid表示用にデータを振り分けるロジック
        var gridDataLogicFunc = function(data, id, rowIdx) {
            var val;
            switch(id) {
                case "no":
                    val = rowIdx + 1;
                  break;
              default:
                  val = data[id];
                  break;
            }
            return val;
        };
        // Gridの設定
        var postGrid = new dhtmlXGridObject(postForm.getContainer("bottomGrid"));
        postGrid.setImagePath(_GH.PATH_TYPE.DHX_IMGS);
        postGrid.setSkin(_GH.PATH_TYPE.SKIN);
        postGrid.enableColumnMove(true);
        loadGridByGet(postGrid, _GH.DATA_PATH.WIN_POST_GRID_JSON, url, null , gridDataLogicFunc);
        postGrid.attachEvent("onRowDblClicked", function (rId/*, cInd*/) {
            if(!_.isEmpty(zipCodeInput)){ zipCodeInput.value = (postGrid.cells(rId, 0)).getValue();}
            if(!_.isEmpty(prefectureSelect)){ prefectureSelect.value = (postGrid.cells(rId, 1)).getValue();}
            if(!_.isEmpty(cityInput)){ cityInput.value = (postGrid.cells(rId, 2)).getValue();}
            if(!_.isEmpty(townInput)){ townInput.value = (postGrid.cells(rId, 3)).getValue();}
            that.closeWindow(windowName, true);
        });
    };

    // =================================================================
    /**
     * [ユーティリティ関数] 担当者へメール送信ウィンドウ
     * @param {Array} employeeIds メール送信する担当者ID配列
     * @param {String} subject メール件名
     * @param {String} body メール本文
     * @param {String} warning 警告メッセージ
     */
    this.doSendToEmpWin = function(employeeIds, subject, body, warning) {
        var winName = "sendToEmpWin";

        var win = this.ghWins.window(winName);
        if (win == null) {
            win = this.ghWins.createWindow(winName, 20, 30, 350, 400);
            win.setText("担当者にメール送信");
            win.hide();
            win.button("park").hide();
            win.button("minmax1").hide();
            win.button("close").attachEvent("onClick", function(){
                that.closeWindow(winName, true);
            });
        }

        //フォームの作成
        var sendToEmpForm = win.attachForm();
        sendToEmpForm.loadStruct(getTemplatePath(_GH.DATA_PATH.PERSONAL_TRANSFER_JSON), "json", function() {
            sendToEmpForm.hideItem("toEmployee");
            sendToEmpForm.setItemValue("toEmployee", "担当者");
            sendToEmpForm.setItemValue("subject", subject);
            sendToEmpForm.setItemValue("body", body);
            if (!_.isEmpty(warning)) {
                sendToEmpForm.setItemLabel("warnMsg", warning);
                sendToEmpForm.showItem("warnMsg");
            }
        });
        sendToEmpForm.enableLiveValidation(true); // ライブバリデーションON
        // 指示報告のクリックイベント設定
        sendToEmpForm.attachEvent('onButtonClick', function (name/*, command*/) {
            switch(name) {
                case "tranferBtn": //保存ボタン
                    if (sendToEmpForm.validate()) {
                        sendMessageToEmployee(employeeIds, sendToEmpForm.getItemValue("subject"), sendToEmpForm.getItemValue("body"));
                        that.closeWindow(winName, true);
                    }
                    break;
            }
        });
        // バリデートエラー時の処理
        sendToEmpForm.attachEvent("onValidateError", function (name/*, value, result*/) {
            dhtmlx.message({
                type:"error",
                text: sendToEmpForm.getItemLabel(name) + "は必須入力です。"
            });
        });
        win.show();
    };

    /**
     * [ユーティリティ関数] メール送信メソッド
     * @param {Object} winName DhtmlxWindow name.
     * @param {Object} mailForm DhtmlxFormオブジェクト
     * @param {Object} ckeditor CDEditorのeditor要素
     * @param {Object} attachments 添付ファイルマップ（key : realName, value: serverName)
     * @param {Array} personalIds List of personal IDs.
     * @param model
     */
    this.sendMail = function (winName, mailForm, ckeditor, attachments, personalIds, model) {
        console.log("メールフォーム", mailForm.getFormData(), attachments, personalIds, mailForm.getItemValue("timerStart"));
        // バリデート
        if (!that.validateMailForm(mailForm, ckeditor)) {
            return;
        }
        var uri = (personalIds.length > 1)? "sendmail/group/": "sendmail/single/";
        uri += (mailForm.getItemValue("timerStart"))? "timer": "direct";
        // 一斉メールでCCが付いている場合、本当に良いか、確認する。
        if (mailForm.getItemValue("ccType") != "0" && _.startsWith(uri, "sendmail/group/")) {
            if (confirm("CCの設定があると、全メールがCCに設定した方に飛びます。\nCCの設定を消しますか？")) {
                // CCを空にする。
                mailForm.setItemValue("ccType", "0");
            }
        }
        var param = mailForm.getFormData();

        // すでに添付されているファイル情報を追加する。
        _.forEach(param, function(val, key) {
            if (_.startsWith(key, "uploadedFile_")) {
                var attachFilename = mailForm.getUserData(key, "filename");
                if (!_.has(attachments, attachFilename)) {
                    attachments[attachFilename] = mailForm.getUserData(key, "filepath");
                }
            }
        });
        // 添付ファイル情報をつける
        param["attachementURLList"] = _.values(attachments);
        // 顧客ID
        param["personalIdList"] = personalIds;
        // タイマー送信時間。JSONで送るため、テンプレートで設定したserverDateのパラメータが効かないため。
        if (_.isDate(param["timerStart"])) {
            param["timerStart"] = getStrDate(param["timerStart"], _GH.FORMAT.YMD_HMS);
        }
        // 本文
        param["body"] = ckeditor.getData();
        dhtmlx.confirm({
            type:"confirm",
            text: "お客様へメールを送信してもよろしいですか?<BR>送信内容が適切かどうかすべてチェックしましたか？",
            callback: function(result){
                if (result) {
                    // 送信ボタンをロック
                    mailForm.lock();
                    window.dhx4.ajax.query({
                        method : "PUT",
                        url : _GHAPI.SERVER + uri,
                        data : JSON.stringify(param),
                        async : false,
                        callback: function(res){
                            if(res.xmlDoc.status === 200){
                                // ライブラリを使っていない場合は、メールの件名と本文をセッションストレージに保存する。
                                if (_.isEmpty(param["libraryId"])) {
                                    console.log("メールの件名と本文をセッションストレージに保存");
                                    setSS(_GH.S05F010ML.MAIL_SUB,   param["subject"]);
                                    setSS(_GH.S05F010ML.MAIL_BODY, param["body"]);
                                } else {
                                    setSS(_GH.S05F010ML.MAIL_SUB,   "");
                                    setSS(_GH.S05F010ML.MAIL_BODY, "");
                                }
                                dhtmlx.message("メールを送信しました。");
                                that.closeWindow(winName, true);

                                // メール送信後の画面更新通知
                                model.append();
                            } else {
                                var message = "サーバーエラーが発生しました。";
                                var params = { 'status' : res.status, 'errorMessage' :message};
                                throw new Error(JSON.stringify({message: message, params: params}));
                            }
                        },
                        headers:{
                            "Content-Type":"application/json"
                        }
                    });
                }
            }
        });
    };
    /**
     * [ユーティリティ関数] テストメール送信メソッド
     * @param {Object} mailForm DhtmlxFormオブジェクト
     * @param {Object} ckeditor CDEditorのeditor要素
     * @param {Object} attachments 添付ファイルマップ（key : realName, value: serverName)
     * @param personalIds
     */
    this.sendTestMail = function (mailForm, ckeditor, attachments, personalIds) {
        console.log("メールフォーム", mailForm.getFormData());

        // バリデート
        if (!that.validateMailForm(mailForm, ckeditor)) {
            return;
        }
        var param = mailForm.getFormData();

        // すでに添付されているファイル情報を追加する。
        _.forEach(param, function(val, key) {
            if (_.startsWith(key, "uploadedFile_")) {
                var attachFilename = mailForm.getUserData(key, "filename");
                if (!_.has(attachments, attachFilename)) {
                    attachments[attachFilename] = mailForm.getUserData(key, "filepath");
                }
            }
        });
        // 添付ファイル情報をつける
        param["attachementURLList"] = _.values(attachments);
        // 顧客ID
        param["personalIdList"] = personalIds;
        // タイマー送信時間はテストメール時は不要。
        delete param["timerStart"];
        // 本文
        param["body"] = ckeditor.getData();

        var uri = "sendmail/test";
        window.dhx4.ajax.query({
            method : "PUT",
            url : _GHAPI.SERVER + uri,
            data : JSON.stringify(param),
            async : false,
            callback: function(res){
                if(res.xmlDoc.status === 200){
                    // メールの件名と本文をセッションストレージに保存する。
                    setSS(_GH.S05F010ML.MAIL_SUB,   param["subject"]);
                    setSS(_GH.S05F010ML.MAIL_BODY, param["body"]);
                    dhtmlx.message("メールを送信しました。");
                    // CCを空にする。
                    mailForm.setItemValue("ccType", "0");
                    // 本番送信を有効化する
                    mailForm.enableItem("sendEmailBtn");
                } else {
                    var message = "サーバーエラーが発生しました。";
                    var params = { 'status' : res.status, 'errorMessage' :message};
                    throw new Error(JSON.stringify({message: message, params: params}));
                }
            },
            headers:{
                "Content-Type":"application/json"
            }
        });
    };

    /**
     * [ユーティリティ関数] ライブラリ新規作成メソッド
     * @param {String} winName ウィンドウ名
     * @param {Object} mailForm DhtmlxFormオブジェクト
     * @param {Object} ckeditor CDEditorのeditor要素
     * @param {Object} attachments 添付ファイルマップ（key : realName, value: serverName)
     * @param {Object} collection ライブラリのコレクション
     */
    this.createTemplate = function(winName, mailForm, ckeditor, attachments, collection) {
        mailForm.updateValues();
        // バリデート
        if (that.validateMailForm(mailForm, ckeditor)) {
            var param = mailForm.getFormData();
            // 添付ファイル情報をつける
            param["attachementURLList"] = _.values(attachments);
            // 本文
            param["mailBody"] = ckeditor.getData();
            // ユーザー名
            param["employeeFullName"] = _ghUser.getName();
            // 承認依頼ボタンを有効にする。
            mailForm.enableItem("referTypeBtn");
            var promiseTemplate = collection.create(param, winName);
            $.when(promiseTemplate).done(function(){
                mailForm.setItemValue("libraryId", param["libraryId"]);
            });
        }
    };

    /**
     * [ユーティリティ関数] ライブラリ更新メソッド
     * @param {String} winName ウィンドウ名
     * @param {Object} mailForm DhtmlxFormオブジェクト
     * @param {Object} ckeditor CDEditorのeditor要素
     * @param {Object} attachments 添付ファイルマップ（key : realName, value: serverName)
     * @param {Object} collection ライブラリのコレクション
     */
    this.updateTemplate = function(winName, mailForm, ckeditor, attachments, collection) {
        mailForm.updateValues();
        // バリデート
        if (that.validateMailForm(mailForm, ckeditor)) {
            var param = mailForm.getFormData();
            // 添付ファイル情報をつける
            param["attachementURLList"] = _.values(attachments);
            // 本文
            param["mailBody"] = ckeditor.getData();
            console.log("ライブラリアップデート", param);
            collection.update(param, winName);
        }
    };
    /**
     * 承認依頼
     */
    this.requestJudgeLibrary = function(winName, mailForm, ckeditor) {
        mailForm.updateValues();
        // バリデート
        if (that.validateMailForm(mailForm, ckeditor)) {
            var libraryApprovalStatus = mailForm.getSelect("approvalSelect").value;
            if (libraryApprovalStatus === "") {
                dhtmlx.alert({
                    title:"承認者チェックエラー",
                    type:"alert-error",
                    text:"承認者が選択されていません。<BR>必ず選択してください。"
                });
            } else {
                mailForm.setItemValue("libraryApprovalStatus", libraryApprovalStatus);
                mailForm.setItemValue("libraryApprovalFlg", _GH.S05F030Lib.APPROVE_FLG.NONE);
                saveForm(mailForm, "library/approvalStatus/" + mailForm.getItemValue("libraryId"));
                that.closeWindow(winName, true);
            }
        }
    };
    /**
     * 承認/差戻し
     */
    this.changeApprovalLibrary = function(winName, mailForm, ckeditor, approvalFlg, approvalStatus) {
        mailForm.updateValues();
        // バリデート
        if (that.validateMailForm(mailForm, ckeditor)) {
            if (mailForm.getItemValue("approvalMemo") === "") {
                dhtmlx.alert({
                    title:"承認メモチェックエラー",
                    type:"alert-error",
                    text:"理由が入力されていません。<BR>必ず入力してください。"
                });
            } else {
                // ライブラリ有効期限を承認日の１ヶ月後とする。
                var expire = getAfterDate(1, 0);
                mailForm.setItemValue("expire", window.dhx4.date2str(expire, "%Y/%m/%d %H:%i:%s"));
                mailForm.setItemValue("libraryApprovalFlg", approvalFlg);
                mailForm.setItemValue("libraryApprovalStatus", approvalStatus);
                saveForm(mailForm, "library/approvalLevel/" + mailForm.getItemValue("libraryId"));
                that.closeWindow(winName, true);
            }
        }
    };
    /**
     * メールフォームのバリデート
     */
    this.validateMailForm = function(mailForm, ckeditor) {
        // バリデート
        if (!mailForm.validate()) {
            dhtmlx.alert({
                title:"宛先・件名チェックエラー",
                type:"alert-error",
                text:"メールの送信先または件名が入力されていません。<BR>必ず入力してください。"
            });
            return false;
        } else if (ckeditor.getData() === "") {
            dhtmlx.alert({
                title:"メール本文チェックエラー",
                type:"alert-error",
                text:"メール本文が入力されていません。<BR>本文は必ず入力してください。"
            });
            return false;
        } else {
            return true;
        }
    };
    /**
     * メール／ライブラリのファイルアップロード用のイベントをセットする。
     * @param {Object} mailForm DhtmlxFormオブジェクト
     * @param {Object} attachments 添付ファイルマップ（key : realName, value: serverName)
     */
    this.setFileUploadEvents = function (mailForm, attachments) {
        // 添付ファイルアップロードの開始
        mailForm.attachEvent("onFileAdd",function (/*realName*/) {
            var uploader = mailForm.getUploader("attachmentFiles");
            uploader.setSendParams("currentUserId", _ghUser.getGhId());
            uploader.setSendParams("uploadType", "mailAttachment");
            $(".dhx_upload_files .dhx_file_delete").hide();
            $(".dhx_upload_controls").hide();
        });
        // ファイルをアップロードして、リストに追加される際のイベント
        mailForm.attachEvent("onUploadFile",function(realName, serverName){
            console.log("onUploadFile", realName, serverName);
            $(".dhx_upload_files .dhx_file_delete").hide();
            attachments[realName] = _GHAPI.SERVER + _GH.URL('FILE_GET_URL') + '?fileName=' + encodeURI(serverName);
        });
        // ファイル削除前の確認
        mailForm.attachEvent("onBeforeFileRemove", function(realName, serverName){
            if (!confirm(realName+"を本当に削除してもよろしいですか？")) return false;
            delURL(_GH.URL("FILE_DELETE_URL") + "?deleteFileName=" + encodeURI(serverName), null, null);
            return true;
        });
        // 添付ファイルアップロードの完了
        mailForm.attachEvent("onUploadComplete",function(count){
            console.log("<b>onUploadComplete</b> "+count+" file"+(count>1?"s were":" was")+" uploaded");
            $(".dhx_file_uploaded .dhx_file_delete").show();
            $(".dhx_upload_controls").show();
        });
        // 添付ファイルアップロードの失敗
        mailForm.attachEvent("onUploadFail",function(realName){
            dhtmlx.alert({
                title:"ファイルアップロードエラー",
                type:"alert-error",
                text:realName + "をアップロードすることができませんでした。"
            });
            $(".dhx_file_uploaded .dhx_file_delete").show();
            $(".dhx_upload_controls").show();
            console.log("<b>onUploadFail</b>, file: "+realName);
        });
    };
    /**
     * メールの編集できるかどうかを判断する。
     * @param {Boolean} isGroupMail 一斉メールかどうか。
     * @param {Number} sendCount 送信人数
     * @return {Boolean} true: メール編集可能、false: メール編集不可
     */
    this.doEditMail = function(isGroupMail, sendCount) {
        if (isGroupMail) {
            //var mgrCount = _GH.S05F010ML.APPROVE["MGR" + _ghUser.getDepartment()]; // MGR承認が必要な人数
            var mgrCount = _GH.S05F010ML.APPROVE["MGR" + _ghUser.getLoginType()]; // MGR承認が必要な人数
            //var mktCount = _GH.S05F010ML.APPROVE["MKT" + _ghUser.getDepartment()]; // MKR承認が必要な人数。MGRよりも大きい。
            var mktCount = _GH.S05F010ML.APPROVE["MKT" + _ghUser.getLoginType()]; // MKR承認が必要な人数。MGRよりも大きい。
            if (sendCount < mgrCount) {
                // 制限人数より少ない場合は許可
                return true;
            } else if (sendCount >= mgrCount && sendCount < mktCount) {
                // 制限人数がMGR以上マーケ未満なら、MGR承認権限・マーケ承認権限があればOK
                return (_ghUser.hasRole(_GH.ROLE.TEMPLATE_CHECK_MANAGER) || _ghUser.hasRole(_GH.ROLE.TEMPLATE_CHECK_MARKETING));
            } else {
                // 制限人数がマーケ以上であれば、マーケ承認が必要
                return _ghUser.hasRole(_GH.ROLE.TEMPLATE_CHECK_MARKETING);
            }
        } else {
            // 個別メールは許可
            return true;
        }
    };
    /**
     * メールウィンドウからライブラリウィンドウに飛ばすためのデータを取得する。
     */
    this.getMailWinFormData = function(ckeditorTextData) {
        var win = this.ghWins.window("mailWin");
        if (_.isEmpty(win)) {
            return {body: ckeditorTextData};
        }
        var mailForm = win.getAttachedObject();
        if (_.isEmpty(mailForm)) {
            return {body: ckeditorTextData};
        } else {
            return {body: ckeditorTextData, mailSubject: mailForm.getItemValue("subject"),
                eventId: mailForm.getItemValue("eventId"), eventName: mailForm.getItemValue("eventName"),
                eventMemo: mailForm.getItemValue("eventMemo")};
        }
    };

    /**
     * 顧客のステータス類を変更する。変更しない場合はnullを指定すること
     * @param {Object} custForm 顧客情報のFormData。getFormData()
     * @param {Boolean} empflg 自分フラグを変更するかどうか
     * @param {Boolean} protect プロテクトフラグを変更するかどうか
     */
    this.changePersonalFlags = function(custForm, empflg, protect) {
        //TODO ログインユーザの権限を見て、判断する必要あり
        var formData = custForm.getFormData();
        var personalId = formData["personalId"];
        var params = {
                protect: formData["protect"],
                empflg: formData["empflg"],
                managerflg: formData["managerflg"],
                centermanagerflg: formData["centermanagerflg"]
        };
        if (protect) {
            if (_ghUser.isManager()) {
                var flgKey = "protect";
                var newValue = (custForm.getItemValue(flgKey) == 1)? 0: 1;
                custForm.setItemValue(flgKey, newValue);
                params[flgKey] = newValue;
            }
        }
        if (empflg) {
            var flgKey;
            var newValue;
            //TODO センター長の判断をどうするか・・・
            if (_ghUser.isManager()) {
                flgKey = "managerflg";
            } else {
                flgKey = "empflg";
            }
            newValue = (custForm.getItemValue(flgKey) == 1)? 0: 1;
            custForm.setItemValue(flgKey, newValue);
            params[flgKey] = newValue;
        }
        postSyncURL("personal/changeFlags/" + personalId, params);
    };
}

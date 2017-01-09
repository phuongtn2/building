/**
 * ライブラリデータを保持する。更新時に listener の処理を同時に実行する。
 */
module.exports = function (listener, dirId) {
    "use strict";
    var that = this;

    this.dirId = dirId;
    var listeners = [];
    this.setListener = function(listener){
        listeners.push(listener);
    };
    this.setListener(listener);

    this.create = function(param, winName){
        var def = $.Deferred();
        var uri = "library/create";
        window.dhx4.ajax.query({
            method : "PUT",
            url : _GHAPI.SERVER + uri,
            data : JSON.stringify(param),
            async : false,
            headers:{"Content-Type":"application/json"},
            callback: function(res){ def.resolve(res);}
        });
        def.done(function(res) {
            if(res.xmlDoc.status === 200){
                param["libraryId"] = JSON.parse(res.xmlDoc.responseText).id;
                // メールの件名と本文をセッションストレージに保存する。
                setSS(_GH.S05F030Lib.MAIL_SUB,  param.mailSubject);
                setSS(_GH.S05F030Lib.MAIL_BODY, param.mailBody);
                _.forEach(listeners, function(listener){
                    listener.create();
                });
                // MSは承認が必要なため、ウィンドウは閉じない。
                if (_ghUser.isExp()) {
                    dhtmlx.message({ type:"info", text: "ライブラリ情報を保存しました。<BR>内容の確認後、承認依頼してください。"});
                } else {
                    _ghWins.closeWindow(winName, false);
                }
            } else {
                var message = "サーバーエラーが発生しました。";
                var params = { 'status' : res.status, 'errorMessage' :message};
                throw new Error(JSON.stringify({message: message, params: params}));
            }
        });
        return def.promise();
    };

    this.update = function(param, winName){
        var uri = "library/update";
        window.dhx4.ajax.query({
            method : "PUT",
            url : _GHAPI.SERVER + uri,
            data : JSON.stringify(param),
            async : false,
            headers:{
                "Content-Type":"application/json"
            },
            callback: function(res){
                if(res.xmlDoc.status === 200){
                    // メールの件名と本文をセッションストレージに保存する。
                    setSS(_GH.S05F030Lib.MAIL_SUB,  param.mailSubject);
                    setSS(_GH.S05F030Lib.MAIL_BODY, param.mailBody);

                    var nextNode = getJSONSync("library/folder/list/" + dirId);

                    _.forEach(listeners, function(listener){
                        listener.update();
                    });
                    _ghWins.closeWindow(winName, false);
                } else {
                    var message = "サーバーエラーが発生しました。";
                    var params = { 'status' : res.status, 'errorMessage' :message};
                    throw new Error(JSON.stringify({message: message, params: params}));
                }
            }
        });
    };

    this.deleteAt = function(selectedId, physicalDelete){
        if (physicalDelete === true) {
            delURL("library/delete/" + selectedId, null, function(res){
                _.forEach(listeners, function(listener){
                    listener.deleteAt(selectedId, res.xmlDoc.status === 200);
                });
            });
        } else {
            var response = postSyncURL("library/trashLibrary", {libraryId: selectedId});
            _.forEach(listeners, function(listener){
                listener.deleteAt(selectedId, !response.error);
            });
        }
    };

    this.deleteList = function(selectedIdList, physicalDelete){
        if (physicalDelete === true) {
            delURL("library/list/delete?" + getArrayQueryParam("libraryIdList", selectedIdList), null, function(res){
                _.forEach(listeners, function(listener){
                    listener.deleteAt(selectedIdList, res.xmlDoc.status === 200);
                });
            });
        } else {
        	var selectedIdsParam = "";
        	_.forEach(selectedIdList, function(selectedId, index) {
        		selectedIdsParam += ("&libraryId=" + selectedId);
        	})
            var response = postSyncURL("library/trashLibrary", null, null, selectedIdsParam);
            _.forEach(listeners, function(listener){
                listener.deleteAt(selectedIdList, !response.error);
            });
        }
    };

    this.move = function(libraryId, libraryDirId){
        if(that.dirId === libraryDirId){return;}

        var response = postSyncURL("library/move/" + libraryId, {libraryDirId: libraryDirId});

        _.forEach(listeners, function(listener){
            listener.move(libraryId, libraryDirId, !response.error);
        });
    };

    this.moveList = function(libraryIdList, libraryDirId){
        if(that.dirId === libraryDirId){return;}
        var selectedIdsParam = "";
    	_.forEach(libraryIdList, function(selectedId, index) {
    		selectedIdsParam += ("&libraryIdList=" + selectedId);
    	})
        var response = postSyncURL("library/list/move/" + libraryDirId, null, null, selectedIdsParam);

        _.forEach(listeners, function(listener){
            listener.move(libraryIdList, libraryDirId, !response.error);
        });
    };
}

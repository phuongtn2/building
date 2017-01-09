/**
 * 案内予約情報を保持する。更新時に listener の処理を同時に実行する。
 */
module.exports = function Schedule(listeners){
    "use strict";
    var that = this;

    var myListeners = [];
    this.setListener = function(listener){
        myListeners.push(listener);
    };
    _.forEach(listeners, function(l){
        that.setListener(l);
    });


    /**
     * 案内予約の追加を行う。リスナーに更新を通知する。
     */
    this.append = function(){
        _.forEach(myListeners, function(listener){
            if(listener.appendSchedule){listener.appendSchedule();}
        });
    };

    /**
     * 案内予約の更新を行う。リスナーに更新を通知する。
     */
    this.update = function(){
        _.forEach(myListeners, function(listener){
            if(listener.updateSchedule){listener.updateSchedule();}
        });
    };
};

/**
 * Created by phuongtn2 on 11/5/2016.
 */
var _building = {
    COOKIE:{
        EXPIRE : (new Date()).getTime() + 1000 * 3600 * 24 * 14,
        LOGIN: "__BUILDING_LOGIN__"
    }
};

var _building_const = {

};
function setCookie(name, value){
    var expire = new Date();
    expire.setTime(_building.COOKIE.EXPIRE);
    document.cookie = name + "=" + encodeURIComponent(value) + ";expires=" + expire.toUTCString();
}

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
function b64_to_utf8(str) {
    if (!str) return "";
    return decodeURIComponent(escape(window.atob(str)));
}
var _buildingUser = (function() {
    "use strict";
    var _Id = 0;
    var _adId = "";
    var _pwd = "";
    var _name = "";
    var _role = [];
    var _authToken = "";
    var _login = false;
    function _saveCookie() {
        var loginInfo = {
            Id: _Id,
            adId: _adId,
            pwd: _pwd,
            name: _name,
            role: _role,
            authToken: _authToken,
            login: _login
        };
        setCookie(_building.COOKIE.LOGIN, encodeURI(JSON.stringify(loginInfo)));
    }

    function _setHeaderInfo(response, adId, pwd) {
        _authToken = response.getResponseHeader("AUTHORIZED_TOKEN");
        _role = safeCommaSplit(response.getResponseHeader("ROLE_ID_LIST"));
        _Id = _.parseInt(response.getResponseHeader("LOGIN_USER_ID"));
        _name = b64_to_utf8(response.getResponseHeader("LOGIN_USER_NAME"));
        if(adId) _adId = adId;
        if(pwd) _pwd = window.btoa(pwd);
        _login = true;
        _saveCookie();
    }
    return {
        getId : function() {
            return _Id;
        },
        setId : function(Id) {
            _Id = Id;
        },
        getAdId : function() {
            return _adId;
        },
        getPwd : function() {
            return window.atob(_pwd);
        },
        getName : function() {
            return _name;
        },
        setName : function(name) {
            _name = name;
        },
        getRole : function() {
            return _role;
        },
        setRole : function(roleString) {
            _role = roleString.split(",");
        },
        hasRole : function(roleString) {
            return _role.indexOf(roleString) >= 0;
        },
        getAuthToken : function() {
            return _authToken;
        },
        setAuthToken : function(authToken) {
            _authToken = authToken;
        },
        isLogin : function() {
            return _login;
        },
        setLogin : function(login) {
            _login = login;
        },
        setHeaderInfo: function(response, adId, pwd) {
            _setHeaderInfo(response, adId, pwd);
        }
    };
}());
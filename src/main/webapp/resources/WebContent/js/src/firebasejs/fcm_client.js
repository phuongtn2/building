// Initialize Firebase
var config = {
    apiKey: "AIzaSyAcw9moU1b8wansKZzKJPtYD9XiY4vpDCI",
    authDomain: "building-a2be9.firebaseapp.com",
    databaseURL: "https://building-a2be9.firebaseio.com",
    storageBucket: "building-a2be9.appspot.com",
    messagingSenderId: "613083078500"
};
firebase.initializeApp(config);

var fcmToken;
var fcmMessaging;

function removeFCMToken(def) {
    console.log('removing token from server...');
    var query = 'token=' + fcmToken;
    window.dhx4.ajax.post(_GHAPI.SERVER + 'fcm/remove-token', encodeURI(query), function (response) {
        console.log(response);
        if (response.xmlDoc.status === 200) {
            if (parseInt(response.xmlDoc.responseText) > 0) {
                window.localStorage.setItem('sentFCMTokenToServer', 0);
            }
        }
        if (def) {
            def.resolve();
        }
    });
}

function sendFCMTokenToServer() {
    console.log('call executeFCMService()');
    if (fcmMessaging) {
        fcmMessaging.getToken()
            .then(function (currentToken) {
                if (currentToken) {
                    console.log('token: ' + currentToken);
                    fcmToken = currentToken;
                    console.log('Sending token to server...');
                    var query = 'token=' + fcmToken;
                    window.dhx4.ajax.post(_GHAPI.SERVER + 'fcm/store-token', encodeURI(query), function (response) {
                        console.log(response);
                        if (response.xmlDoc.status === 200) {
                            if (parseInt(response.xmlDoc.responseText) > 0) {
                                window.localStorage.setItem('sentFCMTokenToServer', 1);
                            }
                        }
                    });
                } else {
                    // Show permission request.
                    console.log('No Instance ID token available. Request permission to generate one.');
                }
            })
            .catch(function (err) {
                console.log('An error occurred while retrieving token. ', err);
            });
    } else {
        navigator.serviceWorker.register(_GHAPI.FCM_PATH.SW_PATH) // /SFA_CLIENT/lib/firebasejs/sw.js
            .then(function (registration) {
                const messaging = firebase.messaging();

                messaging.useServiceWorker(registration);
                // [START refresh_token]
                messaging.onTokenRefresh(function () {
                    messaging.getToken()
                        .then(function (refreshedToken) {
                            console.log('Token refreshed.');
                            fcmToken = refreshedToken;
                            // Send Instance ID token to app server.
                            console.log('Sending token to server...');
                            var query = 'token=' + fcmToken;
                            window.dhx4.ajax.post(_GHAPI.SERVER + 'fcm/store-token', encodeURI(query), function (response) {
                                console.log(response);
                                if (response.xmlDoc.status === 200) {
                                    if (parseInt(response.xmlDoc.responseText) > 0) {
                                        window.localStorage.setItem('sentFCMTokenToServer', 1);
                                    }
                                }
                            });
                        })
                        .catch(function (err) {
                            console.log('Unable to retrieve refreshed token ', err);
                        });
                });
                console.log('set onTokenRefresh handler');
                // [END refresh_token]

                // [START receive_message]
                messaging.onMessage(function (payload) {
                    console.log("Message received. ", payload);
                    if(payload.data.isNew == "true"){
                        /*var page;
                        if(typeof sfa !== 'undefined'){
                            page = sfa;
                        } else if (typeof _contract.contract !== 'undefined'){
                            page = _contract.contract;
                            if (typeof _contract.reportContract !== 'undefined'){
                                _contract.reportContract.setDirectNumber();
                            }
                        }
                        page.setDirectNumber();*/
                    }
                });
                console.log('set onMessage handler');
                // [END receive_message]

                console.log('Requesting permission...');
                // [START request_permission]
                messaging.requestPermission()
                    .then(function () {
                        console.log('Notification permission granted.');
                        // [START_EXCLUDE]
                        messaging.getToken()
                            .then(function (currentToken) {
                                if (currentToken) {
                                    console.log('token: ' + currentToken);
                                    fcmToken = currentToken;
                                    console.log('Sending token to server...');
                                    var query = 'token=' + fcmToken;
                                    window.dhx4.ajax.post(_GHAPI.SERVER + 'fcm/store-token', encodeURI(query), function (response) {
                                        console.log(response);
                                        if (response.xmlDoc.status === 200) {
                                            if (parseInt(response.xmlDoc.responseText) > 0) {
                                                window.localStorage.setItem('sentFCMTokenToServer', 1);
                                            }
                                        }
                                    });
                                } else {
                                    // Show permission request.
                                    console.log('No Instance ID token available. Request permission to generate one.');
                                }
                            })
                            .catch(function (err) {
                                console.log('An error occurred while retrieving token. ', err);
                            });
                        // [END_EXCLUDE]
                    })
                    .catch(function (err) {
                        console.log('Unable to get permission to notify.', err);
                    });
                //
                fcmMessaging = messaging;
                // [END request_permission]
            });
    }

}

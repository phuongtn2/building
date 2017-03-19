importScripts('https://www.gstatic.com/firebasejs/3.5.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/3.5.2/firebase-messaging.js');

var config = {
    apiKey: "AIzaSyAcw9moU1b8wansKZzKJPtYD9XiY4vpDCI",
    authDomain: "building-a2be9.firebaseapp.com",
    databaseURL: "https://building-a2be9.firebaseio.com",
    storageBucket: "building-a2be9.appspot.com",
    messagingSenderId: "613083078500"
};

self.addEventListener('push', function(event) {
  var data = JSON.parse(event.data.text()).data;
  const title = 'Title：' + data.subject;
  const options = {
      body: "「" + data.fullName + "」 ==> 「" + data.totalPrice + "」\r\n" + data.content,
      icon: '/resources/WebContent/imgs/fcm/icon.png',
      data: data.userId
  };
  const notificationPromise = self.registration.showNotification(title, options);
  event.waitUntil(notificationPromise);
});

self.addEventListener('notificationclick', function(event) {

  event.notification.close();
  //show customer page when click notifications.
  event.waitUntil(
    clients.openWindow('/requestBooking#boking/' + event.notification.data)
  );
});

firebase.initializeApp(config);

// Retrieve an instance of Firebase Messaging so that it can handle background
// messages.
const messaging = firebase.messaging();
// [END initialize_firebase_in_sw]

// If you would like to customize notifications that are received in the
// background (Web app is closed or not in browser focus) then you should
// implement this optional method.
// [START background_handler]
messaging.setBackgroundMessageHandler(function(payload) {
    console.log(payload.data);
});
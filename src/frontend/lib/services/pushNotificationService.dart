import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:firebase_core/firebase_core.dart';
import '../authentication/AuthService.dart';

@pragma('vm:entry-point')
Future<void> _firebaseMessagingBackgroundHandler(RemoteMessage message) async {
  // Initialize Firebase in background isolate before using Firebase features
  await Firebase.initializeApp();
  print('Background message: ${message.messageId}');
}

class PushNotificationService {
  PushNotificationService._();
  static final PushNotificationService instance = PushNotificationService._();

  bool _initialized = false;

  Future<void> init() async {
    if (_initialized) return;
    _initialized = true;

    final messaging = FirebaseMessaging.instance;

    // 1) Request permission
    final settings = await messaging.requestPermission(
      alert: true,
      badge: true,
      sound: true,
    );
    print('Notification permission: ${settings.authorizationStatus}');

    // Allow iOS to show notifications while app is in the foreground
    await messaging.setForegroundNotificationPresentationOptions(
      alert: true,
      badge: true,
      sound: true,
    );

    // 2) Register background handler
    FirebaseMessaging.onBackgroundMessage(_firebaseMessagingBackgroundHandler);

    // 3) Foreground messages
    FirebaseMessaging.onMessage.listen((RemoteMessage message) {
      print('Foreground message: ${message.notification?.title}');
      // TODO: show local notification banner if needed
    });

    // 4) When user taps a notification to open the app
    FirebaseMessaging.onMessageOpenedApp.listen((RemoteMessage message) {
      print('Notification tapped: ${message.data}');
      // TODO: navigate based on message.data
    });

    // 5) Send current token to backend & listen for refreshes
    final token = await messaging.getToken();
    if (token != null && token.isNotEmpty) {
      await AuthService.instance.updateFcmTokenOnBackend(token);
    }

    messaging.onTokenRefresh.listen((newToken) {
      AuthService.instance.updateFcmTokenOnBackend(newToken);
    });
  }
}

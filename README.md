# PAM Native Widgets

Updates Android App Widgets and iOS WidgetKit timelines from PHP through process-safe shared storage. Widget content is intentionally bounded; use deep links to open rich application screens.

iOS uses `group.<application-id>.pam-native`, shared by the generated app and widget targets. Widgets are not a background PHP runtime: prepare their display model in the app, then call `Widgets::update()`.

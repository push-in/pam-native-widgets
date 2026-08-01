# PAM Native Widgets

Updates Android App Widgets and iOS WidgetKit timelines from PHP through process-safe shared storage. Widget content is intentionally bounded; use deep links to open rich application screens.

iOS uses `group.<application-id>.pam-native`, shared by the generated app and widget targets. Widgets are not a background PHP runtime: prepare their display model in the app, then call `Widgets::update()`.

## Install

```bash
composer require pushinbr/pam-native-widgets
pam mobile prepare
```

Autolinking creates the WidgetKit extension, shared entitlements, Android receiver, resources, and native bridge.

## Publish widget state

```php
use Pam\Native\Widgets\WidgetContent;
use Pam\Native\Widgets\Widgets;

(new Widgets())->update(
    'account-summary',
    new WidgetContent(
        title: 'Available balance',
        subtitle: 'Updated just now',
        value: '$1,284.00',
        deepLink: 'myapp://account',
    ),
    static fn (bool $updated, ?string $error) => null,
);
```

Keep widget values small and non-sensitive because they can appear while the device is locked. The identifier is stable application metadata, not a per-device widget instance ID.

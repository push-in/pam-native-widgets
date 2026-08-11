# PAM Native Widgets

Updates Android App Widgets and iOS WidgetKit timelines from PHP through process-safe shared storage. Widget content is intentionally bounded; use deep links to open rich application screens.

iOS uses `group.<application-id>.pam-native`, shared by the generated app and widget targets. Widgets are not a background PHP runtime: prepare their display model in the app, then call `Widgets::update()`.

## Install

```bash
pam add widgets
pam doctor
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


## What installation does

`pam add widgets` resolves the official compatible package, performs a non-mutating Composer preflight, updates the normal `composer.json` and `composer.lock`, refreshes generated native integration when required, and leaves the project ready for `pam doctor` validation.

Use `pam packages` to inspect availability and `pam remove widgets` to uninstall the capability safely. Direct Composer commands are an advanced interoperability path; PAM is the supported application workflow.

## API guide

| API | Responsibility |
| --- | --- |
| `Widgets` | Publish bounded process-safe widget state. |
| `WidgetContent` | Define title, subtitle, value, and deep link. |

All coded states, kinds, and variants are sequential integer-backed enums. Use enum cases in application code; do not depend on raw wire numbers.

## Production checklist

- Treat widgets as snapshots, not background PHP processes.
- Keep lock-screen-visible content small and non-sensitive.
- Use stable widget identifiers and valid deep links into named routes.
- Run `pam doctor`, `pam test`, and a signed release build on every supported platform.
- Exercise denial, cancellation, backgrounding, process restart, and offline behavior before release.

## Troubleshooting

- **iOS widget is empty:** verify the shared app group and extension signing.
- **Android widget is stale:** publish new state before requesting refresh.
- **A deep link opens the wrong screen:** validate it through the main app router.
- **Native integration is stale:** run `pam doctor --fix`, rebuild the native host, and inspect the first reported diagnostic.

## Compatibility and support

This package targets PAM Native `0.6.x`, Android API 26+, and iOS 15+ unless a platform-specific section above states a stricter requirement. Platform SDKs, credentials, entitlements, physical hardware, and store configuration remain application responsibilities.

- [PAM documentation](https://push-in.github.io/pam-docs/introduction/)
- [PAM Native overview](https://push-in.github.io/pam-docs/native/overview/)
- [Plugin and native capability model](https://push-in.github.io/pam-docs/native/plugins/)
- [Report an issue](https://github.com/push-in/pam-native-widgets/issues)

Security vulnerabilities should be reported through the repository security policy or GitHub private vulnerability reporting, not a public issue.

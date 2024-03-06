# Android WSA Demo for Windows 11
_Learn how to launch an app for the Windows Subsystem for Android™️_

<img src="https://github.com/AmazonAppDev/android-wsa-demo/blob/main/gifs/aadevsui.gif" width="640" height="336" />

[Windows Subsystem for Android™️](https://learn.microsoft.com/en-us/windows/android/wsa/) enables your Windows 11 device to run Android applications that are available in the [Amazon Appstore](https://developer.amazon.com/apps-and-games/appstore-on-windows-11).

This project is a sample Android App to showcase best practices when optimizing for [Windows Subsystem for Android](https://learn.microsoft.com/en-us/windows/android/wsa/).

## Prerequisites

To run this project, you will need the following:
- Windows 11
- Windows Subsystem for Android™️ (WSA) installed

To set up your development environment with WSA you can follow the instructions here:

[WSA Development Environment Setup](https://learn.microsoft.com/en-us/windows/android/wsa/#set-up-your-development-environment)

## ✅ Features

The project retrieves the first 20 Videos of our [Amazon Appstore Developers Youtube Channel](https://www.youtube.com/c/AmazonAppstoreDevelopers). Thanks to @prof18 for building the Open Source library [YoutubeParser](https://github.com/prof18/YoutubeParser).

Key features include showing how to:
- Implement [adaptive layouts](https://developer.android.com/jetpack/compose/layouts/adaptive) using [Jetpack Compose](https://developer.android.com/jetpack/compose) for responsive UI and window resizing
- Handle mouse events such as hover and right click
- Handle special keyboard events such as `(CTRL-<Key>)`
- Launch Android notifications on Windows 11
- Check availability of hardware features and capabilities

## 💻 Building the Android WSA demo

1. Clone the following repository:

`git clone git@github.com:AmazonAppDev/android-wsa-demo.git`

2. Create a [Google Cloud API key](https://cloud.google.com/docs/authentication/api-keys?hl=en&visit_id=638007358010576297-2547601963&rd=1#creating-browser-api-keys) for the YoutubeParser feed retrieval
3. Put your Browser API Key in your local.properties file in the root of your project

```

google_cloud_api_key="Your Google Cloud API Key"

```

4. Open this project with Android Studio
5. Start WSA opening the Amazon Appstore
6. Connect WSA with Android Studio executing the following command in a terminal:
```
   adb connect 127.0.0.1:58526
```
6. Run the app

For more info about how to test your Android app with WSA you can follow the instructions here:

[![Video screenshot for Testing your App on Windows 11](https://img.youtube.com/vi/z_ehadkRyzY/0.jpg)](https://www.youtube.com/watch?v=z_ehadkRyzY)


## Using the Sample App

On Windows 11 you can resize the window of the app and see how it reacts to the dimensions changes.
If you go to the Notifications section, you can launch a test notification to see how they are integrated into the Windows 11 notification system

<img src="https://github.com/amazonappdev/AADevs/blob/main/gifs/notifications.gif" width="640" height="484" />

## Get support

If you found a bug or want to suggest a new [feature/use case/sample], please [file an issue](../../issues).

If you have questions, comments, or need help with code, we're here to help:
- on Twitter at [@AmazonAppDev](https://twitter.com/AmazonAppDev)
- on Stack Overflow at the [amazon-appstore](https://stackoverflow.com/questions/tagged/amazon-appstore) tag

Sign up to [stay updated with the developer newsletter](https://m.amazonappservices.com/subscribe-newsletter).

## Authors

- [@giolaq](https://twitter.com/giolaq)

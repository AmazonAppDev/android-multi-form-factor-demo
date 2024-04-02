# 📱💻🖥️ Adaptive Android App Demo

[![Android build](https://github.com/AmazonAppDev/android-multi-form-factor-demo/actions/workflows/android.yml/badge.svg)](https://github.com/AmazonAppDev/android-multi-form-factor-demo/actions/workflows/android.yml)
[![License](https://img.shields.io/badge/License-MIT0-green)](LICENSE)

This project is a sample Android app that showcases best practices when optimizing for different form factors, such as phones, tablets, and desktop devices. It demonstrates how to create adaptive layouts and handle various input methods to provide a seamless user experience across different screen sizes and device capabilities. 📐🎨

## ✅ Features

The app retrieves the first 20 videos from the Amazon Appstore Developers YouTube Channel using the open-source library [YoutubeParser](https://github.com/prof18/YoutubeParser) by @prof18. 🙌

Key features include:

- Implementing adaptive layouts using Jetpack Compose for responsive UI and window resizing 🌈
- Handling mouse events such as hover and right click 🖱️
- Handling special keyboard events such as `(CTRL-<Key>)` ⌨️
- Launching Android notifications on different devices 🔔
- Checking availability of hardware features and capabilities 🔍

## 📋 Prerequisites

To run this project, you will need the following:

- [Android Studio](https://developer.android.com/studio) 🛠️
- An Android device or emulator running Android 5.0 (API level 21) or higher 📱

## 💻 Building the Adaptive Android App Demo

1. Clone the repository:
   ```
   git clone https://github.com/AmazonAppDev/android-multi-form-factor-demo.git
   ```

2. Create a Google Cloud API key for the YoutubeParser feed retrieval. 🔑

3. Add your Browser API Key to the `local.properties` file in the root of your project:
   ```
   google_cloud_api_key="Your Google Cloud API Key"
   ```

4. Open the project in Android Studio. 🌟

5. Run the app on your Android device or emulator. ▶️

## 📈 Using the Sample App

Resize the app window on devices with larger screens to see how the layout adapts to different dimensions. In the Notifications section, you can launch a test notification to see how it integrates with the device's notification system. 🪟🔔

## 💬 Get Support

If you encounter a bug or want to suggest a new feature, please [file an issue](https://github.com/AmazonAppDev/android-multi-form-factor-demo/issues). 🐛💡

If you have questions, comments, or need help with the code, we're here to assist you:

- Tweet at us on Twitter [@AmazonAppDev](https://twitter.com/AmazonAppDev) 🐦
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/amazon-appstore) using the `amazon-appstore` tag 📚

Don't forget to sign up for the [developer newsletter](https://developer.amazon.com/developer-news-and-updates) to stay updated with the latest news and resources! 📧

## 👥 Authors

- [@giolaq](https://github.com/giolaq)

## 📄 License

This project is licensed under the [MIT-0 License](LICENSE).

---

We hope this sample app helps you create amazing adaptive Android experiences! 🚀✨

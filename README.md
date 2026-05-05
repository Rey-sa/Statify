# Statify

![GitHub stars](https://img.shields.io/github/stars/Rey-sa/Statify?style=for-the-badge&logo=github) ![GitHub forks](https://img.shields.io/github/forks/Rey-sa/Statify?style=for-the-badge&logo=github) ![GitHub issues](https://img.shields.io/github/issues/Rey-sa/Statify?style=for-the-badge&logo=github)

## 📑 Table of Contents

- [Description](#description)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Quick Start](#quick-start)
- [Screenshots](#screenshots)
- [Project Structure](#project-structure)
- [Development Setup](#development-setup)
- [Contributing](#contributing)

## 📝 Description

Statify is a native Android app that I developed as part of my studies. The app allows users to manually enter, classify, and statistically analyze datasets. The project focused on implementing a clean architecture (separation of logic and UI) and local data storage.

## 🛠️ Tech Stack

- 🤖 Android (Native)

## ⚡ Quick Start

```bash

# Clone the repository
git clone https://github.com/Rey-sa/Statify.git

# Install dependencies and run

# (See Development Setup below)
```

## 📁 Project Structure

```
.
├── Designer (1).jpeg
├── Designer.jpeg
├── app
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src
│       ├── androidTest
│       │   └── java
│       │       └── app
│       │           └── dev
│       │               └── statify
│       │                   └── ExampleInstrumentedTest.java
│       ├── main
│       │   ├── AndroidManifest.xml
│       │   ├── ic_statify-playstore.png
│       │   ├── java
│       │   │   └── app
│       │   │       └── dev
│       │   │           └── statify
│       │   │               ├── Persistence
│       │   │               │   └── SaveLoadHandler.java
│       │   │               ├── Service
│       │   │               │   ├── Calculations.java
│       │   │               │   └── Handler
│       │   │               │       ├── ClassifyInputHandler.java
│       │   │               │       ├── EditItemHandler.java
│       │   │               │       ├── EditTextKeyHandler.java
│       │   │               │       ├── ItemClickHandler.java
│       │   │               │       ├── ModeHandler.java
│       │   │               │       ├── RemoveItemFromListHandler.java
│       │   │               │       └── SubmitHandler.java
│       │   │               └── UI
│       │   │                   ├── Activities
│       │   │                   │   ├── ClassificationActivity.java
│       │   │                   │   ├── FrequenciesActivity.java
│       │   │                   │   ├── MainActivity.java
│       │   │                   │   └── ResultActivity.java
│       │   │                   └── Adapter
│       │   │                       └── Adapter.java
│       │   └── res
│       │       ├── drawable
│       │       │   ├── background_gradient.xml
│       │       │   ├── cell_border.xml
│       │       │   ├── ic_back_arrow.xml
│       │       │   ├── ic_launcher_background.xml
│       │       │   └── ic_launcher_foreground.xml
│       │       ├── layout
│       │       │   ├── classification_layout.xml
│       │       │   ├── frequencies_layout.xml
│       │       │   ├── main_layout.xml
│       │       │   └── results_layout.xml
│       │       ├── layout-land
│       │       │   ├── classification_layout.xml
│       │       │   ├── main_layout.xml
│       │       │   └── results_layout.xml
│       │       ├── mipmap-anydpi-v26
│       │       │   ├── ic_launcher.xml
│       │       │   ├── ic_launcher_round.xml
│       │       │   ├── ic_statify.xml
│       │       │   └── ic_statify_round.xml
│       │       ├── mipmap-hdpi
│       │       │   ├── ic_launcher.webp
│       │       │   ├── ic_launcher_round.webp
│       │       │   ├── ic_statify.webp
│       │       │   ├── ic_statify_background.webp
│       │       │   ├── ic_statify_foreground.webp
│       │       │   └── ic_statify_round.webp
│       │       ├── mipmap-mdpi
│       │       │   ├── ic_launcher.webp
│       │       │   ├── ic_launcher_round.webp
│       │       │   ├── ic_statify.webp
│       │       │   ├── ic_statify_background.webp
│       │       │   ├── ic_statify_foreground.webp
│       │       │   └── ic_statify_round.webp
│       │       ├── mipmap-xhdpi
│       │       │   ├── ic_launcher.webp
│       │       │   ├── ic_launcher_round.webp
│       │       │   ├── ic_statify.webp
│       │       │   ├── ic_statify_background.webp
│       │       │   ├── ic_statify_foreground.webp
│       │       │   └── ic_statify_round.webp
│       │       ├── mipmap-xxhdpi
│       │       │   ├── ic_launcher.webp
│       │       │   ├── ic_launcher_round.webp
│       │       │   ├── ic_statify.webp
│       │       │   ├── ic_statify_background.webp
│       │       │   ├── ic_statify_foreground.webp
│       │       │   └── ic_statify_round.webp
│       │       ├── mipmap-xxxhdpi
│       │       │   ├── ic_launcher.webp
│       │       │   ├── ic_launcher_round.webp
│       │       │   ├── ic_statify.webp
│       │       │   ├── ic_statify_background.webp
│       │       │   ├── ic_statify_foreground.webp
│       │       │   └── ic_statify_round.webp
│       │       ├── values
│       │       │   ├── colors.xml
│       │       │   ├── dimen.xml
│       │       │   ├── strings.xml
│       │       │   ├── styles.xml
│       │       │   └── themes.xml
│       │       ├── values-night
│       │       │   └── themes.xml
│       │       └── xml
│       │           ├── backup_rules.xml
│       │           └── data_extraction_rules.xml
│       └── test
│           └── java
│               └── app
│                   └── dev
│                       └── statify
│                           └── ExampleUnitTest.java
├── build.gradle.kts
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── ic_statify_logo.png
└── settings.gradle.kts
```

## 🛠️ Development Setup

### Native Android Setup
1. Open project in Android Studio
2. Sync Gradle and build project
3. Run on emulator or connected device

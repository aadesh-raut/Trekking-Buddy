# Trekking Buddy

An Android application built with Kotlin and Jetpack Compose for trekking enthusiasts to connect, plan adventures, and share experiences.

## Features

- 🔐 Firebase Authentication (Login & Signup)
- 📊 Firebase Analytics integration
- 🎨 Modern UI built with Jetpack Compose
- 🧭 Navigation using Navigation Compose
- 📱 Material Design 3 components

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Backend**: Firebase (Authentication, Analytics)
- **Navigation**: Navigation Compose
- **Build System**: Gradle (Kotlin DSL)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio** (Hedgehog | 2023.1.1 or later recommended)
- **JDK 11** or higher
- **Android SDK** (API level 24 or higher)
- **Git** (for cloning the repository)
- **Firebase Account** (for authentication and analytics)

## Getting Started

### 1. Clone the Repository

Clone the project from GitHub using one of the following methods:

#### Using HTTPS:
```bash
git clone https://github.com/yourusername/Trekking-Buddy.git
```

#### Using SSH:
```bash
git clone git@github.com:yourusername/Trekking-Buddy.git
```

#### Using GitHub CLI:
```bash
gh repo clone yourusername/Trekking-Buddy
```

After cloning, navigate to the project directory:
```bash
cd Trekking-Buddy
```

### 2. Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or select an existing one
3. Add an Android app to your Firebase project:
   - Package name: `com.example.trekkingbuddy`
   - Download the `google-services.json` file
4. Place the `google-services.json` file in the `app/` directory
   - The file should be at: `app/google-services.json`

### 3. Open Project in Android Studio

1. Launch Android Studio
2. Select **File** → **Open**
3. Navigate to the cloned `Trekking-Buddy` directory
4. Click **OK** to open the project
5. Android Studio will automatically sync the Gradle files and download dependencies

### 4. Sync Gradle Dependencies

If Gradle sync doesn't happen automatically:

1. Click **File** → **Sync Project with Gradle Files**
2. Wait for the sync to complete (this may take a few minutes on first run)

## Running the Project

### Using Android Studio

1. **Connect a Device or Emulator**:
   - Connect an Android device via USB with USB debugging enabled, OR
   - Create and start an Android Virtual Device (AVD) from the AVD Manager

2. **Run the App**:
   - Click the **Run** button (green play icon) in the toolbar, OR
   - Press `Shift + F10` (Windows/Linux) or `Ctrl + R` (Mac), OR
   - Select **Run** → **Run 'app'** from the menu

3. **Select Target Device**:
   - Choose your connected device or emulator from the device selection dialog
   - Click **OK**

The app will build and install on your selected device/emulator.

### Using Command Line

#### On macOS/Linux:
```bash
./gradlew installDebug
```

#### On Windows:
```bash
gradlew.bat installDebug
```

To run on a connected device:
```bash
adb shell am start -n com.example.trekkingbuddy/.MainActivity
```

### Building the APK

To build a debug APK:
```bash
./gradlew assembleDebug
```

The APK will be located at: `app/build/outputs/apk/debug/app-debug.apk`

To build a release APK:
```bash
./gradlew assembleRelease
```

The APK will be located at: `app/build/outputs/apk/release/app-release.apk`

## Project Structure

```
Trekking-Buddy/
├── app/
│   ├── build.gradle.kts          # App-level build configuration
│   ├── google-services.json      # Firebase configuration (add this)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/trekkingbuddy/
│   │   │   │   ├── MainActivity.kt      # Main activity
│   │   │   │   ├── AuthScreens.kt       # Login/Signup screens
│   │   │   │   ├── AuthViewModel.kt     # Authentication view model
│   │   │   │   ├── NavGraph.kt          # Navigation graph
│   │   │   │   └── ui/theme/            # Theme configuration
│   │   │   ├── res/                     # Resources (layouts, drawables, etc.)
│   │   │   └── AndroidManifest.xml      # App manifest
│   │   ├── androidTest/                 # Instrumented tests
│   │   └── test/                        # Unit tests
│   └── proguard-rules.pro               # ProGuard rules
├── build.gradle.kts                    # Project-level build configuration
├── settings.gradle.kts                  # Project settings
├── gradle/
│   └── libs.versions.toml              # Dependency version catalog
└── README.md                            # This file
```

## Troubleshooting

### Gradle Sync Failed
- Ensure you have a stable internet connection
- Check if Android Studio is up to date
- Try **File** → **Invalidate Caches / Restart**

### Firebase Not Working
- Verify `google-services.json` is in the `app/` directory
- Ensure the package name in Firebase matches `com.example.trekkingbuddy`
- Check that Firebase plugins are properly configured in `build.gradle.kts`

### Build Errors
- Clean the project: **Build** → **Clean Project**
- Rebuild: **Build** → **Rebuild Project**
- Delete `.gradle` folder and sync again

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

For questions or support, please open an issue on GitHub.

---

**Note**: Remember to replace `yourusername` in the clone URLs with your actual GitHub username or organization name.


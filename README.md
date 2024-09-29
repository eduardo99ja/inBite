# InBite Android Application

InBite is a modern Android application built with Kotlin, Jetpack Compose, Hilt for dependency injection, and a clean architecture approach. The project is modularized and structured to ensure scalability, maintainability, and ease of testing.

## Project Setup

### Prerequisites

- Android Studio Flamingo or newer
- Gradle 8.0 or newer
- Kotlin 1.9.0 or newer
- Minimum SDK: 24 (Android 7.0)
- Compile SDK: 34 (Android 14)

### Cloning the Project

To clone and run this project, you will need to have [Git](https://git-scm.com) installed on your machine.

1. Clone the repository:
   ```bash
   git clone https://github.com/eduardo99ja/inBite.git
   ```
   
2. Open the project in Android Studio.
3. Sync Gradle files:

    - Android Studio should automatically sync the Gradle files upon opening. If not, click File > Sync Project with Gradle Files.

### API Keys
This project uses external APIs such as Google services. The API keys should be provided in the local.properties file (not included in version control).

To set up the necessary API keys:

1. Create a local.properties file in the root directory of the project.
2. Add your Google API Key:

```properties
GOOGLE_API_KEY=your_google_api_key_here
```
The GOOGLE_API_KEY will be automatically added to the app's BuildConfig file during build time.

### Architecture

The project follows a multi-module structure and uses a clean architecture approach with the following layers:

- Core Modules: Contains reusable components and models (e.g., core:model, core:ui, core:data).
- Feature Modules: Represents different features of the app (e.g., feature:auth).
- Network Layer: Handles all API-related tasks (currently included in core:network).

### Libraries & Tools
#### Core Libraries 

- Kotlin - Programming language for Android development.
- Jetpack Compose - UI toolkit to build UIs in a declarative way.
- Hilt - Dependency injection library for Android.
- Timber - Logging utility.
- Arrow - Functional programming library in Kotlin.

### Android Jetpack Components

- Lifecycle - Lifecycle-aware components.
- ViewModel - Manages UI-related data in a lifecycle-conscious way.
- Navigation - Navigation within a Jetpack Compose app.

### Build & Run

To build and run the project:

- Open Android Studio and make sure the project is synced.
- In the toolbar, select your target device (emulator or connected device).
- Click Run (the green play button) to build and run the app on the selected device.




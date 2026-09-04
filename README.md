# Mi Workstation Launcher

A custom HyperOS-inspired launcher for Android phones and tablets with Workstation Mode.

[![Build & Release APK](https://github.com/HBAABA119/Mi-Workstation-Launcher/actions/workflows/build.yml/badge.svg)](https://github.com/HBAABA119/Mi-Workstation-Launcher/actions/workflows/build.yml)

## Download

| Build Type | Link |
|------------|------|
| **Stable Release** | [Latest Release](https://github.com/HBAABA119/Mi-Workstation-Launcher/releases/latest) |
| **Debug Build** | [Latest Dev Build](https://github.com/HBAABA119/Mi-Workstation-Launcher/releases) |

## Features

- **Workstation Mode** - Productivity-focused launcher optimized for large screens
- **Quick Settings Toggle** - Switch launchers from notification shade
- **Glassmorphism UI** - HyperOS 4 inspired frosted glass design
- **Widget Dashboard** - Clock, Weather, Calendar widgets
- **Smart App Grid** - Auto-categorized apps with search
- **Persistent Dock** - 5 pinned apps + recent apps
- **Universal Support** - Works on Samsung, Xiaomi, OnePlus, Oppo, Vivo, and more

## Screenshots

<!-- Add screenshots here -->

## Supported Devices

| Brand | Models |
|-------|--------|
| Samsung | Galaxy A series, S series, Z series, Note series |
| Xiaomi | Redmi, POCO, Mi series |
| OnePlus | All models |
| Oppo | All models |
| Realme | All models |
| Vivo | All models |
| Huawei | All models |
| Motorola | All models |
| Google | Pixel series |

## Setup in Android Studio

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- Android SDK 34

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/HBAABA119/Mi-Workstation-Launcher.git
   cd Mi-Workstation-Launcher
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Click `File` → `Open`
   - Navigate to the cloned folder
   - Click `OK`

3. **Wait for Gradle Sync**
   - Android Studio will automatically sync Gradle
   - Wait for all downloads to complete
   - If prompted, install any missing SDK components

4. **Build the Project**
   - Click `Build` → `Make Project`
   - Or run: `./gradlew assembleDebug`

5. **Run on Device**
   - Connect your Android device via USB
   - Enable USB Debugging in Developer Options
   - Click `Run` ▶️ or press Shift+F10

### Build Variants

| Variant | Description |
|---------|-------------|
| `debug` | Development build with debugging enabled |
| `release` | Optimized build with ProGuard |

### Command Line Build

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK (requires signing config)
./gradlew assembleRelease

# Clean build
./gradlew clean assembleDebug
```

## Quick Settings Setup

1. Install the app on your device
2. Swipe down from the top to open notification shade
3. Tap the edit (pencil) icon
4. Find "Workstation" tile
5. Drag it to the active tiles area
6. Tap the tile to toggle between launchers

## Configuration

### Change Default Dock Apps

Edit `LauncherState.kt` to customize default dock apps:

```kotlin
fun getDefaultDockApps(): List<String> {
    return listOf(
        "com.android.dialer",        // Phone
        "com.android.mms",           // Messages
        "com.android.chrome",        // Browser
        "com.android.camera",        // Camera
        "com.google.android.apps.photos" // Photos
    )
}
```

### Adjust Grid Columns

In `LauncherActivity.kt`, the grid automatically adapts:
- Phone (< 400dp): 3 columns
- Phone (400-600dp): 4 columns
- Tablet (600-900dp): 6 columns
- Large Tablet (> 900dp): 8 columns

## Architecture

```
com.xiaomi.workstation/
├── data/
│   ├── AppInfo.kt          # App model with categories
│   ├── LauncherState.kt    # Launcher switching logic
│   └── PrefsManager.kt     # SharedPreferences manager
├── service/
│   ├── WorkstationTileService.kt  # Quick Settings tile
│   └── BootReceiver.kt            # Boot receiver
├── ui/
│   ├── LauncherActivity.kt        # Main launcher
│   ├── AppDrawerActivity.kt       # App drawer
│   ├── components/
│   │   ├── GlassPanel.kt         # Glassmorphism base
│   │   ├── ClockWidget.kt        # Clock widget
│   │   ├── WeatherWidget.kt      # Weather widget
│   │   ├── CalendarWidget.kt     # Calendar widget
│   │   ├── SearchBar.kt          # Search bar
│   │   ├── AppGrid.kt           # App grid
│   │   └── Dock.kt              # Bottom dock
│   └── theme/
│       ├── Color.kt             # Ocean Navy palette
│       ├── Type.kt              # Typography
│       └── Theme.kt             # Material3 theme
└── MiWorkstationApp.kt          # Application class
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

- Create an [Issue](https://github.com/HBAABA119/Mi-Workstation-Launcher/issues) for bug reports
- Start a [Discussion](https://github.com/HBAABA119/Mi-Workstation-Launcher/discussions) for questions

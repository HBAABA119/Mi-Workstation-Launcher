# Changelog

All notable changes to Mi Workstation Launcher will be documented in this file.

## [1.1.0] - 2026-09-04

### Fixed
- Wallpaper integration - now properly loads and displays system wallpaper behind launcher
- Glassmorphism UI - complete visual overhaul with proper glass depth, shadows, and borders
- Clock widget - proper time display with live updates
- Weather widget - clean layout with temperature, condition, and location
- Calendar widget - shows current day, date, month with proper styling
- App grid - glass icon containers with proper shadows and borders
- Dock - glass panel with proper depth and icon styling
- Search bar - glass effect with visible styling
- Category tabs - glass background with accent colors when selected

### Changed
- Increased glass surface opacity from 12% to 24-40% for visibility
- Added proper elevation/shadows to all glass components
- Improved text contrast with 70-100% white opacity
- Better border visibility on glass panels
- Wallpaper blur effect for depth
- Dark gradient overlay for text readability

### Technical
- Fixed wallpaper bitmap loading for all drawable types (BitmapDrawable, ColorDrawable, VectorDrawable)
- Added FLAG_SHOW_WALLPAPER to window flags
- Updated theme to enable windowShowWallpaper for launcher activity

## [1.0.0] - 2026-09-04

### Added
- Initial release of Mi Workstation Launcher
- Workstation Mode with productivity-focused UI
- Glassmorphism design inspired by HyperOS 4
- Clock, Weather, and Calendar widgets
- Smart app grid with auto-categorization
- Persistent dock with 5 pinned apps + recent apps
- Global search across apps
- Quick Settings tile to toggle launchers
- Support for all Android devices (Samsung, Xiaomi, OnePlus, Oppo, Vivo, etc.)
- Responsive layout for phones and tablets
- Wallpaper integration
- Boot receiver for launcher state persistence

## [Unreleased]

### Planned
- Customizable icon packs
- Gesture controls
- More widget options (Battery, Music Player)
- Folder support in app grid
- Landscape mode optimizations
- App hiding feature
- Custom dock app selection

# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Android library project that wraps the Clover SDK to provide simplified printing functionality for Clover POS devices. The project contains two modules:

- **CloverPrinterSDK**: The main library module that provides the `CloverPrinter` wrapper class
- **app**: A demo/test application module

## Architecture

### CloverPrinterSDK Library Module

The core functionality is in `CloverPrinterSDK/src/main/java/com/elsistemas/cloverprintersdk/CloverPrinter.java`, which provides a simplified interface over the official Clover SDK:

- **CloverPrinter class**: Main API entry point
  - `printText(String text)`: Wraps `TextPrintJob` from Clover SDK
  - `printImage(byte[] bytes)`: Wraps `ImagePrintJob2` from Clover SDK, converts byte array to Bitmap

The library depends on `CloverSDK.jar` located in `CloverPrinterSDK/libs/`, which is the official Clover Android SDK (version 323).

### Key Dependencies

- Clover SDK JAR file: `CloverPrinterSDK/libs/CloverSDK.jar`
- Clover account handling via `CloverAccount.getAccount(context)`
- Requires Clover-specific permissions defined in AndroidManifest.xml

## Common Commands

### Build
```bash
# Build the entire project
gradlew build

# Build the library module only
gradlew :CloverPrinterSDK:build

# Build the app module only
gradlew :app:build
```

### Testing
```bash
# Run all tests
gradlew test

# Run unit tests for library module
gradlew :CloverPrinterSDK:test

# Run instrumented tests (requires connected device/emulator)
gradlew connectedAndroidTest
```

### Clean
```bash
gradlew clean
```

### Assemble Library AAR
```bash
gradlew :CloverPrinterSDK:assembleRelease
# Output: CloverPrinterSDK/build/outputs/aar/
```

## Build Configuration

- **Gradle Version**: 8.8.1 (AGP)
- **Java Version**: 11 (source and target compatibility)
- **Compile SDK**: 34 (library), 35 (app)
- **Min SDK**: 14 (library), 16 (app)
- **Target SDK**: 35 (app)
- **Package**: `com.elsistemas.cloverprintersdk.sdk`

## Important Notes

- The library requires a Clover device or emulator to function properly
- The `CloverSDK.jar` is a local dependency that must exist in `CloverPrinterSDK/libs/`
- The library uses MultiDex support
- Clover-specific permissions are required: `com.clover.permission.PRINTING` and `GET_ACCOUNTS`

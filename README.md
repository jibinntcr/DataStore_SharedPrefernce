# 📱 Student Enrollment App: SharedPreferences vs. DataStore

This project is a practical demonstration for **CS09: Software Development for Portable Devices** at **BITS Pilani (WILP)**. It highlights the evolution of Android storage from the legacy Preference Framework to modern, reactive DataStore.


## 🎓 Learning Objective

The goal is to understand how to make apps "remember" user settings across restarts while comparing two different architectural patterns:

1. **SharedPreferences (Legacy):** A manual, static key-value store.

2. **Jetpack DataStore (Modern):** A reactive, background-safe storage solution built on Coroutines and Flow.

## 🚀 The "Aha!" Moment: Manual vs. Reactive

The UI is specifically designed to show the technical difference between the two systems:

**SharedPreferences (Manual "Pull"):** When you save data, the UI does not refresh automatically. You must click the **"MANUALLY FETCH"** button to trigger a read from the XML file and update the screen.


**DataStore (Reactive "Push"):** The UI "subscribes" to a live stream (Flow) of data. The moment you hit **"SAVE,"** the result card updates instantly without any extra code or button clicks.


## 🛠️ Project Structure

* **`SettingsStore.kt`**: Contains the DataStore engine, defining the storage file and the reactive `Flow` for student data.


* **`MainActivity.kt`**: The BITS Pilani-themed dashboard built with Jetpack Compose that displays the side-by-side comparison.
* **`build.gradle.kts`**: Includes the `androidx.datastore:datastore-preferences` dependency.


## 📝 Key Features

**Persistent Storage**: Saves Student Name, ID, and Hostel status that survive app close and restart.

**Type Safety**: Uses specific key types (String, Int, Boolean) to prevent common crash-causing typos found in older systems.

**Background Safety**: DataStore operations are handled via Kotlin Coroutines to ensure the UI never lags during a save.


## ⚙️ Setup & Requirements

1. **Min SDK**: 24.
2. **Language**: Kotlin.
3. **UI Framework**: Jetpack Compose.
4. **Dependencies**:
```kotlin
[cite_start]implementation("androidx.datastore:datastore-preferences:1.0.0")  


## 🧪 How to Test the Demo

1. Enter a name in the text field.
2. Click **"SAVE TO BOTH"**.
3. **Observe**: Notice how the **DataStore Card** changes immediately, while the **SharedPreferences Card** stays the same.
4. Click **"MANUALLY FETCH"** to see the SharedPreferences card finally catch up.
5. **Restart Test**: Close the app completely and reopen it. Both values will be restored correctly from their respective storage files.


**Course:** CS09 | Software Development for Portable Devices

**Institution:** BITS Pilani (WILP)

**Instructor:** Jibin N

<img width="2940" height="1860" alt="image" src="https://github.com/user-attachments/assets/1ab0f5c0-4b3e-44a0-830f-a2c75a722514" />



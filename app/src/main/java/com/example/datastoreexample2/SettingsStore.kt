package com.example.datastoreexample2

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 1. Initialize the DataStore (The actual file on the phone)
// This creates a file named "bits_student_prefs" [cite: 190]
val Context.dataStore by preferencesDataStore(name = "bits_student_prefs")

// 2. Define unique Labels (Keys) to avoid typos [cite: 191]
val KEY_NAME = stringPreferencesKey("student_name")

// 3. The WRITE function (Saves data in the background)
// It must be 'suspend' because DataStore uses Coroutines for safety [cite: 163, 199]
suspend fun saveToDataStore(context: Context, name: String) {
    context.dataStore.edit { prefs ->
        prefs[KEY_NAME] = name
    }
}

// 4. The READ function (Returns a 'Flow' - a live stream of data) [cite: 211, 212]
// This allows the UI to update automatically without clicking "Read" [cite: 164, 282]
fun readFromDataStore(context: Context): Flow<String> {
    return context.dataStore.data.map { prefs ->
        prefs[KEY_NAME] ?: "No Name Saved"
    }
}
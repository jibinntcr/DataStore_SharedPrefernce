package com.example.datastoreexample2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

// BITS Pilani Palette
val BitsBlue = Color(0xFF003366)
val BitsRed = Color(0xFFC41230)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StorageDifferenceDemo()
        }
    }
}

@Composable
fun StorageDifferenceDemo() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope() // Needed for background saving [cite: 238, 244]

    // --- DATASTORE: LIVE SUBSCRIPTION ---
    // This variable 'watches' the storage stream (Flow) [cite: 262, 263]
    // It updates the UI AUTOMATICALLY when data is saved [cite: 164, 285]
    val dataStoreName by readFromDataStore(context).collectAsState(initial = "Waiting...")

    // --- SHARED PREFERENCES: MANUAL STATE ---
    // This ONLY changes when we manually write code to change it.
    var sharedPrefsName by remember { mutableStateOf("Click 'Fetch' to see SP data") }

    var nameInput by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // BITS Header
        Box(modifier = Modifier.fillMaxWidth().background(BitsBlue).padding(32.dp), contentAlignment = Alignment.Center) {
            Text("BITS PILANI: MANUAL VS REACTIVE", color = Color.White, fontWeight = FontWeight.Bold)
        }

        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Enter Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // THE SAVE BUTTON
            Button(
                onClick = {
                    // 1. SAVE TO SHARED PREFERENCES (Legacy/Manual)
                    val sp = context.getSharedPreferences("bits_sp", Context.MODE_PRIVATE)
                    sp.edit().putString("sp_name", nameInput).apply()

                    // 2. SAVE TO DATASTORE (Modern/Reactive)
                    scope.launch {
                        saveToDataStore(context, nameInput)
                    }

                    // NOTE: We are NOT manually updating 'sharedPrefsName' or 'dataStoreName' here.
                },
                colors = ButtonDefaults.buttonColors(containerColor = BitsRed),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SAVE TO BOTH")
            }

            Spacer(modifier = Modifier.height(30.dp))

            // DISPLAY 1: DATASTORE (AUTOMATIC)
            Text("1. DataStore Result (Automatic)", fontWeight = FontWeight.Bold, color = BitsBlue)
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))) {
                Text("Storage says: $dataStoreName", modifier = Modifier.padding(16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // DISPLAY 2: SHARED PREFERENCES (MANUAL)
            Text("2. SharedPreferences Result (Manual)", fontWeight = FontWeight.Bold, color = BitsBlue)
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = sharedPrefsName)
                    Button(onClick = {
                        // We must MANUALLY go and read the file to see the change [cite: 124, 126]
                        val sp = context.getSharedPreferences("bits_sp", Context.MODE_PRIVATE)
                        sharedPrefsName = "Storage says: " + (sp.getString("sp_name", "Empty") ?: "Empty")
                    }) {
                        Text("MANUALLY FETCH")
                    }
                }
            }
        }
    }
}
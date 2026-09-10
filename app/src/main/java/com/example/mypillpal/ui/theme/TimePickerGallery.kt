package com.example.mypillpal.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "1. Material 3 Dial Picker")
@Composable
fun PreviewDialPicker() {
    MyPillPalTheme {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("1. Clock Dial (Current)", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))
            TimePicker(state = rememberTimePickerState(initialHour = 8, initialMinute = 0))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "2. Material 3 Text Input")
@Composable
fun PreviewInputPicker() {
    MyPillPalTheme {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("2. Text Input", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))
            TimeInput(state = rememberTimePickerState(initialHour = 8, initialMinute = 0))
        }
    }
}

@Preview(showBackground = true, name = "3. Wheel / Spinner Picker")
@Composable
fun PreviewWheelPicker() {
    MyPillPalTheme {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("3. Wheel Picker (Spinner)", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))
            Row(
                Modifier.height(150.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                WheelColumn(items = (0..23).toList(), label = "HH")
                Text(":", fontSize = 24.sp, modifier = Modifier.padding(horizontal = 8.dp))
                WheelColumn(items = (0..59).toList(), label = "MM")
            }
        }
    }
}

@Composable
fun WheelColumn(items: List<Int>, label: String) {
    Box(
        Modifier
            .width(60.dp)
            .fillMaxHeight()
            .background(Color.LightGray.copy(0.2f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = rememberLazyListState(initialFirstVisibleItemIndex = 8),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items.size) { index ->
                Text(
                    text = String.format("%02d", items[index]),
                    fontSize = if (index == 8) 22.sp else 16.sp,
                    color = if (index == 8) MaterialTheme.colorScheme.primary else Color.Gray,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "4. Bottom Sheet Selector")
@Composable
fun PreviewBottomSheetPicker() {
    MyPillPalTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.1f)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                tonalElevation = 8.dp
            ) {
                Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .background(Color.Gray, RoundedCornerShape(2.dp)))
                    Spacer(Modifier.height(16.dp))
                    Text("Select Time", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(16.dp))
                    // Mock content
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        repeat(3) { Box(Modifier.size(60.dp).background(Color.LightGray, RoundedCornerShape(8.dp))) }
                    }
                    Spacer(Modifier.height(24.dp))
                    Button(onClick = {}, Modifier.fillMaxWidth()) { Text("Confirm Time") }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "5. Inline Stepper Picker")
@Composable
fun PreviewInlinePicker() {
    MyPillPalTheme {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("5. Inline Stepper", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                    .padding(8.dp)
            ) {
                IconButton(onClick = {}) { Icon(Icons.Default.Remove, null) }
                Text(
                    "08:00",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                IconButton(onClick = {}) { Icon(Icons.Default.Add, null) }
            }
        }
    }
}

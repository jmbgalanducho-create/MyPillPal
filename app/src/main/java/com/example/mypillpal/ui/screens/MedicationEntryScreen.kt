package com.example.mypillpal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.window.Dialog
import com.example.mypillpal.R
import com.example.mypillpal.data.local.entity.Medication
import com.example.mypillpal.data.repository.MedicationRepository
import com.example.mypillpal.reminders.ReminderManager
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationEntryScreen(
    repository: MedicationRepository,
    medicationId: Long? = null,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val reminderManager = remember { ReminderManager(context) }
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("Daily") }
    var selectedDays by remember { mutableStateOf(setOf(1, 2, 3, 4, 5, 6, 7)) }
    var reminderTime by remember { mutableStateOf("08:00") }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    val daysOfWeek = listOf(
        1 to stringResource(R.string.sunday),
        2 to stringResource(R.string.monday),
        3 to stringResource(R.string.tuesday),
        4 to stringResource(R.string.wednesday),
        5 to stringResource(R.string.thursday),
        6 to stringResource(R.string.friday),
        7 to stringResource(R.string.saturday)
    )

    LaunchedEffect(selectedDays) {
        frequency = if (selectedDays.size == 7) {
            "Daily"
        } else {
            selectedDays.sorted().joinToString(",")
        }
    }

    LaunchedEffect(medicationId) {
        if (medicationId != null) {
            val med = repository.getMedicationById(medicationId)
            if (med != null) {
                name = med.name
                dosage = med.dosage
                reminderTime = med.reminderTime
                // Parsing indices or "Daily"
                if (med.frequency == "Daily") {
                    selectedDays = setOf(1, 2, 3, 4, 5, 6, 7)
                } else {
                    selectedDays = med.frequency.split(",")
                        .mapNotNull { it.toIntOrNull() }
                        .toSet()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (medicationId == null) stringResource(R.string.add_medication) else stringResource(R.string.edit_medication)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    if (medicationId != null) {
                        IconButton(onClick = { showDeleteConfirmation = true }) {
                            Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text(stringResource(R.string.delete_confirmation_title)) },
                text = { Text(stringResource(R.string.delete_confirmation_message)) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            scope.launch {
                                medicationId?.let { id ->
                                    val med = repository.getMedicationById(id)
                                    if (med != null) {
                                        repository.deleteMedication(med)
                                        reminderManager.cancelReminder(id)
                                    }
                                }
                                showDeleteConfirmation = false
                                onNavigateBack()
                            }
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text(stringResource(R.string.delete))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteConfirmation = false }) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.med_name)) },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(Icons.Default.CameraAlt, contentDescription = null)
                }
            )

            OutlinedTextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text(stringResource(R.string.dosage_label)) },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.frequency_label),
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                daysOfWeek.forEach { (id, label) ->
                    val selected = selectedDays.contains(id)
                    FilterChip(
                        selected = selected,
                        onClick = {
                            selectedDays = if (selected) {
                                // Prevent deselecting all days, or handle "None"
                                if (selectedDays.size > 1) selectedDays - id else selectedDays
                            } else {
                                selectedDays + id
                            }
                        },
                        label = { Text(label, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }

            Text(
                text = stringResource(R.string.reminder_time_label),
                style = MaterialTheme.typography.titleMedium
            )
            
            WheelTimePicker(
                initialTime = reminderTime,
                onTimeChanged = { reminderTime = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    scope.launch {
                        val medication = Medication(
                            id = medicationId ?: 0,
                            name = name,
                            dosage = dosage,
                            frequency = frequency,
                            reminderTime = reminderTime
                        )
                        val id = if (medicationId == null) {
                            repository.insertMedication(medication)
                        } else {
                            repository.updateMedication(medication)
                            medicationId
                        }
                        
                        // Schedule reminder
                        val finalMedication = medication.copy(id = id)
                        reminderManager.scheduleReminder(finalMedication)
                        
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && dosage.isNotBlank()
            ) {
                Text(stringResource(R.string.save_medication))
            }
        }
    }
}

@Composable
fun WheelTimePicker(
    initialTime: String,
    onTimeChanged: (String) -> Unit
) {
    val parts = initialTime.split(":")
    val initialHour = parts.getOrNull(0)?.toIntOrNull() ?: 8
    val initialMinute = parts.getOrNull(1)?.toIntOrNull() ?: 0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WheelPicker(
            count = 24,
            initialIndex = initialHour,
            onIndexChanged = { h ->
                onTimeChanged(String.format(java.util.Locale.getDefault(), "%02d:%s", h, initialTime.split(":")[1]))
            }
        )
        Text(
            ":",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        WheelPicker(
            count = 60,
            initialIndex = initialMinute,
            onIndexChanged = { m ->
                onTimeChanged(String.format(java.util.Locale.getDefault(), "%s:%02d", initialTime.split(":")[0], m))
            }
        )
    }
}

@Composable
fun WheelPicker(
    count: Int,
    initialIndex: Int,
    onIndexChanged: (Int) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = initialIndex, pageCount = { count })

    LaunchedEffect(pagerState.currentPage) {
        onIndexChanged(pagerState.currentPage)
    }

    Box(
        modifier = Modifier
            .width(70.dp)
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        // Selection Highlight
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.small
                )
        )

        VerticalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(45.dp),
            contentPadding = PaddingValues(vertical = 57.dp), // Center the pages
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = lerp(
                            start = 0.3f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = lerp(
                            start = 0.7f,
                            stop = 1.1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleY = lerp(
                            start = 0.7f,
                            stop = 1.1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = String.format(java.util.Locale.getDefault(), "%02d", page),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = if (pageOffset < 0.5f) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 24.sp
                    ),
                    color = if (pageOffset < 0.5f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

package com.example.mypillpal

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import com.example.mypillpal.ui.navigation.Destination
import com.example.mypillpal.ui.screens.DashboardScreen
import com.example.mypillpal.ui.screens.HistoryScreen
import com.example.mypillpal.ui.screens.MedicationEntryScreen
import com.example.mypillpal.ui.screens.SettingsScreen
import com.example.mypillpal.ui.theme.MyPillPalTheme

class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _: Boolean -> }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        checkPermissions()
        
        val repository = (application as PillPalApplication).repository

        setContent {
            CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides this) {
                MyPillPalTheme {
                    val backStack = rememberNavBackStack(Destination.Dashboard as NavKey)
                    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
                    
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        sceneStrategies = listOf(listDetailStrategy),
                        entryProvider = { key ->
                            when (key) {
                                is Destination.Dashboard -> {
                                    NavEntry(
                                        key = key,
                                        metadata = ListDetailSceneStrategy.listPane(
                                            detailPlaceholder = {
                                                Text(stringResource(R.string.select_med_placeholder))
                                            }
                                        )
                                    ) {
                                        DashboardScreen(
                                            repository = repository,
                                            onAddMedication = { backStack.add(Destination.AddMedication) },
                                            onEditMedication = { id -> 
                                                backStack.removeIf { it is Destination.EditMedication }
                                                backStack.add(Destination.EditMedication(id)) 
                                            },
                                            onNavigateToHistory = {
                                                backStack.removeIf { it is Destination.History }
                                                backStack.add(Destination.History)
                                            },
                                            onNavigateToSettings = {
                                                backStack.removeIf { it is Destination.Settings }
                                                backStack.add(Destination.Settings)
                                            }
                                        )
                                    }
                                }
                                is Destination.AddMedication -> {
                                    NavEntry(
                                        key = key,
                                        metadata = ListDetailSceneStrategy.detailPane()
                                    ) {
                                        MedicationEntryScreen(
                                            repository = repository,
                                            onNavigateBack = { backStack.removeLastOrNull() }
                                        )
                                    }
                                }
                                is Destination.EditMedication -> {
                                    NavEntry(
                                        key = key,
                                        metadata = ListDetailSceneStrategy.detailPane()
                                    ) {
                                        MedicationEntryScreen(
                                            repository = repository,
                                            medicationId = (key as Destination.EditMedication).id,
                                            onNavigateBack = { backStack.removeLastOrNull() }
                                        )
                                    }
                                }
                                is Destination.History -> {
                                    NavEntry(
                                        key = key,
                                        metadata = ListDetailSceneStrategy.detailPane()
                                    ) {
                                        HistoryScreen(
                                            repository = repository,
                                            onNavigateBack = { backStack.removeLastOrNull() }
                                        )
                                    }
                                }
                                is Destination.Settings -> {
                                    NavEntry(
                                        key = key,
                                        metadata = ListDetailSceneStrategy.detailPane()
                                    ) {
                                        SettingsScreen(
                                            onNavigateBack = { backStack.removeLastOrNull() }
                                        )
                                    }
                                }
                                else -> error("Unknown key: $key")
                            }
                        }
                    )
                }
            }
        }
    }
}

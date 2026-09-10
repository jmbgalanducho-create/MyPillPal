package com.example.mypillpal.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Destination : NavKey {
    @Serializable
    data object Dashboard : Destination

    @Serializable
    data object AddMedication : Destination

    @Serializable
    data class EditMedication(val id: Long) : Destination

    @Serializable
    data object History : Destination

    @Serializable
    data object Settings : Destination
}

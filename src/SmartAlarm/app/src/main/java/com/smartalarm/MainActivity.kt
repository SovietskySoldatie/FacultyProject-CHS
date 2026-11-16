package com.smartalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.smartalarm.data.repository.InMemoryAlarmRepository
import com.smartalarm.data.repository.InMemoryChallengeRepository
import com.smartalarm.ui.screens.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alarmRepo = InMemoryAlarmRepository()
        val challengeRepo = InMemoryChallengeRepository()

        setContent {
            val alarms by alarmRepo.getAllAlarms().collectAsState(initial = emptyList())
            val challenges by challengeRepo.getAllChallenges().collectAsState(initial = emptyList())
            val scope = rememberCoroutineScope()
            var selectedTab by remember { mutableIntStateOf(0) }

            Scaffold(
                containerColor = DarkBg,
                bottomBar = {
                    NavigationBar(containerColor = CardBg) {
                        NavigationBarItem(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            icon = { Icon(Icons.Default.Notifications, contentDescription = "Alarms") },
                            label = { Text("Alarms") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = NeonLime,
                                selectedTextColor = NeonLime,
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                        NavigationBarItem(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            icon = { Icon(Icons.Default.AddCircle, contentDescription = "New Alarm") },
                            label = { Text("New Alarm") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = NeonLime,
                                selectedTextColor = NeonLime,
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                        NavigationBarItem(
                            selected = selectedTab == 2,
                            onClick = { selectedTab = 2 },
                            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                            label = { Text("Profile") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = NeonLime,
                                selectedTextColor = NeonLime,
                                indicatorColor = Color.Transparent,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )
                        )
                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    when (selectedTab) {
                        0 -> AlarmListScreen(
                            alarms = alarms,
                            onToggleAlarm = { id, enabled ->
                                scope.launch { alarmRepo.toggleAlarmState(id, enabled) }
                            },
                            onDeleteAlarm = { id ->
                                scope.launch { alarmRepo.deleteAlarm(id) }
                            },
                            onNavigateToAdd = { selectedTab = 1 }
                        )
                        1 -> AddAlarmScreen(
                            activeChallenges = challenges.filter { it.isActiveInPool },
                            onSaveAlarm = { newAlarm ->
                                scope.launch { alarmRepo.createAlarm(newAlarm) }
                                selectedTab = 0
                            },
                            onCancel = { selectedTab = 0 }
                        )
                        2 -> ProfileScreen(
                            challenges = challenges,
                            onToggleChallenge = { id, active ->
                                scope.launch { challengeRepo.toggleChallengeActive(id, active) }
                            }
                        )
                    }
                }
            }
        }
    }
}
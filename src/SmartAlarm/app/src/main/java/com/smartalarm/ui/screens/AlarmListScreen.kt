package com.smartalarm.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartalarm.domain.model.Alarm

val DarkBg = Color(0xFF0D0F14)
val CardBg = Color(0xFF161922)
val NeonLime = Color(0xFFA3E635)
val ElectricViolet = Color(0xFF8B5CF6)

@Composable
fun AlarmListScreen(
    alarms: List<Alarm>,
    onToggleAlarm: (String, Boolean) -> Unit,
    onDeleteAlarm: (String) -> Unit,
    onNavigateToAdd: () -> Unit
) {
    Scaffold(
        containerColor = DarkBg,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAdd,
                containerColor = NeonLime,
                contentColor = Color.Black,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Alarm")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Hey there!",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Next alarm in 7h 45m",
                color = NeonLime,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                items(alarms, key = { it.id }) { alarm ->
                    AlarmCard(
                        alarm = alarm,
                        onToggle = { enabled -> onToggleAlarm(alarm.id, enabled) },
                        onDelete = { onDeleteAlarm(alarm.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun AlarmCard(
    alarm: Alarm,
    onToggle: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val borderColor = if (alarm.isEnabled) NeonLime.copy(alpha = 0.5f) else Color.DarkGray
    val timeColor = if (alarm.isEnabled) NeonLime else Color.Gray

    Card(
        colors = CardDefaults.cardColors(containerColor = CardBg),
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = String.format("%02d:%02d", alarm.hour, alarm.minute),
                    color = timeColor,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked = alarm.isEnabled,
                        onCheckedChange = onToggle,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black,
                            checkedTrackColor = NeonLime
                        )
                    )
                    IconButton(onClick = onDelete) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Alarm",
                            tint = Color.Gray
                        )
                    }
                }
            }

            Text(
                text = formatDays(alarm.daysOfWeek),
                color = Color.LightGray,
                fontSize = 13.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Surface(
                color = if (alarm.isEnabled) ElectricViolet else Color(0xFF252A36),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "🎲 Spontaneous",
                    color = if (alarm.isEnabled) Color.White else Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

private fun formatDays(days: Set<Int>): String {
    if (days.isEmpty()) return "One-time alarm"
    if (days == setOf(6, 7)) return "Weekends"
    val names = mapOf(1 to "Mon", 2 to "Tue", 3 to "Wed", 4 to "Thu", 5 to "Fri", 6 to "Sat", 7 to "Sun")
    return days.sorted().mapNotNull { names[it] }.joinToString(", ")
}
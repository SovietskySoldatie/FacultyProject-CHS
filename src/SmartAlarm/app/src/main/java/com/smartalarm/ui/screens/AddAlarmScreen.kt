package com.smartalarm.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartalarm.domain.model.Alarm
import com.smartalarm.domain.model.Challenge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmScreen(
    activeChallenges: List<Challenge>,
    onSaveAlarm: (Alarm) -> Unit,
    onCancel: () -> Unit
) {
    val timeState = rememberTimePickerState(initialHour = 7, initialMinute = 45, is24Hour = true)
    var selectedDays by remember { mutableStateOf(setOf(1, 3, 5)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onCancel) {
                Text("Cancel", color = Color.Gray)
            }
            Text("Add Alarm", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            TextButton(onClick = {
                onSaveAlarm(
                    Alarm(
                        hour = timeState.hour,
                        minute = timeState.minute,
                        daysOfWeek = selectedDays
                    )
                )
            }) {
                Text("Save", color = NeonLime, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TimeInput(
            state = timeState,
            colors = TimePickerDefaults.colors(
                timeSelectorSelectedContainerColor = ElectricViolet,
                timeSelectorSelectedContentColor = Color.White,
                timeSelectorUnselectedContainerColor = CardBg,
                timeSelectorUnselectedContentColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Days Selector
        Text(
            text = "Days Selector",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(10.dp))

        val dayLabels = listOf(1 to "M", 2 to "T", 3 to "W", 4 to "T", 5 to "F", 6 to "S", 7 to "S")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dayLabels.forEach { (dayInt, label) ->
                val isSelected = selectedDays.contains(dayInt)
                FilledIconToggleButton(
                    checked = isSelected,
                    onCheckedChange = {
                        selectedDays = if (isSelected) selectedDays - dayInt else selectedDays + dayInt
                    },
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconToggleButtonColors(
                        containerColor = CardBg,
                        contentColor = Color.Gray,
                        checkedContainerColor = ElectricViolet,
                        checkedContentColor = Color.White
                    )
                ) {
                    Text(label, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Challenge Pool Preview (Read-only preview, no mode toggle)
        Card(
            colors = CardDefaults.cardColors(containerColor = CardBg),
            border = BorderStroke(1.dp, ElectricViolet.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Challenge Pool Preview", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Active in Pool: ${activeChallenges.size} challenges",
                    color = NeonLime,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    activeChallenges.take(2).forEach { challenge ->
                        Surface(color = Color(0xFF252A36), shape = RoundedCornerShape(50)) {
                            Text(
                                text = challenge.title,
                                color = Color.LightGray,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onSaveAlarm(
                    Alarm(
                        hour = timeState.hour,
                        minute = timeState.minute,
                        daysOfWeek = selectedDays
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = NeonLime, contentColor = Color.Black),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text("Set Alarm 🔔", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
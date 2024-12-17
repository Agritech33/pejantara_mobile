package com.agritech.pejantaraapp.ui.components

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DateSelector(
    context: Context,
    modifier: Modifier = Modifier,
    onDateSelected: (String) -> Unit = {}
) {
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Compatible with API 25
            selectedDate = formatter.format(calendar.time)
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = { datePickerDialog.show() },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(if (selectedDate.isEmpty()) "Pilih Tanggal" else selectedDate)
        }
    }
}


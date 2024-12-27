package fd.cmp.movie.screen.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.cancel_action
import cmp_movie.composeapp.generated.resources.confirm_action
import cmp_movie.composeapp.generated.resources.day_names_label
import cmp_movie.composeapp.generated.resources.invalid_date_error
import cmp_movie.composeapp.generated.resources.next_icon_description
import cmp_movie.composeapp.generated.resources.prev_icon_description
import fd.cmp.movie.helper.ColorHelper
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBottomSheet(
    minYear: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.year - 50,
    maxYear: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.year,
    minMonth: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.monthNumber,
    maxMonth: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.monthNumber,
    minDay: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).dayOfMonth,
    maxDay: Int = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).dayOfMonth,
    initialDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    errorMsg: String? = stringResource(Res.string.invalid_date_error),
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedDate by remember { mutableStateOf(initialDate) }
    var currentYear by remember { mutableStateOf(initialDate.year) }
    var currentMonth by remember { mutableStateOf(initialDate.month) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showYearPicker by remember { mutableStateOf(false) }
    var showMonthPicker by remember { mutableStateOf(false) }

    fun isValidDay(date: LocalDate) : Boolean {
        if (date.year == maxYear) {
            if (date.monthNumber == maxMonth) {
                return date.dayOfMonth <= maxDay
            }
        } else if (date.year == minYear) {
            if (date.monthNumber == minMonth) {
                return date.dayOfMonth >= minDay
            }
        }
        return true
    }

    fun isValidMonth(date: LocalDate) : Boolean {
        if (date.year == maxYear) {
            return date.monthNumber <= maxMonth
        } else if (date.year == minYear) {
            return date.monthNumber >= minMonth
        }
        return true
    }

    fun validateDate(date: LocalDate): Boolean {
        val isValidYear = date.year in minYear..maxYear
        val isValidMonth = isValidMonth(date)
        val isValidDay = isValidDay(date)
        return isValidYear && isValidMonth && isValidDay
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = ColorHelper.bottomSheetBackground,
        modifier = Modifier.fillMaxWidth()
    ) {
        when {
            showYearPicker -> {
                YearPicker(
                    minYear = minYear,
                    maxYear = maxYear,
                    selectedYear = currentYear,
                    onYearSelected = {
                        currentYear = it
                        showYearPicker = false
                    },
                    onDismiss = { showYearPicker = false }
                )
            }

            showMonthPicker -> {
                MonthPicker(
                    selectedMonth = currentMonth,
                    onMonthSelected = {
                        currentMonth = it
                        showMonthPicker = false
                    },
                    onDismiss = { showMonthPicker = false }
                )
            }

            else -> {
                DatePickerContent(
                    year = currentYear,
                    month = currentMonth,
                    selectedDate = selectedDate,
                    errorMessage = errorMessage,
                    onDateSelected = { date ->
                        if (validateDate(date)) {
                            selectedDate = date
                            errorMessage = null
                        } else {
                            errorMessage = errorMsg
                        }
                    },
                    onMonthClick = { showMonthPicker = true },
                    onYearClick = { showYearPicker = true },
                    onPreviousMonth = {
                        if (currentMonth == Month.JANUARY && currentYear > minYear) {
                            currentMonth = Month.DECEMBER
                            currentYear -= 1
                        } else if (currentMonth.ordinal > minMonth - 1 || currentYear > minYear) {
                            currentMonth = Month.entries[currentMonth.ordinal - 1]
                        }
                    },
                    onNextMonth = {
                        if (currentMonth == Month.DECEMBER && currentYear < maxYear) {
                            currentMonth = Month.JANUARY
                            currentYear += 1
                        } else if (currentMonth.ordinal < maxMonth - 1 || currentYear < maxYear) {
                            currentMonth = Month.entries[currentMonth.ordinal + 1]
                        }
                    },
                    onConfirm = {
                        if (validateDate(selectedDate)) {
                            onDateSelected(selectedDate)
                            onDismiss()
                        } else {
                            errorMessage = errorMsg
                        }
                    },
                    onCancel = { onDismiss() }
                )
            }
        }
    }
}

@Composable
fun DatePickerContent(
    year: Int,
    month: Month,
    selectedDate: LocalDate,
    errorMessage: String?,
    onDateSelected: (LocalDate) -> Unit,
    onMonthClick: () -> Unit,
    onYearClick: () -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(Res.string.prev_icon_description))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = onMonthClick) {
                    Text(month.name)
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onYearClick) {
                    Text(year.toString())
                }
            }
            IconButton(onClick = onNextMonth) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = stringResource(Res.string.next_icon_description))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Day headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val days = stringResource(Res.string.day_names_label).toCharArray()
            days.forEachIndexed { index, day ->
                Text(
                    text = day.toString(),
                    color = if (index == 0) Color.Red else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 14.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        DayGrid(
            year = year,
            month = month,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected
        )

        Spacer(modifier = Modifier.height(8.dp))

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Confirm and Cancel buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onCancel) {
                Text(stringResource(Res.string.cancel_action))
            }
            Button(onClick = onConfirm) {
                Text(stringResource(Res.string.confirm_action))
            }
        }
    }
}

@Composable
fun DayGrid(
    year: Int,
    month: Month,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    // Determine if the year is a leap year
    fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    // Get the number of days in the month, accounting for leap years
    val daysInMonth = when (month) {
        Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        else -> 31
    }

    // Get the first day of the week (1 = Monday, 7 = Sunday)
    val firstDayOfWeek = LocalDate(year, month.number, 1).dayOfWeek.ordinal

    // Generate all the dates in the month
    val days = (1..daysInMonth).map { day ->
        LocalDate(year, month.number, day)
    }

    // Create grid items with padding for start and end days
    val trailingEmptySlots = (7 - (daysInMonth + firstDayOfWeek - 1) % 7) % 7
    val adjustedTrailingSlots = if (trailingEmptySlots == 7) 0 else trailingEmptySlots
    val gridItems = List(firstDayOfWeek) { null } + days + List(adjustedTrailingSlots) { null }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        content = {
            items(gridItems.size) { index ->
                val date = gridItems[index]
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .background(
                            color = if (date == selectedDate) MaterialTheme.colorScheme.primary else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable(enabled = date != null) {
                            date?.let { onDateSelected(it) }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = date?.dayOfMonth?.toString() ?: "",
                        color = if (date == selectedDate) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )
}

@Composable
fun MonthPicker(
    selectedMonth: Month,
    onMonthSelected: (Month) -> Unit,
    onDismiss: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        content = {
            items(Month.entries.size) { index ->
                val month = Month.entries[index]
                TextButton(onClick = { onMonthSelected(month) }) {
                    Text(
                        text = month.name,
                        color = if (month == selectedMonth) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )
}

@Composable
fun YearPicker(
    minYear: Int,
    maxYear: Int,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var yearRangeStart by remember { mutableStateOf(selectedYear - selectedYear % 12) }

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { yearRangeStart -= 12 }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(Res.string.prev_icon_description))
            }
            Text("$yearRangeStart - ${yearRangeStart + 11}")
            IconButton(onClick = { yearRangeStart += 12 }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = stringResource(Res.string.next_icon_description))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            content = {
                items((yearRangeStart..yearRangeStart + 11).count()) { index ->
                    val year = yearRangeStart + index
                    if (year in minYear..maxYear) {
                        TextButton(onClick = { onYearSelected(year) }) {
                            Text(
                                text = year.toString(),
                                color = if (year == selectedYear) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        )
    }
}
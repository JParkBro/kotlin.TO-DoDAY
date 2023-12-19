package com.jparkbro.tododay.ui.todo

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.jparkbro.tododay.R
import com.jparkbro.tododay.utils.displayText
import com.jparkbro.tododay.utils.rememberFirstVisibleMonthAfterScroll
import com.jparkbro.tododay.utils.rememberFirstVisibleWeekAfterScroll
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.WeekDayPosition
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.core.yearMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

private const val TAG = "TODO_SCREEN"

@Composable
fun TodoScreen(
    adjacentMonths: Long = 100,
    navigateToItemEdit: () -> Unit,
    todoViewModel: TodoViewModel = hiltViewModel()
) {
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember(currentDate) { currentDate.yearMonth }
    val startMonth = remember(currentDate) { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember(currentDate) { currentMonth.plusMonths(adjacentMonths) }
    val daysOfWeek = remember { daysOfWeek() }

    val uiState = todoViewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        val monthState = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )
        val weekState = rememberWeekCalendarState(
            startDate = startMonth.atStartOfMonth(),
            endDate = endMonth.atEndOfMonth(),
            firstVisibleWeekDate = currentDate,
            firstDayOfWeek = daysOfWeek.first(),
        )
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            CalendarTitle(
                isMonthMode = uiState.isMonthMode,
                monthModeToggled = { todoViewModel.toggleMonthMode() },
                monthState = monthState,
                weekState = weekState,
                selectedDate = uiState.selectedDate,
            )
            CalendarHeader(daysOfWeek = daysOfWeek)

            val monthCalendarAlpha by animateFloatAsState(if (uiState.isMonthMode) 1f else 0f, label = "")
            val weekCalendarAlpha by animateFloatAsState(if (uiState.isMonthMode) 0f else 1f, label = "")
            var weekCalendarSize by remember { mutableStateOf(DpSize.Zero) }
            val visibleMonth = rememberFirstVisibleMonthAfterScroll(monthState)
            val weeksInVisibleMonth = visibleMonth.weekDays.count()
            val monthCalendarHeight by animateDpAsState(
                if (uiState.isMonthMode) {
                    weekCalendarSize.height * weeksInVisibleMonth
                } else {
                    weekCalendarSize.height
                },
                tween(durationMillis = 300), label = "",
            )
            val density = LocalDensity.current

            Box {
                HorizontalCalendar(
                    modifier = Modifier
                        .height(monthCalendarHeight)
                        .alpha(monthCalendarAlpha)
                        .zIndex(if (uiState.isMonthMode) 1f else 0f),
                    state = monthState,
                    dayContent = { day ->
                        val isSelectable = day.position == DayPosition.MonthDate
                        Day(
                            day.date,
                            isSelected = uiState.selectedDate == day.date,
                            isSelectable = isSelectable && day.date != uiState.selectedDate,
                            onClick = { date ->
                                todoViewModel.selectDate(date)
                            }
                        )
                    },
                )
                WeekCalendar(
                    modifier = Modifier
                        .wrapContentHeight()
                        .onSizeChanged {
                            val size = density.run { DpSize(it.width.toDp(), it.height.toDp()) }
                            if (weekCalendarSize != size) {
                                weekCalendarSize = size
                            }
                        }
                        .alpha(weekCalendarAlpha)
                        .zIndex(if (uiState.isMonthMode) 0f else 1f),
                    state = weekState,
                    dayContent = { day ->
                        val isSelectable = day.position == WeekDayPosition.RangeDate
                        Day(
                            day.date,
                            isSelected = uiState.selectedDate == day.date,
                            isSelectable = isSelectable && day.date != uiState.selectedDate,
                            onClick = { date ->
                                todoViewModel.selectDate(date)
                            }
                        )
                    },
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        FloatingActionButton(
            modifier = Modifier
                .padding(10.dp)
                .align(alignment = Alignment.End),
            onClick = navigateToItemEdit
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add))
        }
    }
}

@Composable
private fun CalendarTitle(
    isMonthMode: Boolean,
    monthModeToggled: () -> Unit,
    monthState: CalendarState,
    weekState: WeekCalendarState,
    selectedDate: LocalDate,
) {
    val visibleMonth = rememberFirstVisibleMonthAfterScroll(monthState)
    val visibleWeek = rememberFirstVisibleWeekAfterScroll(weekState)

    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MonthAndWeekCalendarTitle(
            isMonthMode = isMonthMode,
            currentMonth = if (isMonthMode) visibleMonth.yearMonth else visibleWeek.days.first().date.yearMonth,
            monthState = monthState,
            weekState = weekState,
        )
        WeekModeToggle(
            modifier = Modifier,
            isMonthMode = isMonthMode,
            monthModeToggled = { monthModeToggled() }
        )
    }
    LaunchedEffect(isMonthMode) {
        if (isMonthMode) {
            val targetMonth = selectedDate.yearMonth
            monthState.scrollToMonth(targetMonth)
            monthState.animateScrollToMonth(targetMonth)
        } else {
            weekState.scrollToWeek(selectedDate)
            weekState.animateScrollToWeek(selectedDate)
        }
    }
}

@Composable
fun MonthAndWeekCalendarTitle(
    isMonthMode: Boolean,
    currentMonth: YearMonth,
    monthState: CalendarState,
    weekState: WeekCalendarState,
) {
    val coroutineScope = rememberCoroutineScope()
    SimpleCalendarTitle(
        modifier = Modifier,
        currentMonth = currentMonth,
        goToPrevious = {
            coroutineScope.launch {
                if (isMonthMode) {
                    val targetMonth = monthState.firstVisibleMonth.yearMonth.previousMonth
                    monthState.animateScrollToMonth(targetMonth)
                } else {
                    val targetDate = weekState.firstVisibleWeek.days.first().date.minusDays(1)
                    weekState.animateScrollToWeek(targetDate)
                }
            }
        },
        goToNext = {
            coroutineScope.launch {
                if (isMonthMode) {
                    val targetMonth = monthState.firstVisibleMonth.yearMonth.nextMonth
                    monthState.animateScrollToMonth(targetMonth)
                } else {
                    val targetDate = weekState.firstVisibleWeek.days.last().date.plusDays(1)
                    weekState.animateScrollToWeek(targetDate)
                }
            }
        },
    )
}

@Composable
fun CalendarHeader(
    daysOfWeek: List<DayOfWeek>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                text = dayOfWeek.displayText(),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
fun Day(
    day: LocalDate,
    isSelected: Boolean,
    isSelectable: Boolean,
    onClick: (LocalDate) -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) Color.Green else Color.Transparent)
            .clickable(
                enabled = isSelectable,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = when {
            isSelected -> Color.White
            isSelectable -> Color.Unspecified
            else -> Color.Gray
        }
        Text(
            text = day.dayOfMonth.toString(),
            color = textColor
        )
    }
}

@Composable
fun WeekModeToggle(
    modifier: Modifier,
    isMonthMode: Boolean,
    monthModeToggled: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable { monthModeToggled() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
    ) {
        Checkbox(
            checked = isMonthMode,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary),
        )
        Text(text = stringResource(R.string.month_mode))
    }
}

@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarNavigationIcon(
            modifier = Modifier
                .fillMaxHeight(),
            icon = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Previous",
            onClick = goToPrevious,
        )
        Text(
            modifier = Modifier
                .testTag("MonthTitle"),
            text = currentMonth.displayText(),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
        )
        CalendarNavigationIcon(
            modifier = Modifier
                .fillMaxHeight(),
            icon = Icons.Default.KeyboardArrowRight,
            contentDescription = "Next",
            onClick = goToNext,
        )
    }
}

@Composable
fun CalendarNavigationIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(shape = CircleShape)
            .clickable(role = Role.Button, onClick = onClick)
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .align(Alignment.Center),
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}
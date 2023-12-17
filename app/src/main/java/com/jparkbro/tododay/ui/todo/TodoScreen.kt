package com.jparkbro.tododay.ui.todo

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
) {
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember(currentDate) { currentDate.yearMonth }
    val startMonth = remember(currentDate) { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember(currentDate) { currentMonth.plusMonths(adjacentMonths) }
    val daysOfWeek = remember { daysOfWeek() }

    var isWeekMode by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }

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
                isWeekMode = isWeekMode,
                weekModeToggled = { newWeekMode ->
                    isWeekMode = newWeekMode
                },
                monthState = monthState,
                weekState = weekState,
                selectedDate = selectedDate,
            )
            CalendarHeader(daysOfWeek = daysOfWeek)

            val monthCalendarAlpha by animateFloatAsState(if (isWeekMode) 0f else 1f, label = "")
            val weekCalendarAlpha by animateFloatAsState(if (isWeekMode) 1f else 0f, label = "")
            var weekCalendarSize by remember { mutableStateOf(DpSize.Zero) }
            val visibleMonth = rememberFirstVisibleMonthAfterScroll(monthState)
            val weeksInVisibleMonth = visibleMonth.weekDays.count()
            val monthCalendarHeight by animateDpAsState(
                if (isWeekMode) {
                    weekCalendarSize.height
                } else {
                    weekCalendarSize.height * weeksInVisibleMonth
                },
                tween(durationMillis = 300), label = "",
            )
            val density = LocalDensity.current

            Box {
                HorizontalCalendar(
                    modifier = Modifier
                        .height(monthCalendarHeight)
                        .alpha(monthCalendarAlpha)
                        .zIndex(if (isWeekMode) 0f else 1f),
                    state = monthState,
                    dayContent = { day ->
                        val isSelectable = day.position == DayPosition.MonthDate
                        Day(
                            day.date,
                            isSelected = selectedDate == day.date,
                            isSelectable = isSelectable && day.date != selectedDate
                        ) { day ->
                            selectedDate = if (selectedDate == day) null else day
                        }
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
                        .zIndex(if (isWeekMode) 1f else 0f),
                    state = weekState,
                    dayContent = { day ->
                        val isSelectable = day.position == WeekDayPosition.RangeDate
                        Day(
                            day.date,
                            isSelected = selectedDate == day.date,
                            isSelectable = isSelectable && day.date != selectedDate
                        ) { day ->
                            selectedDate = if (selectedDate == day) null else day
                        }
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
    isWeekMode: Boolean,
    weekModeToggled: (Boolean) -> Unit,
    monthState: CalendarState,
    weekState: WeekCalendarState,
    selectedDate: LocalDate?,
) {
    val visibleMonth = rememberFirstVisibleMonthAfterScroll(monthState)
    val visibleWeek = rememberFirstVisibleWeekAfterScroll(weekState)

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MonthAndWeekCalendarTitle(
            isWeekMode = isWeekMode,
            currentMonth = if (isWeekMode) visibleWeek.days.first().date.yearMonth else visibleMonth.yearMonth,
            monthState = monthState,
            weekState = weekState,
        )
        WeekModeToggle(
            modifier = Modifier,
            isWeekMode = isWeekMode,
        ) { weekMode ->
            coroutineScope.launch {
                if (weekMode) {
                    if (selectedDate != null) {
                        weekState.scrollToWeek(selectedDate)
                        weekState.animateScrollToWeek(selectedDate)
                    }
                } else {
                    if (selectedDate != null) {
                        val targetMonth = selectedDate.yearMonth
                        monthState.scrollToMonth(targetMonth)
                        monthState.animateScrollToMonth(targetMonth)
                    }
                }
                weekModeToggled(weekMode)
            }
        }
    }
}

@Composable
fun MonthAndWeekCalendarTitle(
    isWeekMode: Boolean,
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
                if (isWeekMode) {
                    val targetDate = weekState.firstVisibleWeek.days.first().date.minusDays(1)
                    weekState.animateScrollToWeek(targetDate)
                } else {
                    val targetMonth = monthState.firstVisibleMonth.yearMonth.previousMonth
                    monthState.animateScrollToMonth(targetMonth)
                }
            }
        },
        goToNext = {
            coroutineScope.launch {
                if (isWeekMode) {
                    val targetDate = weekState.firstVisibleWeek.days.last().date.plusDays(1)
                    weekState.animateScrollToWeek(targetDate)
                } else {
                    val targetMonth = monthState.firstVisibleMonth.yearMonth.nextMonth
                    monthState.animateScrollToMonth(targetMonth)
                }
            }
        },
    )
}

@Composable
fun CalendarHeader(daysOfWeek: List<DayOfWeek>) {
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
    isWeekMode: Boolean,
    weekModeToggled: (isWeekMode: Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable { weekModeToggled(!isWeekMode) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
    ) {
        Checkbox(
            checked = isWeekMode,
            onCheckedChange = null, // Check is handled by parent.
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary),
        )
        Text(text = stringResource(R.string.week_mode))
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
            icon = Icons.Default.KeyboardArrowRight,
            contentDescription = "Next",
            onClick = goToNext,
        )
    }
}

@Composable
private fun CalendarNavigationIcon(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
) {
    Icon(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .align(Alignment.Center),
        imageVector = icon,
        contentDescription = contentDescription,
    )
}

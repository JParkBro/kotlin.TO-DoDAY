package com.jparkbro.tododay.ui.dday

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.chargemap.compose.numberpicker.NumberPicker
import com.jparkbro.tododay.R
import com.jparkbro.tododay.model.DDay
import kotlinx.coroutines.launch
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DDayEntryScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: DDayEntryViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {  },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp
                    ) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.insertDDay()
                                navigateBack()
                            }
                        }
                    ) {
                        Text(text = "저장")
                    }
                },
            )
        }
    ) { innerPadding ->
        DDayManageBody(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            dDayUiState = viewModel.uiState,
            onDDayValueChange = viewModel::updateUiState
        )
    }
}

@Composable
fun DDayManageBody(
    modifier: Modifier = Modifier,
    dDayUiState: DDayManageUiState,
    onDDayValueChange: (DDayManageUiState) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        DDayManageTop(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            dDayUiState = dDayUiState
        )
        DDayInputForm(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            dDayUiState = dDayUiState,
            onDDayValueChange = onDDayValueChange
        )
    }
}

@Composable
fun DDayManageTop(
    modifier: Modifier = Modifier,
    dDayUiState: DDayManageUiState,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Yellow),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = dDayUiState.state,
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(dimensionResource(id = R.dimen.padding_small)),
            onClick = { /*TODO image 선택 추가*/ },
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                imageVector = Icons.Default.ImageSearch,
                contentDescription = ""
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DDayInputForm(
    modifier: Modifier = Modifier,
    dDayUiState: DDayManageUiState,
    onDDayValueChange: (DDayManageUiState) -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_large))
            .background(color = Color.Green),
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
        ) {
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    val updateState = dDayUiState.dDay.copy(state = true)
                    onDDayValueChange(dDayUiState.copy(dDay = updateState))
                }
            ) {
                Text(text = "D-Day")
            }
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = {
                    val updateState = dDayUiState.dDay.copy(state = false)
                    onDDayValueChange(dDayUiState.copy(dDay = updateState))
                }
            ) {
                Text(text = "날짜수")
            }
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = dDayUiState.dDay.title,
            onValueChange = { newTitle ->
                val updateTitle = dDayUiState.dDay.copy(title = newTitle)
                onDDayValueChange(dDayUiState.copy(dDay = updateTitle))
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .clickable { showBottomSheet = true },
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "날짜 선택")
            Text(text = "${dDayUiState.year}. ${dDayUiState.month}. ${dDayUiState.day} (${dDayUiState.dayOfWeek})")
        }
        if(showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
            ) {
                Column(
                    modifier = Modifier
                        .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        NumberPicker(
                            modifier = Modifier
                                .weight(1f),
                            value = dDayUiState.year,
                            onValueChange = { onDDayValueChange(dDayUiState.copy(year = it)) },
                            range = 1900..2100,
                            textStyle = TextStyle(fontSize = 16.sp)
                        )
                        NumberPicker(
                            modifier = Modifier
                                .weight(1f),
                            value = dDayUiState.month,
                            onValueChange = { onDDayValueChange(dDayUiState.copy(month = it)) },
                            range = 1..12,
                            textStyle = TextStyle(fontSize = 16.sp)

                        )
                        val yearMonth = YearMonth.of(dDayUiState.year, dDayUiState.month)
                        val daysInMonth = yearMonth.lengthOfMonth()
                        NumberPicker(
                            modifier = Modifier
                                .weight(1f),
                            value = dDayUiState.day,
                            onValueChange = { onDDayValueChange(dDayUiState.copy(day = it)) },
                            range = 1..daysInMonth,
                            textStyle = TextStyle(fontSize = 16.sp)
                        )
                    }
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = dimensionResource(id = R.dimen.padding_small),
                                vertical = dimensionResource(id = R.dimen.padding_medium)
                            )
                            .background(Color.Green),
                        onClick = { showBottomSheet = false }
                    ) {
                        Text(text = "확인")
                    }
                }
            }
        }
    }
}
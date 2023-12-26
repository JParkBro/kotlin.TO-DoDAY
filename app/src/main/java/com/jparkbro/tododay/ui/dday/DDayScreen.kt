package com.jparkbro.tododay.ui.dday

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jparkbro.tododay.R
import com.jparkbro.tododay.model.DDayEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DDayScreen(
    modifier: Modifier = Modifier,
    navigateToDDayEdit: () -> Unit,
//    navigateToDDayUpdate: (Int) -> Unit,
    dDayViewModel: DDayViewModel = hiltViewModel()
) {
    val uiState = dDayViewModel.uiState

    var typeDropDownState by remember { mutableStateOf(false) }
    var sortDropDownState by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = { Text(text = "전체") },
                navigationIcon = {
                    IconButton(
                        onClick = { typeDropDownState = !typeDropDownState }
                    ) {
                        Icon(imageVector = if (typeDropDownState) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription = "")
                    }
                    DropdownMenu(
                        modifier = Modifier,
                        offset = DpOffset(dimensionResource(id = R.dimen.padding_small), 0.dp),
                        expanded = typeDropDownState,
                        onDismissRequest = { typeDropDownState = !typeDropDownState }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "전체 보기") },
                            onClick = {
                                typeDropDownState = !typeDropDownState
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "날짜수 보기") },
                            onClick = {
                                typeDropDownState = !typeDropDownState
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "D-DAY 보기") },
                            onClick = {
                                typeDropDownState = !typeDropDownState
                            }
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { sortDropDownState = !sortDropDownState }
                    ) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "")
                    }
                    DropdownMenu(
                        modifier = Modifier,
                        offset = DpOffset(dimensionResource(id = R.dimen.padding_small), 0.dp),
                        expanded = sortDropDownState,
                        onDismissRequest = { sortDropDownState = !sortDropDownState }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "D-DAY 순") },
                            onClick = {
                                sortDropDownState = !sortDropDownState
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "오래된 날짜 순") },
                            onClick = {
                                sortDropDownState = !sortDropDownState
                            }
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier,
                onClick = navigateToDDayEdit,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.save)
                )
            }
        }
    ) { innerPadding ->
        DDayBody(
            modifier = Modifier
                .padding(innerPadding),
            dDayList = uiState.dDayList
        )
    }
}

@Composable
fun DDayBody(
    modifier: Modifier = Modifier,
    dDayList: List<DDayEntity>,
//    onDDayClick: (Int) -> Unit,
) {
    if (dDayList.isEmpty()) {
        Text(
            text = stringResource(R.string.no_item_description),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    } else {
        DDayList(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
            dDayList = dDayList,
//                onTodoClick = { onTodoClick(it.id) },
        )
    }
}

@Composable
fun DDayList(
    modifier: Modifier = Modifier,
    dDayList: List<DDayEntity>,
//    onDDayClick: (DDayEntity) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(items = dDayList, key = { it.id }) { dDay ->
            DDayItem(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small)),
//                    .clickable { onDDayClick(dDay) },
                dDay = dDay
            )
        }
    }
}

@Composable
fun DDayItem(
    modifier: Modifier = Modifier,
    dDay: DDayEntity
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = dDay.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
            }
            Text(
                text = dDay.date,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
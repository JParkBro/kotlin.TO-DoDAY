package com.jparkbro.tododay.ui.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jparkbro.tododay.R
import com.jparkbro.tododay.model.Todo
import com.jparkbro.tododay.model.TodoEntity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

private const val TAG = "TODO_COMPONENT"

/** List */
@Composable
fun TodoBody(
    modifier: Modifier = Modifier,
    todoList: List<TodoEntity>,
//    onTodoClick: (Int) -> Unit,
) {
    if (todoList.isEmpty()) {
        Text(
            text = stringResource(R.string.no_item_description),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    } else {
        TodoList(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
            todoList = todoList,
//                onTodoClick = { onTodoClick(it.id) },
        )
    }
}
@Composable
private fun TodoList(
    modifier: Modifier = Modifier,
    todoList: List<TodoEntity>,
//    onTodoClick: (TodoEntity) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = todoList, key = { it.id }) { todo ->
            TodoItem(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small)),
//                    .clickable { onTodoClick(todo) },
                todo = todo,
            )
        }
    }
}
@Composable
private fun TodoItem(
    modifier: Modifier = Modifier,
    todo: TodoEntity,
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
                    text = todo.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = todo.description,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = todo.date,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


/** Entry, Edit 화면 */
@Composable
fun TodoManageBody(
    modifier: Modifier = Modifier,
    todoUiState: TodoManageUiState,
    onTodoValueChange: (Todo) -> Unit,
    onSaveClick: () -> Unit,
    focusManager: FocusManager
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        TodoInputForm(
            modifier = Modifier
                .fillMaxWidth(),
            todo = todoUiState.todo,
            onValueChange = onTodoValueChange,
            focusManager = focusManager,
        )
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onSaveClick,
            enabled = todoUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = stringResource(id = R.string.add))
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoInputForm(
    modifier: Modifier = Modifier,
    todo: Todo,
    onValueChange: (Todo) -> Unit = {},
    focusManager: FocusManager
) {
    val colors : TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.scrim,
        unfocusedTextColor = MaterialTheme.colorScheme.scrim,
        disabledTextColor = MaterialTheme.colorScheme.scrim,
        focusedContainerColor = MaterialTheme.colorScheme.background,
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        disabledContainerColor = MaterialTheme.colorScheme.background,
        focusedPlaceholderColor = MaterialTheme.colorScheme.outline,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline,
        disabledPlaceholderColor = MaterialTheme.colorScheme.outline,
        disabledTrailingIconColor = MaterialTheme.colorScheme.outline,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
        disabledIndicatorColor = MaterialTheme.colorScheme.outline,
    )
    val today = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)

    val selectedDate = datePickerState.selectedDateMillis?.let {
        formatter.format(Date(it))
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = todo.title,
            placeholder = { Text(text = "제목을 입력해 주세요") },
            onValueChange = { onValueChange(todo.copy(title = it)) },
            singleLine = true,
            colors = colors
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { showDatePicker = true }),
            value = todo.date,
            placeholder = { Text(text = "YYYY-MM-DD") },
            trailingIcon = { Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "DatePicker") },
            readOnly = true,
            onValueChange = {  },
            enabled = false,
            singleLine = true,
            colors = colors
        )
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onValueChange(todo.copy(date = selectedDate.toString()))
                            showDatePicker = false
                        }
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDatePicker = false }
                    ) {
                        Text(text = "Cancel")
                    }
                },
            ) {
                DatePicker(
                    state = datePickerState,
                    dateValidator = { date ->
                        date >= today
                    },
                    showModeToggle = false
                )
            }
        }
//        // TODO Time Picker 추가
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = todo.startTime,
                placeholder = { Text(text = "HH:MM") },
                trailingIcon = { Icon(imageVector = Icons.Default.Timer, contentDescription = "TimePicker") },
                readOnly = true,
                onValueChange = { onValueChange(todo.copy(startTime = it)) },
                enabled = false,
                singleLine = true,
                colors = colors
            )
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = todo.endTime,
                placeholder = { Text(text = "HH:MM") },
                trailingIcon = { Icon(imageVector = Icons.Default.Timer, contentDescription = "TimePicker") },
                readOnly = true,
                onValueChange = { onValueChange(todo.copy(endTime = it)) },
                enabled = false,
                singleLine = true,
                colors = colors
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = todo.description,
            placeholder = { Text(text = "내용을 입력해 주세요") },
            onValueChange = { onValueChange(todo.copy(description = it)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = false,
            colors = colors
        )
    }
}
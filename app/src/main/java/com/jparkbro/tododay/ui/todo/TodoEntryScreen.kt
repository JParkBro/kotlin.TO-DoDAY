package com.jparkbro.tododay.ui.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jparkbro.tododay.R
import com.jparkbro.tododay.model.Todo
import com.jparkbro.tododay.ui.common.TododayTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEntryScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: TodoEntryViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = Color.Yellow,
        topBar = {
            TododayTopAppBar(
                title = stringResource(id = R.string.todo_edit),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        TodoEntryBody(
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun TodoEntryBody(
    modifier: Modifier = Modifier,
//    todoUiState: TodoUiState,
//    onTodoValueChange: (Todo) -> Unit,
//    onSaveClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        TodoInputForm(
            todo = Todo()
        )
        Button(
            onClick = { /*TODO*/ },
//            onClick = onSaveClick,
//            enabled = todoUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.add))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoInputForm(
    modifier: Modifier = Modifier,
    todo: Todo,
//    onValueChange: (Todo) -> Unit = {},
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = todo.title,
            onValueChange = { },
            label = { Text(text = stringResource(id = R.string.title)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            enabled = enabled,
            singleLine = true,
        )
        // TODO Date Picker 로 수정
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = todo.date,
            onValueChange = { },
            label = { Text(text = stringResource(id = R.string.title)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            enabled = enabled,
            singleLine = true,
        )

        // TODO Time Picker 로 수정
        Row(

        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = todo.startTime,
                onValueChange = { },
                label = { Text(text = stringResource(id = R.string.title)) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                enabled = enabled,
                singleLine = true,
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = todo.endTime,
                onValueChange = { },
                label = { Text(text = stringResource(id = R.string.title)) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                enabled = enabled,
                singleLine = true,
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = todo.description,
            onValueChange = { },
            label = { Text(text = stringResource(id = R.string.description)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            enabled = enabled,
            singleLine = true,
        )
    }
}
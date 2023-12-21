package com.jparkbro.tododay.ui.todo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jparkbro.tododay.R
import com.jparkbro.tododay.ui.common.TododayTopAppBar
import kotlinx.coroutines.launch

@Composable
fun TodoEditScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: TodoEditViewModel = hiltViewModel()
) {
//    Scaffold(
//        modifier = modifier
//            .fillMaxSize(),
//        topBar = {
//            TododayTopAppBar(
//                title = stringResource(id = R.string.todo_edit),
//                canNavigateBack = true,
//                navigateUp = onNavigateUp
//            )
//        }
//    ) { innerPadding ->
//        TodoManageBody(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxWidth(),
//            todoUiState = viewModel.uiState,
//            onTodoValueChange = viewModel::updateUiState,
//            onSaveClick = {
//                coroutineScope.launch {
//                    viewModel.insertTodo()
//                    navigateBack()
//                }
//            }
//        )
//    }
}
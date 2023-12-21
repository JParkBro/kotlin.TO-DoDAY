package com.jparkbro.tododay.ui.dday

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jparkbro.tododay.R
import kotlinx.coroutines.launch

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
//                                viewModel.insertDDay()
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
                .fillMaxWidth()
        )
    }
}

@Composable
fun DDayManageBody(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        DDayManageTop(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        DDayInputForm(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
        )
    }
}

@Composable
fun DDayManageTop(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Yellow),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "1일",
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

@Composable
fun DDayInputForm(
    modifier: Modifier = Modifier,
) {
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
                onClick = { /*TODO*/ }
            ) {
                Text(text = "D-Day")
            }
            Button(
                modifier = Modifier
                    .weight(1f),
                onClick = { /*TODO*/ }
            ) {
                Text(text = "날짜수")
            }
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {}
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "날짜 선택")
            Text(text = "2023. 12. 09(토)")
        }
        TextButton(
            modifier = Modifier
                .align(Alignment.End),
            onClick = { /*TODO date picker*/ }
        ) {
            Text(
                text = "달력으로 보기"
            )
        }



    }
}
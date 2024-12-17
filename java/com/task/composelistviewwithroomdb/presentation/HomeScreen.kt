package com.task.composelistviewwithroomdb.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.task.composelistviewwithroomdb.data.entity.DataEntity


@Composable
fun HomeScreen() {

    val viewModel = hiltViewModel<HomeViewModel>()
    Content(homeViewModel = viewModel)

}

@Composable
fun Content(
    homeViewModel: HomeViewModel,
) {
    val name by homeViewModel.studentName.collectAsStateWithLifecycle()
    val studentRoll by homeViewModel.studentRollNo.collectAsStateWithLifecycle()


    LaunchedEffect(key1 = true, block = {
        homeViewModel.getDataDetails()
    })

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f), contentAlignment = Alignment.TopCenter
        ) {
            TopContent(homeViewModel = homeViewModel,name,studentRoll)
        }

          BottomContent(homeViewModel = homeViewModel,name,studentRoll)
    }
}

@Composable
fun BottomContent(homeViewModel: HomeViewModel, name: String, studentRoll: String) {
    val onSave: (value: DataEntity) -> Unit = remember {
        return@remember homeViewModel::insertStudent
    }
    OutlinedButton(onClick = {
        onSave(
            DataEntity(
                data = name,
                descryption = studentRoll,
                //  storeDataPassOrFail = checked
            )
        )
    }) {
        Text(text = "Save")
    }
}



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopContent(
        homeViewModel: HomeViewModel,
        name: String,
        studentRoll: String,
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val onRollNoEntered: (value: String) -> Unit = remember {
                return@remember homeViewModel::setStudentRollNo
            }


            OutlinedTextField(
                value = studentRoll, onValueChange = {
                    onRollNoEntered(it)
                },
                placeholder = {
                    Text(text = "Enter the Data")
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(25.dp))
        }
    }



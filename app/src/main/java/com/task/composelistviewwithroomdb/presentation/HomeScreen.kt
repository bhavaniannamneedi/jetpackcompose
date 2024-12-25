package com.task.composelistviewwithroomdb.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            TopContent(homeViewModel = homeViewModel)
        }

          BottomContent(homeViewModel = homeViewModel)
    }
}

@Composable
fun BottomContent(homeViewModel: HomeViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Optional padding to the edges
    ) {

        val data by homeViewModel.userData.collectAsStateWithLifecycle()

        val onSave: (value: DataEntity) -> Unit = remember {
            return@remember homeViewModel::insertStudent
        }
        OutlinedButton(

            // modifier = Modifier.align(Alignment.BottomCenter)
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter) // Align the button at the bottom center
                .padding(bottom = 36.dp, start = 25.dp, end = 25.dp),


            colors = ButtonDefaults.outlinedButtonColors(Color(0xff655D8A)),


            onClick = {
                if(data.isEmpty()){
                    Toast.makeText(homeViewModel.getContext(), "Please enter the data", Toast.LENGTH_SHORT).show()
                }else if(data.toLowerCase().contains("error")){
                    Toast.makeText(homeViewModel.getContext(), "Enter Error", Toast.LENGTH_SHORT).show()
                }else {
                    onSave(
                        DataEntity(
                            descryption = data,
                            //  storeDataPassOrFail = checked
                        )

                    )
                    Toast.makeText(homeViewModel.getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show()

                }
            }) {
            Text(text = "Save", color = Color.White
            ,fontSize = 18.sp)
        }
    }
}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopContent(
        homeViewModel: HomeViewModel,

    ) {
        val studentRoll by homeViewModel.userData.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val onUserDataEntered: (value: String) -> Unit = remember {
                return@remember homeViewModel::setUserData
            }



            OutlinedTextField(modifier = Modifier.testTag("Enter the DB Data"),
                value = studentRoll, onValueChange = {
                    onUserDataEntered(it)
                    homeViewModel.resetInactivityTimer()
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



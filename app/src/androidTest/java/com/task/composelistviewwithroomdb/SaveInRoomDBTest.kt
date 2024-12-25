package com.task.composelistviewwithroomdb

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertTextEquals


import com.task.composelistviewwithroomdb.data.repository.Repository
import com.task.composelistviewwithroomdb.presentation.HomeScreen
import com.task.composelistviewwithroomdb.ui.theme.ComposeListviewwithRoomDBTheme
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import com.task.composelistviewwithroomdb.presentation.BottomContent
import com.task.composelistviewwithroomdb.presentation.HomeViewModel
import com.task.composelistviewwithroomdb.presentation.TopContent

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


class SaveInRoomDBTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<SaveInRoomDB>()

    @After
    fun tearDown() {
    }

    @Inject
    lateinit var repository: Repository


    @Test
    fun testScreen() {
        //composeTestRule.activity.resetInactivityTimer()
        composeTestRule.activity.setContent {
            HomeScreen()

        }
        composeTestRule.onNodeWithTag("Enter the DB Data").assertExists()


    }

    // Verify that the user can input text into the
    @Test
    fun testUserCanTypeInOutlinedTextField() {

        composeTestRule.activity.setContent {
            val viewModel = hiltViewModel<HomeViewModel>()

            TopContent(viewModel)

        }

        composeTestRule.onNodeWithTag("Enter the DB Data").performTextInput("Hello, World!")
        composeTestRule.onNodeWithText("Hello, World!").assertExists()
    }

/*

//Verify that the onValueChange function is called when the user types
    @Test
    fun testOnValueChangeCalled() {
        composeTestRule.activity.setContent {
            val viewModel = hiltViewModel<HomeViewModel>()

            TopContent(viewModel)
            var text by remember { mutableStateOf("") }
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Input") }
            )
        }

        composeTestRule.onNodeWithTag("Enter the DB Data").performTextInput("Test input")
        composeTestRule.onNodeWithText("Test input").assertExists()
    }
*/

    //Verify that the placeholder text is displayed when the OutlinedTextField is empty.
    @Test
    fun testPlaceholderText() {
        composeTestRule.activity.setContent {
            val viewModel = hiltViewModel<HomeViewModel>()

            TopContent(viewModel)

        }
        composeTestRule.onNodeWithTag("Enter the DB Data").performTextInput("Type something here...")
        composeTestRule.onNodeWithText("Type something here...").assertExists()
    }

    //check error
    @Test
    fun testEmptyState() {
        composeTestRule.activity.setContent {
            val viewModel = hiltViewModel<HomeViewModel>()
            TopContent(viewModel)
            BottomContent(viewModel)
        }
        composeTestRule.onNodeWithTag("Enter the DB Data").performTextInput("")

        composeTestRule.onNodeWithText("Save").performClick()
    }

//check error
    @Test
    fun testErrorState() {
        composeTestRule.activity.setContent {
            val viewModel = hiltViewModel<HomeViewModel>()

            TopContent(viewModel)
            BottomContent(viewModel)


        }
    composeTestRule.onNodeWithTag("Enter the DB Data").performTextInput("Type error here...")

        composeTestRule.onNodeWithText("Save").performClick()
        //composeTestRule.onNodeWithText("Invalid data").assertExists()
    }


    //check error
    @Test
    fun testSaveData() {
        composeTestRule.activity.setContent {
            val viewModel = hiltViewModel<HomeViewModel>()
            TopContent(viewModel)
            BottomContent(viewModel)
        }
        composeTestRule.onNodeWithTag("Enter the DB Data").performTextInput("THis is my first record")

        composeTestRule.onNodeWithText("Save").performClick()
    }

}
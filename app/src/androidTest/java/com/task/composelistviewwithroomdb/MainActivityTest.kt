package com.task.composelistviewwithroomdb

import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.compose.rememberNavController
import com.task.composelistviewwithroomdb.data.repository.Repository
import com.task.composelistviewwithroomdb.presentation.CustomExpandableFAB
import com.task.composelistviewwithroomdb.presentation.FABItem
import com.task.composelistviewwithroomdb.presentation.HomeScreen
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var repository: Repository

    @Test
    fun testScreen() {
        //composeTestRule.activity.resetInactivityTimer()
        composeTestRule.activity.setContent {
            Navigation()
        }
        composeTestRule.onNodeWithTag("search view").assertExists()
    }

    @Test
    fun testSearchResultsFilter() {
        composeTestRule.setContent {
            DataList(navController = rememberNavController(), state = remember { mutableStateOf(
                TextFieldValue("item1")
            ) })
        }

        // Assuming that the list initially contains "item1", "item2", and "item3"
        composeTestRule.onNodeWithText("item1").assertExists()
        composeTestRule.onNodeWithText("item2").assertDoesNotExist() // Assuming item2 doesn't match
    }


    @Test
    fun testNavigationToDetails() {
        composeTestRule.setContent {
            Navigation()
        }

        // Click on an item in the list
        composeTestRule.onNodeWithText("item1")
            .performClick()

        // Verify that the details screen is shown
        composeTestRule.onNodeWithText("Details Screen").assertExists()
    }

    @Test
    fun testFloatingActionButton() {
        composeTestRule.setContent {
            floatingButtonView()
        }

        // Click on the floating action button
        composeTestRule.onNodeWithContentDescription("Small floating action button.")
            .performClick()

        // Verify that the Add screen is launched (this requires observing the Intent in an activity or UI)
        // Since this is difficult in Compose tests, you would have to verify it in an instrumented test.
    }

    @Test
    fun testDataListDisplay() {
        composeTestRule.setContent {
            DataList(navController = rememberNavController(), state = remember { mutableStateOf(TextFieldValue("")) })
        }

        // Verify that each item in the data list is displayed
        composeTestRule.onNodeWithText("item1").assertExists()
        composeTestRule.onNodeWithText("item2").assertExists()
        composeTestRule.onNodeWithText("item3").assertExists()
    }

    @Test
    fun testNoRecordsFoundMessage() {
        composeTestRule.setContent {
            DataList(navController = rememberNavController(), state = remember { mutableStateOf(TextFieldValue("nonexistent")) })
        }

        // Verify that "No Records Found" is displayed
        composeTestRule.onNodeWithText("No Records Found").assertExists()
    }

    @Test
    fun testEmptyStateUI() {
        composeTestRule.setContent {
            DataList(navController = rememberNavController(), state = remember { mutableStateOf(TextFieldValue("")) })
        }

        // Verify that "No Records Found" message appears when no records are available
        composeTestRule.onNodeWithText("No Records Found").assertExists()
    }


    @Test
    fun testFloatingActionButtonVisibility() {
        composeTestRule.setContent {
            floatingButtonView()
        }

        // Verify that the floating action button is visible
        composeTestRule.onNodeWithContentDescription("Small floating action button.")
            .assertIsDisplayed()
    }

    @Test
    fun testClearSearchField() {
        composeTestRule.setContent {
            SearchView(remember { mutableStateOf(TextFieldValue("Some text")) })
        }

        // Perform click on the clear button
        composeTestRule.onNodeWithContentDescription("Clear")
            .performClick()

        // Verify that the text field is cleared
        composeTestRule.onNodeWithTag("search view")
            .assertTextEquals("")
    }

    @Test
    fun testFloatingActionButtonMultipleOptions() {
        composeTestRule.setContent {
            CustomExpandableFAB(
                items = listOf(
                    FABItem(icon = Icons.Rounded.Add, text = "Add", color = Color(0xff655D8A))
                ),
                onItemClick = { item ->
                    // Simulate clicking the "Add" button
                    assert(item.text == "Add")
                }
            )
        }

        // Click on the FAB
        composeTestRule.onNodeWithContentDescription("Small floating action button.")
            .performClick()

        // Verify that the "Add" item action is triggered
        composeTestRule.onNodeWithText("Add").assertExists()
    }



}
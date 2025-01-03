package com.task.composelistviewwithroomdb

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.task.composelistviewwithroomdb.data.entity.DataEntity
import com.task.composelistviewwithroomdb.presentation.CustomExpandableFAB
import com.task.composelistviewwithroomdb.presentation.FABItem
import com.task.composelistviewwithroomdb.presentation.HomeViewModel
import com.task.composelistviewwithroomdb.ui.theme.ComposeListviewwithRoomDBTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeListviewwithRoomDBTheme {
                val mContext = LocalContext.current
                // val context = LocalContext.current
                val itemList = listOf(
                    FABItem(icon = Icons.Rounded.Add, text = "Add", color = Color(0xff655D8A))
                )

                Scaffold(floatingActionButton = {
                    val systemUiController = rememberSystemUiController()

                    SideEffect {
                        systemUiController.setStatusBarColor(
                            color = Color(0xff655D8A),
                        )
                    }
                    CustomExpandableFAB(
                        items = itemList,
                        onItemClick = {item ->

                            when(item.text) {
                                "Add" ->

                                    mContext.startActivity(Intent(mContext, SaveInRoomDB::class.java)) }

                        }
                    )
                    }) {

                        padding -> // We need to pass scaffold's inner padding to content. That's why we use Box.
                    Box(modifier = Modifier.padding(20.dp)) {
                        Navigation()
                    }
                }

            }
        }
    }
}


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController)
        }
        composable(
            "details/{dataList}",
            arguments = listOf(navArgument("dataList") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("dataList")?.let { countryName ->
                DetailsScreen(data = countryName)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Column {
        SearchView(textState)
        DataList(navController = navController, state = textState)
    }
}
@Composable
fun DataList(navController: NavController, state: MutableState<TextFieldValue>) {
    val viewModel = hiltViewModel<HomeViewModel>()
    LaunchedEffect(key1 = true, block = {
        viewModel.getDataDetails()
    })
    val contents by viewModel.dataDetailsList.collectAsStateWithLifecycle()

    val listData =contents
    var filteredListData: List<DataEntity>
    if(!contents.isEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            val searchedText = state.value.text
            filteredListData = if (searchedText.isEmpty()) {
                listData
            } else {
                val resultList = ArrayList<DataEntity>()
                for (country in listData) {
                    if (country.descryption.lowercase(Locale.getDefault())
                            .contains(searchedText.lowercase(Locale.getDefault()))
                    ) {
                        resultList.add(country)
                    }
                }
                resultList
            }
            items(filteredListData) { filteredCountry ->
                DataListItem(
                    countryText = filteredCountry.descryption,
                    onItemClick = { selectedCountry ->
                        navController.navigate("details/$selectedCountry") {
                            // Pop up to the start destination of the graph to
                            // avoid building up aƒž large stack of destinations
                            // on the back stack as users select items
                            popUpTo("main") {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    }
                )
                // * List Dividers
                ListDivider()

                // * List Divider Padding
                // ListDividerPadding()

            }
        }
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background( Color.White)
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "No Records Found",
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 22.sp
            )
        }
    }
}
@Composable
fun ListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color =Color.Black
    )
}
@Composable
fun DataListItem(countryText: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(countryText) })
            .background( Color.White)
            .height(57.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp,8.dp, 16.dp,8.dp))
    ) {
        Text(text = countryText, fontSize = 18.sp, color = Color.Black,
            modifier = Modifier.padding(horizontal = 14.dp),
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    OutlinedTextField(

        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier.testTag("search view")
            .fillMaxWidth().padding(vertical = 20.dp).padding(top = 16.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(10.dp)
                            .size(35.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp), // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.outlinedTextFieldColors(
            //focusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            //unfocusedContainerColor = Color.White,
            unfocusedTextColor = Color.Black,
            /* focusedIndicatorColor = Color.Transparent,
             unfocusedIndicatorColor = Color.Transparent,
             disabledIndicatorColor = Color.Transparent,*/
            cursorColor = Color.Red,
        )
    )
}

@Immutable
data class ImmutableStudent(val studentEntity: DataEntity)


@Composable
fun floatingButtonView(){
    Column(
        modifier = Modifier
            .padding(58.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
    ) {

        val mContext = LocalContext.current

        SmallFloatingActionButton(
            onClick = {
                mContext.startActivity(Intent(mContext, SaveInRoomDB::class.java))

                // Fetching the Local Context

            },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary
        ) {
            Icon(Icons.Filled.Add, "Small floating action button.")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FloatingButtonPreview() {
    ComposeListviewwithRoomDBTheme {
        floatingButtonView()
    }
}


@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState)
}

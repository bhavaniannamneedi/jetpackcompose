package com.task.composelistviewwithroomdb

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.task.composelistviewwithroomdb.data.entity.ListModel
import com.task.composelistviewwithroomdb.ui.theme.ComposeListviewwithRoomDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeListviewwithRoomDBTheme{
                ScaffoldExample()
            }
        }


    }



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScaffoldExample() {
        var presses by remember { mutableIntStateOf(0) }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Top app bar")
                    }
                )
            },
            /*bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Bottom app bar",
                    )
                }
            },*/
            floatingActionButton = {
                FloatingActionButton(onClick = { presses++
                    println(presses)

                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
               /* Text(
                    modifier = Modifier.padding(8.dp),
                    text =
                    """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button $presses times.
                """.trimIndent(),
                )*/
              // nestedScroolItems()
                customListView(LocalContext.current)
                selectedImageView()

            }
        }
    }

    @Composable
    private fun selectedImageView() {

        val configuration = LocalConfiguration.current
        var height= configuration.screenHeightDp
        var width=configuration.screenWidthDp

        var hPercentage = (80/height) * 100

        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .size(width = 240.dp, height = 100.dp)
        ) {
            /*Image(
                text = "Outlined",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )*/
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun customListView(context: Context) {
        // in the below line, we are creating and
        // initializing our array list
        lateinit var courseList: List<ListModel>
        courseList = ArrayList<ListModel>()

        // in the below line, we are adding data to our list.
        courseList = courseList + ListModel("2 Slider with top", R.drawable.icon)
        courseList = courseList + ListModel("2 Slider with bottom", R.drawable.icon)
        courseList = courseList + ListModel("3 Slider with top", R.drawable.icon)
        courseList = courseList + ListModel("3 Slider with bottom", R.drawable.icon)
        courseList = courseList + ListModel("4 Slider with top", R.drawable.icon)
        courseList = courseList + ListModel("4 Slider with bottom", R.drawable.icon)


        courseList = courseList + ListModel("2 Open door with top", R.drawable.icon)
        courseList = courseList + ListModel("2 Open door with bottom", R.drawable.icon)
        courseList = courseList + ListModel("3 Open door with top", R.drawable.icon)
        courseList = courseList + ListModel("3 Open door with bottom", R.drawable.icon)
        courseList = courseList + ListModel("4 Open door with top", R.drawable.icon)
        courseList = courseList + ListModel("4 Open door with bottom", R.drawable.icon)


     /*   courseList = courseList + ListModel("Python", R.drawable.icon)
        courseList = courseList + ListModel("C++", R.drawable.icon)
        courseList = courseList + ListModel("C#", R.drawable.icon)
        courseList = courseList + ListModel("Java", R.drawable.icon)
        courseList = courseList + ListModel("Node Js", R.drawable.icon)*/

        // in the below line, we are creating a
        // lazy row for displaying a horizontal list view.
        LazyRow {
            // in the below line, we are setting data for each item of our listview.
            itemsIndexed(courseList) { index, item ->
                // in the below line, we are creating a card for our list view item.
                Card(
                    onClick = {
                        Toast.makeText(
                            context,
                            courseList[index].languageName + " selected..",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    /*onClick = (
                            Toast.makeText(
                                context,
                                courseList[index].languageName + " selected..",
                                Toast.LENGTH_SHORT
                            ).show()
                            ),*/
                    // inside our grid view on below line
                    // we are adding on click for each item of our grid view.
                   /* onClick = {
                        // inside on click we are displaying the toast message.
                        Toast.makeText(
                            context,
                            courseList[index].languageName + " selected..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }*/
                    // in the below line, we are adding
                    // padding from our all sides.
                    modifier = Modifier
                        .padding(8.dp)
                        .width(120.dp),

                    // in the below line, we are adding
                    // elevation for the card.
                  //  elevation = 6.dp
                )
                {
                    // in the below line, we are creating
                    // a row for our list view item.
                    Column(
                        // for our row we are adding modifier
                        // to set padding from all sides.
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // in the below line, inside row we are adding spacer
                        Spacer(modifier = Modifier.height(5.dp))

                        // in the below line, we are adding Image to display the image.
                        Image(
                            // in the below line, we are specifying the drawable image for our image.
                            painter = painterResource(id = courseList[index].languageImg),

                            // in the below line, we are specifying
                            // content description for our image
                            contentDescription = "img",

                            // in the below line, we are setting height
                            // and width for our image.
                            modifier = Modifier
                                .height(60.dp)
                                .width(60.dp)
                                .padding(5.dp),

                            alignment = Alignment.Center
                        )

                        // in the below line, we are adding spacer between image and a text
                        Spacer(modifier = Modifier.height(5.dp))

                        // in the below line, we are creating a text.
                        Text(
                            // inside the text on below line we are
                            // setting text as the language name
                            // from our model class.
                            text = courseList[index].languageName,

                            // in the below line, we are adding padding
                            // for our text from all sides.
                            modifier = Modifier.padding(4.dp),

                            // in the below line, we are adding color for our text
                            color = Color.Black, textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }



    @Composable
    private fun nestedScroolItems() {
        val nestedScrollInterop = rememberNestedScrollInteropConnection()
        // Add the nested scroll connection to your top level @Composable element
        // using the nestedScroll modifier.
        LazyColumn(modifier = Modifier.nestedScroll(nestedScrollInterop)) {
            items(6) { item ->
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(item.toString())
                }
            }
        }
    }
}
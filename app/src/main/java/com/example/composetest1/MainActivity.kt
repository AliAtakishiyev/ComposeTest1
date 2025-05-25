package com.example.composetest1

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.composetest1.ui.theme.ComposeTest1Theme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            ComposeTest1Theme {
                Main()
            }
        }
    }
}

fun loadCitiesFromAssets(context: Context): List<City>{

    val json = context.assets.open("cities.json")
        .bufferedReader().use { it.readText() }

    val gson = Gson()
    val type = object : TypeToken<List<City>>() {}.type


    return gson.fromJson(json, type)
}

@Composable
fun Main() {

    val context = LocalContext.current
    val cities = remember { loadCitiesFromAssets(context) }

    var url by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            items (cities){ city ->



                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(4.dp)
                ){

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        url = city.imageUrl

                        AsyncImage(
                            model = url,
                            contentDescription = null
                        )

                        Text(
                            city.name,
                            modifier = Modifier.padding(4.dp).align(Alignment.TopEnd)
                        )
                    }

                }
            }

        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    ComposeTest1Theme {
        Main()
    }
}
package com.durriix.a06a09a2024

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.durriix.a06a09a2024.ui.theme.A06a09a2024Theme
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A06a09a2024Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    var data by remember { mutableStateOf(listOf<Pair<String, String>>()) }
                    LaunchedEffect(Unit) {
                        withContext(IO) {
                            data = getData()
                        }
                    }
                    LazyColumn() {
                        items(data.size) {
                            var a = data[it]
                            val intent =
                                remember { Intent(Intent.ACTION_VIEW, Uri.parse(a.second)) }
                            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = a.first)
                                Button(onClick = { context.startActivity(intent) }) {
                                    Text(text = "Przejdź")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getData(): List<Pair<String, String>> {
    var page = Jsoup.connect("https://www.wp.pl/").get()
    var href = page.select("[data-testid=\"native-link\"]")
    Log.i("asd", href.size.toString())
    return href.map {
        Pair(it.text(), it.attr("href"))
    }
}
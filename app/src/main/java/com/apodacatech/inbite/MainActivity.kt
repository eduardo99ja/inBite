package com.apodacatech.inbite

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.apodacatech.ui.theme.InBiteTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //if (BuildConfig.DEBUG) {
        //Timber.plant(Timber.DebugTree())
        //}
        setContent {
            InBiteTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Eduardo")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    Column(modifier = modifier) {
        Text(
            text = "Hello $name!",

            )
        Button(onClick = {
            Log.d("login", "Button clicked")
            viewModel.login("eduardo@gmail.com", "Abc123")
        }) {
            Text("Click me")

        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InBiteTheme {
        Greeting("Android")
    }
}
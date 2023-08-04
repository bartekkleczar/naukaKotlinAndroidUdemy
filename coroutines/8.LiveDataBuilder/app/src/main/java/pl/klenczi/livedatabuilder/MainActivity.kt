package pl.klenczi.livedatabuilder

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pl.klenczi.livedatabuilder.ui.theme.LiveDataBuilderTheme

class MainActivity : ComponentActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.users.observe(this, Observer {myUsers ->
            myUsers.forEach{
                Log.i("Main", "name: ${it.name}")
            }
        })
    }
}
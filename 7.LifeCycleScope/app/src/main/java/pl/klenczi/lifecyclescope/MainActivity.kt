package pl.klenczi.lifecyclescope

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.klenczi.lifecyclescope.ui.theme.LifeCycleScopeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            Log.i("Main", "CREATED")
        }

        lifecycleScope.launchWhenStarted {
            Log.i("Main", "STARTED")
        }

        lifecycleScope.launchWhenResumed {
            Log.i("Main", "RESUMED")
        }
        setContent {
            var progress by remember { mutableStateOf(0f) }
            CircularProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            )
            lifecycleScope.launch(Dispatchers.IO) {
                /*delay(5000)
                progress = 1f
                delay(10000)
                progress = 0f*/
                Log.i("Main", "Thread: ${Thread.currentThread().name}")
            }
        }
    }
}
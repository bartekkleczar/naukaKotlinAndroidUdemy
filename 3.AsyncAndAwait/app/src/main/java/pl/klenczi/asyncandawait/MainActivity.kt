package pl.klenczi.asyncandawait

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.klenczi.asyncandawait.ui.theme.AsyncAndAwaitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("Main", "--Calculation started...")
            val stock1 = async { getStock1() }
            val stock2 = async { getStock2() }
            val total = stock1.await() + stock2.await()
            Log.i("Main", "--total: $total")
        }
        setContent {}
    }
}

private suspend fun getStock1(): Int{
    delay(10000)
    Log.i("Main", "--stock 1 returned")
    return 55000
}

private suspend fun getStock2(): Int{
    delay(8000)
    Log.i("Main", "--stock 2 returned")
    return 35000
}
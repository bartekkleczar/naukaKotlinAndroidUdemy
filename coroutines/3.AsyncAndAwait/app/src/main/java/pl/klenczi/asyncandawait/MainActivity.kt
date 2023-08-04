package pl.klenczi.asyncandawait

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        CoroutineScope(Dispatchers.Main).launch {
            Log.i("Main", "--Calculation started...")
            val stock1 = async(Dispatchers.IO) {
                getStock1()
            }
            val stock2 = async(Dispatchers.IO) {
                getStock2()
            }
            val total = stock1.await() + stock2.await()
            Toast.makeText(applicationContext, "Total: $total", Toast.LENGTH_SHORT).show()
        }
        setContent {}
    }
}

private suspend fun getStock1(): Int {
    delay(10000)
    Log.i("Main", "--Stock 1 returned")
    return 55000
}

private suspend fun getStock2(): Int {
    delay(8000)
    Log.i("Main", "--Stock 2 returned")
    return 35000
}
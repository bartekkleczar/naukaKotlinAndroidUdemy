package pl.klenczi.codingchallange1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.klenczi.codingchallange1.ui.theme.CodingChallange1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize()
            ){
                Button(onClick = {
                    CoroutineScope(Dispatchers.Main).launch{
                        Log.i("Mai", "Thread [Main]: ${Thread.currentThread().name}")

                    }
                }) {
                    Text(
                        text = "MAIN",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Button(onClick = {
                    CoroutineScope(Dispatchers.IO).launch{
                        Log.i("Main", "Thread [Background]: ${Thread.currentThread().name}")
                    }
                }) {
                    Text(
                        text = "BACKGROUND",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

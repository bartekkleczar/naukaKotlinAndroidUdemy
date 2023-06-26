package pl.klenczi.unstructuredconcurrency

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var count by remember {
                mutableStateOf(0)
            }
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            count = UserDataManager1().getTotalUserCount()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(110.dp)
                        .padding(top = 50.dp)
                ) {
                    Text(
                        text = "DOWNLOAD USER DATA",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }

                Text(
                    text = count.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 70.dp)
                )

                Button(
                    onClick = { count++ },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(85.dp)
                        .padding(top = 25.dp)
                ) {
                    Text(
                        text = "CLICK HERE",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
            }
        }
    }
}

private fun downloadUserData() {
    for (i in 1..200000) {
        Log.i("Main", "Downloading user $i in ${Thread.currentThread().name}")
    }
}
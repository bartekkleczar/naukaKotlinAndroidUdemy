package pl.klenczi.roomdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pl.klenczi.roomdemo.db.Subscriber
import pl.klenczi.roomdemo.db.SubscriberDao
import pl.klenczi.roomdemo.db.SubscriberDatabase
import pl.klenczi.roomdemo.db.SubscriberRepository
import pl.klenczi.roomdemo.ui.theme.RoomDemoTheme

class MainActivity : ComponentActivity() {
    private lateinit var subscriberViewModel: SubscriberViewModel
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dao = SubscriberDatabase.getInstance(application).subscriberDao
            val repository = SubscriberRepository(dao)
            val factory = SubscriberViewModelFactory(repository)
            subscriberViewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
            displaySubscribersList()
            var textStateName by remember { mutableStateOf("") }
            var textStateEmail by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                val pad = 20.dp
                TextField(
                    value = textStateName, onValueChange = { textStateName = it },
                    label = { Text(text = "Subscriber's Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = pad)
                )
                TextField(
                    value = textStateEmail, onValueChange = { textStateEmail = it },
                    label = { Text(text = "Subscriber's Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = pad)
                )

                Row(
                    modifier = Modifier.padding(top = pad)
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                        modifier = Modifier.background(Color.Transparent, shape = RoundedCornerShape(5.dp)),
                        onClick = {
                            subscriberViewModel.insert(Subscriber(0, textStateName, textStateEmail))
                        }) {
                        Text(text = "SAVE")
                    }
                    Spacer(modifier = Modifier.padding(start = pad))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                        onClick = {
                            subscriberViewModel.clearAllOrDelete()
                        }) {
                        Text(text = "CLEAR ALL")
                    }
                }
                Spacer(modifier = Modifier.padding(top = pad))
                LazyColumn {
                    itemsIndexed(
                        listOf("", "")
                    ) { index, item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(Color.DarkGray, shape = RoundedCornerShape(5.dp))
                        ) {
                            Text(text = "$index: $item")
                        }
                        Spacer(modifier = Modifier.padding(bottom = 20.dp))
                    }
                }
            }
        }
    }
    private fun displaySubscribersList(){
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.i("Main", it.toString())
        })
    }
}
package pl.klenczi.roomdemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

            var textStateName by remember {
                mutableStateOf("")
            }
            var textStateEmail by remember {
                mutableStateOf("")
            }
            var textStateId by remember {
                mutableStateOf(0)
            }
            var textStateSave by remember {
                mutableStateOf(subscriberViewModel.saveOrUpdateButton.value)
            }
            var textStateClearall by remember {
                mutableStateOf(subscriberViewModel.clearAllButton.value)
            }

            var clickedSubscriber = Subscriber(textStateId, textStateName, textStateEmail)

            fun itemClicked(subscriber: Subscriber) {
                Toast.makeText(
                    this,
                    "Selected subscriber is ${subscriber.name}",
                    Toast.LENGTH_SHORT
                ).show()
                subscriberViewModel.isUpdateOrDelete = !subscriberViewModel.isUpdateOrDelete
                when (subscriberViewModel.isUpdateOrDelete) {
                    true -> {
                        textStateSave = "UPDATE"
                        textStateClearall = "DELETE"
                        textStateName = subscriber.name
                        textStateEmail = subscriber.email
                        textStateId = subscriber.id
                    }

                    else -> {
                        textStateSave = subscriberViewModel.saveOrUpdateButton.value
                        textStateClearall = subscriberViewModel.clearAllButton.value
                        textStateName = ""
                        textStateEmail = ""
                        textStateId = 0
                    }
                }
            }

            subscriberViewModel.message.observe(this, Observer {
                it.getContentIfNotHandled()?.let {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            })

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
                        modifier = Modifier.background(
                            Color.Transparent,
                            shape = RoundedCornerShape(5.dp)
                        ),
                        onClick = {
                            when (subscriberViewModel.isUpdateOrDelete) {
                                false -> {
                                    subscriberViewModel.insert(clickedSubscriber)
                                }

                                else -> {
                                    subscriberViewModel.update(clickedSubscriber)
                                }
                            }
                            subscriberViewModel.isUpdateOrDelete = false
                            textStateName = ""; textStateEmail = ""; textStateId = 0
                            Log.i("Main", "$clickedSubscriber")
                        }) {
                        Text(text = textStateSave ?: "SAVE")
                    }
                    Spacer(modifier = Modifier.padding(start = pad))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                        onClick = {
                            when (subscriberViewModel.isUpdateOrDelete) {
                                false -> {
                                    subscriberViewModel.clearAllOrDelete()
                                }

                                else -> {
                                    subscriberViewModel.delete(clickedSubscriber)
                                }
                            }
                            subscriberViewModel.isUpdateOrDelete = false
                            Log.i("Main", "$clickedSubscriber")
                        }) {
                        Text(text = textStateClearall ?: "CLEAR ALL")
                    }
                }
                Spacer(modifier = Modifier.padding(top = pad))
                val subscribersList by subscriberViewModel.subscribers.collectAsState(initial = emptyList())
                LazyColumn {
                    items(
                        subscribersList
                    ) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                                .clickable {
                                    clickedSubscriber = item; itemClicked(item); Log.i(
                                    "Main",
                                    "$clickedSubscriber"
                                )
                                }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = item.email,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(bottom = 20.dp))
                    }
                }
            }
        }
    }
}
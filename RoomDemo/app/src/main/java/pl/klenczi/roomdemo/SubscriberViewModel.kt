package pl.klenczi.roomdemo

import androidx.core.widget.ListViewCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.klenczi.roomdemo.db.Subscriber
import pl.klenczi.roomdemo.db.SubscriberRepository

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {
    val subscribers = repository.subscribers
    var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButton = MutableLiveData<String>()
    val clearAllButton = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButton.value = "SAVE"
        clearAllButton.value = "CLEAR ALL"
    }

    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0, name, email))
        inputName.value = ""
        inputEmail.value = ""
    }

    fun clearAllOrDelete() {
        clearAll()
    }

    fun insert(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(subscriber)
            withContext(Dispatchers.Main){
                statusMessage.value = Event("Subscriber Inserted Successfully")
            }
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(subscriber)
            withContext(Dispatchers.Main){
                statusMessage.value = Event("Subscriber Updated Successfully")
            }
        }
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(subscriber)
            withContext(Dispatchers.Main){
                statusMessage.value = Event("Subscriber Deleted Successfully")
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
            withContext(Dispatchers.Main){
                statusMessage.value = Event("All Subscribers Deleted Successfully")
            }
        }
    }
}
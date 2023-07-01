package pl.klenczi.roomdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.klenczi.roomdemo.db.Subscriber
import pl.klenczi.roomdemo.db.SubscriberRepository

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {
    val subscribers = repository.subscribers
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButton = MutableLiveData<String>()
    val clearAllButton = MutableLiveData<String>()

    init {
        saveOrUpdateButton.value = "Save"
        clearAllButton.value = "ClearAll"
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
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(subscriber)
        }
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(subscriber)
        }
    }

    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}
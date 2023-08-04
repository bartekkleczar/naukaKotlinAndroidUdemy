package pl.klenczi.livedatabuilder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {
    private var userRepository = UserRepository()
    var users = liveData(Dispatchers.IO){
        val result = userRepository.getUsers()
        emit(result)
    }
}
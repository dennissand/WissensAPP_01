package com.example.wissensapp_01

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wissensapp_01.data.FirebaseService
import com.example.wissensapp_01.data.model.Box
import com.example.wissensapp_01.data.model.Card
import kotlinx.coroutines.launch

const val TAG = "MainViewModel"

enum class FirestoreStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _boxes = MutableLiveData<List<Box>>()
    val boxes: LiveData<List<Box>> = _boxes
    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    init {
        viewModelScope.launch {
            _boxes.value = FirebaseService.getBoxData()
            // _cards.value = FirebaseService.getPosts()
        }
    }

    fun deleteBox(box: Box) {
        viewModelScope.launch {
            FirebaseService.deleteBox(box)
            _boxes.value = FirebaseService.getBoxData()
        }
    }
}

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
    private val _boxcards = MutableLiveData<List<Card>>()
    val boxcards: LiveData<List<Card>> = _boxcards

    init {
        viewModelScope.launch {
            _boxes.value = FirebaseService.getBoxData()
            // _cards.value = FirebaseServiceBox.getPosts()
        }
    }

    init {
        viewModelScope.launch {
            _cards.value = FirebaseService.getCardData()
        }
    }

    fun deleteBox(box: Box) {
        viewModelScope.launch {
            _boxes.value = FirebaseService.deleteBox(box)
        }
    }

    fun deleteCard(card: Card) {
        viewModelScope.launch {
            _cards.value = FirebaseService.deleteCard(card)
        }
    }

    fun saveCard(a: String, b: String) {
        viewModelScope.launch {
            _cards.value = FirebaseService.saveCard(a, b)
        }
    }

    fun saveBox(boxName: String, boxContent: String) {
        viewModelScope.launch {
            _boxes.value = FirebaseService.saveBox(boxName, boxContent)
        }
    }

    fun getBoxCards(id: String) {
        _boxcards.value = _cards.value?.filter { it.boxId == id }
    }
}

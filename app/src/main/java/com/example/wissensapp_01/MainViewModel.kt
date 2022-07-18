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
    private val _learncards = MutableLiveData<List<Card>>()
    val learncards: LiveData<List<Card>> = _learncards
    private val _boxloading = MutableLiveData<FirestoreStatus>()
    val boxloading: LiveData<FirestoreStatus> = _boxloading
    private val _cardloading = MutableLiveData<FirestoreStatus>()
    val cardloading: LiveData<FirestoreStatus> = _cardloading

    init {
        viewModelScope.launch {
            _boxloading.value = FirestoreStatus.LOADING
            _boxes.value = FirebaseService.getBoxData()
            _boxloading.value = FirestoreStatus.DONE

            _cardloading.value = FirestoreStatus.LOADING
            _cards.value = FirebaseService.getCardData()
            _cardloading.value = FirestoreStatus.DONE
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

    fun saveCard(a: String, b: String, boxID: String) {
        viewModelScope.launch {
            _cards.value = FirebaseService.saveCard(a, b, boxID)
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

    fun cardLearned(cardLearned: Boolean) {
        _learncards.value = _cards.value?.filter { it.cardLearned == cardLearned }
    }

    fun getLearnCards(id: String) {
        _learncards.value = _cards.value?.filter { it.boxId == id }
    }

    fun getCardById(id: String): Card? {
        return _cards.value?.find { it.cardId == id }
    }

    fun getBoxByID(id: String): Box? {
        return _boxes.value?.find { it.boxId == id }
    }

    fun updateCard(card: Card) {
        viewModelScope.launch {
            _cards.value = FirebaseService.updateCard(card)
        }
    }

    fun updateBox(box: Box) {
        viewModelScope.launch {
            _boxes.value = FirebaseService.updateBox(box)
        }
    }
}

package com.example.wissensapp_01

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wissensapp_01.data.FirebaseService
import com.example.wissensapp_01.data.model.Box
import com.example.wissensapp_01.data.model.Card
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

const val TAG = "MainViewModel"

enum class FirestoreStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _boxes = MutableLiveData<List<Box>>()
    val boxes: LiveData<List<Box>> = _boxes
    val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards
    private val _boxcards = MutableLiveData<List<Card>>()
    val boxcards: LiveData<List<Card>> = _boxcards
    private val _learncards = MutableLiveData<List<Card>>()
    val learncards: LiveData<List<Card>> = _learncards
    private val _boxloading = MutableLiveData<FirestoreStatus>()
    val boxloading: LiveData<FirestoreStatus> = _boxloading
    private val _cardloading = MutableLiveData<FirestoreStatus>()
    val cardloading: LiveData<FirestoreStatus> = _cardloading

    private var uid = firebaseAuth.uid

    fun startDownload() {
        viewModelScope.launch {
            _boxloading.value = FirestoreStatus.LOADING
            _boxes.value = uid?.let { FirebaseService.getBoxData(it) }
            _boxloading.value = FirestoreStatus.DONE

            _cardloading.value = FirestoreStatus.LOADING
            _cards.value = uid?.let { FirebaseService.getCardData(it) }
            _cardloading.value = FirestoreStatus.DONE
        }
    }

    fun deleteBox(box: Box, context: Context) {
        viewModelScope.launch {
            _boxes.value = FirebaseService.deleteBox(box, context)
        }
    }

    fun deleteCard(card: Card, context: Context) {
        viewModelScope.launch {
            _cards.value = FirebaseService.deleteCard(card, context)
        }
    }

    fun saveCard(
        a: String,
        b: String,
        boxID: String,
        cardLearned: Boolean,
        context: Context
    ) {
        viewModelScope.launch {
            _cards.value = uid?.let {
                FirebaseService.saveCard(
                    a,
                    b,
                    boxID,
                    cardLearned,
                    context,
                    it
                )
            }
        }
    }

    fun saveBox(boxName: String, boxContent: String, context: Context) {
        viewModelScope.launch {
            _boxes.value = uid?.let { FirebaseService.saveBox(boxName, boxContent, context, it) }
        }
    }

    fun getBoxCards(id: String) {
        _boxcards.value = _cards.value?.filter { it.boxId == id }
    }

    fun cardtoggeld(card: Card, cardLearned: Boolean, context: Context) {
        card.cardLearned = cardLearned
        updateCard(card, context)
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

    fun updateCard(card: Card, context: Context) {
        viewModelScope.launch {
            _cards.value = FirebaseService.updateCard(card, context)
        }
    }

    fun updateBox(box: Box, context: Context) {
        viewModelScope.launch {
            _boxes.value = FirebaseService.updateBox(box, context)
        }
    }
}

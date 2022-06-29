package com.example.wissensapp_01.data.model

import java.util.*

data class Card(
    var cardId: String = "",
    var a: String = "",
    var b: String = "",
    var boxColor: String = "",
    var boxContent: String = "",
    var boxName: String = "",
    var cardLearned: Boolean = false
)

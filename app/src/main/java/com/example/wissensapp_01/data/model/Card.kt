package com.example.wissensapp_01.data.model

import java.util.*

data class Card(
    var cardId: String = "",
    var a: String = "",
    var b: String = "",
    var boxName: String = "",
    var boxContent: String = "",
    var boxColor: String = "",
    var cardLearned: Boolean = false
)

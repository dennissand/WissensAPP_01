package com.dennissand.wissensapp_01.data.model

data class Card(
    var cardId: String = "",
    var a: String = "",
    var b: String = "",
    var boxId: String = "",
    var cardLearned: Boolean = true,
    var uid: String = ""
)

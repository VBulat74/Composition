package ru.com.bulat.composition.data.entity

data class GameSettings (
    val maxSumValue : Int,
    val minCountRightAnswers : Int,
    val minPercentRightAnswers : Int,
    val gameTimeInSeconds : Int,
)
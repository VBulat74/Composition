package ru.com.bulat.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountRightAnswers: Int,
    val minPercentRightAnswers: Int,
    val gameTimeInSeconds: Int,
) : Parcelable {
    val minCountRightAnswersString : String
        get() = minCountRightAnswers.toString()

    val minPercentRightAnswersString : String
        get() = minPercentRightAnswers.toString()
}
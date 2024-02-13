package ru.com.bulat.composition.data.repository

import ru.com.bulat.composition.data.entity.GameSettings
import ru.com.bulat.composition.data.entity.Level
import ru.com.bulat.composition.data.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue : Int,
        countOgOptions : Int,
    ) : Question

    fun getGameSettings(level: Level) : GameSettings
}
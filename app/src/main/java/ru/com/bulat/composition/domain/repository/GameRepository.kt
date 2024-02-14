package ru.com.bulat.composition.domain.repository

import ru.com.bulat.composition.domain.entity.GameSettings
import ru.com.bulat.composition.domain.entity.Level
import ru.com.bulat.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue : Int,
        countOgOptions : Int,
    ) : Question

    fun getGameSettings(level: Level) : GameSettings
}
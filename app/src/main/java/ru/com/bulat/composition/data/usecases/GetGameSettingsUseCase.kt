package ru.com.bulat.composition.data.usecases

import ru.com.bulat.composition.data.entity.GameSettings
import ru.com.bulat.composition.data.entity.Level
import ru.com.bulat.composition.data.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level) : GameSettings {
        return repository.getGameSettings(level)
    }
}
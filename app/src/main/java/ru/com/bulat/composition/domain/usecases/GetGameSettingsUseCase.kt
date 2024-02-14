package ru.com.bulat.composition.domain.usecases

import ru.com.bulat.composition.domain.entity.GameSettings
import ru.com.bulat.composition.domain.entity.Level
import ru.com.bulat.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level) : GameSettings {
        return repository.getGameSettings(level)
    }
}
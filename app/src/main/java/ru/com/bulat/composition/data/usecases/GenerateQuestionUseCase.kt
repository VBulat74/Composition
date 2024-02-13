package ru.com.bulat.composition.data.usecases

import ru.com.bulat.composition.data.entity.Question
import ru.com.bulat.composition.data.repository.GameRepository

class GenerateQuestionUseCase (
    private val repository: GameRepository,
) {

    operator fun invoke(maxSumValue : Int) : Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}
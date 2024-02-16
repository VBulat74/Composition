package ru.com.bulat.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.com.bulat.composition.R
import ru.com.bulat.composition.data.GameRepositoryImpl
import ru.com.bulat.composition.domain.entity.GameResult
import ru.com.bulat.composition.domain.entity.GameSettings
import ru.com.bulat.composition.domain.entity.Level
import ru.com.bulat.composition.domain.entity.Question
import ru.com.bulat.composition.domain.usecases.GenerateQuestionUseCase
import ru.com.bulat.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level,
) : ViewModel() {

    private lateinit var gameSettings: GameSettings

    private val repository = GameRepositoryImpl

    val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var timer: CountDownTimer? = null

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    init {
        startGame()
    }

    private fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent

        _progressAnswers.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountRightAnswers
        )

        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions == 0) return 0
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (rightAnswer == number) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun getGameSettings() {
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            (gameSettings.gameTimeInSeconds * MILLIS_IN_SECOND),
            MILLIS_IN_SECOND,
        ) {
            override fun onTick(mSec: Long) {
                _formattedTime.value = formatTime(mSec)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun formatTime(mSec: Long): String {
        val seconds = mSec / MILLIS_IN_SECOND
        val minutes = seconds / SECONDS_IN_MINUTE
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTE)

        return String.format("%02d%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            winner = enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestions = countOfQuestions,
            gameSettings = gameSettings,
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECOND = 1000L
        private const val SECONDS_IN_MINUTE = 60
    }
}
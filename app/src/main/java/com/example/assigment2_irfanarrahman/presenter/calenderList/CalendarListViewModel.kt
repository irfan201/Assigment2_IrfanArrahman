package com.example.assigment2_irfanarrahman.presenter.calenderList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assigment2_irfanarrahman.data.model.DiaryEntities
import com.example.assigment2_irfanarrahman.domain.model.DiaryState
import com.example.assigment2_irfanarrahman.domain.usecase.DiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarListViewModel @Inject constructor(private val diaryUseCase: DiaryUseCase) :
    ViewModel() {
    private val _diaryState = MutableStateFlow<DiaryState>(DiaryState.Loading)
    val diaryState: StateFlow<DiaryState> get() = _diaryState


    fun getDiaryDate(date: String) {
        viewModelScope.launch {
            try {
                val diary = diaryUseCase.getDiaryDate(date)
                _diaryState.value = DiaryState.Success(diary)
            } catch (e: Exception) {
                _diaryState.value = DiaryState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteDiary(diaryEntities: DiaryEntities,date: String) {
        viewModelScope.launch {
            try {
                diaryUseCase.deleteDiary(diaryEntities)
                _diaryState.value = DiaryState.Success(diaryUseCase.getDiaryDate(date))
            } catch (e: Exception) {
                _diaryState.value = DiaryState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
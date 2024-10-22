package com.example.assigment2_irfanarrahman.presenter.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assigment2_irfanarrahman.data.model.DiaryEntities
import com.example.assigment2_irfanarrahman.domain.model.DiaryStateDetail
import com.example.assigment2_irfanarrahman.domain.usecase.DiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateDiaryViewModel @Inject constructor(private val diaryUseCase: DiaryUseCase) :
    ViewModel() {
    private val _diaryState = MutableStateFlow<DiaryStateDetail>(DiaryStateDetail.Loading)
    val diaryState: StateFlow<DiaryStateDetail> get() = _diaryState

    private val _diaryData = MutableStateFlow<DiaryEntities?>(null)
    val diaryData: StateFlow<DiaryEntities?> get() = _diaryData


    fun updateDiary(diaryEntities: DiaryEntities) {
        viewModelScope.launch {
            try {
                diaryUseCase.updateDiary(diaryEntities)
                _diaryState.value =
                    DiaryStateDetail.Success(diaryUseCase.getDiaryDetail(diaryEntities.id))
            } catch (e: Exception) {
                _diaryState.value = DiaryStateDetail.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun insertDiary(diaryEntities: DiaryEntities) {
        viewModelScope.launch {
            try {
                diaryUseCase.insertDiary(diaryEntities)
                _diaryState.value = DiaryStateDetail.Success(diaryEntities)
            } catch (e: Exception) {
                _diaryState.value = DiaryStateDetail.Error(e.message ?: "Unknown error")

            }
        }
    }

    suspend fun getDiaryDetail(diaryId: Int) {
        viewModelScope.launch {
            _diaryData.value = diaryUseCase.getDiaryDetail(diaryId)
        }
    }
}
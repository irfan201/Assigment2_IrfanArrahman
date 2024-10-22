package com.example.assigment2_irfanarrahman.presenter.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assigment2_irfanarrahman.domain.model.DiaryStateDetail
import com.example.assigment2_irfanarrahman.domain.usecase.DiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val diaryUseCase: DiaryUseCase) : ViewModel() {

    private val _diaryState = MutableStateFlow<DiaryStateDetail>(DiaryStateDetail.Loading)
    val diaryState: StateFlow<DiaryStateDetail> get() = _diaryState

    fun getDiaryDetail(diaryId: Int) {
        viewModelScope.launch {
            try {
                _diaryState.value = DiaryStateDetail.Success(diaryUseCase.getDiaryDetail(diaryId))
            } catch (e: Exception) {
                _diaryState.value = DiaryStateDetail.Error(e.message ?: "unknown")
            }
        }
    }
}
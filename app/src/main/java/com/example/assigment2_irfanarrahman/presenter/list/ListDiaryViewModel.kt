package com.example.assigment2_irfanarrahman.presenter.list

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
class ListDiaryViewModel @Inject constructor(private val diaryUseCase: DiaryUseCase) :ViewModel() {
    private val _diaryState = MutableStateFlow<DiaryState>(DiaryState.Loading)
    val diaryState: StateFlow<DiaryState> get() = _diaryState


    fun getDiary(){
        viewModelScope.launch {
            try {
                val diary = diaryUseCase.getDiary()
                _diaryState.value = DiaryState.Success(diary)
            } catch (e:Exception){
                _diaryState.value = DiaryState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteDiary(diaryEntities: DiaryEntities){
        viewModelScope.launch {
            try {
                diaryUseCase.deleteDiary(diaryEntities)
                _diaryState.value = DiaryState.Success(diaryUseCase.getDiary())
            } catch (e:Exception){
                _diaryState.value = DiaryState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getDiaryTittle(tittle: String){
        viewModelScope.launch {
            try {
                val diary = diaryUseCase.getDiaryTittle(tittle)
                _diaryState.value = DiaryState.Success(diary)
            } catch (e:Exception){
                _diaryState.value = DiaryState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
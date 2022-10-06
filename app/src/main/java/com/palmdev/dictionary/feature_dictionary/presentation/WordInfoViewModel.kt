package com.palmdev.dictionary.feature_dictionary.presentation

import androidx.lifecycle.ViewModel
import com.palmdev.dictionary.feature_dictionary.domain.use_case.GetWordInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfoUseCase: GetWordInfoUseCase
): ViewModel(){

}
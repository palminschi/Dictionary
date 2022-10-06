package com.palmdev.dictionary.feature_dictionary.domain.repository

import com.palmdev.dictionary.core.util.Resource
import com.palmdev.dictionary.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}
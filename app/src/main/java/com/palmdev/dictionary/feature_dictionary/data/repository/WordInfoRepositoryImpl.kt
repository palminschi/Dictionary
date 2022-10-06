package com.palmdev.dictionary.feature_dictionary.data.repository

import com.palmdev.dictionary.core.util.Resource
import com.palmdev.dictionary.feature_dictionary.data.local.WordInfoDao
import com.palmdev.dictionary.feature_dictionary.data.remote.DictionaryApi
import com.palmdev.dictionary.feature_dictionary.domain.model.WordInfo
import com.palmdev.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val dictionaryApi: DictionaryApi,
    private val wordInfoDao: WordInfoDao
): WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfos = wordInfoDao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordInfos))

        try {
            val remoteWordInfos = dictionaryApi.getWordInfo(word)
            wordInfoDao.deleteWordsInfos(remoteWordInfos.map { it.word })
            wordInfoDao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Oops, something went wrong!",
                data = wordInfos
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = wordInfos
            ))
        }

        val newWordInfos = wordInfoDao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))
    }
}
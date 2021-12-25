package id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.remote

import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.remote.network.ApiResponse
import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.remote.network.ApiService
import id.go.jakarta.dicoding_made_moviecatalogue.core.data.source.remote.response.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(private val apiService: ApiService) {
    suspend fun getMovieNowPlaying(): Flow<ApiResponse<List<ResultsItem>>> {
        return flow {
            try {
                val response = apiService.getMovieNowPlaying()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                }
                else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}
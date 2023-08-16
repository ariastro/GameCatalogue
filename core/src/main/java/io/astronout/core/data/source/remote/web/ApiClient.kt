package io.astronout.core.data.source.remote.web

import com.skydoves.sandwich.ApiResponse
import io.astronout.core.data.source.remote.model.GamesResponse
import javax.inject.Inject

class ApiClient @Inject constructor(private val api: ApiService) : ApiService {

    override suspend fun getGames(page: Int, pageSize: Int, key: String): GamesResponse {
        return api.getGames(page, pageSize, key)
    }

    override suspend fun searchGames(query: String, key: String): ApiResponse<GamesResponse> {
        return api.searchGames(query, key)
    }

}
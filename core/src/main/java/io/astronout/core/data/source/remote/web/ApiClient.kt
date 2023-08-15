package io.astronout.core.data.source.remote.web

import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class ApiClient @Inject constructor(private val api: ApiService) : ApiService {

    override suspend fun getGames(page: Int, pageSize: Int): ApiResponse<Unit> {
        return api.getGames(page, pageSize)
    }

}
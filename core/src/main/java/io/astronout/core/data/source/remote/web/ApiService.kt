package io.astronout.core.data.source.remote.web

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*
import java.util.*

interface ApiService {

    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<Unit>

}
package io.astronout.core.data.source.remote.web

import com.skydoves.sandwich.ApiResponse
import io.astronout.core.BuildConfig
import io.astronout.core.data.source.remote.model.GamesResponse
import retrofit2.http.*
import java.util.*

interface ApiService {

    @GET("games")
    suspend fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("key") key: String = BuildConfig.API_KEY
    ): ApiResponse<GamesResponse>

    @GET("games")
    suspend fun searchGames(
        @Query("search") query: String,
        @Query("key") key: String = BuildConfig.API_KEY
    ): ApiResponse<GamesResponse>

}